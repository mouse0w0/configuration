package configuration.objectmapping;

import configuration.ConfigOptions;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static configuration.objectmapping.ObjectMappers.*;

public class ObjectMappingManager {

    public static final ObjectMappingManager DEFAULT;

    static {
        List<ObjectMapper<?>> mappers = new LinkedList<>();
        mappers.add(STRING_MAPPER);
        mappers.add(BIG_DECIMAL_MAPPER);
        mappers.add(BIG_INTEGER_MAPPER);
        mappers.add(PRIMITIVE_MAPPER);
        mappers.add(LIST_MAPPER);
        mappers.add(MAP_MAPPER);
        DEFAULT = new ObjectMappingManager(mappers);
    }

    private final List<ObjectMapper<?>> mappers;

    public ObjectMappingManager(List<ObjectMapper<?>> mappers) {
        this.mappers = mappers;
    }

    public <T> Object serialize(ConfigOptions options, T value) {
        for (ObjectMapper mapper : mappers) {
            if (mapper.canMap(value.getClass())) {
                try {
                    Object o = mapper.serialize(options, value);
                    if (!checkType(o)) {
                        throw new ObjectMappingException(String.format("Illegal serialized result object %s.", o.getClass().getName()));
                    }
                    return o;
                } catch (ObjectMappingException e) {
                    throw e;
                } catch (Exception e) {
                    throw new ObjectMappingException("Catch a exception when serializing.", e);
                }
            }
        }
        throw new ObjectMappingException(String.format("Cannot serialize type %s.", value.getClass().getName()));
    }

    public <T> T deserialize(ConfigOptions options, Class<T> type, Object raw) {
        if (!checkType(raw)) {
            throw new ObjectMappingException(String.format("Illegal deserialize raw object %s.", raw.getClass().getName()));
        }
        for (ObjectMapper mapper : mappers) {
            if (mapper.canMap(type)) {
                try {
                    return type.cast(mapper.deserialize(options, raw));
                } catch (ObjectMappingException e) {
                    throw e;
                } catch (Exception e) {
                    throw new ObjectMappingException("Catch a exception when deserializing.", e);
                }
            }
        }
        throw new ObjectMappingException(String.format("Cannot deserialize type %s.", type.getName()));
    }

    private boolean checkType(Object obj) {
        return obj instanceof Map
                || obj instanceof String
                || obj instanceof Boolean
                || obj instanceof Number
                || obj instanceof List;
    }
}
