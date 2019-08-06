package com.conference.validation.validator;

public interface Validator<T> {
    boolean isValid(T data);
}