package allinter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.actf.visualization.engines.lowvision.LowVisionException;
import org.eclipse.actf.visualization.engines.lowvision.LowVisionType;
import org.eclipse.actf.visualization.engines.lowvision.image.ImageException;
import org.eclipse.actf.visualization.eval.problem.IProblemItem;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

import static com.google.common.io.Files.getFileExtension;
import static java.nio.file.Files.createTempDirectory;
import static java.nio.file.Files.walk;

public class Linter implements Runnable {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

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

        Path workDirectory = createTempDirectory("htmlchecker");
        try {
            var checker = new allinter.htmlchecker.Checker(browser, this.url, workDirectory.toString());
            checker.run();

            outputResults(checker.getProblemList(), this.htmlCheckerOptions.getOutputReportFilepath());
        } finally {
            walk(workDirectory)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

    private void runLowVisionLint() throws IOException, LowVisionException, ImageException {
        if (! this.lowVisionOptions.isLowvision()) {
            return;
        }

        var checker = new allinter.lowvision.Checker(browser, this.url, createLowVisionType());
        checker.run();

        outputResults(checker.getProblemList(), this.lowVisionOptions.getOutputReportFilepath());
        outputImage(checker.getLowvisionImage(), this.lowVisionOptions.getOutputImageFilepath());
        outputImage(checker.getSourceImage(), this.lowVisionOptions.getSourceImageFilepath());
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

    private void outputResults(final List<IProblemItem> problemList, final Path outputFilepath) throws IOException {
        if (outputFilepath == null) {
            return;
        }

        ensureToExistDirectory(outputFilepath.getParent());

        try (PrintWriter writer = new PrintWriter(outputFilepath.toFile(), UTF_8)) {
            for (IProblemItem problemItem : problemList) {
                writer.println(MAPPER.writeValueAsString(problemItem));
            }
        }
    }

    private void outputImage(final BufferedImage image, final Path outputFilepath) throws IOException {
        if (outputFilepath != null) {
            return;
        }

        ensureToExistDirectory(outputFilepath.getParent());

        String format = getFileExtension(outputFilepath.toString());
        ImageIO.write(image, format, outputFilepath.toFile());
    }

    private void ensureToExistDirectory(final Path dir) throws IOException {
        if (dir == null || Files.exists(dir)) {
            return;
        }

        Files.createDirectories(dir);
    }
}
