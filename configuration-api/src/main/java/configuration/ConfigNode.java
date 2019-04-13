package configuration;

import java.util.function.Supplier;

public abstract class ConfigNode {

    protected final ConfigOptions options;

    public ConfigNode(ConfigOptions options) {
        this.options = options;
    }

    public Object get(String path, Object defaultValue) {
        Object value = get(path);
        return value != null ? value : defaultValue;
    }

    public Object get(String path, Supplier<Object> defaultValue) {
        Object value = get(path);
        return value != null ? value : defaultValue.get();
    }

    public abstract Object get(String path);

    public abstract Object set(String path, Object object);

    public boolean has(String path) {
        return get(path) != null;
    }

    protected abstract Object getRawValue();

    public boolean isList() {
        return this instanceof ListConfigNode;
    }

    public boolean isMap() {
        return this instanceof MapConfigNode;
    }
}
