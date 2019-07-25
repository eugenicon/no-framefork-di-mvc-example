package com.conference.util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Reflection {

    public static Method getMethod(Class<?> type, String methodName) {
        return Arrays.stream(type.getMethods())
                .filter(m -> m.getName().equals(methodName))
                .filter(m -> m.getDeclaringClass().equals(type))
                .findFirst()
                .orElse(null);
    }

    public static List<Class> getClasses(String packageName, Class<? extends Annotation>... annotations) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String packageUrl = packageName.replace(".", File.separator);
        URL resourceUrl = Objects.requireNonNull(classLoader.getResource(packageUrl));

        try {
            Path path = Paths.get(resourceUrl.toURI());
            return Files.walk(path)
                    .filter(Files::isRegularFile)
                    .map(p -> {
                        String filePath = p.toString().replace(File.separator, ".");
                        return getClass(filePath.substring(filePath.indexOf(packageName), filePath.lastIndexOf(".")));
                    })
                    .filter(Objects::nonNull)
                    .flatMap(c -> Stream.concat(Stream.of(c), Arrays.stream(c.getClasses())))
                    .filter(c -> annotations.length == 0 || Arrays.stream(annotations).allMatch(c::isAnnotationPresent))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public static List<Class> getGenericTypes(Type type) {
        if (type instanceof ParameterizedType) {
            return Arrays.stream(((ParameterizedType) type).getActualTypeArguments())
                    .map(Class.class::cast)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    private static Class<?> getClass(String typeName) {
        try {
            return Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
