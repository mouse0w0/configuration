package configuration.parser;

import configuration.Config;

import java.io.InputStream;
import java.io.OutputStream;

public class GsonConfigParser implements ConfigParser {
    @Override
    public String[] getSupportedFileTypes() {
        return new String[]{"json"};
    }

    @Override
    public Config read(InputStream inputStream) throws ConfigParseException {
        return null;
    }

    @Override
    public void write(OutputStream outputStream, Config config) {

    }
}
