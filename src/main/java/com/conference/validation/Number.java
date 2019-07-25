package com.conference.validation;

import com.conference.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Number {
    int min() default Integer.MIN_VALUE;
    int max() default Integer.MAX_VALUE;
    String message();

    @Component
    class AnnotationValidationProcessor implements Validator<Number, Integer> {

        @Override
        public boolean isValid(Integer data, Number annotation) {
            return data != null && data <= annotation.max() && data >= annotation.min();
        }

        @Override
        public String getErrorMessage(Number annotation) {
            return annotation.message();
        }

        @Override
        public Class<Number> getSupportedType() {
            return Number.class;
        }
    }
}
