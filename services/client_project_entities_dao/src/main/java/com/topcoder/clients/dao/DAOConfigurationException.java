/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao;

import javax.ejb.ApplicationException;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This runtime exception signals an issue if the configured value is invalid
 * (in this design, when EntityManager is not configured or if it is invalid -
 * null).
 * </p>
 * <p>
 * Wraps the underlying exceptions when using the configured values.
 * </p>
 * <p>
 * <strong>THREAD SAFETY:</strong> This exception is not thread safe because
 * parent exception is not thread safe. The application should handle this
 * exception in a thread-safe manner.
 * </p>
 *
 * @author Mafy, TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class DAOConfigurationException extends BaseRuntimeException {
    /**
     * The serial version id of this class.
     */
    private static final long serialVersionUID = 5911416335410742883L;

    /**
     * Constructs a new 'DAOConfigurationException' instance with the given
     * message.
     *
     * @param message
     *                the exception message
     */
    public DAOConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs a new 'DAOConfigurationException' exception with the given
     * message and cause.
     *
     *
     * @param message
     *                the exception message
     * @param cause
     *                the exception cause
     */
    public DAOConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new 'DAOConfigurationException' exception with the given
     * message and data.
     *
     *
     * @param message
     *                the exception message
     * @param data
     *                the additional exception data
     */
    public DAOConfigurationException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Constructs a new 'DAOConfigurationException' exception with the given
     * message, cause, and data.
     *
     *
     * @param message
     *                the exception message
     * @param cause
     *                the exception cause
     * @param data
     *                the additional exception data
     */
    public DAOConfigurationException(String message, Throwable cause,
            ExceptionData data) {
        super(message, cause, data);
    }
}
