package allinter;

import com.github.kklisura.cdt.launch.ChromeArguments;
import com.github.kklisura.cdt.launch.ChromeLauncher;
import com.github.kklisura.cdt.services.ChromeDevToolsService;
import com.github.kklisura.cdt.services.ChromeService;
import com.github.kklisura.cdt.services.types.ChromeTab;
import picocli.CommandLine;

import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.Callable;

public class App implements Callable<Integer> {
    @CommandLine.Parameters(index = "0", arity = "0..1", description = "The url to check")
    private String url = null;

    @CommandLine.Option(names = "--lang", description = "specifies lang. default is unspecified(use system default)")
    private Locale lang = Locale.getDefault();

    @CommandLine.Option(names = "--no-interactive", negatable = true, description = "specifies to execute google-chrome as headless or not")
    private boolean interactive = true;

    @CommandLine.Option(names = "--no-sandbox", negatable = true, description = "specifies to execute google-chrome within sandbox or not")
    private boolean sandbox = true;

    @CommandLine.Option(names = "--window-size", description = "specifies the browser window size. default is unspecified (auto)")
    private String windowSize = null;

    @CommandLine.ArgGroup(exclusive = false, heading = "\nhtml accessibility options\n")
    private HtmlCheckerOptions htmlCheckerOptions = new HtmlCheckerOptions();

    @CommandLine.ArgGroup(exclusive = false, heading = "\nlow vision options\n")
    private LowVisionOptions lowVisionOptions = new LowVisionOptions();

    public static void main(String[] args) {
        System.exit(execute(args));
    }

    public static int execute(String[] args) {
        int exitCode = new CommandLine(new App())
                .registerConverter(Locale.class, s -> new Locale.Builder().setLanguageTag(s).build())
                .execute(args);
        return exitCode;
    }

    public HtmlCheckerOptions getHtmlCheckerOptions() {
        return this.htmlCheckerOptions;
    }

    public LowVisionOptions getLowVisionOptions() {
        return this.lowVisionOptions;
    }

    @Override
    public Integer call() throws Exception {
        try (final ChromeLauncher launcher = new ChromeLauncher()) {
            final ChromeArguments.Builder argumentsBuilder = ChromeArguments.defaults(!this.interactive);
            argumentsBuilder.additionalArguments("disable-backgrounding-occluded-windows", true);
            argumentsBuilder.additionalArguments("disable-breakpad", true);
            argumentsBuilder.additionalArguments("disable-dev-shm-usage", true);
            argumentsBuilder.additionalArguments("disable-features", "site-per-process,TranslateUI");
            argumentsBuilder.additionalArguments("disable-ipc-flooding-protection", true);
            argumentsBuilder.additionalArguments("disable-renderer-backgrounding", true);
            argumentsBuilder.additionalArguments("disable-session-crashed-bubble", true);
            argumentsBuilder.additionalArguments("enable-features", "NetworkService,NetworkServiceInProcess");
            argumentsBuilder.additionalArguments("force-color-profile", "srgb");
            argumentsBuilder.additionalArguments("keep-alive-for-test", true);
            argumentsBuilder.additionalArguments("lang", this.lang.toLanguageTag());
            argumentsBuilder.additionalArguments("password-store", "basic");
            argumentsBuilder.additionalArguments("use-mock-keychain", true);
            if (!this.sandbox) {
                argumentsBuilder.additionalArguments("no-sandbox", true);
            }
            if (this.windowSize != null && !this.windowSize.isEmpty()) {
                argumentsBuilder.additionalArguments("window-size", this.windowSize);
            }
            final ChromeService chromeService = launcher.launch(argumentsBuilder.build());
            final ChromeTab tab = prepareTab(chromeService);
            final ChromeDevToolsService devToolsService = chromeService.createDevToolsService(tab);

            if (this.interactive) {
                final ApplicationTab applicationTab = new ApplicationTab(this, chromeService, tab, devToolsService);

                // Ctrl + C
                Runtime.getRuntime().addShutdownHook(new Thread(() -> applicationTab.stop()));

                // Ctrl + Q on Google Chrome
                startAliveMonitor(launcher, applicationTab);

                applicationTab.start(Optional.ofNullable(this.url));
            } else {
                if (this.url == null || this.url.isEmpty()) {
                    throw new IllegalArgumentException("url must be specified on no-interactive");
                }
                BrowserTab browserTab = new BrowserTab(chromeService, tab, devToolsService);
                browserTab.navigate(this.url);

                Linter linter = new Linter(browserTab, this.url, this.htmlCheckerOptions, this.lowVisionOptions);
                linter.run();
            }
        }

        return 0;
    }

    private static ChromeTab prepareTab(final ChromeService chromeService) {
        return chromeService.createTab();
//        final ChromeTab tab;
//
//        if (chromeService.getTabs().size() > 0) {
//            tab = chromeService.getTabs().get(0);
//            chromeService.createDevToolsService(tab);
//        } else {
//            tab = chromeService.createTab();
//        }
//
//        return tab;
    }

    private static void startAliveMonitor(final ChromeLauncher launcher, final ApplicationTab tab) {
        final Thread thread = new Thread(() -> {
            while (true) {
                if (!launcher.isAlive()) {
                    tab.stop();
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        thread.start();
    }
}
