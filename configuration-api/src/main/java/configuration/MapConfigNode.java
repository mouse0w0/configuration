package configuration;

import configuration.PathParser.Key;

import java.util.Map;

public class MapConfigNode extends ConfigNode {

    private final Map<String, ConfigNode> map;

    public MapConfigNode(ConfigOptions options) {
        super(options);
        map = options.getMapFactory().get();
    }

    @Override
    public Object get(String path) {
        Key[] keys = PathParser.parse(path, options);
        ConfigNode node = this;
        for (int i = 0; i < keys.length; i++) {
            if (node == null || !node.isMap())
                return null;

            Key key = keys[i];
            node = ((MapConfigNode) node).getInternal(key.getName());

            if (key.isList()) {
                for (int index : key.getIndexs()) {
                    if (node == null || !node.isList())
                        return null;
                    node = ((ListConfigNode) node).getInternal(index);
                }
            }
        }

        return node.getRawValue();
    }

    @Override
    public Object set(String path, Object object) {
        Key[] keys = PathParser.parse(path, options);
        ConfigNode node = this;
        for (int i = 0; i < keys.length - 1; i++) {

            Key key = keys[i];
            node = ((MapConfigNode) node).getOrNewInternal(key.getName(), key.isList());

            if (key.isList()) {
                int[] indexs = key.getIndexs();
                for (int j = 0; j < indexs.length - 1; j++) {
                    node = ((ListConfigNode) node).getOrNewInternal(j, true);
                }
                node = ((ListConfigNode) node).getOrNewInternal(indexs[indexs.length - 1], false);
            }
        }

        Key lastKey = keys[keys.length - 1];
        if (lastKey.isList()) {
            node = ((MapConfigNode) node).getOrNewInternal(lastKey.getName(), true);

            int[] indexs = lastKey.getIndexs();
            for (int j = 0; j < indexs.length - 1; j++) {
                node = ((ListConfigNode) node).getOrNewInternal(j, true);
            }
            return ((ListConfigNode) node).setInternal(indexs[indexs.length - 1], object);
        } else {
            return ((MapConfigNode) node).setInternal(lastKey.getName(), object);
        }
    }

    @Override
    protected Object getRawValue() {
        return this;
    }

    public Map<String, ConfigNode> getBackingMap() {
        return map;
    }

    ConfigNode getInternal(String key) {
        return map.get(key);
    }

    ConfigNode getOrNewInternal(String key, boolean isList) {
        ConfigNode node = getInternal(key);
        if (node == null || (!node.isList() && !node.isMap())) {
            node = isList ? new ListConfigNode(options) : new MapConfigNode(options);
            setInternal(key, node);
        }
        return node;
    }

    ConfigNode setInternal(String key, Object value) {
        return value == null ? map.remove(key) : map.put(key, new ValueConfigNode(options, value));
    }

}
