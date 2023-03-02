package ru.clevertec.json.util;

import lombok.*;

@EqualsAndHashCode
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Primitive {

    private static final String HELLO_WORLD = "Hello World";

    private byte aByte;
    private short aShort;
    private char aChar;
    private int anInt;
    private long aLong;
    private float aFloat;
    private double aDouble;
    private String string;

    public byte getaByte() {
        return aByte;
    }

    public short getaShort() {
        return aShort;
    }

    public char getaChar() {
        return aChar;
    }

    public int getAnInt() {
        return anInt;
    }

    public long getaLong() {
        return aLong;
    }

    public float getaFloat() {
        return aFloat;
    }

    public double getaDouble() {
        return aDouble;
    }

    public String getString() {
        return string;
    }

    public static Primitive random() {
        Primitive primitive = new Primitive();
        primitive.aByte = RandomUtil.getByte();
        primitive.aShort = RandomUtil.getShort();
        primitive.aChar = RandomUtil.getChar();
        primitive.anInt = RandomUtil.getInt();
        primitive.aLong = RandomUtil.getLong();
        primitive.aFloat = RandomUtil.getFloat();
        primitive.aDouble = RandomUtil.getDouble();
        primitive.string = RandomUtil.getString();
        return primitive;
    }
}
