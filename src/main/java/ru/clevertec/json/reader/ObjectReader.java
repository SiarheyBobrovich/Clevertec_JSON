package ru.clevertec.json.reader;

import ru.clevertec.json.exception.JsonParseException;
import ru.clevertec.json.parser.ObjectParser;
import ru.clevertec.json.reader.api.ClassReader;
import ru.clevertec.json.reader.api.ReaderFacade;
import ru.clevertec.json.util.ReflectionUtil;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ObjectReader implements ClassReader {

    private final ObjectParser parser;

    private ObjectReader() {
        this.parser = ObjectParser.getInstance();
    }

    @Override
    public <T> T getObject(String json, Class<T> obj, ReaderFacade reader) {
        if ("null".equals(json) || json == null) return null;
        final Object instance = ReflectionUtil.getInstance(obj);
        final Map<String, String> fieldsValue = parser.parseJson(json);

        ReflectionUtil.getAllDeclaredFields(obj).stream()
                .filter(f -> fieldsValue.containsKey(f.getName()))
                .map(f -> {
                    f.setAccessible(true);
                    return f;
                })
                .collect(Collectors.toMap(f -> f, f -> fieldsValue.get(f.getName())))
                .forEach((key, value) -> {
                    try {
                        key.set(instance, reader.readStringToObject(value, key.getType()));
                    } catch (IllegalAccessException ex) {
                        throw new JsonParseException(ex);
                    }
                });
        return (T) instance;
    }
    @Override
    public boolean canReadClass(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredConstructors())
                .anyMatch(c -> c.getParameterCount() == 0);
    }


    private static final class InstanceHolder {
        private static final ObjectReader instance = new ObjectReader();
    }
    public static ObjectReader getInstance() {
        return ObjectReader.InstanceHolder.instance;
    }
}
