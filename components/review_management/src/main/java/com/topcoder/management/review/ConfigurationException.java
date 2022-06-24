/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review;

/**
 * This exception is thrown from the constructor taking a namespace argument, if
 * failed to load configuration values from the ConfigManager or the
 * configuration values are invalid.
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class ConfigurationException extends ReviewManagementException {

    /**
     * Create the instance with error message.
     * @param msg
     *            the error message
     */
    public ConfigurationException(String msg) {
        super(msg);
    }

    /**
     * Create the instance with error message and cause exception.
     * @param msg
     *            the error message
     * @param cause
     *            the cause exception
     */
    public ConfigurationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
