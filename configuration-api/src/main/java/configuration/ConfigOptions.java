package configuration;

import java.util.*;
import java.util.function.Supplier;

public class ConfigOptions {

    private char pathSeparator = '.';
    private char arrayLeft = '[';
    private char arrayRight = ']';

    private String allowIdentifierChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890_-";

    private Supplier<Map<String, Object>> mapFactory = HashMap::new;
    private Supplier<List<Object>> listFactory = LinkedList::new;

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

    public String getAllowIdentifierChars() {
        return allowIdentifierChars;
    }

    public void setAllowIdentifierChars(String allowIdentifierChars) {
        this.allowIdentifierChars = allowIdentifierChars;
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
}
