package com.conference.validation;

import com.conference.Component;
import com.conference.util.Reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        for (Field field : data.getClass().getDeclaredFields()) {
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                if (validators.containsKey(annotation.annotationType())) {
                    Validator<Annotation, Object> validator = validators.get(annotation.annotationType());
                    Object value = Reflection.getFieldValue(data, field);
                    if (!validationResult.hasError(field.getName()) && !validator.isValid(value, annotation)) {
                        String errorMessage = validator.getErrorMessage(annotation);
                        validationResult.add(field.getName(), errorMessage);
                    }
                }
            }
        }
        return validationResult;
    }
}
