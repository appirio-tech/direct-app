/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

/**
 * Helper class for the component.
 *
 * @author iamajia
 * @version 1.0
 */
final class Helper {
    /**
     * Private empty constructor.
     */
    private Helper() {
    }
    /**
     * Check if the number is positive.
     *
     * @param number
     *            the number to check.
     * @param name
     *            the number name
     * @throws IllegalArgumentException
     *             if the number is not positive.
     */
    public static void checkNumberPositive(long number, String name) {
        if (number <= 0) {
            throw new IllegalArgumentException("the number " + name + " should be positive.");
        }
    }

    /**
     * Check if the object is null.
     *
     * @param obj
     *            the object to check.
     * @param name
     *            the object name
     * @throws IllegalArgumentException
     *             if the object is null.
     */
    public static void checkObjectNotNull(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException("the object " + name + " should not be null.");
        }
    }

    /**
     * Check if the string is null or empty.
     *
     * @param str
     *            the string to check.
     * @param name
     *            the parameter name.
     * @throws IllegalArgumentException
     *             if the string is null or empty.
     */
    public static void checkStringNotNullOrEmpty(String str, String name) {
        checkObjectNotNull(str, name);
        if (str.trim().length() == 0) {
            throw new IllegalArgumentException("the string " + name + " should not be empty.");
        }
    }
}