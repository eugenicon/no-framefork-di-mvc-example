package com.conference.servlet.resolver;

import com.conference.servlet.ControllerRegistry;

public interface Resolver {
    void resolve(ControllerRegistry controllerRegistry, RequestResolution requestResolution) throws Exception;
}
