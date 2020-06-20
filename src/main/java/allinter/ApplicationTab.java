package allinter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kklisura.cdt.protocol.commands.*;
import com.github.kklisura.cdt.protocol.commands.Runtime;
import com.github.kklisura.cdt.protocol.types.dom.Node;
import com.github.kklisura.cdt.protocol.types.page.Navigate;
import com.github.kklisura.cdt.protocol.types.runtime.CallArgument;
import com.github.kklisura.cdt.protocol.types.runtime.RemoteObject;
import com.github.kklisura.cdt.services.ChromeDevToolsService;
import com.github.kklisura.cdt.services.ChromeService;
import com.github.kklisura.cdt.services.types.ChromeTab;
import org.eclipse.actf.visualization.engines.lowvision.LowVisionException;
import org.eclipse.actf.visualization.engines.lowvision.image.ImageException;
import org.eclipse.actf.visualization.eval.problem.IProblemItem;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static allinter.cdt.Util.callFunctionOn;
import static allinter.cdt.Util.navigateAndWait;

public class ApplicationTab {
    private static final String URL = System.getProperty("allinter.url", "http://sunny4381.github.io/allinter/index.html");
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final App app;

    private final ChromeService chromeService;
    private final ChromeTab tab;
    private final ChromeDevToolsService devToolsService;

    private final Runtime runtime;
    private final Page page;
    private final Network network;
    private final DOM dom;
    private final CSS css;

    private final Object applicationWait = new Object();
    private volatile boolean applicationDone = false;

    public ApplicationTab(final App app, final ChromeService chromeService, final ChromeTab tab, final ChromeDevToolsService devToolsService) {
        this.app = app;
        this.chromeService = chromeService;
        this.tab = tab;
        this.devToolsService = devToolsService;

        this.runtime = this.devToolsService.getRuntime();
        this.runtime.enable();

        this.page = this.devToolsService.getPage();
        this.page.enable();
        this.page.setLifecycleEventsEnabled(true);

        this.network = this.devToolsService.getNetwork();
        this.network.enable();

        this.dom = this.devToolsService.getDOM();
        this.dom.enable();

        this.css = this.devToolsService.getCSS();
        this.css.enable();

        this.runtime.onExecutionContextCreated(event -> {
//            currentContext = event.getContext();
        });

        this.runtime.addBinding("hostCallback");
        this.runtime.onBindingCalled(event -> {
            if (event.getName().equals("hostCallback")) {
                onHostCallback(event.getPayload());
            }
        });
    }

    public void start(final Optional<String> url) {
        try {
            final Navigate navigate = navigateAndWait(this.devToolsService, this.page, URL, 10000);
            if (navigate.getErrorText() != null && ! navigate.getErrorText().isEmpty()) {
                throw new RuntimeException(navigate.getErrorText());
            }

            if (url.isPresent()) {
                try {
                    openUrl(url.get());
                } catch (Exception ex) {
                    throw new RuntimeException("unable to open '" + url + "' and validate it", ex);
                }
            }

            while (!this.applicationDone) {
                synchronized (this.applicationWait) {
                    if (!this.applicationDone) {
                        this.applicationWait.wait();
                    }
                }
            }
        } catch (InterruptedException ex) {
        }
    }

    public void stop() {
        synchronized (this.applicationWait) {
            if (! this.applicationDone) {
                this.applicationDone = true;
                this.applicationWait.notifyAll();
            }
        }
    }

    private void onHostCallback(final String payload) {
        try {
            onHostCallback(OBJECT_MAPPER.readTree(payload));
        } catch (JsonProcessingException ex) {
            throw new UnsupportedOperationException("malformed command", ex);
        } catch (Exception ex) {
            throw new RuntimeException("something happened", ex);
        }
    }

    private void onHostCallback(final JsonNode command) throws Exception {
        final String name = command.get("name").asText();
        if (name == null || name.isEmpty()) {
            return;
        }

        if (name.equals("quit")) {
            stop();
            return;
        }

        if (name.equals("open")) {
            openUrl(command.get("payload").get("url").asText());
            return;
        }

        if (name.equals("highlight")) {
            final JsonNode payload = command.get("payload");
            highlightElementOnBrowser(payload.get("tabId").asText(), payload.get("cssPath").asText());
        }
    }

    abstract class BaseResponse {
        private final String name;
        private final String url;
        private final String tabId;

        public BaseResponse(final String name, final String url, final String tabId) {
            this.name = name;
            this.url = url;
            this.tabId = tabId;
        }

        public String getName() {
            return this.name;
        }

        public String getUrl() {
            return this.url;
        }

        public String getTabId() {
            return this.tabId;
        }

        public void send() {
            // convert nodeId to removeObjectId
            final Node document = ApplicationTab.this.dom.getDocument();
            final RemoteObject remoteObject = ApplicationTab.this.dom.resolveNode(document.getNodeId(), null, null, null);

            // make arguments
            final JsonNode json = OBJECT_MAPPER.valueToTree(this);
            final CallArgument argument = new CallArgument();
            argument.setValue(json);

            callFunctionOn(
                    ApplicationTab.this.devToolsService,
                    ApplicationTab.this.runtime,
                    remoteObject.getObjectId(),
                    "function(arg) { return window.postMessage(arg); }",
                    Collections.singletonList(argument)
            );
        }
    }

    class ValidatingResponse extends BaseResponse {
        public static final String NAME = "allinter.validating";

        public ValidatingResponse(final String url, final String tabId) {
            super(NAME, url, tabId);
        }
    }

    class CompletedResponse extends BaseResponse {
        public static final String NAME = "allinter.completed";

        public CompletedResponse(final String url, final String tabId) {
            super(NAME, url, tabId);
        }
    }

    class HtmlCheckerStartingResponse extends BaseResponse {
        public static final String NAME = "allinter.htmlChecker.starting";

        public HtmlCheckerStartingResponse(final String url, final String tabId) {
            super(NAME, url, tabId);
        }
    }

    class HtmlCheckerDisabledResponse extends BaseResponse {
        public static final String NAME = "allinter.htmlChecker.disabled";

        public HtmlCheckerDisabledResponse(final String url, final String tabId) {
            super(NAME, url, tabId);
        }
    }

    class HtmlCheckerErrorResponse extends BaseResponse {
        public static final String NAME = "allinter.htmlChecker.error";
        private final Exception exception;

        public HtmlCheckerErrorResponse(final String url, final String tabId, final Exception exception) {
            super(NAME, url, tabId);
            this.exception = exception;
        }

        public String getErrorMessage() {
            return this.exception.getMessage();
        }
    }

    class HtmlCheckerResultResponse extends BaseResponse {
        public static final String NAME = "allinter.htmlChecker.result";
        private final List<IProblemItem> problems;

        public HtmlCheckerResultResponse(final String url, final String tabId, final List<IProblemItem> problems) {
            super(NAME, url, tabId);
            this.problems = problems;
        }

        public List<IProblemItem> getProblems() {
            return this.problems;
        }
    }

    class LowVisionStartingResponse extends BaseResponse {
        public static final String NAME = "allinter.lowVision.starting";

        public LowVisionStartingResponse(final String url, final String tabId) {
            super(NAME, url, tabId);
        }
    }

    class LowVisionDisabledResponse extends BaseResponse {
        public static final String NAME = "allinter.lowVision.disabled";

        public LowVisionDisabledResponse(final String url, final String tabId) {
            super(NAME, url, tabId);
        }
    }

    class LowVisionErrorResponse extends BaseResponse {
        public static final String NAME = "allinter.lowVision.error";
        private final Exception exception;

        public LowVisionErrorResponse(final String url, final String tabId, final Exception exception) {
            super(NAME, url, tabId);
            this.exception = exception;
        }

        public String getErrorMessage() {
            return this.exception.getMessage();
        }
    }

    class LowVisionResultResponse extends BaseResponse {
        public static final String NAME = "allinter.lowVision.result";
        private final List<IProblemItem> problems;
        private final BufferedImage sourceImage;
        private final BufferedImage outputImage;

        public LowVisionResultResponse(final String url, final String tabId, final List<IProblemItem> problems, final BufferedImage sourceImage, final BufferedImage outputImage) {
            super(NAME, url, tabId);
            this.problems = problems;
            this.sourceImage = sourceImage;
            this.outputImage = outputImage;
        }

        public List<IProblemItem> getProblems() {
            return this.problems;
        }

        public String getSourceImageDataUrl() throws IOException {
            if (this.sourceImage == null) {
                return null;
            }

            return "data:image/jpeg;base64," + base64Image(this.sourceImage);
        }

        public String getOutputImageDataUrl() throws IOException {
            if (this.outputImage == null) {
                return null;
            }

            return "data:image/jpeg;base64," + base64Image(this.outputImage);
        }

        public String base64Image(final BufferedImage image) throws IOException {
            if (image == null) {
                return null;
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpeg", baos);
            baos.close();

            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(baos.toByteArray());
        }
    }

    private void openUrl(final String url) {
        final ChromeTab tab = this.chromeService.createTab();
        final ChromeDevToolsService devTools = this.chromeService.createDevToolsService(tab);
        final BrowserTab browserTab = new BrowserTab(this.chromeService, tab, devTools);
        browserTab.navigate(url);

        new ValidatingResponse(url, tab.getId()).send();

        if (this.app.getHtmlCheckerOptions().isHtmlChecker()) {
            new HtmlCheckerStartingResponse(url, tab.getId()).send();

            try {
                final allinter.htmlchecker.Checker result = allinter.htmlchecker.Checker.validate(
                        browserTab, url, this.app.getHtmlCheckerOptions());
                new HtmlCheckerResultResponse(url, tab.getId(), result.getProblemList()).send();
            } catch (Exception ex) {
                new HtmlCheckerErrorResponse(url, tab.getId(), ex).send();
            }
        } else {
            new HtmlCheckerDisabledResponse(url, tab.getId()).send();
        }

        if (this.app.getLowVisionOptions().isLowvision()) {
            new LowVisionStartingResponse(url, tab.getId()).send();

            try {
                final allinter.lowvision.Checker result = allinter.lowvision.Checker.validate(
                        browserTab, url, this.app.getLowVisionOptions());
                new LowVisionResultResponse(
                        url, tab.getId(), result.getProblemList(), result.getSourceImage(), result.getLowvisionImage()).send();
            } catch (LowVisionException | IOException | ImageException ex) {
                new LowVisionErrorResponse(url, tab.getId(), ex).send();
            }
        } else {
            new LowVisionDisabledResponse(url, tab.getId()).send();
        }

        new CompletedResponse(url, tab.getId()).send();

        this.chromeService.activateTab(this.tab);
    }

    private void highlightElementOnBrowser(final String tabId, final String cssPath) {
        if (tabId == null || tabId.isEmpty()) {
            return;
        }
        if (cssPath == null || cssPath.isEmpty()) {
            return;
        }

        Optional<ChromeTab> browserTab = this.chromeService.getTabs().stream().filter((tab) -> tab.getId().equals(tabId)).findFirst();
        if (! browserTab.isPresent()) {
            return;
        }

        this.chromeService.activateTab(browserTab.get());
    }
}
