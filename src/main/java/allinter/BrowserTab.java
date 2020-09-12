package allinter;

import allinter.cdt.DocumentImpl;
import allinter.cdt.Rect;
import allinter.cdt.StyleSheetImpl;
import allinter.cdt.Util;
import allinter.lowvision.CurrentStylesImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kklisura.cdt.protocol.commands.DOM;
import com.github.kklisura.cdt.protocol.commands.Page;
import com.github.kklisura.cdt.protocol.commands.Runtime;
import com.github.kklisura.cdt.protocol.events.runtime.ExecutionContextCreated;
import com.github.kklisura.cdt.protocol.support.types.EventHandler;
import com.github.kklisura.cdt.protocol.types.css.CSSStyleSheetHeader;
import com.github.kklisura.cdt.protocol.types.page.*;
import com.github.kklisura.cdt.protocol.types.runtime.*;
import com.github.kklisura.cdt.services.ChromeDevToolsService;
import com.github.kklisura.cdt.services.ChromeService;
import com.github.kklisura.cdt.services.types.ChromeTab;
import com.google.common.io.Resources;
import compat.RGB;
import org.eclipse.actf.model.dom.html.HTMLParserFactory;
import org.eclipse.actf.model.dom.html.IHTMLParser;
import org.eclipse.actf.model.dom.html.ParseException;
import org.eclipse.actf.model.ui.*;
import org.eclipse.actf.model.ui.editor.browser.ICurrentStyles;
import org.eclipse.actf.model.ui.editor.browser.IWebBrowserACTF;
import org.eclipse.actf.model.ui.editor.browser.IWebBrowserStyleInfo;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class BrowserTab implements IWebBrowserACTF, IModelService {
    private final ChromeService chromeService;
    private final ChromeTab tab;
    private final ChromeDevToolsService devToolsService;
    private final Runtime runtime;
    private final Page page;
    private String frameId = null;
    private Integer executionContextId = null;
    private boolean isUrlExists = false;
    private List<StyleSheetImpl> styleSheetList;

    public BrowserTab(final ChromeService chromeService, final ChromeTab tab, final ChromeDevToolsService devToolsService) {
        this.chromeService = chromeService;
        this.devToolsService = devToolsService;
        this.tab = tab;

        this.runtime = this.devToolsService.getRuntime();
        this.runtime.enable();
        this.runtime.onExecutionContextCreated(new EventHandler<ExecutionContextCreated>() {
            @Override
            public void onEvent(ExecutionContextCreated event) {
                setExecutionContextId(event.getContext().getId());
            }
        });

        this.page = this.devToolsService.getPage();
        this.page.enable();
        this.page.setLifecycleEventsEnabled(true);

        this.devToolsService.getNetwork().enable();
        this.devToolsService.getNetwork().setCacheDisabled(Boolean.TRUE);

        this.devToolsService.getDOM().enable();
        this.devToolsService.getCSS().enable();

        this.styleSheetList = new ArrayList();
        this.devToolsService.getCSS().onStyleSheetAdded((event) -> {
            CSSStyleSheetHeader header = event.getHeader();
            StyleSheetImpl styleSheet = new StyleSheetImpl(this.devToolsService, header.getStyleSheetId());
            styleSheet.setFrameId(header.getFrameId());
            styleSheet.setSourceURL(header.getHasSourceURL(), header.getSourceURL());
            styleSheet.setSourceMapURL(header.getSourceMapURL());
            styleSheet.setOrigin(header.getOrigin());
            styleSheet.setTitle(header.getTitle());
            styleSheet.setOwnerNodeId(header.getOwnerNode());
            styleSheet.setIsInline(header.getIsInline());
            styleSheet.setStartLine(header.getStartLine());
            styleSheet.setStartColumn(header.getStartColumn());
            styleSheet.setLength(header.getLength());

            String text = this.devToolsService.getCSS().getStyleSheetText(header.getStyleSheetId());
            styleSheet.setStyleSheetText(text);

            this.styleSheetList.add(styleSheet);
        });
        this.devToolsService.getCSS().onStyleSheetRemoved((event) -> {
            String styleSheetId = event.getStyleSheetId();
            Optional<StyleSheetImpl> item = this.styleSheetList.stream().filter((styleSheet) -> styleSheetId.equals(styleSheet.getId())).findFirst();
            if (item.isPresent()) {
                this.styleSheetList.remove(item.get());
            }
        });
    }

    @Override
    public void setFocusAddressText(boolean selectAll) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void showAddressText(boolean flag) {
        throw new UnsupportedOperationException("not implemented");
    }

    public Integer getExecutionContextId() {
        return this.executionContextId;
    }

    public void setExecutionContextId(final Integer executionContextId) {
        this.executionContextId = executionContextId;
    }

    @Override
    public void navigate(String url) {
        this.styleSheetList.clear();

        final Navigate navigate;
        try {
            navigate = Util.navigateAndWait(this.devToolsService, url, 10000);
        } catch (InterruptedException e) {
            throw new RuntimeException("load timeout");
        }

        this.frameId = navigate.getFrameId();
        this.isUrlExists = true;
    }

    @Override
    public void goBackward() {
        final NavigationEntry entry = findNavigationEntry(-1);
        if (entry == null) {
            return;
        }

        this.page.navigateToHistoryEntry(entry.getId());
    }

    @Override
    public void goForward() {
        final NavigationEntry entry = findNavigationEntry(1);
        if (entry == null) {
            return;
        }

        this.page.navigateToHistoryEntry(entry.getId());
    }

    private NavigationEntry findNavigationEntry(final int delta) {
        NavigationHistory history = this.page.getNavigationHistory();
        int index = history.getCurrentIndex() + delta;
        if (index < 0 || index >= history.getEntries().size()) {
            return null;
        }

        return history.getEntries().get(index);
    }

    @Override
    public void navigateStop() {
        this.page.stopLoading();
    }

    @Override
    public void navigateRefresh() {
        this.page.reload();
    }

    @Override
    public int getReadyState() {
        final String readyState = String.valueOf(Util.evaluate(this.devToolsService, "document.readyState"));
        if ("complete".equals(readyState)) {
            return READYSTATE_COMPLETE;
        } else if ("loading".equals(readyState)) {
            return READYSTATE_LOADING;
        } else if ("interactive".equals(readyState)) {
            return READYSTATE_INTERACTIVE;
        }

        return READYSTATE_UNINITIALIZED;
    }

    @Override
    public boolean isReady() {
        return this.getReadyState() == READYSTATE_COMPLETE;
    }

    @Override
    public String getLocationName() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public boolean isUrlExists() {
        return this.isUrlExists;
    }

    @Override
    public int getNavigateErrorCode() {
        this.isUrlExists = false;
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void setWebBrowserSilent(boolean bSilent) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void setDisableScriptDebugger(boolean bDisable) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public boolean isDisableScriptDebugger() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void highlightElementById(String id) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void hightlightElementByAttribute(String name, String value) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void clearHighlight() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void setFontSize(int fontSize) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public int getFontSize() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public IWebBrowserStyleInfo getStyleInfo() {
        return new IWebBrowserStyleInfo() {
            @Override
            public Map<String, ICurrentStyles> getCurrentStyles() {
                return BrowserTab.this.getCurrentStyles();
            }

            @Override
            public ModelServiceSizeInfo getSizeInfo(boolean isWhole) {
                throw new UnsupportedOperationException("not implemented");
            }

            @Override
            public RGB getUnvisitedLinkColor() {
                throw new UnsupportedOperationException("not implemented");
            }

            @Override
            public RGB getVisitedLinkColor() {
                throw new UnsupportedOperationException("not implemented");
            }
        };
    }

    public Map<String, ICurrentStyles> getCurrentStyles() {
        final String js;
        try {
            js = Resources.toString(this.getClass().getResource("getCurrentStyles.js"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Object[] array = (Object[]) Util.evaluate(this.devToolsService, js);
        HashMap<String, ICurrentStyles> ret = new HashMap<>(array.length);
        for (Object _item : array) {
            CurrentStylesImpl currentStyles = CurrentStylesImpl.convertFrom((Map<String, Object>)_item);
            ret.put(currentStyles.getXPath(), currentStyles);
        }

        return ret;
    }

    @Override
    public int getBrowserAddress() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public int setTimeout(String script, int interval) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public boolean clearTimeout(int id) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public int setInterval(String script, int interval) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public boolean clearInterval(int id) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public String[] getSupportMIMETypes() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public String[] getSupportExtensions() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public String getCurrentMIMEType() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void open(String url) {
        this.navigate(url);
    }

    @Override
    public void open(File target) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public String getURL() {
        return this.devToolsService.getDOM().getDocument().getDocumentURL();
    }

    @Override
    public String getTitle() {
        final DOM dom = this.devToolsService.getDOM();
        dom.enable();

        final Integer nodeId = dom.getDocument().getNodeId();
        final RemoteObject remoteObject = dom.resolveNode(nodeId, null, null, this.getExecutionContextId());
        if (remoteObject == null) {
            return null;
        }

        final String objectId = remoteObject.getObjectId();
        if (objectId == null) {
            return null;
        }

        final Object title = Util.getPropertyByObjectId(this.devToolsService, objectId, "title");
        this.runtime.releaseObject(objectId);

        return String.valueOf(title);
    }

    @Override
    public String getID() {
        return this.tab.getId();
    }

    @Override
    public Document getDocument() {
        final IHTMLParser parser = HTMLParserFactory.createHTMLParser();
        try {
            parser.parse(new ByteArrayInputStream(getPageSource().getBytes(StandardCharsets.UTF_8)));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

        return parser.getDocument();
    }

    @Override
    public Document getLiveDocument() {
        return new DocumentImpl(this.devToolsService, this.styleSheetList);
    }

//    @Override
//    public Composite getTargetComposite() {
//        throw new UnsupportedOperationException("not implemented");
//    }

    public String getPageSource() {
        final Frame frame = this.page.getFrameTree().getFrame();
        final ResourceContent frameContent = this.page.getResourceContent(frame.getId(), frame.getUrl());
        return frameContent.getContent();
    }

    public String getContent() {
        return (String) Util.evaluate(this.devToolsService, "document.documentElement.outerHTML");
    }

    // ブラウザの「ページのソースを表示」で表示される HTML をファイルへ保存する。
    // つまり javascript 実行前のソースを保存
    @Override
    public File saveOriginalDocument(String file) {
        if (file == null) {
            return null;
        }

        try (final FileWriter fileWrite = new FileWriter(file)) {
            fileWrite.write(getPageSource());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new File(file);
    }

    // javavascript 実行後の、現在 ブラウザが表示している DOM を出力する。
    @Override
    public File saveDocumentAsHTMLFile(String file) {
        if (file == null) {
            return null;
        }

        try (final FileWriter fileWrite = new FileWriter(file)) {
            fileWrite.write(this.getContent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new File(file);
    }

    @Override
    public void jumpToNode(org.w3c.dom.Node target) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public IModelServiceScrollManager getScrollManager() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public ImagePositionInfo[] getAllImagePosition() {
        final DOM dom = this.devToolsService.getDOM();
        dom.enable();

        final List<Integer> nodeIds = dom.querySelectorAll(dom.getDocument().getNodeId(), "img");
        if (nodeIds.size() == 0) {
            return new ImagePositionInfo[0];
        }

        final List<ImagePositionInfo> list = new ArrayList<>(nodeIds.size());
        for (Integer nodeId : nodeIds) {
            final RemoteObject remoteObject = dom.resolveNode(nodeId, null, null, null);
            if (remoteObject == null) {
                continue;
            }

            final String objectId = remoteObject.getObjectId();
            if (objectId == null) {
                continue;
            }

            Object src = Util.getPropertyByObjectId(this.devToolsService, objectId, "src");
            Rect rect = getBoundingClientRect(objectId);

            this.runtime.releaseObject(objectId);

            int x = rect.getX().intValue();
            int y = rect.getY().intValue();
            int width = rect.getWidth().intValue();
            int height = rect.getHeight().intValue();

            list.add(new ImagePositionInfo(x, y, width, height, String.valueOf(src)));
        }

        ImagePositionInfo[] result = new ImagePositionInfo[list.size()];
        list.toArray(result);
        return result;
    }

    private Rect getBoundingClientRect(final String objectId) {
        final CallFunctionOn callFunctionOn = this.runtime.callFunctionOn(
                "function() { return JSON.stringify(this.getBoundingClientRect()); }",
                objectId,
                Collections.emptyList(),
                Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, null, null);

        if (callFunctionOn == null) {
            return null;
        }

        Rect rect = null;
        {
            final RemoteObject remoteObject = callFunctionOn.getResult();
            if (remoteObject != null) {
                String value = (String) remoteObject.getValue();

                try {
                    rect = new ObjectMapper().readValue(value, Rect.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        String error = null;
        final ExceptionDetails exceptionDetails = callFunctionOn.getExceptionDetails();
        if (exceptionDetails != null) {
            final RemoteObject remoteObject = exceptionDetails.getException();
            if (remoteObject != null) {
                error = remoteObject.getDescription();
            }
        }

        if (error != null) {
            throw new RuntimeException(error);
        }

        return rect;
    }

//    @Override
//    public IModelServiceHolder getModelServiceHolder() {
//        throw new UnsupportedOperationException("not implemented");
//    }

    @Override
    public Object getAttribute(String key) {
        throw new UnsupportedOperationException("not implemented");
    }

    public void saveScreenshot(final String outputFilename) throws IOException {
        Util.saveScreenshot(this.devToolsService, outputFilename);
    }

    public byte[] takeScreenshot() {
        return Util.takeScreenshot(this.devToolsService);
    }
}
