/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.exceptions;

/**
 * <p>
 * This exception is base of other custom exceptions, it is for general error.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author LOY, sparemax
 * @version 1.0
 */
public class AssetException extends Exception {
    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = 4335120864965758582L;

    /**
     * Creates the exception with the provided message.
     *
     * @param message
     *            the error message.
     */
    public AssetException(String message) {
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
    public AssetException(String message, Throwable cause) {
        super(message, cause);
    }
}
