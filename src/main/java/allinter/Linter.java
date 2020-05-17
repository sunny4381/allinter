package allinter;

import org.eclipse.actf.visualization.engines.lowvision.LowVisionException;
import org.eclipse.actf.visualization.engines.lowvision.LowVisionType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public class Linter implements Runnable {
    private final BrowserTab browser;
    private final String url;
    private final HtmlCheckerOptions htmlCheckerOptions;
    private final LowVisionOptions lowVisionOptions;

    public Linter(final BrowserTab browser, final String url, final HtmlCheckerOptions htmlCheckerOptions, final LowVisionOptions lowVisionOptions) {
        this.browser = browser;
        this.url = url;
        this.htmlCheckerOptions = htmlCheckerOptions;
        this.lowVisionOptions = lowVisionOptions;
    }

    public void run() {
        try {
            runHtmlChecker();
            runLowVisionLint();
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void runHtmlChecker() throws Exception {
        if (! this.htmlCheckerOptions.isHtmlChecker()) {
            return;
        }

        Path workDirectory = Files.createTempDirectory("htmlchecker");
        try {
            var checker = new allinter.htmlchecker.Checker(browser, this.url, workDirectory.toString());
            checker.run();
        } finally {
            Files.walk(workDirectory)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

    private void runLowVisionLint() throws IOException, LowVisionException {
        if (! this.lowVisionOptions.isLowvision()) {
            return;
        }

        var checker = new allinter.lowvision.Checker(browser, this.url, createLowVisionType());
        checker.run();
    }

    private LowVisionType createLowVisionType() throws LowVisionException {
        LowVisionType lowVisionType = new LowVisionType();

        if (this.lowVisionOptions.isLowvisionEyesight()) {
            lowVisionType.setEyesight(this.lowVisionOptions.isLowvisionEyesight());
            lowVisionType.setEyesightDegree(this.lowVisionOptions.getLowvisionEyesightDegree());
        }
        if (this.lowVisionOptions.isLowvisionCVD()) {
            lowVisionType.setCVD(this.lowVisionOptions.isLowvisionCVD());
            lowVisionType.setCVDType(this.lowVisionOptions.getLowvisionCVDType());
        }
        if (this.lowVisionOptions.isLowvisionColorFilter()) {
            lowVisionType.setColorFilter(this.lowVisionOptions.isLowvisionColorFilter());
            lowVisionType.setColorFilterDegree(this.lowVisionOptions.getLowvisionColorFilterDegree());
        }

        return lowVisionType;
    }
}
