/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

/**
 * <p>
 * Helper which contains utility methods used by this package.
 * </p>
 * <p>
 * Thread safety: This class is thread safe by being immutable.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Helper {

    /**
     * Private constructor to prevent class instantiation.
     */
    private Helper() {
        // does nothing
    }

    /**
     * Validates that the given value is not null.
     *
     * @param value the value to validate
     * @param name the name of the variable
     *
     * @throws IllegalArgumentException if the argument is null
     */
    public static void checkNotNull(Object value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(name + " cannot be null.");
        }
    }

    /**
     * Validates that the given value is not null or empty.
     *
     * @param value the value to validate
     * @param name the name of the variable
     *
     * @throws IllegalArgumentException if the argument is null or empty
     */
    public static void checkNotNullOrEmpty(String value, String name) {
        checkNotNull(value, name);
        if (value.trim().length() == 0) {
            throw new IllegalArgumentException(name + " cannot be empty.");
        }
    }

}
