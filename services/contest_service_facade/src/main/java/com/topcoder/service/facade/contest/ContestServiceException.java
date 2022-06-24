/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.contest;

import javax.ejb.ApplicationException;


/**
 * <p>
 * This exception is thrown if an error occurs during the delegate methods of
 * the concrete service..
 * </p>
 *
 * <p>
 * <strong>Thread safety: </strong>This class is not thread safe.
 * </p>
 *
 * @author PE
 * @version 1.0
 *
 * @since TopCoder Service Layer Integration 3 Assembly
 */
@ApplicationException(rollback = true)
public class ContestServiceException extends Exception {
    /**
     * <p>
     * Constructor with the error message.
     * </p>
     *
     * @param message
     *            useful message containing a description of why the exception
     *            was thrown - may be null.
     */
    public ContestServiceException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructor with the error message and inner cause.
     * </p>
     *
     * <p>
     * </p>
     *
     * @param message
     *            useful message containing a description of why the exception
     *            was thrown - may be null.
     * @param cause
     *            the inner cause.
     */
    public ContestServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
