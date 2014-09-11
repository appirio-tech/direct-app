/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard;

import java.util.Iterator;
import java.util.List;

/**
 * Helper class for com.topcoder.management.scorecard package.
 * @author zhuzeyuan
 * @version 1.0.1
 */
final class Helper {
    /**
     * Private constructor to prevent initialization of class instance.
     */
    private Helper() {
    }

    /**
     * Check if the object is null.
     * @param obj
     *            the object to check.
     * @param name
     *            the object's name
     * @throws IllegalArgumentException
     *             if the object is null.
     */
    static void assertObjectNotNull(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException("the object " + name + " should not be null.");
        }
    }

    /**
     * Check if the string is empty.
     * @param str
     *            the string to check.
     * @param name
     *            the string's name.
     * @throws IllegalArgumentException
     *             if the object is empty.
     */
    static void assertStringNotEmpty(String str, String name) {
        assertObjectNotNull(str, name);
        if (str.trim().length() == 0) {
            throw new IllegalArgumentException("the string " + name + " should not be empty.");
        }
    }

    /**
     * Check if the integer is greater than zero.
     * @param number
     *            the integer number to check.
     * @param name
     *            the integer's name.
     * @throws IllegalArgumentException
     *             if the number if less than or equals to zero.
     */
    static void assertIntegerGreaterThanZero(long number, String name) {
        if (number <= 0) {
            throw new IllegalArgumentException(name + " must be positive.");
        }
    }

    /**
     * Check if the List is of Long instances, and each Long number is greater than zero.
     * @param list
     *            the List to check.
     * @param name
     *            the List's name.
     * @throws IllegalArgumentException
     *             if list is null, empty or contains illegal instance or non-positive long numbers.
     */
    static void checkLongList(List list, String name) {
        Helper.assertObjectNotNull(list, name);
        if (list.size() == 0) {
            throw new IllegalArgumentException(name + " must not be empty");
        }
        for (Iterator it = list.iterator(); it.hasNext();) {
            Object now = it.next();
            if (!(now instanceof Long)) {
                throw new IllegalArgumentException(name + " must contain Long instances");
            }
            Helper.assertIntegerGreaterThanZero(((Long) now).longValue(), "Any element in " + name);
        }
    }

    /**
     * Check if the List is of String instances, and each String number is greater than zero.
     * @param list
     *            the List to check.
     * @param name
     *            the List's name.
     * @throws IllegalArgumentException
     *             if list is null, empty or contains illegal instance or empty string members.
     */
    static void checkStringList(List list, String name) {
        Helper.assertObjectNotNull(list, name);
        if (list.size() == 0) {
            throw new IllegalArgumentException(name + " must not be empty");
        }
        for (Iterator it = list.iterator(); it.hasNext();) {
            Object now = it.next();
            if (!(now instanceof String)) {
                throw new IllegalArgumentException(name + " must contain String instances");
            }
            Helper.assertStringNotEmpty((String) now, "Any element in " + name);
        }
    }
}