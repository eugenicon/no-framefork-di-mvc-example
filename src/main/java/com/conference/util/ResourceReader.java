package com.conference.util;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;

public class ResourceReader {
    private static final String CLASSPATH_PREFIX = "classpath:";

    public static String getResourceAsString(String path) {
        try(Scanner sc = new Scanner(getResourceAsStream(path))) {
            return sc.useDelimiter("\\A").next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Properties getResourceAsProperties(String path) {
        Properties properties = new Properties();
        try {
            properties.load(getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    private static InputStream getResourceAsStream(String path) {
        boolean isClasspathResource = path.startsWith(CLASSPATH_PREFIX) || !Files.isRegularFile(Paths.get(path));
        try {
            if (isClasspathResource) {
                String resourceName = path.replace(CLASSPATH_PREFIX, "");
                return ResourceReader.class.getClassLoader().getResourceAsStream(resourceName);
            } else {
                return new FileInputStream(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(new byte[]{});
    }
}
