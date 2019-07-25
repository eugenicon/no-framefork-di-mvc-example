package com.conference.converter;

import com.conference.Component;

import java.util.List;

@Component
public class ConverterRegistrationBean {
    public ConverterRegistrationBean(ConversionService service, List<Converter> converters) {
        converters.forEach(service::register);
    }
}
