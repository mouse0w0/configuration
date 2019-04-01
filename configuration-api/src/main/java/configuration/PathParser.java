package configuration;

import java.util.ArrayList;
import java.util.List;

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
        return parse(path, options.getPathSeparator(), options.getArrayLeft(), options.getArrayRight(), options.getAllowIdentifierChars());
    }

    public static Key[] parse(String path, char pathSeparator, char arrayLeft, char arrayRight, String allowIdentifierChars) {
        List<Key> keys = new ArrayList<>();
        List<Integer> indexs = new ArrayList<>();
        StringBuilder name = new StringBuilder();
        int index = 0;
        boolean inIndex = false;

        for (char c : path.toCharArray()) {
            if (c == pathSeparator) {
                keys.add(new Key(name.toString(), indexs.stream().mapToInt(Integer::intValue).toArray()));
                name = new StringBuilder();
                indexs.clear();
            } else if (c == arrayLeft) {
                index = 0;
                inIndex = true;
            } else if (c == arrayRight) {
                indexs.add(index);
                inIndex = false;
            } else {
                if (inIndex) {
                    if ("0123456789".indexOf(c) == -1) {
                        throw new InvalidPathException(path);
                    }
                    index = index * 10 + c - '0';
                } else {
                    if (allowIdentifierChars.indexOf(c) == -1) {
                        throw new InvalidPathException(path, c);
                    }
                    name.append(c);
                }
            }
        }
        keys.add(new Key(name.toString(), indexs.stream().mapToInt(Integer::intValue).toArray()));

        return keys.toArray(new Key[keys.size()]);
    }
}
