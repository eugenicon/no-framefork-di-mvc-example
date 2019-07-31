package com.conference.validation;

import com.conference.Component;

import java.lang.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface ValidData {
    String field() default "";
    Class<? extends Validator> validator();
    String message();

    @Component
    class AnnotationValidationProcessor implements Validator<ValidData, Object> {
        private final Map<Object, Validator<Annotation, Object>> validators;

        public AnnotationValidationProcessor(List<Validator<Annotation, Object>> validators) {
            this.validators = validators.stream().collect(Collectors.toMap(Validator::getClass, Function.identity()));
        }

        @Override
        public Class<ValidData> getSupportedType() {
            return ValidData.class;
        }

        @Override
        public boolean isValid(Object data, ValidData annotation) {
            Validator<Annotation, Object> validator = validators.get(annotation.validator());
            return validator == null || validator.isValid(data, annotation);
        }

        @Override
        public String getErrorMessage(ValidData annotation) {
            Validator<Annotation, Object> validator = validators.get(annotation.validator());
            return validator != null ? validator.getErrorMessage(annotation) : "";
        }
    }
}
