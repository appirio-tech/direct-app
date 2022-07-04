/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.exception;

/**
 * <p>
 * This is a common exception to be used in direct application.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Exceptions are not thread safe and they are not expected to be used concurrently.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 * @since Direct - View/Edit/Activate Studio Contests Assembly
 */
public class DirectException extends Exception {
    /**
     * <p>
     * The generated serial version uid.
     * </p>
     */
    private static final long serialVersionUID = 5678978773444465687L;

    /**
     * <p>
     * Create a new DirectException with error message.
     * </p>
     *
     * @param message the error message to set
     */
    public DirectException(String message) {
        super(message);
    }

    /**
     * <p>
     * Create a new DirectException with error message and root cause.
     * </p>
     *
     * @param message the error message to set
     * @param cause the chained cause exception
     */
    public DirectException(String message, Throwable cause) {
        super(message, cause);
    }
}
