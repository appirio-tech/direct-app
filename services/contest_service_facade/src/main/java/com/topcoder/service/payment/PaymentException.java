/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.payment;


/**
 * <p>
 * The exception class that captures any errors or failed transactions that
 * happen on processing the payment through implementing classes. This exception
 * captures the error code and error message from the underlying implementation
 * of the payment processors.
 * </p>
 *
 * @author shailendra_80
 */
public class PaymentException extends Exception {
    /**
     * Default serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * The error code for the failed payment.
     */
    private String errorCode;

    /**
     * A do nothing default constructor.
     */
    public PaymentException() {
    }

    /**
     * @param message
     */
    public PaymentException(String message) {
        super(message);
    }

    /**
     * <p>
     * This constructor initializes this exception instance for given error code
     * and error message.
     * </p>
     *
     * @param errorCode
     *            the error code.
     * @param errorMessage
     *            the error message.
     */
    public PaymentException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    /**
     * <p>
     * This constructor initializes this exception instance for given error
     * message and exception cause.
     * </p>
     *
     * @param message
     *            the error message
     * @param cause
     *            the exception cause
     */
    public PaymentException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * This constructor initializes this exception instance for given error
     * code, error message and exception cause.
     * </p>
     *
     * @param errorCode
     *            the error code
     * @param message
     *            the error message
     * @param cause
     *            the exception cause
     */
    public PaymentException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * <p>
     * Gets the error code.
     * </p>
     *
     * @return the error code.
     */
    public String getErrorCode() {
        return errorCode;
    }
}
