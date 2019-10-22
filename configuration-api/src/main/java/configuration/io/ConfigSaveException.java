package configuration.io;

public class ConfigSaveException extends RuntimeException {
    public ConfigSaveException(String message) {
        super(message);
    }

    public ConfigSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
