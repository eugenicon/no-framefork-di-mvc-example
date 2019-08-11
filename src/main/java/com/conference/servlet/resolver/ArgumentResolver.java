package com.conference.servlet.resolver;

import com.conference.Component;
import com.conference.Order;
import com.conference.converter.ConversionService;
import com.conference.service.AuthenticatedUser;
import com.conference.service.AuthenticationService;
import com.conference.servlet.ControllerRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

@Order(0)
@Component
public class ArgumentResolver implements Resolver {
    private final List<BiFunction<Parameter, HttpServletRequest, Object>> argumentProviders = new ArrayList<>();
    private final ConversionService conversionService;
    private final AuthenticationService authenticationService;

    public ArgumentResolver(ConversionService conversionService, AuthenticationService authenticationService) {
        this.conversionService = conversionService;
        this.authenticationService = authenticationService;

        argumentProviders.add((param, req) -> param.getType().equals(HttpServletRequest.class) ? req : null);
        argumentProviders.add((param, req) -> param.getType().equals(HttpSession.class) ? req.getSession() : null);
        argumentProviders.add((param, req) -> param.getType().equals(AuthenticatedUser.class) ? this.authenticationService.getAuthentication(req.getSession()).orElse(null) : null);
        argumentProviders.add((param, req) -> req.getParameter(param.getName()));
        argumentProviders.add((param, req) -> req.getAttribute(param.getName()));
        argumentProviders.add((param, req) -> conversionService.convert(req, param.getType()));
    }

    @Override
    public void resolve(ControllerRegistry controllerRegistry, RequestResolution requestResolution) {
        requestResolution.setArguments(prepareMethodArguments(controllerRegistry.getParameters(), requestResolution.getRequest()));
    }

    private Object[] prepareMethodArguments(Parameter[] parameters, HttpServletRequest req) {
        return Arrays.stream(parameters)
                .map(parameter -> argumentProviders.stream()
                        .map(p -> p.apply(parameter, req))
                        .filter(Objects::nonNull)
                        .findFirst()
                        .map(value -> conversionService.convert(value, parameter.getType()))
                        .orElse(null))
                .toArray();
    }
}