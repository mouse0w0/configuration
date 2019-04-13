package configuration;

import configuration.objectmapping.ObjectMappingManager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class ConfigOptions {

    private final ObjectMappingManager objectMappingManager = new ObjectMappingManager();

    private char pathSeparator = '.';
    private char arrayLeft = '[';
    private char arrayRight = ']';

    private Pattern keyValidator = Pattern.compile("[A-Za-z0-9_-]*");

    private Supplier<Map<String, ConfigNode>> mapFactory = HashMap::new;
    private Supplier<List<ConfigNode>> listFactory = LinkedList::new;

    public char getPathSeparator() {
        return pathSeparator;
    }

    public void setPathSeparator(char pathSeparator) {
        this.pathSeparator = pathSeparator;
    }

    public char getArrayLeft() {
        return arrayLeft;
    }

    public void setArrayLeft(char arrayLeft) {
        this.arrayLeft = arrayLeft;
    }

    public char getArrayRight() {
        return arrayRight;
    }

    public void setArrayRight(char arrayRight) {
        this.arrayRight = arrayRight;
    }

    public Pattern getKeyValidator() {
        return keyValidator;
    }

    public void setKeyValidator(Pattern keyValidator) {
        this.keyValidator = keyValidator;
    }

    public Supplier<Map<String, ConfigNode>> getMapFactory() {
        return mapFactory;
    }

    public void setMapFactory(Supplier<Map<String, ConfigNode>> mapFactory) {
        this.mapFactory = mapFactory;
    }

    public Supplier<List<ConfigNode>> getListFactory() {
        return listFactory;
    }

    public void setListFactory(Supplier<List<ConfigNode>> listFactory) {
        this.listFactory = listFactory;
    }

    public ObjectMappingManager getObjectMappingManager() {
        return objectMappingManager;
    }

    public <T> ConfigNode serialize(T value) {
        return null;
    }

    public <T> T deserialize(ConfigNode node) {
        return null;
    }
}
