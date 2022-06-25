package configuration.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public final class ConfigParsers {
    private static final List<ConfigParser> PARSERS;

    static {
        List<ConfigParser> foundParsers = new ArrayList<>();
        ServiceLoader.load(ConfigParser.class).forEach(foundParsers::add);
        PARSERS = foundParsers;
    }

    public static List<ConfigParser> getParsers() {
        return PARSERS;
    }

    public static ConfigParser getParser(String name) {
        for (ConfigParser parser : PARSERS) {
            if (parser.getName().equals(name)) {
                return parser;
            }
        }
        return null;
    }

    public static ConfigParser getParserByFileType(String fileType) {
        for (ConfigParser parser : PARSERS) {
            for (String supported : parser.getSupportedFileTypes()) {
                if (supported.equals(fileType)) {
                    return parser;
                }
            }
        }
        return null;
    }

    private ConfigParsers() {
    }
}
