package ru.clevertec.json.mapper;

import ru.clevertec.json.exception.JsonFormatException;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonStringMapper {
    private final String numberFormat = "(([-0-9.eE]+)|(null))";
    private final String characterFormat = "(" +
            "([0-9]{1,5})|" +                   //digit format
            "(\"\\\\[uU][0-9a-fA-F]{4}\")|" +   //Hex format
            "(\"?null\"?)|" +                   //null
            "(\"\\\\[bfnrt\"]\")|" +            //spice format
            "(\".\")" +                         //any symbol
            ")";
    private final String stringFormat = "\"([^\"]*(\"{2})?[^\"]*)*\"";
    private final String arrayFormat = "(\\[{%d}).+?(]{%d})";
    private JsonStringMapper() {
    }
    private static final class InstanceHolder {
        private static final JsonStringMapper instance = new JsonStringMapper();
    }
    public static JsonStringMapper getInstance() {
        return InstanceHolder.instance;
    }

    /**
     * Parse json string for object value and return the value as string
     * @param json the JSON string
     * @throws JsonFormatException if value not found
     * @return value
     */
    public String getObject(String json) {
        if ("null".equals(json)) return "null";
        StringBuilder builder = new StringBuilder();
        int brackets = 0;
        for (char c : json.toCharArray()) {
            if (c == '{') brackets++;
            if (c == '}') brackets--;
            builder.append(c);
            if (brackets == 0) break;
        }

        return builder.toString();
    }

    /**
     * Parse json string for number and return the value as string
     * @param json the JSON string
     * @throws JsonFormatException if value not found
     * @return value of name
     */
    public String getNumber(String json) throws JsonFormatException {
        Pattern numberPattern = Pattern.compile(this.numberFormat);
        return getValue(numberPattern, json);
    }

    /**
     * Parse json string for char and return the value as string
     * @param json the JSON string
     * @return value of name
     * @throws JsonFormatException if value not found
     */
    public String getCharValue(String json) throws JsonFormatException {
        Pattern charPattern = Pattern.compile(this.characterFormat);
        return getValue(charPattern, json);
    }

    /**
     * Parse json string for string value and return the value as string
     * @param json the JSON string
     * @return value of name
     * @throws JsonFormatException if value not found
     */
    public String getStringValue(String json) throws JsonFormatException {
        Pattern stringPattern = Pattern.compile(this.stringFormat);
        return getValue(stringPattern, json);
    }

    /**
     * Parse json string for array value and return the value as string
     * @param json the JSON string
     * @param brackets count brackets
     * @return value of name
     * @throws JsonFormatException if value not found
     */
    public String getArrayString(String json, int brackets) throws JsonFormatException {
        String arrayFormat = String.format(this.arrayFormat, brackets, brackets);
        Pattern arrrayPattern = Pattern.compile(arrayFormat);
        return getValue(arrrayPattern, json);
    }
    private String getValue(Pattern pattern, String json) throws JsonFormatException {
        Matcher matcher = pattern.matcher(json);
        return matcher.results()
                .map(MatchResult::group)
                .findFirst()
                .orElseThrow(() -> new JsonFormatException("Json format error"));
    }
}
