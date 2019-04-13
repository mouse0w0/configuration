package configuration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConfigTest {

    @Test
    public void testConfig() {
        Config config = new Config();
        config.set("root.parent.child", "Hello Config");
        assertEquals(config.get("root.parent.child"), "Hello Config");
        assertTrue(config.has("root.parent"));
    }
}
