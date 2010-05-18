/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

/**
 * <p>
 * Helper which contains utility code used by this component.
 * </p>
 *
 * <p>
 * Thread safety: This class is thread safe by being immutable.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class ActionHelper {

    /**
     * Represents the validation error message for field that cannot be null.
     */
    static final String CANNOT_BE_NULL = "${getText(fieldName)} cannot be null";

    /**
     * Represents the validation error message for field that cannot be null or empty.
     */
    static final String CANNOT_BE_NULL_OR_EMPTY = "${getText(fieldName)} cannot be null or empty string";

    /**
     * Represents the validation error message for field that must be greater than or equal to 0.
     */
    static final String GREATER_THAN_OR_EQUAL_TO_ZERO = "${getText(fieldName)} must be >= 0";

    /**
     * Represents the validation error message for field that must be greater than 0.
     */
    static final String GREATER_THAN_ZERO = "${getText(fieldName)} must be > 0";

    /**
     * Represents the validation error message for field that must be between 1 and 100 characters.
     */
    static final String BETWEEN_1_AND_100_CHARS =
        "${getText(fieldName)} cannot be null and must be between 1 and 100 characters";

    /**
     * Private constructor to prevent class instantiation.
     */
    private ActionHelper() {
        // does nothing
    }

    /**
     * Checks that injected field is not null.
     *
     * @param field the field to check
     * @param name the name of the field
     * @throws IllegalStateException if field is null
     */
    public static void checkInjectedFieldNull(Object field, String name) {
        if (field == null) {
            throw new IllegalStateException(name + " was not injected (is null)");
        }
    }
}
