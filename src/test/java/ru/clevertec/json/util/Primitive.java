package ru.clevertec.json.util;

import java.util.Objects;

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

    protected Primitive(){}
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

    public byte getaByte() {
        return aByte;
    }

    public void setaByte(byte aByte) {
        this.aByte = aByte;
    }

    public short getaShort() {
        return aShort;
    }

    public void setaShort(short aShort) {
        this.aShort = aShort;
    }

    public char getaChar() {
        return aChar;
    }

    public void setaChar(char aChar) {
        this.aChar = aChar;
    }

    public int getAnInt() {
        return anInt;
    }

    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }

    public long getaLong() {
        return aLong;
    }

    public void setaLong(long aLong) {
        this.aLong = aLong;
    }

    public float getaFloat() {
        return aFloat;
    }

    public void setaFloat(float aFloat) {
        this.aFloat = aFloat;
    }

    public double getaDouble() {
        return aDouble;
    }

    public void setaDouble(double aDouble) {
        this.aDouble = aDouble;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Primitive primitive)) return false;
        return aByte == primitive.aByte && aShort == primitive.aShort && aChar == primitive.aChar && anInt == primitive.anInt && aLong == primitive.aLong && Float.compare(primitive.aFloat, aFloat) == 0 && Double.compare(primitive.aDouble, aDouble) == 0 && Objects.equals(string, primitive.string);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aByte, aShort, aChar, anInt, aLong, aFloat, aDouble, string);
    }

    @Override
    public String toString() {
        return "Primitive{" +
                  "aByte=" + aByte +
                ", aShort=" + aShort +
                ", aChar=" + aChar +
                ", anInt=" + anInt +
                ", aLong=" + aLong +
                ", aFloat=" + aFloat +
                ", aDouble=" + aDouble +
                ", string='" + string + '\'' +
                '}';
    }
}
