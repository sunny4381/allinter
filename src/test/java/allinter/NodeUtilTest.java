package allinter;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class NodeUtilTest {

    @Test
    public void testEscapeId() {
        // https://developer.mozilla.org/en-US/docs/Web/API/CSS/escape
        assertThat(NodeUtil.escapeId(".foo#bar"), is("\\.foo\\#bar"));
        assertThat(NodeUtil.escapeId("()[]{}"), is("\\(\\)\\[\\]\\{\\}"));
        assertThat(NodeUtil.escapeId("--a"), is("--a"));
        assertThat(NodeUtil.escapeId("0"), is("\\30 "));
        assertThat(NodeUtil.escapeId("2015"), is("\\32 015"));
        assertThat(NodeUtil.escapeId("\u0000"), is("\ufffd"));
    }
}
