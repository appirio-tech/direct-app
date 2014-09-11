/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao;

import javax.ejb.ApplicationException;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception signals an issue if the needed entity (in this design,
 * entities inherited from AuditableEntity) can not be found. The additional
 * details about the missing entity can be specified in the data field from the
 * constructor.
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
public class EntityNotFoundException extends DAOException {
    /**
     * The serial version id of this class.
     */
    private static final long serialVersionUID = -8114941005997328686L;

    /**
     * Constructs a new 'EntityNotFoundException' exception with the given
     * message.
     *
     * @param message
     *                the exception message
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new 'EntityNotFoundException' exception with the given
     * message and cause.
     *
     * @param message
     *                the exception message
     * @param cause
     *                the exception cause
     */
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new 'EntityNotFoundException' exception with the given
     * message and data.
     *
     *
     * @param message
     *                the exception message
     * @param data
     *                the additional exception data
     */
    public EntityNotFoundException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Constructs a new 'EntityNotFoundException' exception with the given
     * message, cause and data.
     *
     *
     * @param message
     *                the exception message
     * @param cause
     *                the exception cause
     * @param data
     *                the additional exception data
     */
    public EntityNotFoundException(String message, Throwable cause,
            ExceptionData data) {
        super(message, cause, data);
    }
}
