package ru.clevertec.json.parser;

import ru.clevertec.json.exception.JsonFormatException;
import ru.clevertec.json.exception.JsonParseException;
import ru.clevertec.json.mapper.JsonStringMapper;

import java.util.HashMap;
import java.util.Map;

public class ObjectParser {
    private final JsonStringMapper mapper;

    private ObjectParser() {
        this.mapper = JsonStringMapper.getInstance();
    }

    private static final class InstanceHolder {
        private static final ObjectParser instance = new ObjectParser();
    }
    public static ObjectParser getInstance() {
        return InstanceHolder.instance;
    }

    /**
     * Parsing json and return field name = value map
     * @param json object as JSON
     * @return All fields with value
     */
    public Map<String, String> parseJson(String json) {
        if (!(json.startsWith("{") && json.endsWith("}"))) {
            throw new JsonParseException("not an object");
        }
        json = json.substring(1, json.length() - 1);
        Map<String, String> fieldJsonMap = new HashMap<>();
        String value = null;
        String name;

        while (!json.isEmpty()) {
            name = getName(json);
            json = json.substring(name.length() + 1);

            if (isNull(json)) {
                value = "null";
            }
            if (isNumber(json.charAt(0))) {
                value = getNumber(json);
            }else if (isString(json.charAt(0))) {
                value = getString(json);
            }else if (isArray(json.charAt(0))) {
                value = getArray(json);
            } else if (isObject(json.charAt(0))) {
                value = getObject(json);
            }

            int valueLength = value.length() + 1;
            if (json.length() <=valueLength) {
                json = "";
            }else {
                json = json.substring(value.length() + 1);
            }

            fieldJsonMap.put(normalizeName(name), value);
        }
        return fieldJsonMap;
    }

    private boolean isNull(String json) {
        return json.startsWith("null");
    }

    private String getObject(String json) {
        return mapper.getObject(json);
    }

    private String getArray(String json) {
        long brackets = json.chars().takeWhile(c -> c == '[').count();
        return mapper.getArrayString(json, (int)brackets);
    }

    private String getString(String json) {
        String result;
        try {
            result = mapper.getStringValue(json);
        }catch (JsonFormatException e) {
            result = mapper.getCharValue(json);
        }
        return result;
    }

    private String getNumber(String json) {
        return mapper.getNumber(json);
    }

    private String normalizeName(String s) {
        return s.replaceAll("\"", "");
    }

    private String getName(String json) {
        if (!json.startsWith("\"")) throw new JsonParseException("not a name");
        StringBuilder builder = new StringBuilder();
        int quotes = 0;
        for (char c : json.toCharArray()) {
            if (quotes == 2) {
                break;
            }
            builder.append(c);
            if (c == '"') quotes++;
        }

        return builder.toString();
    }

    private boolean isNumber(char c) {
        return c >='0' && c <='9';
    }
    private boolean isString(char c) {
        return c =='"';
    }
    private boolean isArray(char c) {
        return c == '[';
    }
    private boolean isObject(char c) {
        return c == '{';
    }
}
