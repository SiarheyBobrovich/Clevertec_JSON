package ru.clevertec.json.reader;

import ru.clevertec.json.reader.api.ClassReader;
import ru.clevertec.json.reader.api.ReaderFacade;

public class StringReader implements ClassReader {
    @Override
    public <T> T getObject(String json, Class<T> obj, ReaderFacade reader) {
        if ("null".equals(json) || json == null) return null;
        return obj.cast(json.substring(1, json.length() - 1));
    }
    @Override
    public boolean canReadClass(Class<?> clazz) {
        return clazz == String.class;
    }

    private static final class InstanceHolder {
        private static final StringReader instance = new StringReader();
    }
    public static StringReader getInstance() {
        return StringReader.InstanceHolder.instance;
    }
}
