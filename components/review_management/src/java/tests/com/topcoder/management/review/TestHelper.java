/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review;

import com.topcoder.util.config.ConfigManager;

import java.lang.reflect.Field;

import java.util.Iterator;


/**
 * <p>
 * Helper class to simplify the unit testing.
 * </p>
 *
 * @author icyriver
 * @version 1.0
 */
final class TestHelper {
    /**
     * <p>
     * The private constructor to avoid creating instance of this class.
     * </p>
     */
    private TestHelper() {
    }

    /**
     * <p>
     * Clear the namespaces in ConfigManager.
     * </p>
     *
     * @throws Exception if configuration could not be clear.
     */
    public static void clearNamespace() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();

        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * <p>
     * Check the given <code>obj</code> for null.
     * </p>
     *
     * @param obj the object to check.
     * @param paramName the paramName of the argument.
     *
     * @throws IllegalArgumentException if obj is null.
     */
    public static void checkNull(Object obj, String paramName) {
        if (obj == null) {
            throw new IllegalArgumentException("Parameter argument: '" + paramName
                + "' can not be null!");
        }
    }

    /**
     * <p>
     * Check the given <code>String</code> for null and empty.
     * </p>
     *
     * @param str string to check.
     * @param paramName paramName of the argument.
     *
     * @throws IllegalArgumentException if str is empty string.
     */
    public static void checkString(String str, String paramName) {
        // check null
        checkNull(str, paramName);

        // check empty
        if (str.trim().length() == 0) {
            throw new IllegalArgumentException("Parameter argument: '" + paramName
                + "' can not be empty string!");
        }
    }

    /**
     * <p>
     * Check the given <code>number</code> for positive number.
     * </p>
     *
     * @param number the number to be check for positive number.
     * @param paramName the paramName of the argument.
     *
     * @throws IllegalArgumentException if the passed number isn't a positive number.
     */
    public static void checkPositive(long number, String paramName) {
        // check if the number is a positive number.
        if (number <= 0) {
            throw new IllegalArgumentException("The argument '" + paramName + "': " + number
                + " is not a positive number.");
        }
    }

    /**
     * <p>
     * Returns the value of the given field in the given Object using Reflection.
     * </p>
     *
     * @param obj the given Object instance to get the field value.
     * @param fieldName the name of the filed to get value from the object.
     *
     * @return the field value in the obj.
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            return field.get(obj);
        } catch (SecurityException e) {
            //ignore the exception and return null.
        } catch (NoSuchFieldException e) {
            //ignore the exception and return null.
        } catch (IllegalArgumentException e) {
            //ignore the exception and return null.
        } catch (IllegalAccessException e) {
            //ignore the exception and return null.
        }

        return null;
    }
}
