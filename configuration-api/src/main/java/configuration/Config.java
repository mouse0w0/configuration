package configuration;

import java.util.function.Supplier;

public class Config {

    private final ConfigOptions options;
    private final ConfigNode root;

    public Config() {
        this(new ConfigOptions());
    }

    public Config(ConfigOptions options) {
        this.options = options;
        this.root = new ConfigNode(options);
    }

    public Object get(String path) {
        return root.get(path);
    }

    public Object get(String path, Object defaultValue) {
        Object value = get(path);
        return value != null ? value : defaultValue;
    }

    public Object get(String path, Supplier<Object> defaultValue) {
        Object value = get(path);
        return value != null ? value : defaultValue.get();
    }

    public Object set(String path, Object value) {
        return root.set(path, value);
    }

    public ConfigOptions getOptions() {
        return options;
    }
}
