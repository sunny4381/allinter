package allinter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kklisura.cdt.protocol.commands.*;
import com.github.kklisura.cdt.protocol.commands.Runtime;
import com.github.kklisura.cdt.protocol.types.dom.Node;
import com.github.kklisura.cdt.protocol.types.overlay.HighlightConfig;
import com.github.kklisura.cdt.protocol.types.page.Navigate;
import com.github.kklisura.cdt.protocol.types.runtime.CallArgument;
import com.github.kklisura.cdt.protocol.types.runtime.RemoteObject;
import com.github.kklisura.cdt.services.ChromeDevToolsService;
import com.github.kklisura.cdt.services.ChromeService;
import com.github.kklisura.cdt.services.types.ChromeTab;
import org.eclipse.actf.visualization.eval.problem.IProblemItem;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

import static allinter.cdt.Util.*;

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

        final JsonNode stateNode = command.get("state");
        final String state = stateNode != null ? stateNode.asText() : null;

        try {
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
                highlightElementOnBrowser(payload.get("tabId").asText(), payload.get("cssPath").asText(), state);
            }

            if (name.equals("syncSettings")) {
                syncSettings();
            }

            if (name.equals("setSettings")) {
                final JsonNode payload = command.get("payload");
                setSettings(payload);
            }
        } catch (Exception ex) {
            if (state != null && !state.isEmpty()) {
                new StateErrorMessage(state, ex).send();
            }

            throw ex;
        }
    }

    abstract class BaseMessage {
        private final String name;

        public BaseMessage(final String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
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

    abstract class BaseValidationResponse extends BaseMessage {
        private final String url;
        private final String tabId;

        public BaseValidationResponse(final String name, final String url, final String tabId) {
            super(name);
            this.url = url;
            this.tabId = tabId;
        }

        public String getUrl() {
            return this.url;
        }

        public String getTabId() {
            return this.tabId;
        }
    }

    abstract class BaseValidationErrorResponse extends BaseValidationResponse {
        private final Exception exception;

        public BaseValidationErrorResponse(final String name, final String url, final String tabId, final Exception exception) {
            super(name, url, tabId);
            this.exception = exception;
        }

        public String getErrorClass() {
            return this.exception.getClass().getCanonicalName();
        }

        public String getErrorMessage() {
            return this.exception.getMessage();
        }

        public String[] getErrorTraces() {
            final StackTraceElement[] elements = this.exception.getStackTrace();
            final String[] ret = new String[elements.length];

            for (int i = 0; i < elements.length; i++) {
                ret[i] = elements[i].toString();
            }

            return ret;
        }
    }

    class ValidatingValidationResponse extends BaseValidationResponse {
        public static final String NAME = "allinter.validating";

        public ValidatingValidationResponse(final String url, final String tabId) {
            super(NAME, url, tabId);
        }
    }

    class CompletedValidationResponse extends BaseValidationResponse {
        public static final String NAME = "allinter.completed";

        public CompletedValidationResponse(final String url, final String tabId) {
            super(NAME, url, tabId);
        }
    }

    class HtmlCheckerStartingValidationResponse extends BaseValidationResponse {
        public static final String NAME = "allinter.htmlChecker.starting";

        public HtmlCheckerStartingValidationResponse(final String url, final String tabId) {
            super(NAME, url, tabId);
        }
    }

    class HtmlCheckerDisabledValidationResponse extends BaseValidationResponse {
        public static final String NAME = "allinter.htmlChecker.disabled";

        public HtmlCheckerDisabledValidationResponse(final String url, final String tabId) {
            super(NAME, url, tabId);
        }
    }

    class HtmlCheckerErrorValidationResponse extends BaseValidationErrorResponse {
        public static final String NAME = "allinter.htmlChecker.error";

        public HtmlCheckerErrorValidationResponse(final String url, final String tabId, final Exception exception) {
            super(NAME, url, tabId, exception);
        }
    }

    class HtmlCheckerResultValidationResponse extends BaseValidationResponse {
        public static final String NAME = "allinter.htmlChecker.result";
        private final List<IProblemItem> problems;

        public HtmlCheckerResultValidationResponse(final String url, final String tabId, final List<IProblemItem> problems) {
            super(NAME, url, tabId);
            this.problems = problems;
        }

        public List<IProblemItem> getProblems() {
            return this.problems;
        }
    }

    class LowVisionStartingValidationResponse extends BaseValidationResponse {
        public static final String NAME = "allinter.lowVision.starting";

        public LowVisionStartingValidationResponse(final String url, final String tabId) {
            super(NAME, url, tabId);
        }
    }

    class LowVisionDisabledValidationResponse extends BaseValidationResponse {
        public static final String NAME = "allinter.lowVision.disabled";

        public LowVisionDisabledValidationResponse(final String url, final String tabId) {
            super(NAME, url, tabId);
        }
    }

    class LowVisionErrorValidationResponse extends BaseValidationErrorResponse {
        public static final String NAME = "allinter.lowVision.error";

        public LowVisionErrorValidationResponse(final String url, final String tabId, final Exception exception) {
            super(NAME, url, tabId, exception);
        }
    }

    class LowVisionResultValidationResponse extends BaseValidationResponse {
        public static final String NAME = "allinter.lowVision.result";
        private final List<IProblemItem> problems;
        private final BufferedImage sourceImage;
        private final BufferedImage outputImage;

        public LowVisionResultValidationResponse(final String url, final String tabId, final List<IProblemItem> problems, final BufferedImage sourceImage, final BufferedImage outputImage) {
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

    class SettingsMessage extends BaseMessage {
        public static final String NAME = "allinter.settings";

        private final LowVisionOptions lowVisionOptions;

        public SettingsMessage(final LowVisionOptions lowVisionOptions) {
            super(NAME);
            this.lowVisionOptions = lowVisionOptions;
        }

        public LowVisionOptions getLowVision() {
            return this.lowVisionOptions;
        }
    }

    class StateSuccessMessage extends BaseMessage {
        public static final String NAME = "allinter.stateSuccess";
        private final String state;

        public StateSuccessMessage(final String state) {
            super(NAME);
            this.state = state;
        }

        public String getState() {
            return this.state;
        }
    }

    class StateErrorMessage extends BaseMessage {
        public static final String NAME = "allinter.stateError";
        private final String state;
        private final String errorClass;
        private final String errorMessage;
        private final String[] errorTraces;

        public StateErrorMessage(final String state, final Exception exception) {
            super(NAME);
            this.state = state;
            this.errorClass = exception.getClass().getCanonicalName();
            this.errorMessage = exception.getMessage();
            final StackTraceElement[] elements = exception.getStackTrace();
            this.errorTraces = new String[elements.length];

            for (int i = 0; i < elements.length; i++) {
                this.errorTraces[i] = elements[i].toString();
            }
        }

        public StateErrorMessage(final String state, final String errorMessage) {
            super(NAME);
            this.state = state;
            this.errorClass = null;
            this.errorMessage = errorMessage;
            this.errorTraces = new String[0];
        }

        public String getState() {
            return this.state;
        }

        public String getErrorClass() {
            return this.errorClass;
        }

        public String getErrorMessage() {
            return this.errorMessage;
        }

        public String[] getErrorTraces() {
            return this.errorTraces;
        }
    }

    private void openUrl(final String url) {
        final ChromeTab tab = this.chromeService.createTab();
        final ChromeDevToolsService devTools = this.chromeService.createDevToolsService(tab);
        final BrowserTab browserTab = new BrowserTab(this.chromeService, tab, devTools);
        browserTab.navigate(url);

        new ValidatingValidationResponse(url, tab.getId()).send();

        if (this.app.getHtmlCheckerOptions().isHtmlChecker()) {
            new HtmlCheckerStartingValidationResponse(url, tab.getId()).send();

            try {
                final allinter.htmlchecker.Checker result = allinter.htmlchecker.Checker.validate(
                        browserTab, url, this.app.getHtmlCheckerOptions());
                new HtmlCheckerResultValidationResponse(url, tab.getId(), result.getProblemList()).send();
            } catch (Exception ex) {
                new HtmlCheckerErrorValidationResponse(url, tab.getId(), ex).send();
            }
        } else {
            new HtmlCheckerDisabledValidationResponse(url, tab.getId()).send();
        }

        if (this.app.getLowVisionOptions().isLowvision()) {
            new LowVisionStartingValidationResponse(url, tab.getId()).send();

            try {
                final allinter.lowvision.Checker result = allinter.lowvision.Checker.validate(
                        browserTab, url, this.app.getLowVisionOptions());
                new LowVisionResultValidationResponse(
                        url, tab.getId(), result.getProblemList(), result.getSourceImage(), result.getLowvisionImage()).send();
            } catch (Exception ex) {
                new LowVisionErrorValidationResponse(url, tab.getId(), ex).send();
            }
        } else {
            new LowVisionDisabledValidationResponse(url, tab.getId()).send();
        }

        new CompletedValidationResponse(url, tab.getId()).send();

        this.chromeService.activateTab(this.tab);
    }

    private void highlightElementOnBrowser(final String tabId, final String cssPath, final String state) {
        if (tabId == null || tabId.isEmpty()) {
            throw new IllegalArgumentException("tabId is required, but it's empty");
        }
        if (cssPath == null || cssPath.isEmpty()) {
            throw new IllegalArgumentException("cssPath is required, but it's empty");
        }

        Optional<ChromeTab> browserTab = this.chromeService.getTabs().stream().filter((tab) -> tab.getId().equals(tabId)).findFirst();
        if (! browserTab.isPresent()) {
            throw new UnsupportedOperationException("browser tab has been closed");
        }

        final ChromeDevToolsService devTools = this.chromeService.createDevToolsService(browserTab.get());
        final DOM dom = devTools.getDOM();
        dom.enable();
        final Runtime runtime = devTools.getRuntime();
        runtime.enable();

        final Integer nodeId = dom.querySelector(dom.getDocument().getNodeId(), cssPath);
        if (nodeId == null) {
            new StateErrorMessage(state, cssPath + ": element is not found").send();
            return;
        }

        final RemoteObject remoteObject = dom.resolveNode(nodeId, null, null, null);
        if (remoteObject == null) {
            new StateErrorMessage(state, cssPath + ": element is not found").send();
            return;
        }

        final List<List<Double>> quads = getContentQuadsSafely(devTools, dom, nodeId);
        if (quads == null || quads.isEmpty()) {
            new StateErrorMessage(state, cssPath + ": element is not visible").send();
            return;
        }

        this.chromeService.activateTab(browserTab.get());

        final Overlay overlay = devTools.getOverlay();
        overlay.enable();

        final HighlightConfig highlightConfig = new HighlightConfig();
        highlightConfig.setShowInfo(true);
        highlightConfig.setShowStyles(true);
        highlightConfig.setShowExtensionLines(true);
        // highlightConfig.setShowRulers(true);
        overlay.highlightNode(highlightConfig, nodeId, null, null, null);

        callFunctionOn(
            devTools, runtime, remoteObject.getObjectId(),
            "function() { this.scrollIntoView(); }", Collections.emptyList()
        );

        new StateSuccessMessage(state).send();
    }

    private void syncSettings() {
        new SettingsMessage(this.app.getLowVisionOptions()).send();
    }

    private void setSettings(final JsonNode settings) {
        final JsonNode lowVision = settings.get("lowVision");

        final LowVisionOptions options = this.app.getLowVisionOptions();
        options.setEyesight(lowVision.get("eyesight").asBoolean());
        options.setEyesightDegree((float) lowVision.get("eyesightDegree").asDouble());
        options.setCvd(lowVision.get("cvd").asBoolean());
        options.setCvdTypeByName(lowVision.get("cvdType").asText());
        options.setColorFilter(lowVision.get("colorFilter").asBoolean());
        options.setColorFilterDegree((float) lowVision.get("colorFilterDegree").asDouble());

        syncSettings();
    }
}
