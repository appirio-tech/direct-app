/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibilityvalidation;

/**
 * <p>
 * ContestEligibilityValidationManagerException is thrown by ContestEligibilityValidationManager.
 * </p>
 * <p>
 * ContestEligibilityValidationManagerException is a root exception of the
 * <code>UnsupportedContestEligibilityValidatiorException</code>.
 * </p>
 * <p>
 * Thread Safety: This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ContestEligibilityValidationManagerException extends Exception {

    /**
     * Create the exception, call parent constructor with the same signature.
     */
    public ContestEligibilityValidationManagerException() {
        super();
    }

    /**
     * Create the exception, call parent constructor with the same signature.
     *
     * @param message
     *            the error message to set
     */
    public ContestEligibilityValidationManagerException(String message) {
        super(message);
    }

    /**
     * Create the exception, call parent constructor with the same signature.
     *
     * @param cause
     *            the inner cause exception to set
     */
    public ContestEligibilityValidationManagerException(Throwable cause) {
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
    public ContestEligibilityValidationManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}