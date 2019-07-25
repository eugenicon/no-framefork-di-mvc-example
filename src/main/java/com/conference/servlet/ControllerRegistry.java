package com.conference.servlet;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ControllerRegistry {
    private final Object controller;
    private final Method controllerMethod;
    private final Parameter[] parameters;

    ControllerRegistry(Object controller, Method controllerMethod) {
        this.controller = controller;
        this.controllerMethod = controllerMethod;
        this.parameters = controllerMethod.getParameters();
    }

    public Object getController() {
        return controller;
    }

    public Parameter[] getParameters() {
        return parameters;
    }

    public Method getMethod() {
        return controllerMethod;
    }
}