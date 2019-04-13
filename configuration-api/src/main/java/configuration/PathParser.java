package configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PathParser {

    public static class Key {
        private String name;
        private int[] indexs;

        public Key(String name, int[] indexs) {
            this.name = name;
            this.indexs = indexs;
        }

        public String getName() {
            return name;
        }

        public int[] getIndexs() {
            return indexs;
        }

        public boolean isList() {
            return indexs.length != 0;
        }
    }

    public static Key[] parse(String path, ConfigOptions options) {
        return parse(path, options.getPathSeparator(), options.getArrayLeft(), options.getArrayRight(), options.getKeyValidator());
    }

    public static Key[] parse(String path, char pathSeparator, char arrayLeft, char arrayRight, Pattern keyValidator) {
        List<Key> keys = new ArrayList<>();
        List<Integer> indexs = new ArrayList<>();
        StringBuilder name = new StringBuilder();
        int index = -1;
        boolean inIndex = false;

        for (char c : path.toCharArray()) {
            if (c == pathSeparator) {
                if (!keyValidator.matcher(name).matches()) {
                    throw new InvalidPathException(path, c);
                }

                keys.add(new Key(name.toString(), indexs.stream().mapToInt(Integer::intValue).toArray()));
                name = new StringBuilder();
                indexs.clear();
            } else if (c == arrayLeft) {
                inIndex = true;
            } else if (c == arrayRight) {
                indexs.add(index);
                inIndex = false;
                index = -1;
            } else {
                if (inIndex) {
                    if ("0123456789".indexOf(c) == -1) {
                        throw new InvalidPathException(path);
                    }
                    if (index < 0) {
                        index = 0;
                    }
                    index = index * 10 + c - '0';
                } else {
                    name.append(c);
                }
            }
        }
        keys.add(new Key(name.toString(), indexs.stream().mapToInt(Integer::intValue).toArray()));

        return keys.toArray(new Key[keys.size()]);
    }
}
