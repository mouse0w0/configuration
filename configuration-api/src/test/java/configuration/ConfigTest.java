package configuration;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConfigTest {

    @Test
    public void testConfig() {
        Config config = new Config();
        config.set("root.parent.child1", "Hello config");
        config.set("root.parent.array1", Arrays.asList("Hello array"));
        config.set("root.parent.array2", Arrays.asList(Arrays.asList("Hello two level array")));
        assertEquals(config.get("root.parent.child1"), "Hello config");
        assertEquals(config.get("root.parent.array1[0]"), "Hello array");
        assertTrue(config.get("root.parent.array1") instanceof List);
        assertEquals(config.get("root.parent.array2[0][0]"), "Hello two level array");
        assertTrue(config.get("root.parent.array2") instanceof List);
        assertTrue(config.get("root.parent.array2[0]") instanceof List);
    }
}
