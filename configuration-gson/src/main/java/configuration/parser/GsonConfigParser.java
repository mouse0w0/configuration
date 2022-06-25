package configuration.parser;

import com.google.gson.*;
import configuration.Config;
import configuration.ConfigOptions;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

public class GsonConfigParser implements ConfigParser {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public String getName() {
        return "json";
    }

    @Override
    public String[] getSupportedFileTypes() {
        return new String[]{"json"};
    }

    @Override
    public Config read(InputStream inputStream, ConfigOptions options) throws Exception {
        JsonObject jsonObject;
        try (InputStreamReader reader = new InputStreamReader(inputStream)) {
            jsonObject = gson.fromJson(reader, JsonObject.class);
        }
        Config config = new Config(options);
        jsonObject.entrySet().forEach(entry -> config.set(entry.getKey(), toValue(entry.getValue(), options)));
        return config;
    }

    private Map<String, Object> toMap(JsonObject object, ConfigOptions options) {
        Map<String, Object> map = options.getMapFactory().get();
        object.entrySet().forEach(entry -> map.put(entry.getKey(), toValue(entry.getValue(), options)));
        return map;
    }

    private List<Object> toList(JsonArray array, ConfigOptions options) {
        List<Object> list = options.getListFactory().get();
        array.forEach(element -> list.add(toValue(element, options)));
        return list;
    }

    private Object toValue(JsonElement element, ConfigOptions options) {
        if (element.isJsonObject()) {
            return toMap(element.getAsJsonObject(), options);
        } else if (element.isJsonArray()) {
            return toList(element.getAsJsonArray(), options);
        } else if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isBoolean()) {
                return primitive.getAsBoolean();
            } else if (primitive.isString()) {
                return primitive.getAsString();
            } else {
                return primitive.getAsNumber();
            }
        } else {
            return null;
        }
    }

    @Override
    public void write(OutputStream outputStream, Config config) throws Exception {
        try (OutputStreamWriter writer = new OutputStreamWriter(outputStream)) {
            gson.toJson(fromMap(config.getRoot(), config.getOptions()), writer);
        }
    }

    private JsonObject fromMap(Map<String, Object> map, ConfigOptions options) {
        JsonObject object = new JsonObject();
        map.entrySet().forEach(entry -> object.add(entry.getKey(), fromValue(entry.getValue(), options)));
        return object;
    }

    private JsonArray fromList(List<Object> list, ConfigOptions options) {
        JsonArray array = new JsonArray();
        list.forEach(o -> array.add(fromValue(o, options)));
        return array;
    }

    private JsonElement fromValue(Object object, ConfigOptions options) {
        if (object instanceof Map) {
            return fromMap((Map<String, Object>) object, options);
        } else if (object instanceof List) {
            return fromList((List<Object>) object, options);
        } else if (object instanceof String) {
            return new JsonPrimitive((String) object);
        } else if (object instanceof Boolean) {
            return new JsonPrimitive((Boolean) object);
        } else if (object instanceof Number) {
            return new JsonPrimitive((Number) object);
        }
        return null;
    }
}
