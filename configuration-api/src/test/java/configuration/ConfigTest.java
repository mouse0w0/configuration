package configuration;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigTest {

    @Test
    public void testConfig() {
        Config config = new Config();
        config.set("root.parent.child", "Hello Config");
        config.set("root.parent.number", 1);
        config.set("root.parent.boolean", true);
        config.set("root.parent.bigInteger", new BigInteger("10000000000000000000000000000000000000000000000000"));
        assertEquals(config.getString("root.parent.child"), "Hello Config");
        assertEquals(config.getInt("root.parent.number"), 1);
        assertEquals(config.getString("root.parent.number"), "1");
        assertTrue(config.getBoolean("root.parent.boolean"));
        assertEquals(config.getBigInteger("root.parent.bigInteger"), new BigInteger("10000000000000000000000000000000000000000000000000"));
        assertTrue(config.has("root.parent"));
        assertNotNull(config.getMap("root.parent"));
        assertNotNull(config.getConfig("root.parent"));
        Config child = config.getConfig("root.parent");
        assertEquals(child.getString("child"), "Hello Config");
    }
}
