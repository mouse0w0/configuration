package configuration.parser;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import configuration.Config;
import configuration.ConfigOptions;

import java.io.InputStream;
import java.io.OutputStream;

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
        Config config = new Config(options);
        config.set("", options.serialize(toml.toMap()));
        return config;
    }

    @Override
    public void write(OutputStream outputStream, Config config) throws Exception {
        tomlWriter.write(config.get(""), outputStream);
    }
}
