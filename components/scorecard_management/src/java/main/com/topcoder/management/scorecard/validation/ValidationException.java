/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.validation;

import com.topcoder.util.errorhandling.BaseException;

/**
 * Represents an exception related to validating scorecard. Inner exception should be provided to give more details
 * about the error. It is used in classes of the validation package.<br>
 * Thread safety: This class is immutable and thread safe.
 * @author tuenm, zhuzeyuan
 * @version 1.0.1
 */
public class ValidationException extends BaseException {

    /**
     * Create a new ValidationException instance with the specified error message.
     * @param message
     *            the error message of the exception
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Create a new ValidationException instance with the specified error message and inner exception.
     * @param message
     *            the error message of the exception
     * @param cause
     *            the inner exception
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
