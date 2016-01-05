package com.dev.backend;

public class Utils {

    /**
     * Parse Integer
     *
     * @param value Integer value in string
     * @return Integer or null
     */
    public static Integer parseInteger(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (Exception e) {
            return null;
        }
    }
}
