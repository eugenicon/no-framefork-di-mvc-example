package com.conference.validation;

import com.conference.Component;
import com.conference.util.Reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Component
public class ValidationService {
    private final Map<Class<? extends Annotation>, Validator<Annotation, Object>> validators = new HashMap<>();

    public ValidationService(List<Validator<Annotation, Object>> validators) {
        for (Validator<Annotation, Object> validator : validators) {
            this.validators.put(validator.getSupportedType(), validator);
        }
    }

    public <T> ValidationResult validate(T data) {
        ValidationResult validationResult = new ValidationResult();
        ValidData validAnnotation = data.getClass().getAnnotation(ValidData.class);
        if (validAnnotation != null) {
            validate(validationResult, validAnnotation, () -> data, validAnnotation.field());
        }
        for (Field field : data.getClass().getDeclaredFields()) {
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                validate(validationResult, annotation, () -> Reflection.getFieldValue(data, field), field.getName());
            }
        }
        return validationResult;
    }

    private <T> void validate(ValidationResult validationResult, Annotation annotation, Supplier<T> value, String fieldName) {
        if (validators.containsKey(annotation.annotationType())) {
            Validator<Annotation, Object> validator = validators.get(annotation.annotationType());
            if (!validationResult.hasError(fieldName) && !validator.isValid(value.get(), annotation)) {
                String errorMessage = validator.getErrorMessage(annotation);
                validationResult.add(fieldName, errorMessage);
            }
        }
    }
}
