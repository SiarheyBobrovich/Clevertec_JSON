package ru.clevertec.json.reader;

import ru.clevertec.json.exception.JsonParseException;
import ru.clevertec.json.reader.api.ClassReader;
import ru.clevertec.json.reader.api.ReaderFacade;
import ru.clevertec.json.util.ReflectionUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class NumericReader implements ClassReader {
    private NumericReader() {}

    @Override
    public <T> T getObject(String json, Class<T> obj, ReaderFacade reader) {
        if ("null".equals(json) || json == null) return null;
        final Number number;

        if (obj == byte.class || obj == Byte.class) number = Byte.parseByte(json);
        else if (obj == short.class || obj == Short.class) number = Short.parseShort(json);
        else if (obj == int.class || obj == Integer.class) number = Integer.parseInt(json);
        else if (obj == long.class || obj == Long.class) number = Long.parseLong(json);
        else if (obj == float.class || obj == Float.class) number = Float.parseFloat(json);
        else if (obj == double.class || obj == Double.class) number = Double.parseDouble(json);
        else if (obj == BigDecimal.class) number = new BigDecimal(json);
        else if (obj == BigInteger.class) number = new BigInteger(json);
        else if (obj == AtomicInteger.class) number = new AtomicInteger(Integer.parseInt(json));
        else if (obj == AtomicLong.class) number = new AtomicLong(Long.parseLong(json));
        else throw new JsonParseException(String.format("%s not supported", obj));

        return (T) number;
    }
    @Override
    public boolean canReadClass(Class<?> clazz) {
        return ReflectionUtil.isNumber(clazz);
    }

    private static final class InstanceHolder {
        private static final NumericReader instance = new NumericReader();
    }
    public static NumericReader getInstance() {
        return NumericReader.InstanceHolder.instance;
    }
}
