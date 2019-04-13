package configuration.objectmapping;

import configuration.ConfigNode;
import configuration.ConfigOptions;

public interface ObjectMapper<T> {

    ConfigNode serialize(ConfigOptions options, T value);

    T deserialize(ConfigOptions options, ConfigNode node);
}
