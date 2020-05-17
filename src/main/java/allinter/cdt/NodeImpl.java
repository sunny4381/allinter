package allinter.cdt;

import com.github.kklisura.cdt.protocol.types.dom.Node;
import com.github.kklisura.cdt.services.ChromeDevToolsService;

import java.util.List;

public class NodeImpl implements org.w3c.dom.Node {
    public final static int ELEMENT_NODE = 1;
    public final static int DOCUMENT_NODE = 9;

    private final ChromeDevToolsService service;
    private final Node backend;

    public NodeImpl(ChromeDevToolsService service, Node backend) {
        this.service = service;
        this.backend = backend;
    }

    public ChromeDevToolsService getService() {
        return service;
    }

    public Node getBackend() {
        return backend;
    }

    @Override
    public String getNodeName() {
        return backend.getNodeName();
    }

    @Override
    public String getNodeValue() throws org.w3c.dom.DOMException {
        return backend.getNodeValue();
    }

    @Override
    public void setNodeValue(String nodeValue) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public short getNodeType() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.Node getParentNode() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.NodeList getChildNodes() {
        final List<Node> children = getBackend().getChildren();
        if (children == null || children.isEmpty()) {
            return NodeListImpl.emptyList();
        }

        return new NodeListImpl(getService(), children);
    }

    @Override
    public org.w3c.dom.Node getFirstChild() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.Node getLastChild() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.Node getPreviousSibling() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.Node getNextSibling() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.NamedNodeMap getAttributes() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.Document getOwnerDocument() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.Node insertBefore(org.w3c.dom.Node newChild, org.w3c.dom.Node refChild) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.Node replaceChild(org.w3c.dom.Node newChild, org.w3c.dom.Node oldChild) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.Node removeChild(org.w3c.dom.Node oldChild) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.Node appendChild(org.w3c.dom.Node newChild) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public boolean hasChildNodes() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.Node cloneNode(boolean deep) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void normalize() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public boolean isSupported(String feature, String version) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public String getNamespaceURI() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public String getPrefix() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void setPrefix(String prefix) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public String getLocalName() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public boolean hasAttributes() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public String getBaseURI() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public short compareDocumentPosition(org.w3c.dom.Node other) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public String getTextContent() throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void setTextContent(String textContent) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public boolean isSameNode(org.w3c.dom.Node other) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public String lookupPrefix(String namespaceURI) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public boolean isDefaultNamespace(String namespaceURI) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public String lookupNamespaceURI(String prefix) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public boolean isEqualNode(org.w3c.dom.Node arg) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public Object getFeature(String feature, String version) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public Object setUserData(String key, Object data, org.w3c.dom.UserDataHandler handler) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public Object getUserData(String key) {
        throw new UnsupportedOperationException("not implemented");
    }
}
