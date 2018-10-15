package com.test.springbootdemo.core.util;

public class Util {
    /**
     * method to check string object for null or empty
     * @param string
     *      String object to check
     * @return
     */
    public static boolean isNullOrEmpty(String string) {
        return (((string == null) || (string.trim().length() == 0)) ? true : false);
    }

    public static boolean isNull(Object object) {
        return object == null;
    }

    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }
}
