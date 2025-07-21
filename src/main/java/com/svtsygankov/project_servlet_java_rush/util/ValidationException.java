package com.svtsygankov.project_servlet_java_rush.util;

import java.util.Map;

public class ValidationException extends Exception {
    private final Map<String, String> errors;

    public ValidationException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}