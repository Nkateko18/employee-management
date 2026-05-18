package com.employee.backend.util;

public class InputSanitizer {
    public static String sanitize(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("[<>\"'%;()&+]", "").trim();
    }
    
}
