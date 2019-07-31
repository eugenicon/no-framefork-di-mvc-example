package com.conference.converter;

import com.conference.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class StringToDateConverter implements Converter<String, Date> {
    private final DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    @Override
    public Date convert(String string) {
        if (string == null) {
            return null;
        }
        try {
            if (string.length() == 5) {
                return format.parse("01/01/0001 " + string);
            } else {
                return format.parse(string);
            }
        } catch (Exception e) {
            return null;
        }
    }
}
