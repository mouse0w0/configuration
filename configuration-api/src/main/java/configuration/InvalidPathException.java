package configuration;

public class InvalidPathException extends RuntimeException {

    public InvalidPathException(String path) {
        super("Invalid path \"" + path + "\"");
    }

    public InvalidPathException(String path, char illegalIdentifier) {
        super("Invalid path \"" + path + "\", illegal identifier character '" + illegalIdentifier + "'");
    }
}
