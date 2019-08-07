package com.conference.servlet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GlobalExceptionMapping {
    String KEY = "GLOBAL_EXCEPTION";
    Class<? extends Exception> value();
}
