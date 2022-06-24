/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.exceptions;

/**
 * <p>
 * This exception is thrown if some required entity is not found.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author LOY, sparemax
 * @version 1.0
 */
public class EntityNotFoundException extends PersistenceException {
    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = 682630818317752079L;

    /**
     * Creates the exception with the provided message.
     *
     * @param message
     *            the error message.
     */
    public EntityNotFoundException(String message) {
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
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
