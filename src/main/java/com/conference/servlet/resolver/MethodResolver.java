package com.conference.servlet.resolver;

import com.conference.Component;
import com.conference.Order;
import com.conference.servlet.ControllerRegistry;
import com.conference.servlet.View;

import java.lang.reflect.Method;

@Order(2)
@Component
public class MethodResolver implements Resolver {
    @Override
    public void resolve(ControllerRegistry controllerRegistry, RequestResolution requestResolution) throws Exception {
        Object[] methodArguments = requestResolution.getArguments();
        Object controller = controllerRegistry.getController();
        Method method = controllerRegistry.getMethod();
        Object result = method.invoke(controller, methodArguments);
        if (result instanceof View) {
            View view = ((View) result);
            view.getAttributes().forEach(requestResolution.getRequest()::setAttribute);
            requestResolution.setUrl(view.getUrl());
        } else {
            requestResolution.setUrl(String.valueOf(result));
        }
    }
}
