package configuration.objectmapping;

import configuration.ConfigOptions;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface ObjectMappers {

    ObjectMapper<Number> NUMBER_MAPPER = new ObjectMapper<Number>() {
        @Override
        public boolean canMap(Class type) {
            return Number.class.isAssignableFrom(type);
        }

        @Override
        public Object serialize(ConfigOptions options, Number value) {
            return value;
        }

        @Override
        public Number deserialize(ConfigOptions options, Object raw) {
            if (raw instanceof String) {
                return new BigDecimal((String) raw);
            }
            if (raw instanceof Number) {
                return (Number) raw;
            }
            throw new ObjectMappingException(String.format("Cannot deserialize %s to Number.", raw.getClass()));
        }
    };

    ObjectMapper<Boolean> BOOLEAN_MAPPER = new ObjectMapper<Boolean>() {
        @Override
        public boolean canMap(Class<?> type) {
            return Boolean.class == type;
        }

        @Override
        public Object serialize(ConfigOptions options, Boolean value) throws Exception {
            return value;
        }

        @Override
        public Boolean deserialize(ConfigOptions options, Object raw) throws Exception {
            if (raw instanceof String) {
                return Boolean.valueOf((String) raw);
            }
            if (Boolean.class == raw.getClass()) {
                return (Boolean) raw;
            }
            throw new ObjectMappingException(String.format("Cannot deserialize %s to Boolean.", raw.getClass()));
        }
    };

    ObjectMapper<String> STRING_MAPPER = new ObjectMapper<String>() {
        @Override
        public boolean canMap(Class<?> type) {
            return type == String.class;
        }

        @Override
        public Object serialize(ConfigOptions options, String value) {
            return value;
        }

        @Override
        public String deserialize(ConfigOptions options, Object raw) {
            return raw.toString();
        }
    };

    ObjectMapper<BigInteger> BIG_INTEGER_MAPPER = new ObjectMapper<BigInteger>() {
        @Override
        public boolean canMap(Class<?> type) {
            return type == BigInteger.class;
        }

        @Override
        public Object serialize(ConfigOptions options, BigInteger value) {
            return value.toString();
        }

        @Override
        public BigInteger deserialize(ConfigOptions options, Object raw) {
            return new BigInteger(raw.toString());
        }
    };

    ObjectMapper<BigDecimal> BIG_DECIMAL_MAPPER = new ObjectMapper<BigDecimal>() {
        @Override
        public boolean canMap(Class<?> type) {
            return type == BigDecimal.class;
        }

        @Override
        public Object serialize(ConfigOptions options, BigDecimal value) {
            return value.toString();
        }

        @Override
        public BigDecimal deserialize(ConfigOptions options, Object raw) {
            return new BigDecimal(raw.toString());
        }
    };

    ObjectMapper<List<Object>> LIST_MAPPER = new ObjectMapper<List<Object>>() {
        @Override
        public boolean canMap(Class type) {
            return List.class.isAssignableFrom(type);
        }

        @Override
        public Object serialize(ConfigOptions options, List<Object> value) {
            List<Object> result = options.getListFactory().get();
            value.forEach(o -> result.add(options.serialize(o)));
            return result;
        }

        @Override
        public List<Object> deserialize(ConfigOptions options, Object raw) {
            if (raw instanceof List)
                return (List<Object>) raw;
            return null;
        }
    };

    ObjectMapper<Map<String, Object>> MAP_MAPPER = new ObjectMapper<Map<String, Object>>() {
        @Override
        public boolean canMap(Class<?> type) {
            return Map.class.isAssignableFrom(type);
        }

        @Override
        public Object serialize(ConfigOptions options, Map<String, Object> value) {
            Map<String, Object> result = options.getMapFactory().get();
            value.entrySet().forEach(entry -> result.put(entry.getKey(), options.serialize(entry.getValue())));
            return result;
        }

        @Override
        public Map<String, Object> deserialize(ConfigOptions options, Object raw) {
            if (raw instanceof Map)
                return (Map<String, Object>) raw;
            return null;
        }
    };
}
