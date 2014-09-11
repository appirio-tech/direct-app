/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * Represents an exception related to loading configuration settings. Inner
 * exception should be provided to give more details about the error. It is used
 * in classes that have to load configuration settings such as
 * ProjectManagerImpl and InformixProjectPersistence.
 * </p>
 * <p>
 * Thread safety: This class is immutable and thread safe.
 * </p>
 *
 * @author tuenm, iamajia
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ConfigurationException extends BaseException {

    /**
     * Create a new ConfigurationException instance with the specified error
     * message.
     *
     * @param message
     *            the error message of the exception
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * Create a new ConfigurationException instance with the specified error
     * message and inner exception.
     *
     * @param message
     *            the error message of the exception
     * @param cause
     *            the inner exception
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
