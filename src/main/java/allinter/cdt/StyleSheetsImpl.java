package allinter.cdt;

import org.eclipse.actf.model.dom.dombycom.IStyleSheets;

import java.util.Collections;
import java.util.List;

// see IHTMLStyleSheetsCollection interface of IE11
// https://docs.microsoft.com/en-us/previous-versions/windows/internet-explorer/ie-developer/platform-apis/aa768629%28v%3dvs.85%29
public class StyleSheetsImpl implements IStyleSheets {
    private static final StyleSheetsImpl EMPTY_INSTANCE = new StyleSheetsImpl(Collections.emptyList());
    private final List<StyleSheetImpl> styleSheetList;

    public StyleSheetsImpl(final List<StyleSheetImpl> styleSheetList) {
        this.styleSheetList = Collections.unmodifiableList(styleSheetList);
    }

    public static StyleSheetsImpl empty() {
        return EMPTY_INSTANCE;
    }

    @Override
    public int getLength() {
        return this.styleSheetList.size();
    }

    @Override
    public StyleSheetImpl item(int index) {
        return this.styleSheetList.get(index);
    }
}
