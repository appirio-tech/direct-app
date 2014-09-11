/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.onlinereview.services.uploads;

/**
 * <p>
 * This exception wraps all exceptions related with the Persistence layer.
 * </p>
 *
 * <p>
 * Thread safety: This class is thread safe since it is immutable.
 * </p>
 *
 * @author fabrizyo, cyberjag
 * @version 1.0
 */
public class PersistenceException extends UploadServicesException {

    /**
     * <p>
     * Constructs the exception with error message.
     * </p>
     *
     * @param message the error message
     */
    public PersistenceException(String message) {
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
    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
