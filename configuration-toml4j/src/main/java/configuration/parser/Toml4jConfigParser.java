package configuration.parser;

import configuration.Config;
import configuration.ConfigOptions;

import java.io.InputStream;
import java.io.OutputStream;

public class Toml4jConfigParser implements ConfigParser {
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
        return null;
    }

    @Override
    public void write(OutputStream outputStream, Config config) {

    }
}
