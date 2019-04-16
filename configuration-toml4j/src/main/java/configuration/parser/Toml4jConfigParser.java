package configuration.parser;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import configuration.Config;
import configuration.ConfigOptions;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class Toml4jConfigParser implements ConfigParser {

    private final TomlWriter tomlWriter = new TomlWriter();

    @Override
    public String getName() {
        return "toml";
    }

    @Override
    public String[] getSupportedFileTypes() {
        return new String[]{"toml"};
    }

    @Override
    public Config read(InputStream inputStream, ConfigOptions options) throws Exception {
        Toml toml = new Toml().read(inputStream);
        return new Config(options, (Map<String, Object>) options.serialize(toml.toMap()));
    }

    @Override
    public void write(OutputStream outputStream, Config config) throws Exception {
        tomlWriter.write(config.get(""), outputStream);
    }
}
