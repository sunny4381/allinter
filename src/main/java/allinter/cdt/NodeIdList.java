package allinter.cdt;

import com.github.kklisura.cdt.protocol.types.dom.Node;
import com.github.kklisura.cdt.services.ChromeDevToolsService;

import java.util.List;

import static allinter.cdt.NodeListImpl.wrap;

public class NodeIdList implements org.w3c.dom.NodeList {
    private final ChromeDevToolsService service;
    private final List<Integer> nodeList;

    public NodeIdList(ChromeDevToolsService service, List<Integer> nodeList) {
        this.service = service;
        this.nodeList = nodeList;
    }

    @Override
    public org.w3c.dom.Node item(int index) {
        if (index < 0 || index >= this.nodeList.size()) {
            return null;
        }

        final Integer nodeId = this.nodeList.get(index);
        if (nodeId == null) {
            return null;
        }

        final Node node = this.service.getDOM().describeNode(nodeId, null, null, null, Boolean.FALSE);
        return wrap(this.service, node);
    }

    @Override
    public int getLength() {
        return this.nodeList.size();
    }
}
