/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * Represents an exception related to persistence operations such as cannot
 * create connection, database table does not exist, etc. Inner exception should
 * be provided to give more details about the error. It is used in persistence
 * implementation classes.
 * </p>
 * <p>
 * Thread safety: This class is immutable and thread safe.
 * </p>
 *
 * @author tuenm, iamajia
 * @version 1.0
 */
@SuppressWarnings("serial")
public class PersistenceException extends BaseException {

    /**
     * Create a new PersistenceException instance with the specified error
     * message.
     *
     * @param message
     *            the error message of the exception
     */
    public PersistenceException(String message) {
        super(message);
    }

    /**
     * Create a new PersistenceException instance with the specified error
     * message and inner exception.
     *
     * @param message
     *            the error message of the exception
     * @param cause
     *            the inner exception
     */
    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
