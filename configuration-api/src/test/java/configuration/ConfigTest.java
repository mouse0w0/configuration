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
        config.set("root.parent.bigInteger", new BigInteger("9223372036854775808"));
        assertEquals("Hello Config", config.getString("root.parent.child"));
        assertEquals(1, config.getInt("root.parent.number"));
        assertEquals("1", config.getString("root.parent.number"));
        assertTrue(config.getBoolean("root.parent.boolean"));
        assertEquals(new BigInteger("9223372036854775808"), config.getBigInteger("root.parent.bigInteger"));
        assertTrue(config.has("root.parent"));
        assertNotNull(config.getMap("root.parent"));
        assertNotNull(config.getConfig("root.parent"));
        Config child = config.getConfig("root.parent");
        assertEquals("Hello Config", child.getString("child"));
    }
}
