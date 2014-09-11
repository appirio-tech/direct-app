/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

/**
 * <p>
 * This is a Helper Class of this component.
 * </p>
 *
 * @author haozhangr
 * @version 1.0
 */
public final class Helper {
    /**
     * <p>
     * The private constructor of the helper class.
     * </p>
     */
    private Helper() {
        // empty
    }

    /**
     * Checks whether the given <code>arg</code> is null.
     *
     * @param arg
     *            the argument to check
     * @param name
     *            the name of the argument
     *
     * @throws IllegalArgumentException
     *             if the given Object is null
     */
    public static final void checkNull(Object arg, String name) {
        if (arg == null) {
            throw new IllegalArgumentException(name + " should not be null.");
        }
    }

    /**
     * Checks whether the given <code>arg</code> is positive.
     *
     * @param arg
     *            the argument to check
     * @param name
     *            the name of the argument
     *
     * @throws IllegalArgumentException
     *             if the given arg is not positive
     */
    public static final void checkNonPositive(long arg, String name) {
        if (arg <= 0) {
            throw new IllegalArgumentException(name + " should be positive.");
        }
    }

    /**
     * Checks whether the given <code>arg</code> is empty.
     *
     * @param arg
     *            the String to check
     * @param name
     *            the name of the argument
     *
     * @throws IllegalArgumentException
     *             if the given string is empty or null
     */
    public static final void checkString(String arg, String name) {
        checkNull(arg, name);

        if (arg.trim().length() == 0) {
            throw new IllegalArgumentException(name + " should not be empty.");
        }
    }
}
