package configuration;

import configuration.PathParser.Key;

import java.util.List;

public class ListConfigNode extends ConfigNode {

    private final List<ConfigNode> list;

    public ListConfigNode(ConfigOptions options) {
        super(options);
        list = options.getListFactory().get();
    }

    @Override
    public Object get(String path) {
        Key[] keys = PathParser.parse(path, options);

        ConfigNode node = this;
        Key key0 = keys[0];
        if (!key0.isList())
            return null;

        for (int index : key0.getIndexs()) {
            if (node == null || !node.isList())
                return null;
            node = ((ListConfigNode) node).getInternal(index);
        }

        for (int i = 1; i < keys.length; i++) {
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

    public List<ConfigNode> getBackingList() {
        return list;
    }

    ConfigNode getInternal(int index) {
        return list.get(index);
    }
}
