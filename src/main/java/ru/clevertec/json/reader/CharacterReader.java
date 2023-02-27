package ru.clevertec.json.reader;

import ru.clevertec.json.reader.api.PrimitiveReader;
import ru.clevertec.json.util.JsonUtil;

import java.util.Map;

public class CharacterReader implements PrimitiveReader<Character> {
    private final Map<String, Character> chars = Map.of(
            "\\b", '\b',
            "\\t", '\t',
            "\\n", '\n',
            "\\f", '\f',
            "\\r", '\r',
            "\\\"", '"'
    );
    @Override
    public Character readStringToObject(String json, Class<? extends Character> obj) {
        if ("null".equals(json) || json == null) return null;
        final String jsonWithoutBrackets = json.substring(1, json.length() - 1);
        if (chars.containsKey(jsonWithoutBrackets)) return chars.get(jsonWithoutBrackets);
        if (jsonWithoutBrackets.startsWith("\\") && jsonWithoutBrackets.length() > 2)
            return (char) JsonUtil.hexToInt(jsonWithoutBrackets.substring(2));
        if (json.matches("\\d{1,5}")) return (char) Integer.parseInt(json);
        return jsonWithoutBrackets.charAt(jsonWithoutBrackets.length() - 1);
    }
}
