/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

/**
 * <p>
 * This exception indicates any error related to configuration, such as missing properties, invalid
 * property values, etc. It is thrown from the ScreeningManagerFactory methods.
 * </p>
 *
 * @author colau, haozhangr
 * @version 1.0
 */
public class ConfigurationException extends ScreeningManagementException {

    /**
     * <p>
     * Creates the exception with the specified message.
     * </p>
     *
     *
     * @param message
     *            the message describing the exception
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates the exception with the specified message and cause.
     * </p>
     *
     *
     * @param message
     *            the message describing the exception
     * @param cause
     *            the cause of the exception
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
