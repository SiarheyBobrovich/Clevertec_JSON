package ru.clevertec.json.writer.api;

public interface JsonWriter {
    /**
     * Write any object as String
     * @param o Object to write
     * @return object as Json
     */
    String writeObjectAsString(Object o);

}
