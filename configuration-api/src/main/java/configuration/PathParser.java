package configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PathParser {
    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    public static String[] parse(String path, ConfigOptions options) {
        return parse(path, options.getPathSeparator(), options.getKeyValidator());
    }

    public static String[] parse(String path, char pathSeparator, Pattern keyValidator) {
        if (path == null || path.isEmpty()) {
            return EMPTY_STRING_ARRAY;
        }

        final List<String> keys = new ArrayList<>();
        final StringBuilder key = new StringBuilder();

        for (char c : path.toCharArray()) {
            if (c == pathSeparator) {
                if (!keyValidator.matcher(key).matches()) {
                    throw new InvalidPathException(path, c);
                }

                keys.add(key.toString());
                key.setLength(0);
            } else {
                key.append(c);
            }
        }
        keys.add(key.toString());

        return keys.toArray(EMPTY_STRING_ARRAY);
    }
}
