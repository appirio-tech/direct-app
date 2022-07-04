/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

/**
 * <p>
 * Helper class for com.topcoder.management.team. Please do NOT access this class from outside packages.
 * </p>
 * <p>
 * Thread-Safety: safe.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class Helper {
    /** Provide the number 100. */
    public static final int HUNDRED = 100;

    /** Provide the string max length. */
    private static final int STRING_MAXLEN = 255;

    /**
     * Private empty constructor.
     */
    private Helper() {
    }

    /**
     * <p>
     * Check if the given number is non-negative.
     * <p>
     * @param number
     *            the number to check.
     * @param name
     *            the number's name
     * @throws IllegalArgumentException
     *             if the number is non-negative.
     */
    public static void assertNonNegative(long number, String name) {
        if (number < 0) {
            throw new IllegalArgumentException(name + " should be non-negative. (" + name + "=" + number + ")");
        }
    }

    /**
     * <p>
     * Check if the given object is null.
     * </p>
     * @param obj
     *            the object to check.
     * @param name
     *            the object's name
     * @throws IllegalArgumentException
     *             if the object is null.
     */
    public static void assertObjectNotNull(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException(name + " must not be null.");
        }
    }

    /**
     * <p>
     * Check if the string is null or empty.
     * </p>
     * @param str
     *            the string to check.
     * @param name
     *            the string's name.
     * @throws IllegalArgumentException
     *             if the string is null or empty.
     */
    public static void assertStringNotNullOrEmpty(String str, String name) {
        assertObjectNotNull(str, name);
        if (str.trim().length() == 0) {
            throw new IllegalArgumentException(name + " must not be empty.");
        }
    }

    /**
     * <p>
     * Check if the string is null or empty, and assert it is no longer than 255.
     * </p>
     * @param str
     *            the string to check.
     * @param name
     *            the string's name.
     * @throws IllegalArgumentException
     *             if the string is null or empty.
     */
    public static void assertStringNotNullOrEmpty_ShorterThan256(String str, String name) {
        assertStringNotNullOrEmpty(str, name);
        if (str.length() > STRING_MAXLEN) {
            throw new IllegalArgumentException(name + " must not be longer than 255");
        }
    }
}