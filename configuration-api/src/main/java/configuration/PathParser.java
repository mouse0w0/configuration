package configuration;

import configuration.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PathParser {

    public static String[] parse(String path, ConfigOptions options) {
        return parse(path, options.getPathSeparator(), options.getKeyValidator());
    }

    public static String[] parse(String path, char pathSeparator, Pattern keyValidator) {
        if (StringUtils.isNullOrEmpty(path)) {
            return new String[0];
        }

        List<String> keys = new ArrayList<>();
        StringBuilder name = new StringBuilder();

        for (char c : path.toCharArray()) {
            if (c == pathSeparator) {
                if (!keyValidator.matcher(name).matches()) {
                    throw new InvalidPathException(path, c);
                }

                keys.add(name.toString());
                name = new StringBuilder();
            } else {
                name.append(c);
            }
        }
        keys.add(name.toString());

        return keys.toArray(new String[0]);
    }
}
