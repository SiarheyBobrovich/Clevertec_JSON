package ru.clevertec.json.exception;

public class JsonParseException extends RuntimeException {
    public JsonParseException(Exception e) {
        super(e);
    }
    public JsonParseException(String message) {
        super(message);
    }
}
