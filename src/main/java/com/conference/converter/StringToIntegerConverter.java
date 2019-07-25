package com.conference.converter;

import com.conference.Component;

@Component
public class StringToIntegerConverter implements Converter<String, Integer> {
    @Override
    public Integer convert(String string) {
        if (string.matches("\\d+")) {
            return Integer.valueOf(string);
        } else {
            return null;
        }
    }
}
