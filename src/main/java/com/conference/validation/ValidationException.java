package com.conference.validation;

public class ValidationException extends Exception {
    private final ValidationResult validationResult;
    private final Object value;

    public ValidationException(ValidationResult validationResult, Object value) {
        super("Invalid data");
        this.validationResult = validationResult;
        this.value = value;
    }

    public ValidationResult getValidationResult() {
        return validationResult;
    }

    public <T> T getValue() {
        return (T) value;
    }
}
