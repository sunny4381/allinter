package allinter.cdt;

import com.github.kklisura.cdt.protocol.types.dom.Node;
import com.github.kklisura.cdt.services.ChromeDevToolsService;

import java.util.List;

public class ElementImpl extends NodeImpl implements org.w3c.dom.Element, org.w3c.dom.Node {
    private static final String EMPTY_STRING = "";

    public ElementImpl(ChromeDevToolsService service, Node backend) {
        super(service, backend);
    }

    @Override
    public String getTagName() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public String getAttribute(String name) {
        List<String> attributes = getBackend().getAttributes();
        for (int i = 0; i < attributes.size(); i += 2) {
            if (attributes.get(i).equals(name)) {
                return attributes.get(i + 1);
            }
        }

        return EMPTY_STRING;
    }

    @Override
    public void setAttribute(String name, String value) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void removeAttribute(String name) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.Attr getAttributeNode(String name) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.Attr setAttributeNode(org.w3c.dom.Attr newAttr) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.Attr removeAttributeNode(org.w3c.dom.Attr oldAttr) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.NodeList getElementsByTagName(String name) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public String getAttributeNS(String namespaceURI, String localName) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void setAttributeNS(String namespaceURI, String qualifiedName, String value) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void removeAttributeNS(String namespaceURI, String localName) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.Attr getAttributeNodeNS(String namespaceURI, String localName) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.Attr setAttributeNodeNS(org.w3c.dom.Attr newAttr) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.NodeList getElementsByTagNameNS(String namespaceURI, String localName) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public boolean hasAttribute(String name) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public boolean hasAttributeNS(String namespaceURI, String localName) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public org.w3c.dom.TypeInfo getSchemaTypeInfo() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void setIdAttribute(String name, boolean isId) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void setIdAttributeNode(org.w3c.dom.Attr idAttr, boolean isId) throws org.w3c.dom.DOMException {
        throw new UnsupportedOperationException("not implemented");
    }
}
