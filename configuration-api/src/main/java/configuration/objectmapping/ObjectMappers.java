package configuration.objectmapping;

import configuration.ConfigOptions;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface ObjectMappers {

    ObjectMapper<?> PRIMITIVE_MAPPER = new ObjectMapper() {
        @Override
        public boolean canMap(Class type) {
            return type == Boolean.class
                    || Number.class.isAssignableFrom(type)
                    || type == Character.class;
        }

        @Override
        public Object serialize(ConfigOptions options, Object value) {
            return value;
        }

        @Override
        public Object deserialize(ConfigOptions options, Object raw) {
            return raw instanceof String ? new BigDecimal((String) raw) : raw;
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

    ObjectMapper<List<?>> LIST_MAPPER = new ObjectMapper<List<?>>() {
        @Override
        public boolean canMap(Class type) {
            return List.class.isAssignableFrom(type);
        }

        @Override
        public Object serialize(ConfigOptions options, List<?> value) {
            List<Object> result = options.getListFactory().get();
            value.forEach(o -> result.add(options.serialize(o)));
            return result;
        }

        @Override
        public List<?> deserialize(ConfigOptions options, Object raw) {
            return (List<?>) raw;
        }
    };

    ObjectMapper<Map<String, ?>> MAP_MAPPER = new ObjectMapper<Map<String, ?>>() {
        @Override
        public boolean canMap(Class<?> type) {
            return Map.class.isAssignableFrom(type);
        }

        @Override
        public Object serialize(ConfigOptions options, Map<String, ?> value) {
            Map<String, Object> result = options.getMapFactory().get();
            value.entrySet().forEach(entry -> result.put(entry.getKey(), options.serialize(entry.getValue())));
            return result;
        }

        @Override
        public Map<String, ?> deserialize(ConfigOptions options, Object raw) {
            return (Map<String, ?>) raw;
        }
    };
}
