/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.exceptions;

/**
 * <p>
 * This exception is for general error of the services.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author LOY, sparemax
 * @version 1.0
 */
public class ServiceException extends AssetException {
    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = -4814821003368374747L;

    /**
     * Creates the exception with the provided message.
     *
     * @param message
     *            the error message.
     */
    public ServiceException(String message) {
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
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
