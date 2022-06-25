package configuration.io;

import configuration.Config;
import configuration.ConfigOptions;
import configuration.parser.ConfigParseException;
import configuration.parser.ConfigParser;
import configuration.parser.ConfigParsers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ConfigIOUtils {
    public static Config load(File file) throws ConfigParseException, ConfigIOException {
        return load(file.toPath(), new ConfigOptions());
    }

    public static Config load(File file, ConfigOptions options) throws ConfigParseException, ConfigIOException {
        return load(file.toPath(), options);
    }

    public static Config load(Path file) throws ConfigParseException, ConfigIOException {
        return load(file, new ConfigOptions());
    }

    public static Config load(Path file, ConfigOptions options) throws ConfigParseException, ConfigIOException {
        final String extension = getFileExtension(file);
        final ConfigParser parser = ConfigParsers.getParserByFileType(extension);
        if (parser == null) {
            throw new ConfigParseException(String.format("Not found parser for file extension %s.", extension));
        }

        try (InputStream inputStream = Files.newInputStream(file)) {
            return parser.read(inputStream, options);
        } catch (ConfigParseException | ConfigIOException e) {
            throw e;
        } catch (Exception e) {
            throw new ConfigIOException("Cannot load config cause by occurring a exception.", e);
        }
    }

    public static void save(File file, Config config) throws ConfigParseException, ConfigIOException {
        save(file.toPath(), config);
    }

    public static void save(Path file, Config config) throws ConfigParseException, ConfigIOException {
        final String extension = getFileExtension(file);
        final ConfigParser parser = ConfigParsers.getParserByFileType(extension);
        if (parser == null) {
            throw new ConfigParseException(String.format("Not found parser for file extension %s.", extension));
        }

        if (!Files.exists(file)) {
            final Path parent = file.getParent();
            if (!Files.exists(parent)) {
                try {
                    Files.createDirectories(parent);
                } catch (IOException e) {
                    throw new ConfigIOException("cannot save config cause by cannot create parent.", e);
                }
            }

            try {
                Files.createFile(file);
            } catch (IOException e) {
                throw new ConfigIOException("Cannot save config cause by cannot create file.", e);
            }
        }

        try (OutputStream outputStream = Files.newOutputStream(file)) {
            parser.write(outputStream, config);
        } catch (ConfigParseException | ConfigIOException e) {
            throw e;
        } catch (Exception e) {
            throw new ConfigIOException("Cannot save config cause by occurring a exception.", e);
        }
    }

    private static String getFileExtension(Path file) {
        final String fileName = file.getFileName().toString();
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }

    private ConfigIOUtils() {
    }
}
