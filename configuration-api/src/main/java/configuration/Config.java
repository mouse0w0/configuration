package configuration;

import configuration.io.ConfigIOUtils;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public final class Config {

    private final ConfigOptions options;
    private final Map<String, Object> root;

    public Config() {
        this(new ConfigOptions());
    }

    public Config(ConfigOptions options) {
        this(options, options.getMapFactory().get());
    }

    private Config(ConfigOptions options, Map<String, Object> root) {
        this.options = options;
        this.root = root;
    }

    public Map<String, Object> getRoot() {
        return root;
    }

    public ConfigOptions getOptions() {
        return options;
    }

    public Object get(String path) {
        String[] keys = PathParser.parse(path, options);

        Object obj = root;
        for (int i = 0; i < keys.length; i++) {
            if (!(obj instanceof Map))
                return null;

            obj = ((Map<String, Object>) obj).get(keys[i]);
        }
        return obj;
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

    public String getString(String path) {
        return get(path, String.class);
    }

    public String getString(String path, String defaultValue) {
        return get(path, String.class, defaultValue);
    }

    public boolean getBoolean(String path) {
        return getBoolean(path, false);
    }

    public boolean getBoolean(String path, boolean defaultValue) {
        return get(path, Boolean.class, defaultValue);
    }

    public Number getNumber(String path) {
        return getNumber(path, 0);
    }

    public Number getNumber(String path, Number defaultValue) {
        return get(path, Number.class, defaultValue);
    }

    public int getInt(String path) {
        return getInt(path, 0);
    }

    public int getInt(String path, int defaultValue) {
        return get(path, Number.class, defaultValue).intValue();
    }

    public long getLong(String path) {
        return getLong(path, 0L);
    }

    public long getLong(String path, long defaultValue) {
        return get(path, Number.class, defaultValue).longValue();
    }

    public float getFloat(String path) {
        return getFloat(path, 0F);
    }

    public float getFloat(String path, float defaultValue) {
        return get(path, Number.class, defaultValue).floatValue();
    }

    public double getDouble(String path) {
        return getDouble(path, 0D);
    }

    public double getDouble(String path, double defaultValue) {
        return get(path, Number.class, defaultValue).doubleValue();
    }

    public Map<String, Object> getMap(String path) {
        return get(path, Map.class);
    }

    public Map<String, Object> getMap(String path, Map<String, Object> defaultValue) {
        return get(path, Map.class, defaultValue);
    }

    public List<Object> getList(String path) {
        return get(path, List.class);
    }

    public List<Object> getList(String path, List<Object> defaultValue) {
        return get(path, List.class, defaultValue);
    }

    public BigInteger getBigInteger(String path) {
        return get(path, BigInteger.class);
    }

    public BigInteger getBigInteger(String path, BigInteger defaultValue) {
        return get(path, BigInteger.class, defaultValue);
    }

    public BigDecimal getBigDecimal(String path) {
        return get(path, BigDecimal.class);
    }

    public BigDecimal getBigDecimal(String path, BigDecimal defaultValue) {
        return get(path, BigDecimal.class, defaultValue);
    }

    public Config getConfig(String path) {
        Map<String, Object> map = getMap(path);
        return map == null ? null : new Config(options, map);
    }

    public Object set(String path, Object value) {
        String[] keys = PathParser.parse(path, options);

        if (keys.length == 0) {
            if (value instanceof Map) {
                Map<String, Object> oldRoot = options.getMapFactory().get();
                oldRoot.putAll(root);
                root.clear();
                root.putAll((Map<String, Object>) value);
                return oldRoot;
            } else {
                throw new IllegalArgumentException("Cannot set value to empty path");
            }
        }

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

    public Set<String> getKeys() {
        return root.keySet();
    }

    public Set<String> getKeysDeeply() {
        Set<String> keys = new HashSet<>();
        root.forEach((key, value) -> {
            keys.add(key);
            if (value instanceof Map) {
                getKeysDeeply(keys, key, (Map<String, Object>) value);
            }
        });
        return keys;
    }

    private void getKeysDeeply(Set<String> keys, String parent, Map<String, Object> map) {
        map.forEach((key, value) -> {
            final String path = parent + "." + key;
            keys.add(path);
            if (value instanceof Map) {
                getKeysDeeply(keys, path, (Map<String, Object>) value);
            }
        });
    }

    public void save(Path path) {
        ConfigIOUtils.save(path, this);
    }

    public void save(File file) {
        ConfigIOUtils.save(file, this);
    }
}
