package ru.clevertec.json.reader;

import ru.clevertec.json.reader.api.ClassReader;
import ru.clevertec.json.reader.api.ReaderFacade;
import ru.clevertec.json.util.ReflectionUtil;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArrayReader implements ClassReader {

    private ArrayReader() {}
    private static final class InstanceHolder {
        private static final ArrayReader instance = new ArrayReader();
    }
    public static ArrayReader getInstance() {
        return ArrayReader.InstanceHolder.instance;
    }

    @Override
    public <T> T getObject(String json, Class<T> obj, ReaderFacade reader) {
        if ("null".equals(json) || json == null) return null;
        final Class<?> componentType = obj.getComponentType();
        final String subJson;
        final Matcher matcher;
        final T result;

        if (componentType.isArray()) {
            final int bracketsCount = ReflectionUtil.getArrayIndex(obj) - 1;
            subJson = json.substring(1);
            matcher = Pattern.compile("(\\[{" + bracketsCount + "}).+?(]{" + bracketsCount + "})")
                    .matcher(subJson);

            result = obj.cast(getObjectsOrArraysArray(matcher, componentType, reader));
        } else if (!(ReflectionUtil.isPrimitive(componentType) || componentType == String.class)) {
            subJson = json.substring(1, json.length() - 1);
            matcher = Pattern.compile("\\{.+?}").matcher(subJson);

            return obj.cast(getObjectsOrArraysArray(matcher, componentType, reader));
        } else {
            subJson = json.replaceAll("[\\[\\]]", "");
            final String[] array = subJson.split(",");
            result = obj.cast(
                    Arrays.stream(array)
                            .map(s -> reader.readStringToObject(s, componentType))
                            .toList()
                            .toArray(getArrayInstance(componentType, array.length)));
        }

        return result;
    }
    private <T> T getObjectsOrArraysArray(Matcher matcher,
                                          Class<T> componentType,
                                          ReaderFacade reader) {
        final int length = (int) matcher.results().count();
        matcher.reset();
        final T[] array = matcher.results()
                .map(MatchResult::group)
                .map(r -> reader.readStringToObject(r, componentType))
                .toList().toArray(getArrayInstance(componentType, length));
        return (T) array;
    }
    private <T> T[] getArrayInstance(Class<T> componentType, int length) {
         return (T[]) Array.newInstance(componentType, length);
    }
    @Override
    public boolean canReadClass(Class<?> clazz) {
        return clazz.isArray();
    }
}
