package ru.clevertec.json.reader;

import ru.clevertec.json.exception.JsonParseException;
import ru.clevertec.json.parser.ObjectParser;
import ru.clevertec.json.reader.api.ClassReader;
import ru.clevertec.json.reader.api.ReaderFacade;
import ru.clevertec.json.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
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
                    final Class<?> fieldType = key.getType();
                    final Object valueObj;
                    try {
                        if (!"null".equals(value) && key.getGenericType() instanceof ParameterizedType type) {
                            valueObj = getCollection(fieldType, type, value, reader);
                        }else {
                            valueObj = reader.readStringToObject(value, key.getType());
                        }
                        key.set(instance, valueObj);
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

    private Collection<?> getCollection(Class<?> fieldType,
                                        ParameterizedType type,
                                        String json, ReaderFacade reader) {
        final Class<?> actualTypeArgument = (Class<?>) type.getActualTypeArguments()[0];;
        final Collection<?> collection;
        Object[] array;
        if (ReflectionUtil.isClassInstanceOf(fieldType, List.class)) {
            array = (Object[]) reader.readStringToObject(json, actualTypeArgument.arrayType());
            collection = Arrays.stream(array).collect(Collectors.toList());
        }else if (ReflectionUtil.isClassInstanceOf(fieldType, Set.class)){
            array = (Object[]) reader.readStringToObject(json, actualTypeArgument.arrayType());
            collection = Arrays.stream(array).collect(Collectors.toSet());
        }else {
            throw new JsonParseException("Unsupported type" + fieldType.getName());
        }

        return collection;
    }

    private static final class InstanceHolder {
        private static final ObjectReader instance = new ObjectReader();
    }
    public static ObjectReader getInstance() {
        return ObjectReader.InstanceHolder.instance;
    }
}
