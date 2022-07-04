/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * It is used by the by some service methods to signal that a requested entity has not been found.
 * </p>
 * <p>
 * <b>Thread Safety:</b> Exceptions are not thread safe and they are not expected to be used concurrently.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class EntityNotFoundException extends ProjectMilestoneManagementException {
    /**
     * <p>
     * Creates a new instance of this exception with the given message.
     * </p>
     *
     * @param message
     *            the detailed error message of this exception
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance of this exception with the given message and cause.
     * </p>
     *
     * @param message
     *            the detailed error message of this exception
     * @param cause
     *            the inner cause of this exception
     */
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Creates a new instance of this exception with the given message and exception data.
     * </p>
     *
     * @param message
     *            the detailed error message of this exception
     * @param data
     *            the exception data
     */
    public EntityNotFoundException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * <p>
     * Creates a new instance of this exception with the given message, cause and exception data.
     * </p>
     *
     * @param message
     *            the detailed error message of this exception
     * @param cause
     *            the inner cause of this exception
     * @param data
     *            the exception data
     */
    public EntityNotFoundException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
