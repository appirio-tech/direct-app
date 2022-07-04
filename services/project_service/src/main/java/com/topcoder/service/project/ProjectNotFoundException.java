/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown to indicate that a required project was not found. It is thrown by those methods of the
 * ProjectPersistence contract which require a project entity.
 * </p>
 * <p>
 * It extends <code>{@link ProjectServiceException}</code> to get the functionality of error messages, cause wrapping
 * and exception data. We also declare four constructors that map onto super class constructors of the same signature.
 * </p>
 * <p>
 * <b>Thread Safety</b>: This class is thread safe as it has no state and the super class is thread safe.
 * </p>
 *
 * @author humblefool, FireIce
 * @version 1.0
 */
public class ProjectNotFoundException extends PersistenceException {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = -2715677348421096324L;

    /**
     * <p>
     * Constructs this exception without an error message or cause or exception data.
     * </p>
     */
    public ProjectNotFoundException() {
    }

    /**
     * <p>
     * Constructs this exception with an error message.
     * </p>
     *
     * @param message
     *            The error message describing this exception. Possibly null/empty.
     */
    public ProjectNotFoundException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructs this exception with an error message and a cause.
     * </p>
     *
     * @param message
     *            The error message describing this exception. Possibly null/empty.
     * @param cause
     *            The cause of this exception. Possibly null.
     */
    public ProjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Constructs this exception with an error message and a cause and exception data.
     * </p>
     *
     * @param message
     *            The error message describing this exception. Possibly null/empty.
     * @param cause
     *            The cause of this exception. Possibly null.
     * @param exceptionData
     *            The exception data. Possibly null.
     */
    public ProjectNotFoundException(String message, Throwable cause, ExceptionData exceptionData) {
        super(message, cause, exceptionData);
    }
}
