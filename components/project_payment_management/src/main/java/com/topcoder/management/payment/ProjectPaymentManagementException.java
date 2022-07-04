/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown by implementations of managers when some not expected error occurred. Also this exception
 * is used as a base class for other specific checked custom exceptions.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author maksimilian, sparemax
 * @version 1.0
 */
public class ProjectPaymentManagementException extends BaseCriticalException {
    /**
     * The serial version ID.
     */
    private static final long serialVersionUID = 2750606710986383009L;

    /**
     * <p>
     * Constructor with error message.
     * </p>
     *
     * @param message
     *            the explanation of the error. Can be empty string or <code>null</code> (useless, but allowed).
     */
    public ProjectPaymentManagementException(String message) {
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
     *            the underlying cause of the error. Can be <code>null</code>, which means that initial exception
     *            is nonexistent or unknown.
     */
    public ProjectPaymentManagementException(String message, Throwable cause) {
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
     *            the additional data to attach to the exception. If this is <code>null</code>, a new
     *            ExceptionData() will be automatically used instead.
     */
    public ProjectPaymentManagementException(String message, ExceptionData data) {
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
     *            the underlying cause of the error. Can be <code>null</code>, which means that initial exception
     *            is nonexistent or unknown.
     * @param data
     *            the additional data to attach to the exception. If this is <code>null</code>, a new
     *            ExceptionData() will be automatically used instead.
     */
    public ProjectPaymentManagementException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
