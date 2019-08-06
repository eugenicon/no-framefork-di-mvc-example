package com.conference.validation.annotation;

import java.lang.annotation.Annotation;

public interface ValidationAnnotationProcessor<A extends Annotation, T> {
    boolean isValid(T data, A annotation);

    String getErrorMessage(A annotation);

    Class<A> getSupportedType();
}