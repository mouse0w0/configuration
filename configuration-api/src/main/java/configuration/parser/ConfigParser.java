package configuration.parser;

import configuration.Config;
import configuration.ConfigOptions;

import java.io.InputStream;
import java.io.OutputStream;

public interface ConfigParser {

    String getName();

    String[] getSupportedFileTypes();

    Config read(InputStream inputStream, ConfigOptions options) throws Exception;

    void write(OutputStream outputStream, Config config) throws Exception;
}
