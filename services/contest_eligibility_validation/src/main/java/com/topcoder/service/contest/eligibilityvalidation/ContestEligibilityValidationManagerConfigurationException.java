/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibilityvalidation;

/**
 * <p>
 * ContestEligibilityValidationManagerConfigurationException is thrown by
 * <code>ContestEligibilityValidationManagerBean</code> when any errors occurred in initialize method.
 * </p>
 * <p>
 * Thread Safety: This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ContestEligibilityValidationManagerConfigurationException extends RuntimeException {

    /**
     * Create the exception, call parent constructor with the same signature.
     */
    public ContestEligibilityValidationManagerConfigurationException() {
        super();
    }

    /**
     * Create the exception, call parent constructor with the same signature.
     *
     * @param message
     *            the error message to set
     */
    public ContestEligibilityValidationManagerConfigurationException(String message) {
        super(message);
    }

    /**
     * Create the exception, call parent constructor with the same signature.
     *
     * @param cause
     *            the inner cause exception to set
     */
    public ContestEligibilityValidationManagerConfigurationException(Throwable cause) {
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
    public ContestEligibilityValidationManagerConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}