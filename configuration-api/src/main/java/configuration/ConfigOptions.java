package configuration;

import configuration.objectmapping.ObjectMappingManager;

import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class ConfigOptions {

    private char pathSeparator = '.';

    private Pattern keyValidator = Pattern.compile("[A-Za-z0-9_-]*");

    private Supplier<Map<String, Object>> mapFactory = LinkedHashMap::new;
    private Supplier<List<Object>> listFactory = LinkedList::new;

    private ObjectMappingManager objectMappingManager = ObjectMappingManager.DEFAULT;

    public char getPathSeparator() {
        return pathSeparator;
    }

    public Pattern getKeyValidator() {
        return keyValidator;
    }

    public Supplier<Map<String, Object>> getMapFactory() {
        return mapFactory;
    }

    public Supplier<List<Object>> getListFactory() {
        return listFactory;
    }

    public ObjectMappingManager getObjectMappingManager() {
        return objectMappingManager;
    }

    public <T> Object serialize(T value) {
        return objectMappingManager.serialize(this, value);
    }

    public <T> T deserialize(Class<T> type, Object raw) {
        return objectMappingManager.deserialize(this, type, raw);
    }

    public static final class Builder {
        private char pathSeparator = '.';
        private Pattern keyValidator = Pattern.compile("[A-Za-z0-9_-]*");
        private Supplier<Map<String, Object>> mapFactory = HashMap::new;
        private Supplier<List<Object>> listFactory = LinkedList::new;
        private ObjectMappingManager objectMappingManager = ObjectMappingManager.DEFAULT;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder pathSeparator(char pathSeparator) {
            this.pathSeparator = pathSeparator;
            return this;
        }

        public Builder keyValidator(Pattern keyValidator) {
            this.keyValidator = keyValidator;
            return this;
        }

        public Builder mapFactory(Supplier<Map<String, Object>> mapFactory) {
            this.mapFactory = mapFactory;
            return this;
        }

        public Builder listFactory(Supplier<List<Object>> listFactory) {
            this.listFactory = listFactory;
            return this;
        }

        public Builder objectMappingManager(ObjectMappingManager objectMappingManager) {
            this.objectMappingManager = objectMappingManager;
            return this;
        }

        public ConfigOptions build() {
            ConfigOptions configOptions = new ConfigOptions();
            configOptions.objectMappingManager = this.objectMappingManager;
            configOptions.listFactory = this.listFactory;
            configOptions.pathSeparator = this.pathSeparator;
            configOptions.mapFactory = this.mapFactory;
            configOptions.keyValidator = this.keyValidator;
            return configOptions;
        }
    }
}
