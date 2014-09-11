/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao;

import javax.ejb.ApplicationException;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is the base exception for all exceptions raised from
 * operations performed on entities (in this design, entities inherited from
 * AuditableEntity) from this design (excerpt loading of the configurations).
 * </p>
 * <p>
 * This exception wraps exceptions raised from persistence, from usage of the
 * J2EE utilities or used TopCoder components.
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
public class DAOException extends BaseCriticalException {
    /**
     * The serial version id of this class.
     */
    private static final long serialVersionUID = 888144206957496686L;

    /**
     * Constructs a new 'DAOException' instance with the given message.
     *
     * @param message
     *                the exception message
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Constructs a new 'DAOException' exception with the given message and
     * cause.
     *
     * @param message
     *                the exception message
     * @param cause
     *                the exception cause
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new 'DAOException' exception with the given message and
     * data.
     *
     * @param message
     *                the exception message
     * @param data
     *                the additional exception data
     */
    public DAOException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Constructs a new 'DAOException' exception with the given message, cause,
     * and data.
     *
     *
     * @param message
     *                the exception message
     * @param cause
     *                the exception cause
     * @param data
     *                the additional exception data
     */
    public DAOException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
