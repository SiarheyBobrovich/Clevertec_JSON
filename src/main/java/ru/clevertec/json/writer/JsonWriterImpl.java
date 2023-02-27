package ru.clevertec.json.writer;

import ru.clevertec.json.util.ReflectionUtil;
import ru.clevertec.json.writer.api.JsonWriter;
import ru.clevertec.json.exception.JsonParseException;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonWriterImpl implements JsonWriter {

    @Override
    public String writeObjectAsString(Object value) {
        if (value == null) return "null";
        final Class<?> valueClass = value.getClass();
        final String json;

        if (ReflectionUtil.isNumber(value)) json  = String.valueOf(value);
        else if (valueClass == Character.class) json = characterToString((char)value);
        else if (valueClass == String.class) json = "\"" + value + "\"";
        else if (valueClass.isArray()) json = arrayToString((Object[]) value);
        else if (ReflectionUtil.isClassInstanceOf(valueClass, Collection.class)) json = arrayToString(((Collection<?>) value).toArray());
        else if (ReflectionUtil.isClassInstanceOf(valueClass, Map.class)) json = mapToString((Map<?, ?>) value);
        else json = "{" +
                    ReflectionUtil.getAllDeclaredFields(valueClass).stream()
                            .filter(f -> !Modifier.isStatic(f.getModifiers()))
                            .collect(Collectors.toMap(
                                    field -> "\"" + field.getName() + "\"",
                                    field -> {
                                        field.setAccessible(true);
                                        try {
                                            return writeObjectAsString(field.get(value));
                                        } catch (IllegalAccessException e) {
                                            throw new JsonParseException(e);
                                        }
                                    }))
                            .entrySet().stream()
                            .map(entry -> entry.getKey() + ":" + entry.getValue())
                            .collect(Collectors.joining(","))
                    + "}";
        return json;
    }

    private String characterToString(char o) {
        if (o == 34) return "\"\\" + o + "\"";
        return o > 32 && o != 92 ? "\"" + o + "\"" : "" + (int)o;
    }

    private String arrayToString(Object[] o) {
        return  "[" +
                Arrays.stream(o)
                        .map(this::writeObjectAsString)
                        .collect(Collectors.joining(","))
                + "]";
    }
    private String mapToString(Map<?, ?> map) {
        return "{" +
                map.entrySet().stream()
                        .map(entry -> this.writeObjectAsString(entry.getKey()) + ":" + this.writeObjectAsString(entry.getValue()))
                        .collect(Collectors.joining(","))
                + "}";
    }
}
