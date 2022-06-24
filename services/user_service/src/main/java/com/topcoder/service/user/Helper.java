/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.user;

import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class provides static utility methods which are used to facilitate the coding or reduce the redundancies.
 * </p>
 *
 * <p>
 * Thread Safety: This class is thread safe since it is immutable.
 * </p>
 *
 * @author snow01
 * @author ernestobf
 * @since Cockpit Release Assembly for Receipts
 * @version 1.1
 * @since 1.0
 */
public final class Helper {
    /**
     * <p>
     * Private constructor to prevent instantiation of this class.
     * </p>
     */
    private Helper() {
    }

    /**
     * <p>
     * Checks whether the param is null.
     * </p>
     *
     * @param <T> the type of the exception to log
     * @param logger
     *            the logger to log the exception with
     * @param exception
     *            the exception to log
     * @return the exception
     * @since 1.2
     */
    public static <T extends Throwable> T logException(final Log logger, T exception) {

        if (logger != null) {
            // This will log the message and StackTrace of the exception.
            logger.log(Level.ERROR, exception, exception.getMessage());

            Throwable cause = exception.getCause();

            while (cause != null) {
                logger.log(Level.ERROR, cause, "INNER: " + cause.getMessage());
            }
        }
        return exception;
    }

    /**
     * <p>
     * Checks whether the parameter is null.
     * </p>
     *
     * @param logger
     *            the logger to log the exception with, can be null
     * @param param
     *            the parameter to check
     * @param paramName
     *            the name of the parameter
     * @throws IllegalArgumentException
     *             if the param is null
     */
    public static void checkNull(final Object param, final String paramName) {
        if (param == null) {
            throw new IllegalArgumentException("The parameter '" + paramName + "' should not be null.");
        }
    }

    /**
     * <p>
     * Checks whether the parameter is null.
     * </p>
     *
     * @param logger
     *            the logger to log the exception with, can be null
     * @param param
     *            the parameter to check
     * @param paramName
     *            the name of the parameter
     * @throws IllegalArgumentException
     *             if <code>param</code> is null
     * @since 1.0
     */
    public static void checkNull(final Log logger, final Object param, final String paramName) {
        if (param == null) {
            throw logException(logger, new IllegalArgumentException("The parameter '" + paramName
                    + "' should not be null."));
        }
    }


 /**
     * <p>
     * Checks whether the parameter is empty string.
     * </p>
     *
     * @param logger
     *            the logger to log the exception with, can be null.
     * @param param
     *            the parameter to check
     * @param paramName
     *            the name of the parameter
     * @throws IllegalArgumentException
     *             if <code>param</code> is empty string.
     * @since 1.0
     */
    public static void checkEmpty(final Log logger, final String param, final String paramName) {
        if ((param != null) && (param.trim().length() == 0)) {
            throw logException(logger, new IllegalArgumentException("The parameter '" + paramName
                    + "' should not be empty."));
        }
    }

 /**
     * <p>
     * Checks whether the parameter is null or an empty string. Calling this method is equivalent to calling
     * <code>checkNull</code> and <code>checkEmpty</code>.
     * </p>
     *
     * @param logger
     *            the logger to log the exception with, can be null
     * @param param
     *            the parameter to check
     * @param paramName
     *            the name of the parameter
     * @throws IllegalArgumentException
     *             if <code>param</code> is an empty string.
     * @since 11
     */
    public static void checkNullEmpty(final Log logger, final String param, final String paramName) {
        checkNull(logger, param, paramName);
        checkEmpty(logger, param, paramName);
    }



    /**
     * <p>
     * Checks whether the param is empty string.
     * </p>
     *
     * @param param
     *            the parameter to check
     * @param paramName
     *            the name of the parameter
     * @throws IllegalArgumentException
     *             if the param is empty string.
     */
    public static void checkEmpty(final String param, final String paramName) {
        if ((param != null) && (param.trim().length() == 0)) {
            throw new IllegalArgumentException("The parameter '" + paramName + "' should not be empty.");
        }
    }



	 /**
     * <p>
     * Checks whether the parameter is non-positive.
     * </p>
     *
     * @param logger
     *            the logger to log the exception with, can be null.
     * @param param
     *            the parameter to check
     * @param paramName
     *            the name of the parameter
     * @throws IllegalArgumentException
     *             if <code>param</code> is non-positive
     * @since 1.1
     */
    public static void checkNonPositive(final Log logger, final long param, final String paramName) {
        if (param <= 0) {
            throw logException(logger, new IllegalArgumentException("The parameter '" + paramName
                    + "' should not non-positive."));
        }
    }

    /**
     * <p>
     * Checks whether the parameter contains non-positive elements.
     * </p>
     *
     * @param logger
     *            the logger to log the exception with, can be null.
     * @param param
     *            the parameter to check
     * @param paramName
     *            the name of the parameter
     * @throws IllegalArgumentException
     *             if <code>param</code> contains non-positive elements
     * @since 1.1
     */
    public static void checkNonPositive(final Log logger, final long[] param, final String paramName) {
        for (int i = 0; i < param.length; i++) {
            if (param[i] <= 0) {
                throw logException(logger, new IllegalArgumentException("The parameter '" + paramName
                        + "' should not contain non-positive elements."));
            }
        }
    }

    /**
     * <p>
     * Checks whether the parameter is an empty array.
     * </p>
     *
     * @param logger
     *            the logger to log the exception with, can be null.
     * @param param
     *            the parameter to check
     * @param paramName
     *            the name of the parameter
     * @throws IllegalArgumentException
     *             if <code>param</code> is an empty array
     * @since 1.1
     */
    public static void checkEmpty(final Log logger, final long[] param, final String paramName) {
        if ((param != null) && (param.length == 0)) {
            throw logException(logger, new IllegalArgumentException("The parameter '" + paramName
                    + "' should not be empty."));
        }
    }

}
