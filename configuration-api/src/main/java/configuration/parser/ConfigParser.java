package configuration.parser;

import configuration.Config;

import java.io.InputStream;
import java.io.OutputStream;

public interface ConfigParser {

    String getName();

    String[] getSupportedFileTypes();

    Config read(InputStream inputStream) throws ConfigParseException;

    void write(OutputStream outputStream, Config config) throws ConfigParseException;
}
