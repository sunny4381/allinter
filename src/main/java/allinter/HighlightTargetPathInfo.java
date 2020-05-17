package allinter;

public class HighlightTargetPathInfo {
    private final String cssPath;
    private final String xpath;

    public HighlightTargetPathInfo(String cssPath, String xpath) {
        this.cssPath = cssPath;
        this.xpath = xpath;
    }

    public String getCssPath() {
        return this.cssPath;
    }

    public String getXpath() {
        return this.xpath;
    }
}
