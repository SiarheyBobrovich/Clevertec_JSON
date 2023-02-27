package ru.clevertec.json.reader.api;

public interface PrimitiveReader<T> {
    /**
     * Read primitive Json and create new instance
     * @param json - instance format
     * @param obj Instance class
     * @return new instance of object
     */
    T readStringToObject(String json, Class<? extends T> obj);
}
