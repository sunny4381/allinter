package allinter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kklisura.cdt.protocol.commands.*;
import com.github.kklisura.cdt.protocol.commands.Runtime;
import com.github.kklisura.cdt.services.ChromeDevToolsService;
import com.github.kklisura.cdt.services.ChromeService;
import com.github.kklisura.cdt.services.types.ChromeTab;

import java.util.Optional;

import static allinter.cdt.Util.navigateAndWait;

public class ApplicationTab {
    private static final String URL = System.getProperty("allinter.url", "http://sunny4381.github.io/allinter/index.html");

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
            final var navigate = navigateAndWait(this.devToolsService, this.page, URL, 10000);
            if (navigate.getErrorText() != null && ! navigate.getErrorText().isEmpty()) {
                throw new RuntimeException(navigate.getErrorText());
            }

            if (url.isPresent()) {
                openUrl(url.get());
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
            onHostCallback(new ObjectMapper().readTree(payload));
        } catch (JsonProcessingException ex) {
            throw new UnsupportedOperationException("malformed command", ex);
        }
    }

    private void onHostCallback(final JsonNode command) {
        final var name = command.get("name").asText();
        if (name == null || name.isEmpty()) {
            return;
        }

        if (name.equals("quit")) {
            stop();
            return;
        }

        if (name.equals("open")) {
            openUrl(command.get("payload").get("url").asText());
        }
    }

    private void openUrl(final String url) {
        final var tab = this.chromeService.createTab();
        final var devTools = this.chromeService.createDevToolsService(tab);
        var browserTab = new BrowserTab(this.chromeService, tab, devTools);
        browserTab.navigate(url);

        var linter = new Linter(browserTab, url, this.app.getHtmlCheckerOptions(), this.app.getLowVisionOptions());
        linter.run();
    }
}
