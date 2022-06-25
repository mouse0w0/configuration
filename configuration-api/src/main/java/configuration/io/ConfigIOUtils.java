package configuration.io;

import configuration.Config;
import configuration.ConfigOptions;
import configuration.parser.ConfigParseException;
import configuration.parser.ConfigParser;
import configuration.parser.ConfigParsers;

import java.io.*;
import java.nio.file.Path;

public class ConfigIOUtils {

    public static Config load(File file, ConfigOptions options) throws ConfigParseException, ConfigIOException {
        String extensionName = getExtensionName(file);
        ConfigParser parser = ConfigParsers.getParserByFileType(extensionName);
        if (parser == null) {
            throw new ConfigParseException(String.format("Not found parser for file extension %s.", extensionName));
        }

        try (InputStream inputStream = new FileInputStream(file)) {
            return parser.read(inputStream, options);
        } catch (ConfigParseException e) {
            throw e;
        } catch (Exception e) {
            throw new ConfigIOException("Cannot load config cause by catch a exception.", e);
        }
    }

    public static Config load(File file) throws ConfigParseException, ConfigIOException {
        return load(file, new ConfigOptions());
    }

    public static Config load(Path path, ConfigOptions options) throws ConfigParseException, ConfigIOException {
        return load(path.toFile(), options);
    }

    public static Config load(Path path) throws ConfigParseException, ConfigIOException {
        return load(path.toFile(), new ConfigOptions());
    }

    public static void save(File file, Config config) throws ConfigParseException, ConfigIOException {
        String extensionName = getExtensionName(file);
        ConfigParser parser = ConfigParsers.getParserByFileType(extensionName);
        if (parser == null) {
            throw new ConfigParseException(String.format("Not found parser for file extension %s.", extensionName));
        }

        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new ConfigIOException("Cannot save config cause by catch a exception.", e);
            }
        }

        try (OutputStream outputStream = new FileOutputStream(file)) {
            parser.write(outputStream, config);
        } catch (ConfigParseException e) {
            throw e;
        } catch (Exception e) {
            throw new ConfigIOException("Cannot save config cause by catch a exception.", e);
        }
    }

    public static void save(Path path, Config config) throws ConfigParseException, ConfigIOException {
        save(path.toFile(), config);
    }

    private static String getExtensionName(File file) {
        return file.getName().substring(file.getName().lastIndexOf('.') + 1);
    }
}
