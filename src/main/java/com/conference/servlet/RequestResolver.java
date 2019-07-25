package com.conference.servlet;

import com.conference.Component;
import com.conference.converter.ConversionService;
import com.conference.servlet.annotation.GetMapping;
import com.conference.servlet.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RequestResolver {
    private static final String REQUEST_PATH_REGEX = "\\{([^}]+)}";
    private final Map<String, Map<String, ControllerResolver>> mappings = new HashMap<>();
    private final List<BiFunction<Parameter, HttpServletRequest, Object>> argumentProviders = new ArrayList<>();
    private final ConversionService conversionService;

    public RequestResolver(ConversionService conversionService) {
        this.conversionService = conversionService;

        mappings.put(GetMapping.METHOD, new HashMap<>());
        mappings.put(PostMapping.METHOD, new HashMap<>());

        argumentProviders.add((param, req) -> param.getType().equals(HttpServletRequest.class) ? req : null);
        argumentProviders.add((param, req) -> req.getParameter(param.getName()));
        argumentProviders.add((param, req) -> req.getAttribute(param.getName()));
        argumentProviders.add((param, req) -> conversionService.convert(req, param.getType()));
    }

    public void register(List<?> controllers, String contextPath) {
        for (Object controller : controllers) {
            for (Method method : controller.getClass().getDeclaredMethods()) {
                registerMapping(GetMapping.METHOD, GetMapping.class, GetMapping::value, controller, method, contextPath);
                registerMapping(PostMapping.METHOD, PostMapping.class, PostMapping::value, controller, method, contextPath);
            }
        }
    }

    private <T extends Annotation> void registerMapping(String httpMethod, Class<T> annotationType, Function<T, String> urlSource, Object controller, Method controllerMethod, String contextPath) {
        T annotation = controllerMethod.getAnnotation(annotationType);
        if (annotation != null) {
            String urlMapping = contextPath + urlSource.apply(annotation);
            mappings.get(httpMethod).put(urlMapping, new ControllerResolver(controller, controllerMethod));
        }
    }

    public String resolve(HttpServletRequest req) throws Exception {
        Map<String, ControllerResolver> registryMap = mappings.get(req.getMethod());
        if (registryMap != null) {
            String requestUrl = resolveUrl(req, registryMap.keySet());
            ControllerResolver resolverRegistry = registryMap.get(requestUrl);
            if (resolverRegistry != null) {
                return resolverRegistry.invokeController(req);
            }
        }
        return "";
    }

    private String resolveUrl(HttpServletRequest request, Set<String> keys) {
        String requestURI = request.getRequestURI();
        if (keys.contains(requestURI)) {
            return requestURI;
        }
        for (String key : keys) {
            if (key.contains("{")) {
                String pattern = key.replaceAll(REQUEST_PATH_REGEX, "(.+)");
                Matcher requestMatcher = Pattern.compile(pattern).matcher(requestURI);
                Matcher keyMatcher = Pattern.compile(REQUEST_PATH_REGEX).matcher(key);
                if (requestMatcher.find() && keyMatcher.find()) {
                    for (int i = 0; i < keyMatcher.groupCount(); i++) {
                        String paramName = keyMatcher.group(i + 1);
                        String paramValue = requestMatcher.group(i + 1);
                        request.setAttribute(paramName, paramValue);
                    }
                    return key;
                }
            }
        }
        return "";
    }

    private class ControllerResolver {
        private final Object controller;
        private final Method controllerMethod;

        ControllerResolver(Object controller, Method controllerMethod) {
            this.controller = controller;
            this.controllerMethod = controllerMethod;
        }

        String invokeController(HttpServletRequest req) throws Exception {
            Object[] methodArguments = Arrays.stream(controllerMethod.getParameters())
                    .map(parameter -> argumentProviders.stream()
                            .map(p -> p.apply(parameter, req))
                            .filter(Objects::nonNull)
                            .findFirst()
                            .map(value -> conversionService.convert(value, parameter.getType()))
                            .orElse(null)
                    ).toArray();
            Object result = controllerMethod.invoke(controller, methodArguments);
            if (result instanceof View) {
                View view = ((View) result);
                view.getAttributes().forEach(req::setAttribute);
                return view.getUrl();
            }
            return String.valueOf(result);
        }
    }
}
