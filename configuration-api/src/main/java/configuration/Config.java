package configuration;

import java.util.Map;
import java.util.function.Supplier;

public class Config {

    private final ConfigOptions options;

    private final Map<String, Object> root;

    public Config() {
        this(new ConfigOptions());
    }

    public Config(ConfigOptions options) {
        this(options, options.getMapFactory().get());
    }

    protected Config(ConfigOptions options, Map<String, Object> root) {
        this.options = options;
        this.root = root;
    }

    public Object get(String path) {
        String[] keys = PathParser.parse(path, options);

        Map<String, Object> map = root;
        for (int i = 0; i < keys.length - 1; i++) {
            Object child = map.get(keys[i]);
            if (!(child instanceof Map))
                return null;

            map = (Map<String, Object>) child;
        }

        return map.get(keys[keys.length - 1]);
    }

    public Object get(String path, Object defaultValue) {
        Object value = get(path);
        return value != null ? value : defaultValue;
    }

    public Object get(String path, Supplier<Object> defaultValue) {
        Object value = get(path);
        return value != null ? value : defaultValue.get();
    }

    public <T> T get(String path, Class<T> type) {
        Object o = get(path);
        return o != null ? options.deserialize(type, o) : null;
    }

    public <T> T get(String path, Class<T> type, T defaultValue) {
        Object o = get(path);
        return o != null ? options.deserialize(type, o) : defaultValue;
    }

    public <T> T get(String path, Class<T> type, Supplier<T> defaultValue) {
        Object o = get(path);
        return o != null ? options.deserialize(type, o) : defaultValue.get();
    }

    public Object set(String path, Object value) {
        String[] keys = PathParser.parse(path, options);

        Map<String, Object> map = root;
        for (int i = 0; i < keys.length - 1; i++) {
            Object child = map.get(keys[i]);
            if (!(child instanceof Map)) {
                if (value == null)
                    return null;

                child = options.getMapFactory().get();
                map.put(keys[i], child);
            }

            map = (Map<String, Object>) child;
        }

        return value == null ? map.remove(keys[keys.length - 1]) : map.put(keys[keys.length - 1], options.serialize(value));
    }

    public boolean has(String path) {
        return get(path) != null;
    }

    public ConfigOptions getOptions() {
        return options;
    }
}
