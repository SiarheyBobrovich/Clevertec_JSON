package ru.clevertec.json;

import ru.clevertec.json.api.JsonMapper;
import ru.clevertec.json.reader.ReaderFacadeImpl;
import ru.clevertec.json.reader.api.ReaderFacade;
import ru.clevertec.json.writer.JsonWriterImpl;
import ru.clevertec.json.writer.api.JsonWriter;

public class JsonMapperImpl implements JsonMapper {

    private final JsonWriter jsonWriter;
    private final ReaderFacade readerFacade;

    public JsonMapperImpl() {
        jsonWriter = new JsonWriterImpl();
        readerFacade = new ReaderFacadeImpl();
    }

    @Override
    public String writeObjectAsString(Object value) {
        return jsonWriter.writeObjectAsString(value);
    }

    @Override
    public <T> T readStringToObject(String json, Class<T> obj) {
        return readerFacade.readStringToObject(json, obj);
    }
}
