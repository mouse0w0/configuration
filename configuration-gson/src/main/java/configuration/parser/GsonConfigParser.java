package configuration.parser;

import com.google.gson.JsonParser;
import configuration.Config;
import configuration.ConfigOptions;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class GsonConfigParser implements ConfigParser {

    private final JsonParser jsonParser = new JsonParser();

    @Override
    public String getName() {
        return "json";
    }

    @Override
    public String[] getSupportedFileTypes() {
        return new String[]{"json"};
    }

    @Override
    public Config read(InputStream inputStream, ConfigOptions options) throws Exception {
        Config config = new Config(options);
        try (InputStreamReader reader = new InputStreamReader(inputStream)) {

        }
        return null;
    }

    @Override
    public void write(OutputStream outputStream, Config config) {

    }
}
