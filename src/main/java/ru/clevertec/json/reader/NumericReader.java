package ru.clevertec.json.reader;

import ru.clevertec.json.exception.JsonParseException;
import ru.clevertec.json.reader.api.PrimitiveReader;

public class NumericReader implements PrimitiveReader<Number> {
    @Override
    public Number readStringToObject(String json, Class<? extends Number> obj) {
        if ("null".equals(json) || json == null) return null;
        if (obj == byte.class || obj == Byte.class) return Byte.parseByte(json);
        if (obj == short.class || obj == Short.class) return Short.parseShort(json);
        if (obj == int.class || obj == Integer.class) return Integer.parseInt(json);
        if (obj == long.class || obj == Long.class) return Long.parseLong(json);
        if (obj == float.class || obj == Float.class) return Float.parseFloat(json);
        if (obj == double.class || obj == Double.class) return Double.parseDouble(json);
        throw new JsonParseException("Class must be not null");
    }
}
