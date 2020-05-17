package allinter.cdt;

import com.github.kklisura.cdt.protocol.types.dom.Node;
import com.github.kklisura.cdt.services.ChromeDevToolsService;

import java.util.List;

import static allinter.cdt.NodeImpl.DOCUMENT_NODE;
import static allinter.cdt.NodeImpl.ELEMENT_NODE;

public class NodeListImpl implements org.w3c.dom.NodeList {
    private final ChromeDevToolsService service;
    private final List<Node> nodeList;

    public NodeListImpl(ChromeDevToolsService service, List<Node> nodeList) {
        this.service = service;
        this.nodeList = nodeList;
    }

    private static final org.w3c.dom.NodeList EMPTY_LIST = new org.w3c.dom.NodeList() {
        @Override
        public org.w3c.dom.Node item(int index) {
            return null;
        }

        @Override
        public int getLength() {
            return 0;
        }
    };

    public static org.w3c.dom.NodeList emptyList() {
        return EMPTY_LIST;
    }

    public static org.w3c.dom.Node wrap(ChromeDevToolsService service, Node node) {
        switch (node.getNodeType()) {
        case ELEMENT_NODE:
            return new ElementImpl(service, node);
        case DOCUMENT_NODE:
            throw new UnsupportedOperationException("not implemented");
        default:
            return new NodeImpl(service, node);
        }
    }

    @Override
    public org.w3c.dom.Node item(int index) {
        if (index < 0 || index >= this.nodeList.size()) {
            return null;
        }

        final Node node = this.nodeList.get(index);
        if (node == null) {
            return null;
        }

        return wrap(this.service, node);
    }

    @Override
    public int getLength() {
        return this.nodeList.size();
    }
}
