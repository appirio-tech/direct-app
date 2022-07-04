/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service;

import com.topcoder.util.errorhandling.BaseCriticalException;

import javax.ejb.ApplicationException;

/**
  * <p>This exception is thrown if an error occurs during interaction with the persistence.</p>
  * <p><strong>Thread safety: </strong></p> <p>This class is not thread safe.</p>
  *
  * @author caru, Retunsky
  * @version 1.0
  */
@ApplicationException(rollback = true)
public class PersistenceException extends BaseCriticalException {
    /**
     * <p>Constructor with the error message.</p>
     *
     * @param message useful message containing a description of why the exception was thrown - may be null.
     */
    public PersistenceException(String message) {
        super(message);
    }

    /**
     * <p>Constructor with the error message and inner cause.<p>
     *
     * @param message useful message containing a description of why the exception was thrown - may be null.
     * @param cause   the inner cause.
     */
    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}