package configuration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PathParserTest {

    @Test
    public void testPathParser() {
        String[] keys = PathParser.parse("root.parent.child", new ConfigOptions());
        assertEquals(keys[0], "root");
        assertEquals(keys[1], "parent");
        assertEquals(keys[2], "child");
    }
}
