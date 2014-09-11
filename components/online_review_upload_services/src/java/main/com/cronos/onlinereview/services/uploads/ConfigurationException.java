/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.onlinereview.services.uploads;

/**
 * <p>
 * This is the configuration exception of this component. It is thrown when there are invalid configuration
 * values and missing configuration properties.
 * </p>
 *
 * <p>
 * Thread safety: This class is thread safe since it is immutable.
 * </p>
 *
 * @author fabrizyo, cyberjag
 * @version 1.0
 */
public class ConfigurationException extends UploadServicesException {

    /**
     * <p>
     * Constructs the exception with error message.
     * </p>
     *
     * @param message the error message
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructs the exception with error message and inner cause.
     * </p>
     *
     * @param message the error message
     * @param cause   the cause of this exception
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
