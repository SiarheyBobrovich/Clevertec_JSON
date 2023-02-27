package ru.clevertec.json.reader;

import ru.clevertec.json.exception.JsonParseException;
import ru.clevertec.json.parser.ObjectParser;
import ru.clevertec.json.reader.api.JsonReader;
import ru.clevertec.json.reader.api.PrimitiveReader;
import ru.clevertec.json.util.ReflectionUtil;

import java.lang.reflect.Array;
import java.util.Map;
import java.util.Arrays;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JsonReaderImpl implements JsonReader {
    private final PrimitiveReader<Character> characterJsonParser;
    private final PrimitiveReader<Number> numberJsonParser;
    private final ObjectParser parser;

    public JsonReaderImpl() {
        this.numberJsonParser = new NumericReader();
        this.characterJsonParser = new CharacterReader();
        this.parser = new ObjectParser();
    }

    @Override
    public <T> T readStringToObject(String json, Class<T> objClass) {
        if ("null".equals(json))
            return null;
        if (ReflectionUtil.isNumber(objClass))
            return (T) numberJsonParser.readStringToObject(json, (Class<? extends Number>) objClass);
        if (objClass == Character.class || objClass == char.class)
            return (T) characterJsonParser.readStringToObject(json, (Class<Character>) objClass);
        if (objClass == String.class)
            return objClass.cast(json.substring(1, json.length() - 1));
        if (objClass.isArray())
            return getArray(json, objClass);

        return getObject(json, objClass);
    }

    <T> T getObject(String json, Class<T> objClass) {
        final Object instance = ReflectionUtil.getInstance(objClass);
        final Map<String, String> stringStringMap = parser.parseJson(json);

        ReflectionUtil.getAllDeclaredFields(objClass).stream()
                .filter(f -> stringStringMap.containsKey(f.getName()))
                .map(f -> {
                    f.setAccessible(true);
                    return f;
                })
                .collect(Collectors.toMap(f -> f, f -> stringStringMap.get(f.getName())))
                .forEach((key, value) -> {
                    try {
                        key.set(instance, readStringToObject(value, key.getType()));
                    } catch (IllegalAccessException ex) {
                        throw new JsonParseException(ex);
                    }
                });
        return (T) instance;
    }

    private <T> T getArray(String json, Class<T> obj) {
        final Class<?> componentType = obj.getComponentType();
        final String subJson;
        final Pattern pattern;
        if (componentType.isArray()) {
            final int bracketsCount = ReflectionUtil.getArrayIndex(obj) - 1;
            subJson = json.substring(1);
            pattern = Pattern.compile("(\\[{" + bracketsCount + "}).+?(]{" + bracketsCount + "})");

            return obj.cast(getObjectsOrArraysArray(pattern, subJson, componentType));
        } else if (!(ReflectionUtil.isPrimitive(componentType) || componentType == String.class)) {
            subJson = json.substring(1, json.length() - 1);
            pattern = Pattern.compile("\\{.+?}");

            return obj.cast(getObjectsOrArraysArray(pattern, subJson, componentType));
        } else {
            subJson = json.replaceAll("[\\[\\]]", "");
            final String[] array = subJson.split(",");
            return obj.cast(
                    Arrays.stream(array)
                            .map(s -> readStringToObject(s, componentType))
                            .toList()
                            .toArray((T[]) Array.newInstance(componentType, array.length)));
        }
    }

    private  <T> T getObjectsOrArraysArray(Pattern pattern, String subJson, Class<T> componentType) {
        final Matcher matcher = pattern.matcher(subJson);
        final int length = (int) matcher.results().count();
        matcher.reset();
        final T[] array = matcher.results()
                .map(MatchResult::group)
                .map(r -> readStringToObject(r, componentType))
                .toList().toArray((T[]) Array.newInstance(componentType, length));
        return (T) array;
    }
}
