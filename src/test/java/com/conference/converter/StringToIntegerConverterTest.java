package com.conference.converter;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringToIntegerConverterTest {
    private StringToIntegerConverter converter = new StringToIntegerConverter();

    @ParameterizedTest(name = "convert({0}) = {1}")
    @MethodSource("testData")
    void convert(String incomingString, Integer expected) {
        Integer actual = converter.convert(incomingString);
        assertEquals(expected, actual);
    }

    static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of("1", 1),
                Arguments.of("42", 42),
                Arguments.of("0", 0),
                Arguments.of("", null),
                Arguments.of("0d", null),
                Arguments.of(null, null)
        );
    }
}