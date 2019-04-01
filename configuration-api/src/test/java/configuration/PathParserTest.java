package configuration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PathParserTest {

    @Test
    public void testPathParser() {
        PathParser.Key[] keys = PathParser.parse("root.parent.children[1][0].child", new ConfigOptions());
        assertEquals(keys[0].getName(), "root");
        assertEquals(keys[1].getName(), "parent");
        assertEquals(keys[2].getName(), "children");
        assertTrue(keys[2].isList());
        assertEquals(keys[2].getIndexs()[0], 1);
        assertEquals(keys[2].getIndexs()[1], 0);
        assertEquals(keys[3].getName(), "child");
        assertFalse(keys[3].isList());

        keys = PathParser.parse("[1][0].child", new ConfigOptions());
        assertTrue(keys[0].isList());
        assertEquals(keys[0].getIndexs()[0], 1);
        assertEquals(keys[0].getIndexs()[1], 0);
        assertEquals(keys[1].getName(), "child");
        assertFalse(keys[3].isList());
    }
}
