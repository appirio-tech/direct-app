/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.exceptions;

/**
 * <p>
 * This exception is thrown if there is any configuration error.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author LOY, sparemax
 * @version 1.0
 */
public class AssetConfigurationException extends RuntimeException {
    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = -1126833368968519153L;

    /**
     * Creates the exception with the provided message.
     *
     * @param message
     *            the error message.
     */
    public AssetConfigurationException(String message) {
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
    public AssetConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
