package com.conference.converter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ConversionService {
    private List<ConversionRegistry> conversionRegistries = new ArrayList<>();

    public void register(Converter converter) {
        Method convertMethod = getConvertMethod(converter);
        Class<?> incomingType = convertMethod.getParameterTypes()[0];
        Class<?> returnType = convertMethod.getReturnType();
        conversionRegistries.add(new ConversionRegistry(incomingType, returnType, converter));
    }

    private Method getConvertMethod(Converter converter) {
        return Arrays.stream(converter.getClass().getMethods())
                .filter(m -> m.getName().equals("convert"))
                .filter(m -> !m.getDeclaringClass().equals(Converter.class))
                .findFirst()
                .orElse(null);
    }

    @SuppressWarnings("unchecked")
    public <T, R> R convert(T incomingData, Class<R> returnType) {
        if (incomingData == null || returnType.isInstance(incomingData)) {
            return returnType.cast(incomingData);
        }

        Class<T> incomingType = (Class<T>) incomingData.getClass();
        return conversionRegistries.stream()
                .filter(reg -> reg.incomingType.isAssignableFrom(incomingType))
                .filter(reg -> reg.returnType.isAssignableFrom(returnType))
                .map(reg -> reg.converter.convert(incomingData))
                .map(returnType::cast)
                .filter(Objects::nonNull)
                .findFirst().orElse(null);
    }

    private class ConversionRegistry {
        private Class incomingType;
        private Class returnType;
        private Converter converter;

        private ConversionRegistry(Class incomingType, Class returnType, Converter converter) {
            this.incomingType = incomingType;
            this.returnType = returnType;
            this.converter = converter;
        }
    }
}
