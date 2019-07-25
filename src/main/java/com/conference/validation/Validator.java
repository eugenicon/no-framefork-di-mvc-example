package com.conference.validation;

import java.lang.annotation.Annotation;

public interface Validator<A extends Annotation, T> {
    boolean isValid(T data, A annotation);

    String getErrorMessage(A annotation);

    Class<A> getSupportedType();
}