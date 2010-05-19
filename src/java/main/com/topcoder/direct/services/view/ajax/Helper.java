/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.ajax;


/**
 * <p>
 * This class is used by this component only. Provide some common utility methods.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> It's thread safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class Helper {
    /**
     * <p>
     * Private constructor.
     * </p>
     */
    private Helper() {
    }

    /**
     * <p>
     * Checks whether the parameter is null.
     * </p>
     *
     * @param param
     *            the parameter to check
     * @param paramName
     *            the name of the parameter
     *
     * @throws IllegalArgumentException
     *             if the parameter is null
     */
    public static void checkNull(final Object param, final String paramName) {
        if (param == null) {
            throw new IllegalArgumentException("The parameter '" + paramName + "' should not be null.");
        }
    }

    /**
     * <p>
     * Checks whether a string is null or empty, if it is, <code>IllegalArgumentException
     * </code> is thrown.
     * </p>
     *
     * @param param
     *            the string to check
     * @param paramName
     *            the parameter name
     *
     * @throws IllegalArgumentException
     *             if the string is null or empty
     */
    public static void checkString(final String param, final String paramName) {
        Helper.checkNull(param, paramName);
        if (param.trim().length() == 0) {
            throw new IllegalArgumentException("The parameter '" + paramName + "' should not be empty.");
        }
    }
}
