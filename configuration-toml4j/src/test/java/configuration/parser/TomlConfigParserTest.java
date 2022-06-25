package configuration.parser;

import configuration.Config;
import configuration.io.ConfigIOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.math.BigInteger;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class TomlConfigParserTest {

    @TempDir
    public static Path tempDir;

    @Test
    public void testLoadedParser() {
        assertNotNull(ConfigParsers.getParser("toml"));
    }

    @Test
    public void testTomlParser() {
        Config config = new Config();
        config.set("root.parent.child", "Hello Config");
        config.set("root.parent.number", 1);
        config.set("root.parent.boolean", true);
        config.set("root.parent.bigInteger", new BigInteger("9223372036854775808"));

        System.out.println(tempDir.resolve("config.toml").toAbsolutePath());
        config.save(tempDir.resolve("config.toml"));

        config = ConfigIOUtils.load(tempDir.resolve("config.toml"));
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
