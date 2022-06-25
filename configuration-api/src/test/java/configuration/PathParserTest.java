package configuration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PathParserTest {

    @Test
    public void testPathParser() {
        String[] keys = PathParser.parse("root.parent.child", new ConfigOptions());
        assertEquals("root", keys[0]);
        assertEquals("parent", keys[1]);
        assertEquals("child", keys[2]);
    }
}
