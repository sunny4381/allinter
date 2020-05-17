package allinter;

import picocli.CommandLine;

public class HtmlCheckerOptions {
    @CommandLine.Option(names = "--no-html-checker", negatable = true)
    private boolean htmlChecker = true;

    public HtmlCheckerOptions() {
    }

    public boolean isHtmlChecker() {
        return htmlChecker;
    }

    public void setHtmlChecker(boolean htmlChecker) {
        this.htmlChecker = htmlChecker;
    }
}
