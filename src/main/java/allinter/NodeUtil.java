package allinter;

import org.eclipse.actf.util.dom.TreeWalkerImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeFilter;

import java.util.ArrayList;

public class NodeUtil {
    public static String getFullXPath(Node node) {
        ArrayList<String> stack = new ArrayList<>();
        while (node.getParentNode() != null) {
            int sibCount = getNumberOfSiblings(node);
            int sibIndex = getIndexWithinSiblings(node);
            if (sibCount > 1) {
                stack.add(0, node.getNodeName().toLowerCase() + '[' + (sibIndex + 1) + ']');
            } else {
                stack.add(0, node.getNodeName().toLowerCase());
            }
            node = node.getParentNode();
        }

        return "/" + String.join("/", stack);
    }

    static String getAttribute(Node node, String name) {
        Node attrNode = node.getAttributes().getNamedItem(name);
        if (attrNode == null) {
            return null;
        }

        return attrNode.getNodeValue();
    }

    static int getIndexWithinSiblings(final Node node) {
        final String nodeName = node.getNodeName().toLowerCase();

        Node sib = node;
        int nth = 1;
        while ((sib = sib.getPreviousSibling()) != null) {
            if (sib.getNodeName().toLowerCase().equals(nodeName)) {
                nth++;
            }
        }

        return nth;
    }

    static int getNumberOfSiblings(final Node node) {
        final String nodeName = node.getNodeName().toLowerCase();

        int count = 0;
        NodeList siblings = node.getParentNode().getChildNodes();
        for (int i = 0; i < siblings.getLength(); i++) {
            final Node sib = siblings.item(i);
            if (sib.getNodeName().toLowerCase().equals(nodeName)) {
                count++;
            }
        }

        return count;
    }

    static boolean isUniqueId(final Document document, final String id) {
        int count = 0;

        TreeWalkerImpl walker = new TreeWalkerImpl(document.getDocumentElement(), NodeFilter.SHOW_ELEMENT, (node) -> {
            if (id.equals(getAttribute(node, "id"))) {
                return NodeFilter.FILTER_ACCEPT;
            } else {
                return NodeFilter.FILTER_SKIP;
            }
        }, false);

        while (walker.nextNode() != null) {
            count++;
        }

        return count == 1;
    }

    // from CSS.escape polyfill - https://github.com/mathiasbynens/CSS.escape/blob/master/css.escape.js
    public static String escapeId(final String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }

        final int length = value.length();
        final StringBuffer result = new StringBuffer(length);
        final char firstChar = value.charAt(0);
        for (int i = 0; i < length; i++) {
            final char ch = value.charAt(i);
            if (ch == '\u0000') {
                result.append('\uFFFD');
                continue;
            }

            if (isControl(ch) || (i == 0 && isDigit(ch)) || (i == 1 && isDigit(ch) && firstChar == '\u002D')) {
                result.append('\\');
                result.append(Integer.toHexString(ch));
                result.append(' ');
                continue;
            }

            if (i == 0 && length == 1 && ch == '\u002D') {
                result.append('\\');
                result.append(ch);
                continue;
            }

            if (ch >= '\u0080' || ch == '\u002D' || ch == '\u005F' || isDigit(ch) || isAlphabet(ch)) {
                result.append(ch);
                continue;
            }

            result.append('\\');
            result.append(ch);
        }

        return result.toString();
    }

    private static boolean isControl(char ch) {
        return ch >= '\u0001' && ch <= '\u001F';
    }

    private static boolean isDigit(char ch) {
        return ch >= '\u0030' && ch <= '\u0039';
    }

    private static boolean isAlphabet(char ch) {
        return (ch >= '\u0041' && ch <= '\u005A') || (ch >= '\u0061' && ch <= '\u007A');
    }

    public static String getCssPath(Node node) {
        if (!(node instanceof Element)) {
            return "";
        }

        Document document = node.getOwnerDocument();

        ArrayList<String> paths = new ArrayList<>();
        while (node.getNodeType() == Node.ELEMENT_NODE) {
            String nodeName = node.getNodeName().toLowerCase();
            String id = getAttribute(node, "id");
            if (id != null && isUniqueId(document, id)) {
                paths.add(0, nodeName + "#" + escapeId(id));
                break;
            }

            int nth = getIndexWithinSiblings(node);
            if (nth != 1) {
                paths.add(0, nodeName + ":nth-of-type(" + nth + ")");
            } else {
                paths.add(0, nodeName);
            }
            node = node.getParentNode();
        }
        return String.join(" > ", paths);
    }
}
