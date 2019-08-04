package com.conference.servlet;

import com.conference.Component;
import com.conference.servlet.annotation.ExceptionMapping;
import com.conference.servlet.annotation.GetMapping;
import com.conference.servlet.annotation.PostMapping;
import com.conference.servlet.resolver.RequestResolution;
import com.conference.servlet.resolver.Resolver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RequestResolver {
    private static final Logger LOGGER = LogManager.getLogger(RequestResolver.class);
    private static final String REQUEST_PATH_REGEX = "\\{([^}]+)}";
    private final Map<String, Map<String, ControllerRegistry>> mappings = new HashMap<>();
    private final List<Resolver> resolvers;

    public RequestResolver(List<Resolver> resolvers) {
        this.resolvers = resolvers;

        mappings.put(GetMapping.KEY, new HashMap<>());
        mappings.put(PostMapping.KEY, new HashMap<>());
        mappings.put(ExceptionMapping.KEY, new HashMap<>());
    }

    public void register(List<?> controllers, String contextPath) {
        for (Object controller : controllers) {
            for (Method method : controller.getClass().getDeclaredMethods()) {
                registerMapping(GetMapping.KEY, GetMapping.class, a -> contextPath + a.value(), controller, method);
                registerMapping(PostMapping.KEY, PostMapping.class, a -> contextPath + a.value(), controller, method);
                registerMapping(ExceptionMapping.KEY, ExceptionMapping.class, a -> getExceptionMappingKey(controller, a.value()), controller, method);
            }
        }
    }

    private <T extends Annotation> void registerMapping(String httpMethod, Class<T> annotationType, Function<T, String> urlSource, Object controller, Method controllerMethod) {
        T annotation = controllerMethod.getAnnotation(annotationType);
        if (annotation != null) {
            String urlMapping = urlSource.apply(annotation);
            mappings.get(httpMethod).put(urlMapping, new ControllerRegistry(controller, controllerMethod));
            LOGGER.info("Registered {} mapping for {}: {}.{}", httpMethod, urlMapping, controller.getClass().getSimpleName(), controllerMethod.getName());
        }
    }

    public String resolve(HttpServletRequest req) throws Exception {
        RequestResolution requestResolution = new RequestResolution(req);
        Map<String, ControllerRegistry> registryMap = mappings.get(req.getMethod());
        if (registryMap != null) {
            String requestUrl = getControllerMappingKey(req, registryMap.keySet());
            ControllerRegistry resolverRegistry = registryMap.get(requestUrl);
            try {
                resolveRequest(requestResolution, resolverRegistry, resolvers);
            } catch (Exception e) {
                Exception cause = e.getCause() == null ? e : (Exception) e.getCause();
                LOGGER.warn(cause);
                req.setAttribute("exception", cause);
                requestResolution.setException(cause);
                String exceptionKey = getExceptionMappingKey(resolverRegistry.getController(), cause.getClass());
                ControllerRegistry resolver = mappings.get(ExceptionMapping.KEY).get(exceptionKey);
                resolveRequest(requestResolution, resolver, resolvers);
            }
        }

        return requestResolution.getUrl();
    }

    private void resolveRequest(RequestResolution requestResolution, ControllerRegistry controllerRegistry, List<? extends Resolver> resolvers) throws Exception {
        if (controllerRegistry != null) {
            for (int i = 0; i < resolvers.size() && !requestResolution.isResolved(); i++) {
                resolvers.get(i).resolve(controllerRegistry, requestResolution);
            }
        } else if (requestResolution.hasException()) {
            throw requestResolution.getException();
        }
    }

    private String getControllerMappingKey(HttpServletRequest request, Set<String> keys) {
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

    private String getExceptionMappingKey(Object controller, Class<? extends Exception> exceptionClass) {
        return String.format("%s:%s", controller.getClass().getSimpleName(), exceptionClass.getSimpleName());
    }
}
