package configuration.io;

public class ConfigIOException extends RuntimeException {
    public ConfigIOException(String message) {
        super(message);
    }

    public ConfigIOException(String message, Throwable cause) {
        super(message, cause);
    }
}
