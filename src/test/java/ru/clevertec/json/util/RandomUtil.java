package ru.clevertec.json.util;

import java.util.Random;

public class RandomUtil {
    private static final Random RANDOM = new Random();
    private static final String[] STRINGS = {"first", "second", "t h i r d", "f o r th", "five", "SIX", "S-E-V-E-N"};

    private RandomUtil() {
    }

    public static byte getByte() {
        return  (byte)RANDOM.nextInt(128);
    }

    public static short getShort() {
        return (short)RANDOM.nextInt(Short.MAX_VALUE);
    }

    public static char getChar() {
        return (char)RANDOM.nextInt( 0, Character.MAX_VALUE);
    }

    public static int getInt() {
        return RANDOM.nextInt(Integer.MAX_VALUE);
    }

    public static long getLong() {
        return RANDOM.nextLong(Long.MAX_VALUE);
    }

    public static float getFloat() {
        return RANDOM.nextFloat(Float.MAX_VALUE);
    }

    public static double getDouble() {
        return RANDOM.nextDouble(Double.MAX_VALUE);
    }

    public static String getString() {
        return STRINGS[RANDOM.nextInt(STRINGS.length)];
    }
}
