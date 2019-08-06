package com.conference.validation.annotation;

import com.conference.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Matches {
    String regex();
    String message();

    String LETTERS_ONLY = "[a-zA-Z\\s]+";
    String EMAIL = "\\S+@\\S+\\.\\S+";

    @Component
    class MatchesProcessor implements ValidationAnnotationProcessor<Matches, String> {

        @Override
        public boolean isValid(String data, Matches annotation) {
            return data != null && data.matches(annotation.regex());
        }

        @Override
        public String getErrorMessage(Matches annotation) {
            return annotation.message();
        }

        @Override
        public Class<Matches> getSupportedType() {
            return Matches.class;
        }
    }
}
