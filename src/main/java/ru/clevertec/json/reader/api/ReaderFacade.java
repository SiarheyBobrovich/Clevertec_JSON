package ru.clevertec.json.reader.api;

public interface ReaderFacade {
    /**
     * Read a json and create object
     * @param json Object as JSON
     * @param obj object class
     * @return new instance of object
     * @param <T> Object type
     */
    <T> T readStringToObject(String json, Class<T> obj);
}
