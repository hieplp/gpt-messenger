package com.hieplp.messenger.util;

public class States {

    private States() {
        throw new IllegalStateException("Utility class: States");
    }

    public static boolean isNull(Object object) {
        return object == null;
    }

    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
