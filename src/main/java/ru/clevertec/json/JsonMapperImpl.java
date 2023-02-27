package ru.clevertec.json;

import ru.clevertec.json.api.JsonMapper;
import ru.clevertec.json.reader.JsonReaderImpl;
import ru.clevertec.json.reader.api.JsonReader;
import ru.clevertec.json.writer.JsonWriterImpl;
import ru.clevertec.json.writer.api.JsonWriter;

public class JsonMapperImpl implements JsonMapper {

    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    public JsonMapperImpl() {
        jsonWriter = new JsonWriterImpl();
        jsonReader = new JsonReaderImpl();
    }

    @Override
    public String writeObjectAsString(Object value) {
        return jsonWriter.writeObjectAsString(value);
    }

    @Override
    public <T> T readStringToObject(String json, Class<T> obj) {
        return jsonReader.readStringToObject(json, obj);
    }
}
