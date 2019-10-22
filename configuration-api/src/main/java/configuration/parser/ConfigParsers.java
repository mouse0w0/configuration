package configuration.parser;

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

    private ConfigParsers() {
    }
}
