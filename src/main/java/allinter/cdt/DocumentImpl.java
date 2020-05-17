package allinter.cdt;

import com.github.kklisura.cdt.protocol.commands.DOM;
import com.github.kklisura.cdt.protocol.types.dom.Node;
import com.github.kklisura.cdt.services.ChromeDevToolsService;
import org.eclipse.actf.model.dom.dombycom.IDocumentEx;
import org.eclipse.actf.model.dom.dombycom.IStyleSheets;
import org.w3c.dom.Element;

import java.util.Collections;
import java.util.List;

public class DocumentImpl extends NodeImpl implements org.w3c.dom.Document, IDocumentEx {
    private final List<StyleSheetImpl> styleSheetList;

    public DocumentImpl(ChromeDevToolsService service, List<StyleSheetImpl> styleSheetList) {
        super(service, service.getDOM().getDocument());
        this.styleSheetList = Collections.unmodifiableList(styleSheetList);
    }

    @Override
    public org.w3c.dom.DocumentType getDoctype() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.DOMImplementation getImplementation() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.Element getDocumentElement() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.Element createElement(String tagName) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.DocumentFragment createDocumentFragment() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.Text createTextNode(String data) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.Comment createComment(String data) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.CDATASection createCDATASection(String data) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.ProcessingInstruction createProcessingInstruction(String target, String data) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.Attr createAttribute(String name) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.EntityReference createEntityReference(String name) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.NodeList getElementsByTagName(String tagName) {
        final DOM dom = getService().getDOM();
        final Node document = dom.getDocument();
        final List<Integer> nodeIdList = dom.querySelectorAll(document.getNodeId(), tagName);
        return new NodeIdList(getService(), nodeIdList);
    }

    @Override
    public org.w3c.dom.Node importNode(org.w3c.dom.Node importedNode, boolean deep) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.Element createElementNS(String namespaceURI, String qualifiedName) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.Attr createAttributeNS(String namespaceURI, String qualifiedName) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.Element getElementById(String elementId) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public String getInputEncoding() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public String getXmlEncoding() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public boolean getXmlStandalone() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void setXmlStandalone(boolean xmlStandalone) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public String getXmlVersion() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void setXmlVersion(String xmlVersion) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public boolean getStrictErrorChecking() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void setStrictErrorChecking(boolean strictErrorChecking) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public String getDocumentURI() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void setDocumentURI(String documentURI) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.Node adoptNode(org.w3c.dom.Node source) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.DOMConfiguration getDomConfig() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void normalizeDocument() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.Node renameNode(org.w3c.dom.Node n, String namespaceURI, String qualifiedName) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public List<org.w3c.dom.Node> getElementsByIdInAllFrames(String id) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public String getCompatMode() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public Element getTargetElement(String target) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public IStyleSheets getStyleSheets() {
        return new StyleSheetsImpl(this.styleSheetList);
    }
}
