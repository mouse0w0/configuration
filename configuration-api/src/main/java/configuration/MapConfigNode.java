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
        return null;
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

}
