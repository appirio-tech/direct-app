/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.impl;

import com.topcoder.management.review.application.ReviewApplicationManagementException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown by implementations of ReviewAuctionPersistence when some not expected error occurred.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public class ReviewAuctionPersistenceException extends ReviewApplicationManagementException {
    /**
     * The serial version ID.
     */
    private static final long serialVersionUID = 7046509256832518517L;

    /**
     * <p>
     * Constructor with error message.
     * </p>
     *
     * @param message
     *            the explanation of the error. Can be empty string or <code>null</code> (useless, but allowed).
     */
    public ReviewAuctionPersistenceException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructor with error message and inner cause.
     * </p>
     *
     * @param message
     *            the explanation of the error. Can be empty string or <code>null</code> (useless, but allowed).
     * @param cause
     *            the underlying cause of the error. Can be <code>null</code>, which means that initial exception is
     *            nonexistent or unknown.
     */
    public ReviewAuctionPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Constructor with error message and exception data.
     * </p>
     *
     * @param message
     *            the explanation of the error. Can be empty string or <code>null</code> (useless, but allowed).
     * @param data
     *            the additional data to attach to the exception. If this is <code>null</code>, a new ExceptionData()
     *            will be automatically used instead.
     */
    public ReviewAuctionPersistenceException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * <p>
     * Constructor with error message, inner cause and exception data.
     * </p>
     *
     * @param message
     *            the explanation of the error. Can be empty string or <code>null</code> (useless, but allowed).
     * @param cause
     *            the underlying cause of the error. Can be <code>null</code>, which means that initial exception is
     *            nonexistent or unknown.
     * @param data
     *            the additional data to attach to the exception. If this is <code>null</code>, a new ExceptionData()
     *            will be automatically used instead.
     */
    public ReviewAuctionPersistenceException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
