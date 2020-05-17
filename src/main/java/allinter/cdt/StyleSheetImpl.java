package allinter.cdt;

import com.github.kklisura.cdt.protocol.types.css.StyleSheetOrigin;
import com.github.kklisura.cdt.services.ChromeDevToolsService;
import org.eclipse.actf.model.dom.dombycom.IStyleSheet;
import org.eclipse.actf.model.dom.dombycom.IStyleSheets;

// see IHTMLStyleSheet interface of IE11
// https://docs.microsoft.com/en-us/previous-versions/windows/internet-explorer/ie-developer/platform-apis/aa768661(v%3Dvs.85)
public class StyleSheetImpl implements IStyleSheet {
    private final ChromeDevToolsService service;
    private final String id;
    private String frameId;
    private Boolean hasSourceURL;
    private String sourceURL;
    private String sourceMapURL;
    private StyleSheetOrigin origin;
    private String title;
    private Integer ownerNodeId;
    private Boolean isInline;
    private Double startLine;
    private Double startColumn;
    private Double length;
    private String text;

    public StyleSheetImpl(ChromeDevToolsService service, String id) {
        this.service = service;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setFrameId(String frameId) {
        this.frameId = frameId;
    }

    public String getSourceURL() {
        return this.sourceURL;
    }

    @Override
    public String getHref() {
        return getSourceURL();
    }

    public void setSourceURL(Boolean hasSourceURL, String sourceURL) {
        this.hasSourceURL = hasSourceURL;
        this.sourceURL = sourceURL;
    }

    public String getSourceMapURL() {
        return this.sourceMapURL;
    }

    public void setSourceMapURL(String sourceMapURL) {
        this.sourceMapURL = sourceMapURL;
    }

    public StyleSheetOrigin getOrigin() {
        return this.origin;
    }

    public void setOrigin(StyleSheetOrigin origin) {
        this.origin = origin;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOwnerNodeId() {
        return this.ownerNodeId;
    }

    public void setOwnerNodeId(Integer ownerNodeId) {
        this.ownerNodeId = ownerNodeId;
    }

    public void setIsInline(Boolean isInline) {
        this.isInline = isInline;
    }

    public void setStartLine(Double startLine) {
        this.startLine = startLine;
    }

    public void setStartColumn(Double startColumn) {
        this.startColumn = startColumn;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public void setStyleSheetText(String text) {
        this.text = text;
    }

    @Override
    public String getCssText() {
        return this.text;
    }

    // retrieves stylesheets imported by css's `@import` statement
    @Override
    public IStyleSheets getImports() {
        return StyleSheetsImpl.empty();
    }

    @Override
    public IStyleSheet getParentStyleSheet() {
        return null;
    }
}
