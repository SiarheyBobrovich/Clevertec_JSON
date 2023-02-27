package ru.clevertec.json.reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CharacterReaderTest {

    private CharacterReader characterReader;
    private ObjectMapper mapper;
    @BeforeEach
    void setUp() {
        characterReader = new CharacterReader();
        mapper = new ObjectMapper();
    }
    @Test
    void checkWriteObjectAsStringNull() throws JsonProcessingException {
        Character expected = null;
        String actualJson = mapper.writeValueAsString(expected);
        Character actual = characterReader.readStringToObject(actualJson, Character.class);

        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }
    @Test
    void checkWriteObjectAsStringChars() throws JsonProcessingException {
        List<Character> characterList =
                Stream.iterate(0, x -> x < Character.MAX_VALUE, x -> x + 1)
                        .map(integer -> (char) integer.intValue())
                        .toList();
        for (Character character: characterList) {
            String expected = mapper.writeValueAsString(character);
            Character actual = characterReader.readStringToObject(expected, Character.class);

            Assertions.assertThat(character)
                    .isEqualTo(actual);
        }
    }
}
