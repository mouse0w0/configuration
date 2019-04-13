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
        Key[] keys = PathParser.parse(path, options);
        ConfigNode node = this;
        Key key0 = keys[0];
        if (!key0.isList())
            throw new IllegalArgumentException("The first key isn't list.");

        if (keys.length == 1) {
            int[] indexs = key0.getIndexs();
            for (int j = 0; j < indexs.length - 1; j++) {
                node = ((ListConfigNode) node).getOrNewInternal(j, true);
            }
            return ((ListConfigNode) node).setInternal(indexs[indexs.length - 1], object);
        }

        for (int index : key0.getIndexs()) {
            node = ((ListConfigNode) node).getOrNewInternal(index, true);
        }

        for (int i = 1; i < keys.length - 1; i++) {
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

    public List<ConfigNode> getBackingList() {
        return list;
    }

    ConfigNode getInternal(int index) {
        return index < list.size() ? list.get(index) : null;
    }

    ConfigNode getOrNewInternal(int index, boolean isList) {
        ConfigNode node = getInternal(index);
        if (node == null || (!node.isList() && !node.isMap())) {
            node = isList ? new ListConfigNode(options) : new MapConfigNode(options);
            setInternal(index, node);
        }
        return node;
    }

    ConfigNode setInternal(int index, Object value) {
        if (index == -1) {
            list.add(new ValueConfigNode(options, value));
            return null;
        }
        if (list.size() <= index) {
            for (int i = list.size(); i <= index; i++) {
                list.add(ValueConfigNode.NULL);
            }
        }
        return list.set(index, value == null ? ValueConfigNode.NULL : new ValueConfigNode(options, value));
    }
}
