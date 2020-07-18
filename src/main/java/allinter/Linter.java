package allinter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.actf.visualization.engines.lowvision.LowVisionException;
import org.eclipse.actf.visualization.engines.lowvision.image.ImageException;
import org.eclipse.actf.visualization.eval.problem.IProblemItem;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.google.common.io.Files.getFileExtension;

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

        final allinter.htmlchecker.Checker result =
                allinter.htmlchecker.Checker.validate(browser, this.url, this.htmlCheckerOptions);

        if (this.htmlCheckerOptions.getOutputReportFilepath() != null) {
            outputResults(result.getProblemList(), this.htmlCheckerOptions.getOutputReportFilepath());
        }
    }

    private void runLowVisionLint() throws IOException, LowVisionException, ImageException {
        if (! this.lowVisionOptions.isLowvision()) {
            return;
        }

        final allinter.lowvision.Checker result =
                allinter.lowvision.Checker.validate(browser, this.url, this.lowVisionOptions);

        if (this.lowVisionOptions.getOutputReportFilepath() != null) {
            outputResults(result.getProblemList(), this.lowVisionOptions.getOutputReportFilepath());
        }
        if (this.lowVisionOptions.getOutputImageFilepath() != null) {
            outputImage(result.getLowvisionImage(), this.lowVisionOptions.getOutputImageFilepath());
        }
        if (this.lowVisionOptions.getSourceImageFilepath() != null) {
            outputImage(result.getSourceImage(), this.lowVisionOptions.getSourceImageFilepath());
        }
    }

    private void outputResults(final List<IProblemItem> problemList, final Path outputFilepath) throws IOException {
        if (outputFilepath == null) {
            return;
        }

        ensureToExistDirectory(outputFilepath.getParent());

        try (PrintWriter writer = new PrintWriter(outputFilepath.toFile(), UTF_8.name())) {
            for (IProblemItem problemItem : problemList) {
                writer.println(MAPPER.writeValueAsString(problemItem));
            }
        }
    }

    private void outputImage(final BufferedImage image, final Path outputFilepath) throws IOException {
        if (outputFilepath == null) {
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
