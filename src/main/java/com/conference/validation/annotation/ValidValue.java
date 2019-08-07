package com.conference.validation.annotation;

import com.conference.Component;
import com.conference.validation.validator.Validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidValue {
    Class<? extends Validator> validator();
    String message();

    @Component
    class ValidDataProcessor implements ValidationAnnotationProcessor<ValidValue, Object> {
        private final Map<Class, Validator<Object>> validators;

        public ValidDataProcessor(List<Validator<Object>> validators) {
            this.validators = validators.stream().collect(Collectors.toMap(Validator::getClass, Function.identity()));
        }

        @Override
        public Class<ValidValue> getSupportedType() {
            return ValidValue.class;
        }

        @Override
        public boolean isValid(Object data, ValidValue annotation) {
            Validator<Object> validator = validators.get(annotation.validator());
            return validator == null || validator.isValid(data);
        }

        @Override
        public String getErrorMessage(ValidValue annotation) {
            return annotation.message();
        }
    }
}
