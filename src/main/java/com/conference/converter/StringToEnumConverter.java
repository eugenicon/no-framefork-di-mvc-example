package com.conference.converter;

import com.conference.Component;

import java.util.Arrays;

@Component
public class StringToEnumConverter implements Converter<String, Enum> {
    @Override
    public Enum convert(String string) {
        return null;
    }

    @Override
    public <T extends Enum> T convert(String s, Class<T> returnType) {
        return Arrays.stream(returnType.getEnumConstants())
                .filter(value -> value.name().equalsIgnoreCase(s))
                .findFirst().orElse(null);
    }
}
