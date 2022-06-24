/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

/**
 * <p>This is a helper class to provide some useful utilities.</p>
 *
 * <p>In version 1.1, it only added a new method to validate non-null, positive Long type.</p>
 *
 * @author kinfkong, Xuchen
 * @version 1.1
 * @since 1.0
 */
public final class Helper {

    /**
     * <p>The private constructor used to avoid creating instances.</p>
     */
    private Helper() {
        // do nothing
    }

    /**
     * <p>Checks if the value of long is positive.</p>
     *
     * @param value the long value
     * @param name the name of the parameter
     *
     * @throws IllegalArgumentException if the value is less than or equal to 0
     */
    public static void checkLongPositive(long value, String name) {
        if (value <= 0) {
            throw new IllegalArgumentException("The parameter [" + name + "] must be positive.");
        }
    }

    /**
     * <p>Checks if the value is non-null and positive.</p>
     *
     * @param value the Long type value
     * @param name the name of the parameter
     *
     * @throws IllegalArgumentException if the value is null or is a wrapper of value &lt;= 0
     * @since 1.1
     */
    public static void checkLongPositive(Long value, String name) {
        checkNull(value, name);
        checkLongPositive(value.longValue(), name);
    }

    /**
     * Checks if the value of an object is null.
     *
     * @param obj the parameter object
     * @param name the name of the parameter
     *
     * @throws IllegalArgumentException if obj is null
     */
    public static void checkNull(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException("The parameter [" + name + "] cannot be null.");
        }
    }
}
