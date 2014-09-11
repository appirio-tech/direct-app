/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.persistence;

import javax.ejb.ApplicationException;

import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * <p>
 * This is a exception for ProjectPersistenceBean.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException
public class ProjectPersistenceBeanException extends BaseRuntimeException {
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
    public ProjectPersistenceBeanException(String message, Throwable cause) {
        super(message, cause);
    }
}
