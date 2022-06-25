package configuration.io;

import configuration.Config;
import configuration.ConfigException;
import configuration.ConfigOptions;
import configuration.parser.ConfigParser;
import configuration.parser.ConfigParsers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ConfigIOUtils {
    public static Config load(File file) throws ConfigException {
        return load(file.toPath(), new ConfigOptions());
    }

    public static Config load(File file, ConfigOptions options) throws ConfigException {
        return load(file.toPath(), options);
    }

    public static Config load(Path file) throws ConfigException {
        return load(file, new ConfigOptions());
    }

    public static Config load(Path file, ConfigOptions options) throws ConfigException {
        final String extension = getFileExtension(file);
        final ConfigParser parser = ConfigParsers.getParserByFileType(extension);
        if (parser == null) {
            throw new ConfigException("Not found parser for extension " + extension);
        }

        try (InputStream inputStream = Files.newInputStream(file)) {
            return parser.read(inputStream, options);
        } catch (ConfigException e) {
            throw e;
        } catch (Exception e) {
            throw new ConfigException("Cannot load config cause by occurring an exception", e);
        }
    }

    public static void save(File file, Config config) throws ConfigException {
        save(file.toPath(), config);
    }

    public static void save(Path file, Config config) throws ConfigException {
        final String extension = getFileExtension(file);
        final ConfigParser parser = ConfigParsers.getParserByFileType(extension);
        if (parser == null) {
            throw new ConfigException("Not found parser for extension " + extension);
        }

        if (!Files.exists(file)) {
            final Path parent = file.getParent();
            if (!Files.exists(parent)) {
                try {
                    Files.createDirectories(parent);
                } catch (IOException e) {
                    throw new ConfigException("cannot save config cause by cannot create parent", e);
                }
            }

            try {
                Files.createFile(file);
            } catch (IOException e) {
                throw new ConfigException("Cannot save config cause by cannot create file", e);
            }
        }

        try (OutputStream outputStream = Files.newOutputStream(file)) {
            parser.write(outputStream, config);
        } catch (ConfigException e) {
            throw e;
        } catch (Exception e) {
            throw new ConfigException("Cannot save config cause by occurring an exception", e);
        }
    }

    private static String getFileExtension(Path file) {
        final String fileName = file.getFileName().toString();
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }

    private ConfigIOUtils() {
    }
}
