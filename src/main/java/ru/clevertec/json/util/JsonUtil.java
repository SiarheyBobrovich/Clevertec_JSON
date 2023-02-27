package ru.clevertec.json.util;

import java.util.concurrent.atomic.AtomicInteger;

public class JsonUtil {

    /**
     * Convert HEX value of a number to decimal value
     * @param s HEX value
     * @return decimal
     */
    public static int hexToInt(String s) {
        AtomicInteger pow = new AtomicInteger();
        return new StringBuilder(s.toLowerCase()).reverse().chars()
                .map(c ->
                    c == 'a' ? 10 :
                    c == 'b' ? 11 :
                    c == 'c' ? 12 :
                    c == 'd' ? 13 :
                    c == 'e' ? 14 :
                    c == 'f' ? 15 : c - '0')
                 .map(i -> (int) (Math.pow(16.0, 0.0 + pow.getAndIncrement()) * i))
                 .sum();
    }
}
