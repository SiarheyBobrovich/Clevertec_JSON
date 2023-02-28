package ru.clevertec.json.reader.api;

public interface ClassReader {
    /**
     * Read Json and create new instance of class
     * @param json - instance format
     * @param obj Instance class
     * @param reader the json reader
     * @return new instance of object
     */
    <T> T getObject(String json, Class<T> obj, ReaderFacade reader);

    /**
     * Check class that this reader can read(parse)
     * @param clazz current class to check
     * @return cheking result
     */
    boolean canReadClass(Class<?> clazz);
}
