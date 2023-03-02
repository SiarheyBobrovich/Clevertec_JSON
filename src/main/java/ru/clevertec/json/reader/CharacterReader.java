package ru.clevertec.json.reader;

import ru.clevertec.json.reader.api.ClassReader;
import ru.clevertec.json.reader.api.ReaderFacade;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class CharacterReader implements ClassReader {
    private final Map<String, Character> chars = Map.of(
            "\\b", '\b',
            "\\t", '\t',
            "\\n", '\n',
            "\\f", '\f',
            "\\r", '\r',
            "\\\"", '"'
    );

    private CharacterReader() {}
    private static final class InstanceHolder {
        private static final CharacterReader instance = new CharacterReader();
    }
    public static CharacterReader getInstance() {
        return CharacterReader.InstanceHolder.instance;
    }

    @Override
    public <T> T getObject(String json, Class<T> obj, ReaderFacade reader) {
        if ("null".equals(json) || json == null) return null;
        final Character result;
        final String jsonWithoutBrackets = json.length() == 1 ? json : json.substring(1, json.length() - 1);

        if (isSpace(jsonWithoutBrackets))
            result = chars.get(jsonWithoutBrackets);
        else if (jsonWithoutBrackets.startsWith("\\") && jsonWithoutBrackets.length() > 2)
            result = hexToInt(jsonWithoutBrackets.substring(2));
        else if (json.matches("\\d{1,5}"))
            result = (char) Integer.parseInt(json);
        else result = jsonWithoutBrackets.charAt(jsonWithoutBrackets.length() - 1);

        return (T) result;
    }
    @Override
    public boolean canReadClass(Class<?> clazz) {
        return clazz == Character.class || clazz == char.class;
    }
    private boolean isSpace(String s) {
        return chars.containsKey(s);
    }

    /**
     * Convert HEX value of a number to decimal value
     * @param s HEX value
     * @return decimal
     */
    public char hexToInt(String s) {
        AtomicInteger pow = new AtomicInteger();
        return (char) new StringBuilder(s.toLowerCase()).reverse().chars()
                .map(c ->
                    c == 'a' ? 10 :
                    c == 'b' ? 11 :
                    c == 'c' ? 12 :
                    c == 'd' ? 13 :
                    c == 'e' ? 14 :
                    c == 'f' ? 15 :
                    c - '0')
                .map(i -> (int) (Math.pow(16.0, 0.0 + pow.getAndIncrement()) * i))
                .sum();
    }
}
