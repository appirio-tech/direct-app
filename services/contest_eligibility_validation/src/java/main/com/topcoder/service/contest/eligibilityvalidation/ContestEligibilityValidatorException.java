/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibilityvalidation;

/**
 * <p>
 * ContestEligibilityValidatorException is thrown by ContestEligibilityValidator.
 * </p>
 * <p>
 * Thread Safety: This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ContestEligibilityValidatorException extends Exception {

    /**
     * Create the exception, call parent constructor with the same signature.
     */
    public ContestEligibilityValidatorException() {
        super();
    }

    /**
     * Create the exception, call parent constructor with the same signature.
     *
     * @param message
     *            the error message to set
     */
    public ContestEligibilityValidatorException(String message) {
        super(message);
    }

    /**
     * Create the exception, call parent constructor with the same signature.
     *
     * @param cause
     *            the inner cause exception to set
     */
    public ContestEligibilityValidatorException(Throwable cause) {
        super(cause);
    }

    /**
     * Create the exception, call parent constructor with the same signature.
     *
     * @param message
     *            the error message to set
     * @param cause
     *            the cause error to set
     */
    public ContestEligibilityValidatorException(String message, Throwable cause) {
        super(message, cause);
    }
}