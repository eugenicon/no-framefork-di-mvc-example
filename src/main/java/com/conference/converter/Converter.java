package com.conference.converter;

public interface Converter<T, R> {
    R convert(T t);

    default <S extends R> S convert(T t, Class<S> returnType) {
        return returnType.cast(convert(t));
    }
}
