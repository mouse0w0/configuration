package configuration.objectmapping;

import configuration.ConfigOptions;

public interface ObjectMapper<T> {

    boolean canMap(Class<?> type);

    /**
     * @param options
     * @param value
     * @return Must be primitive type's wrapper class (such as {@link Integer}, {@link Double}), {@link String}, {@link java.util.Map}, {@link java.util.List}
     */
    Object serialize(ConfigOptions options, T value);

    /**
     * @param options
     * @param raw     Must be primitive type's wrapper class (such as {@link Integer}, {@link Double}), {@link String}, {@link java.util.Map}, {@link java.util.List}
     * @return
     */
    T deserialize(ConfigOptions options, Object raw);
}
