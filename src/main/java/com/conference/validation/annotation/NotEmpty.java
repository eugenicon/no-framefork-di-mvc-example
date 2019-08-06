package com.conference.validation.annotation;

import com.conference.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NotEmpty {
    String value();

    @Component
    class NotEmptyProcessor implements ValidationAnnotationProcessor<NotEmpty, Object> {

        @Override
        public boolean isValid(Object data, NotEmpty annotation) {
            if (data instanceof Collection) {
                return !((Collection) data).isEmpty();
            }
            return data != null && !String.valueOf(data).matches("\\s*");
        }

        @Override
        public String getErrorMessage(NotEmpty annotation) {
            return annotation.value();
        }

        @Override
        public Class<NotEmpty> getSupportedType() {
            return NotEmpty.class;
        }
    }
}
