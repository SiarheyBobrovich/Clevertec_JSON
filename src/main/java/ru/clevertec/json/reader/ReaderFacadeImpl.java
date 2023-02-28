package ru.clevertec.json.reader;

import ru.clevertec.json.exception.JsonParseException;
import ru.clevertec.json.reader.api.ReaderFacade;
import ru.clevertec.json.reader.api.ClassReader;

import java.util.ArrayList;
import java.util.List;

public class ReaderFacadeImpl implements ReaderFacade {
    private final List<ClassReader> classReaders;

    public ReaderFacadeImpl() {
        classReaders = new ArrayList<>();
        classReaders.add(NumericReader.getInstance());
        classReaders.add(CharacterReader.getInstance());
        classReaders.add(StringReader.getInstance());
        classReaders.add(ArrayReader.getInstance());
        classReaders.add(ObjectReader.getInstance());
    }

    @Override
    public <T> T readStringToObject(String json, Class<T> objClass) {
        if ("null".equals(json)) return null;
        return classReaders.stream()
                .filter(classReader -> classReader.canReadClass(objClass))
                .findFirst()
                .map(classReader -> classReader.getObject(json, objClass, this))
                .orElseThrow(() -> new JsonParseException(String.format("Can't read %s", objClass.getName())));
    }
}
