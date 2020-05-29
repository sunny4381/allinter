package allinter;

import picocli.CommandLine;

import java.nio.file.Path;
import java.nio.file.Paths;

public class HtmlCheckerOptions {
    @CommandLine.Option(names = "--no-html-checker", negatable = true)
    private boolean htmlChecker = true;

    @CommandLine.Option(names = "--html-checker-output-report", description = "specifies output report file. default is hc-report.json")
    private Path outputReportFilepath = Paths.get("hc-report.json");

    public HtmlCheckerOptions() {
    }

    public boolean isHtmlChecker() {
        return htmlChecker;
    }

    public void setHtmlChecker(boolean htmlChecker) {
        this.htmlChecker = htmlChecker;
    }

    public Path getOutputReportFilepath() {
        return outputReportFilepath;
    }

    public void setOutputReportFilepath(Path outputReportFilepath) {
        this.outputReportFilepath = outputReportFilepath;
    }
}
