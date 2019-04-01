package configuration;

import configuration.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ConfigNode {

    private final ConfigOptions options;

    private final Map<String, Object> map;

    public ConfigNode(ConfigOptions options) {
        this.options = options;
        this.map = options.getMapFactory().get();
    }

    public Object get(String path) {
        if (StringUtils.isNullOrEmpty(path)) {
            return this;
        }

        PathParser.Key[] keys = PathParser.parse(path, options);

        ConfigNode node = this;
        for (int i = 0; i < keys.length - 1; i++) {
            node = node.getNodeInternal(keys[i]);
            if (node == null) {
                return null;
            }
        }

        return node.getInternal(keys[keys.length - 1]);
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
        PathParser.Key[] keys = PathParser.parse(path, options);

        if (keys.length == 1) {
            return setInternal(keys[0], value);
        }

        ConfigNode node = this;
        for (int i = 0; i < keys.length - 1; i++) {
            ConfigNode child = node.getNodeInternal(keys[i]);
            if (child == null) {
                if (value == null) {
                    return null;
                }
                node = node.createNodeInternal(keys[i]);
            } else {
                node = child;
            }
        }

        return node.setInternal(keys[keys.length - 1], value);
    }

    protected ConfigNode getNodeInternal(PathParser.Key key) {
        Object value = getInternal(key);
        return value instanceof ConfigNode ? (ConfigNode) value : null;
    }

    protected ConfigNode createNodeInternal(PathParser.Key key) {
        ConfigNode node = new ConfigNode(options);
        setInternal(key, node);
        return node;
    }

    protected Object getInternal(PathParser.Key key) {
        if (key.isList()) {
            Object value = map.get(key.getName());
            int[] indexs = key.getIndexs();
            for (int i = 0; i < indexs.length; i++) {
                if (!(value instanceof List))
                    return null;
                int index = indexs[i];
                if (index >= ((List) value).size())
                    return null;
                value = ((List) value).get(index);
            }
            return value;
        } else {
            return map.get(key.getName());
        }
    }

    protected Object setInternal(PathParser.Key key, Object value) {
        if (key.isList()) {
            throw new UnsupportedOperationException();
        }
        return value == null ? map.remove(key.getName()) : map.put(key.getName(), value);
    }
}
