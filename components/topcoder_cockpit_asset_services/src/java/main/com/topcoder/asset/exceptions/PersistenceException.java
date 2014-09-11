/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.exceptions;

/**
 * <p>
 * This exception is for persistence error.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author LOY, sparemax
 * @version 1.0
 */
public class PersistenceException extends ServiceException {
    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = 5666741906398494055L;

    /**
     * Creates the exception with the provided message.
     *
     * @param message
     *            the error message.
     */
    public PersistenceException(String message) {
        super(message);
    }

    /**
     * Creates the exception with the provided message and cause.
     *
     * @param message
     *            the error message.
     * @param cause
     *            the error cause.
     */
    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
