package configuration.objectmapping;

import configuration.ConfigNode;

public interface ObjectMapper<T> {

    ConfigNode serialize(T value);

    T deserialize(ConfigNode node);
}
