package configuration.parser;

import configuration.Config;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;

public final class ConfigParsers {

    private static final List<ConfigParser> REGISTERED_PARSERS;

    static {
        List<ConfigParser> foundParsers = new ArrayList<>();
        ServiceLoader.load(ConfigParser.class).forEach(foundParsers::add);
        REGISTERED_PARSERS = foundParsers;
    }

    public static List<ConfigParser> getRegisteredParsers() {
        return REGISTERED_PARSERS;
    }

    public static ConfigParser getParser(String name) {
        for (ConfigParser parser : REGISTERED_PARSERS) {
            if (parser.getName().equals(name)) {
                return parser;
            }
        }
        return null;
    }

    public static ConfigParser getParserByFileType(String fileType) {
        for (ConfigParser parser : REGISTERED_PARSERS) {
            if (Arrays.stream(parser.getSupportedFileTypes()).anyMatch(s -> s.equals(fileType))) {
                return parser;
            }
        }
        return null;
    }

    public static boolean hasParser(String name) {
        return getParser(name) != null;
    }

    public static boolean hasParserByFileType(String fileType) {
        return getParserByFileType(fileType) != null;
    }

    public static Config load(File file) throws ConfigParseException {
        String extensionName = getExtensionName(file);
        ConfigParser parser = getParserByFileType(extensionName);
        if (parser == null) {
            throw new ConfigParseException(String.format("Not found parser for file extension %s.", extensionName));
        }

        try (InputStream inputStream = new FileInputStream(file)) {
            return parser.read(inputStream);
        } catch (IOException e) {
            throw new ConfigParseException("Cannot load config cause by catch a exception.", e);
        }
    }

    public static Config load(Path path) throws ConfigParseException {
        return load(path.toFile());
    }

    public static void save(File file, Config config) throws ConfigParseException {
        String extensionName = getExtensionName(file);
        ConfigParser parser = getParserByFileType(extensionName);
        if (parser == null) {
            throw new ConfigParseException(String.format("Not found parser for file extension %s.", extensionName));
        }

        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new ConfigParseException("Cannot save config cause by catch a exception.", e);
            }
        }

        try (OutputStream outputStream = new FileOutputStream(file)) {
            parser.write(outputStream, config);
        } catch (IOException e) {
            throw new ConfigParseException("Cannot save config cause by catch a exception.", e);
        }
    }

    public static void save(Path path, Config config) throws ConfigParseException {
        save(path.toFile(), config);
    }

    private static String getExtensionName(File file) {
        return file.getName().substring(file.getName().lastIndexOf('.') + 1);
    }

    private ConfigParsers() {
    }

}
