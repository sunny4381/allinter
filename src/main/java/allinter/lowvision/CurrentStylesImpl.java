package allinter.lowvision;

import org.eclipse.actf.model.ui.editor.browser.ICurrentStyles;
import org.eclipse.swt.graphics.Rectangle;
import allinter.cdt.Rect;
import allinter.cdt.Style;
import org.w3c.dom.Element;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

public class CurrentStylesImpl implements ICurrentStyles {
    private String xpath;
    private String cssPath;
    private String tagName;
    private Rect rect;
    private Style style;
    private Style computedStyle;
    private String href;
    private String[] texts;
    private String[] descendantTextsWithBGImage;

    private static String[] EMPTY_STRING_ARRAY = new String[0];

    public CurrentStylesImpl() {
    }

    public static CurrentStylesImpl convertFrom(Map<String, ?> map) {
        CurrentStylesImpl ret = new CurrentStylesImpl();

        map.forEach((key, value) -> {
            switch (key) {
                case "xpath":
                    ret.setXPath((String) value);
                    break;
                case "cssPath":
                    ret.setCssPath((String) value);
                    break;
                case "tagName":
                    ret.setTagName((String) value);
                    break;
                case "rect":
                    ret.setRect(Rect.convertFrom((Map<String, Object>) value));
                    break;
                case "style":
                    ret.setStyle(Style.convertFrom((Map<String, Object>) value));
                    break;
                case "computedStyle":
                    ret.setComputedStyle(Style.convertFrom((Map<String, Object>) value));
                    break;
                case "href":
                    ret.setHref((String) value);
                    break;
                case "texts": {
                        String[] array = null;
                        if (value != null) {
                            array = Arrays.stream((Object[]) value).toArray(String[]::new);
                        }
                        ret.setTexts(array);
                    }
                    break;
                case "descendantTextsWithBGImage": {
                        String[] array = null;
                        if (value != null) {
                            array = Arrays.stream((Object[]) value).toArray(String[]::new);
                        }
                        ret.setDescendantTextsWithBGImage(array);
                    }
                    break;
            }
        });

        return ret;
    }

    private static String orDefault(String actualValue, String defaultValue) {
        if (actualValue == null || actualValue.isEmpty()) {
            return defaultValue;
        }

        return actualValue;
    }

    private static boolean isFullyTransparent(String color) {
        if (color == null || color.isEmpty()) {
            return false;
        }

        color = color.replaceAll("\\s", "");
        return color.startsWith("rgba(") && color.endsWith(",0)");
    }

    @Override
    public String getXPath() {
        return this.xpath;
    }

    public void setXPath(String xpath) {
        this.xpath = xpath;
    }

    @Override
    public String getCssPath() {
        return cssPath;
    }

    public void setCssPath(String cssPath) {
        this.cssPath = cssPath;
    }

    @Override
    public String getTagName() {
        return this.tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public Style getStyle() {
        return this.style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public Style getComputedStyle() {
        return computedStyle;
    }

    public void setComputedStyle(Style computedStyle) {
        this.computedStyle = computedStyle;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String[] getTexts() {
        return this.texts;
    }

    public void setTexts(String[] value) {
        this.texts = value;
    }

    @Override
    public Rectangle getRectangle() {
        if (this.rect == null) {
            return null;
        }

        return new Rectangle(
                this.rect.getX().intValue(), this.rect.getY().intValue(),
                this.rect.getWidth().intValue(), this.rect.getHeight().intValue());
    }

    @Override
    public boolean isLink() {
        return this.href != null;
    }

    @Override
    public URL getLinkURL() {
        if (this.href == null) {
            return null;
        }

        try {
            return new URL(this.href);
        } catch (MalformedURLException e) {
            return null;
        }
    }

//    @Override
//    public String getBackgroundColor() {
//        if (isFullyTransparent(this.style.backgroundColor)) {
//            return "transparent";
//        }
//        return orDefault(this.style.backgroundColor, "transparent");
//    }

//    @Override
//    public String getBackgroundRepeat() {
//        return this.style.backgroundRepeat;
//    }

//    @Override
//    public String getBackgroundImage() {
//        return orDefault(this.style.backgroundImage, "none");
//    }

//    @Override
//    public String getColor() {
//        if (isFullyTransparent(this.style.color)) {
//            return "transparent";
//        }
//        return orDefault(this.style.color, "transparent");
//    }

//    @Override
//    public String getDisplay() {
//        return this.style.display;
//    }

//    @Override
//    public String getFontFamily() {
//        return this.style.fontFamily;
//    }

    @Override
    public String getFontSize() {
        return this.style.fontSize;
    }

    @Override
    public String getComputedFontSize() {
        return this.computedStyle.fontSize;
    }

//    @Override
//    public String getFontStyle() {
//        return orDefault(this.style.fontStyle, "normal");
//    }

//    @Override
//    public String getFontVariant() {
//        return orDefault(this.style.fontVariant, "normal");
//    }

//    @Override
//    public String getLetterSpacing() {
//        return orDefault(this.style.letterSpacing, "normal");
//    }

//    @Override
//    public String getLineHeight() {
//        return orDefault(this.style.lineHeight, "normal");
//    }

//    @Override
//    public String getPosition() {
//        return orDefault(this.style.position, "static");
//    }

//    @Override
//    public String getTextAlign() {
//        return orDefault(this.style.textAlign, "start");
//    }

//    @Override
//    public String getTextDecoration() {
//        return orDefault(this.style.textDecorationLine, "none");
//    }

//    @Override
//    public String getVisibility() {
//        return orDefault(this.style.visibility, "visible");
//    }

    @Override
    public String getComputedColor() {
        if (isFullyTransparent(this.computedStyle.color)) {
            return "transparent";
        }
        return orDefault(this.computedStyle.color, "transparent");
    }

    @Override
    public String getComputedBackgroundColor() {
        if (isFullyTransparent(this.computedStyle.backgroundColor)) {
            return "transparent";
        }
        return orDefault(this.computedStyle.backgroundColor, "transparent");
    }

    @Override
    public String getComputedBackgroundImage() {
        return orDefault(this.computedStyle.backgroundImage, "none");
    }

    @Override
    public String getComputedOpacity() {
        return orDefault(this.computedStyle.opacity, "1");
    }

    @Override
    public boolean hasChildText() {
        return this.texts != null && this.texts.length > 0;
    }

    @Override
    public String[] getChildTexts() {
        if (this.texts == null) {
            return EMPTY_STRING_ARRAY;
        }

        return this.texts;
    }

    @Override
    public boolean hasDescendantTextWithBGImage() {
        return this.descendantTextsWithBGImage != null && this.descendantTextsWithBGImage.length > 0;
    }

    @Override
    public String[] getDescendantTextsWithBGImage() {
        if (this.descendantTextsWithBGImage == null) {
            return EMPTY_STRING_ARRAY;
        }

        return this.descendantTextsWithBGImage;
    }

    public void setDescendantTextsWithBGImage(String[] value) {
        this.descendantTextsWithBGImage = value;
    }

    @Override
    public Element getElement() {
//        throw new NotImplementedException();
        return null;
    }
}
