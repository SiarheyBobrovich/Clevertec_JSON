package ru.clevertec.json.writer;

import ru.clevertec.json.util.ReflectionUtil;
import ru.clevertec.json.writer.api.JsonWriter;
import ru.clevertec.json.exception.JsonParseException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonWriterImpl implements JsonWriter {

    @Override
    public String writeObjectAsString(Object obj) {
        if (obj == null) return "null";
        final Class<?> objClass = obj.getClass();
        final String json;

        if (ReflectionUtil.isNumber(obj)) json  = String.valueOf(obj);
        else if (objClass == Character.class) json = characterToString((char)obj);
        else if (objClass == String.class) json = "\"" + obj + "\"";
        else if (objClass.isArray()) json = arrayToString((Object[]) obj);
        else if (ReflectionUtil.isClassInstanceOf(objClass, Collection.class)) json = arrayToString(((Collection<?>) obj).toArray());
        else if (ReflectionUtil.isClassInstanceOf(objClass, Map.class)) json = mapToString((Map<?, ?>) obj);
        else json = "{" +
                    ReflectionUtil.getAllDeclaredFields(objClass).stream()
                            .filter(f -> !Modifier.isStatic(f.getModifiers()))
                            .collect(Collectors.toMap(
                                    field -> "\"" + field.getName() + "\"",
                                    field -> {
                                        field.setAccessible(true);
                                        Object fieldValue = getFieldValue(field, obj);
                                        return writeObjectAsString(fieldValue);
                                    }))
                            .entrySet().stream()
                            .map(entry -> entry.getKey() + ":" + entry.getValue())
                            .collect(Collectors.joining(","))
                    + "}";
        return json;
    }

    private Object getFieldValue(Field field, Object obj) {
        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            throw new JsonParseException(e);
        }
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
                        .map(entry -> {
                            final Object key = entry.getKey();
                            final boolean isNotNum = !ReflectionUtil.isNumber(key);
                            String keyString = this.writeObjectAsString(key);
                            String valueString = this.writeObjectAsString(entry.getValue());
                            return new StringBuilder()
                                    .append(keyString)
                                    .insert(0, isNotNum ? "" : "\"")
                                    .append(isNotNum ? "" : "\"")
                                    .append(":")
                                    .append(valueString).toString();
                        }).collect(Collectors.joining(","))
                + "}";
    }
}
