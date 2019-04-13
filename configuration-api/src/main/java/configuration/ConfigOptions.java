package configuration;

import configuration.objectmapping.ObjectMappingManager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class ConfigOptions {

    private char pathSeparator = '.';

    private Pattern keyValidator = Pattern.compile("[A-Za-z0-9_-]*");

    private Supplier<Map<String, Object>> mapFactory = HashMap::new;
    private Supplier<List<Object>> listFactory = LinkedList::new;

    private ObjectMappingManager objectMappingManager = ObjectMappingManager.DEFAULT;

    public char getPathSeparator() {
        return pathSeparator;
    }

    public void setPathSeparator(char pathSeparator) {
        this.pathSeparator = pathSeparator;
    }

    public Pattern getKeyValidator() {
        return keyValidator;
    }

    public void setKeyValidator(Pattern keyValidator) {
        this.keyValidator = keyValidator;
    }

    public Supplier<Map<String, Object>> getMapFactory() {
        return mapFactory;
    }

    public void setMapFactory(Supplier<Map<String, Object>> mapFactory) {
        this.mapFactory = mapFactory;
    }

    public Supplier<List<Object>> getListFactory() {
        return listFactory;
    }

    public void setListFactory(Supplier<List<Object>> listFactory) {
        this.listFactory = listFactory;
    }

    public ObjectMappingManager getObjectMappingManager() {
        return objectMappingManager;
    }

    public void setObjectMappingManager(ObjectMappingManager objectMappingManager) {
        this.objectMappingManager = objectMappingManager;
    }

    public <T> Object serialize(T value) {
        return objectMappingManager.serialize(this, value);
    }

    public <T> T deserialize(Class<T> type, Object raw) {
        return objectMappingManager.deserialize(this, type, raw);
    }
}
