package com.conference.validation;

import java.util.HashMap;
import java.util.Map;

public class ValidationResult {
    private final Map<String, String> validationMap = new HashMap<>();

    public void add(String key, String result) {
        validationMap.put(key, result);
    }

    public boolean hasError(String key) {
        return validationMap.containsKey(key);
    }

    public String getError(String key) {
        return validationMap.get(key);
    }

    public boolean isSuccess() {
        return validationMap.isEmpty();
    }

    @Override
    public String toString() {
        return "ValidationResult" + validationMap;
    }
}
