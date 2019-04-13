package configuration;

import configuration.util.StringUtils;

public class ValueConfigNode extends ConfigNode {

    public static final ValueConfigNode NULL = new ValueConfigNode(null, null);

    private final Object value;

    public ValueConfigNode(ConfigOptions options, Object value) {
        super(options);
        this.value = value;
    }

    @Override
    public Object get(String path) {
        return StringUtils.isNullOrEmpty(path) ? value : null;
    }

    @Override
    public Object set(String path, Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean has(String path) {
        return StringUtils.isNullOrEmpty(path);
    }

    @Override
    protected Object getRawValue() {
        return this;
    }
}
