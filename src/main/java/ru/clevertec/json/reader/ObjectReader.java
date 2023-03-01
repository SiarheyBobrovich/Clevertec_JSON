package ru.clevertec.json.reader;

import ru.clevertec.json.exception.JsonParseException;
import ru.clevertec.json.parser.ObjectParser;
import ru.clevertec.json.reader.api.ClassReader;
import ru.clevertec.json.reader.api.ReaderFacade;
import ru.clevertec.json.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
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
        final Map<Field, String> fields = ReflectionUtil.getAllDeclaredFields(obj).stream()
                .filter(f -> fieldsValue.containsKey(f.getName()))
                .map(f -> {
                    f.setAccessible(true);
                    return f;
                })
                .collect(Collectors.toMap(
                        f -> f,
                        f -> fieldsValue.get(f.getName())));

        fields.entrySet().stream()
                .filter(e -> !"null".equals(e.getValue()))
                .filter(e -> e.getKey().getGenericType() instanceof ParameterizedType)
                .filter(e -> ReflectionUtil.isClassInstanceOf(e.getKey().getType(), Collection.class))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                .forEach((key, value) -> {
                    final Class<?> fieldType = key.getType();
                    final ParameterizedType genericType = (ParameterizedType)key.getGenericType();
                    final Object valueObj;
                    valueObj = getCollection(fieldType, genericType, value, reader);
                    setFieldObject(key, instance, valueObj);
                });

        fields.entrySet().stream()
                .filter(e -> ReflectionUtil.isClassInstanceOf(e.getKey().getType(), Map.class))
                .filter(e -> !"null".equals(e.getValue()))
                .filter(e -> e.getKey().getGenericType() instanceof ParameterizedType)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                .forEach((key, value) -> {
                    final ParameterizedType genericType = (ParameterizedType)key.getGenericType();
                    final Object valueObj;
                    valueObj = getMap(genericType, value, reader);
                    setFieldObject(key, instance, valueObj);
                });

        fields.entrySet().stream()
                .filter(e -> !(e.getKey().getGenericType() instanceof ParameterizedType))
                .forEach(fieldJson -> {
                    final Class<?> fieldType = fieldJson.getKey().getType();
                    final String fieldJsonValue = fieldJson.getValue();
                    final Object valueObj = reader.readStringToObject(fieldJsonValue, fieldType);
                    setFieldObject(fieldJson.getKey(), instance, valueObj);
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
        final Class<?> actualTypeArgument = (Class<?>) type.getActualTypeArguments()[0];
        final Collection<?> collection;
        Object[] array;
        if (ReflectionUtil.isClassInstanceOf(fieldType, List.class)) {
            array = (Object[]) reader.readStringToObject(json, actualTypeArgument.arrayType());
            collection = Arrays.stream(array).collect(Collectors.toList());
        }else if (ReflectionUtil.isClassInstanceOf(fieldType, Set.class)){
            array = (Object[]) reader.readStringToObject(json, actualTypeArgument.arrayType());
            collection = Arrays.stream(array).collect(Collectors.toSet());
        }else {
            throw new JsonParseException("Unsupported type: " + fieldType.getName());
        }

        return collection;
    }

    private Map<?,?> getMap(ParameterizedType type,
                            String json, ReaderFacade reader) {
        final Class<?> keyType = (Class<?>) type.getActualTypeArguments()[0];
        final Class<?> valueType = (Class<?>) type.getActualTypeArguments()[1];
        final Map<Object, Object> map = new HashMap<>();
        final Map<String, String> keyValueMap = parser.parseJson(json);

        keyValueMap.forEach((k, v) -> {
            if (!ReflectionUtil.isNumber(keyType)) {
                k = "\"" + k + "\"";
            }

            Object key = reader.readStringToObject(k, keyType);
            Object value = reader.readStringToObject(v, valueType);
            map.put(key, value);
        });

        return map;
    }

    private void setFieldObject(Field field, Object instance, Object value) {
        try {
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            throw new JsonParseException(e);
        }
    }

    private static final class InstanceHolder {
        private static final ObjectReader instance = new ObjectReader();
    }
    public static ObjectReader getInstance() {
        return ObjectReader.InstanceHolder.instance;
    }
}
