package com.conference;


import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.conference.util.Reflection.getClasses;
import static com.conference.util.Reflection.getGenericTypes;


@SuppressWarnings("unchecked")
public class ComponentResolver {
    private static ComponentResolver instance;

    private Map<Class, Object> componentInstances = new HashMap<>();

    private ComponentResolver() {
        List<Class> componentClasses = getClasses(getClass().getPackage().getName(), Component.class);
        componentClasses.forEach(c -> resolveComponentInstance(componentClasses, c));
    }

    private Object resolveComponentInstance(List<Class> declaredComponentClasses, Class componentClass) {
        if (!componentInstances.containsKey(componentClass)) {
            componentInstances.put(componentClass, createInstance(declaredComponentClasses, componentClass));
        }
        return componentInstances.get(componentClass);
    }

    private Object createInstance(List<Class> declaredComponentClasses, Class componentClass) {
        Constructor constructor = componentClass.getDeclaredConstructors()[0];
        Parameter[] parameters = constructor.getParameters();
        Object[] args = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Class parameterType = parameters[i].getType();
            if (parameterType.equals(List.class)) {
                Class actualParameterType = getGenericTypes(parameters[i].getParameterizedType()).get(0);
                List<Class> classList = declaredComponentClasses.stream().filter(actualParameterType::isAssignableFrom).collect(Collectors.toList());
                args[i] = classList.stream().map(c -> resolveComponentInstance(declaredComponentClasses, c)).collect(Collectors.toList());
            } else if (componentInstances.containsKey(parameterType) || declaredComponentClasses.contains(parameterType)) {
                args[i] = resolveComponentInstance(declaredComponentClasses, parameterType);
            }
        }
        try {
            return constructor.newInstance(args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T getComponent(Class<T> type) {
        return (T) getInstance().componentInstances.get(type);
    }

    public static List getAnnotatedComponents(Class<? extends Annotation> annotation) {
        Map<Class, Object> instances = getInstance().componentInstances;
        return instances.keySet().stream()
                .filter(c -> c.isAnnotationPresent(annotation))
                .map(instances::get)
                .collect(Collectors.toList());
    }

    private static ComponentResolver getInstance() {
        if (instance == null) {
            synchronized (ComponentResolver.class) {
                if (instance == null) {
                    instance = new ComponentResolver();
                }
            }
        }
        return instance;
    }
}