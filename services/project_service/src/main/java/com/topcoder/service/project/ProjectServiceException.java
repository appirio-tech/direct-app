/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import com.topcoder.util.errorhandling.BaseNonCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown to indicate a generic error in the component. It acts as a base for other exceptions of the
 * component as well as a convenience for applications using this component.
 * </p>
 * <p>
 * It extends <code>{@link BaseNonCriticalException}</code> to get the functionality of error messages, cause wrapping
 * and exception data. We also declare four constructors that map onto super class constructors of the same signature.
 * </p>
 * <p>
 * <b>Thread Safety</b>: This class is thread safe as it has no state and the super class is thread safe.
 * </p>
 *
 * @author humblefool, FireIce
 * @version 1.0
 */
public class ProjectServiceException extends BaseNonCriticalException {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = -3184275871797615398L;

    /**
     * <p>
     * Constructs this exception without an error message or cause or exception data.
     * </p>
     */
    public ProjectServiceException() {
        // super() is implicitly called.
    }

    /**
     * <p>
     * Constructs this exception with an error message.
     * </p>
     *
     * @param message
     *            The error message describing this exception. Possibly null/empty.
     */
    public ProjectServiceException(String message) {
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
    public ProjectServiceException(String message, Throwable cause) {
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
    public ProjectServiceException(String message, Throwable cause, ExceptionData exceptionData) {
        super(message, cause, exceptionData);
    }
}
