package com.conference.servlet;

import java.util.HashMap;
import java.util.Map;

public class View {
    private final String url;
    private final Map<String, Object> attributes = new HashMap<>();

    public View(String url) {
        this.url = url;
    }

    public static View of(String url) {
        return new View(url);
    }

    public <T> View with(String name, T value) {
        attributes.put(name, value);
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
