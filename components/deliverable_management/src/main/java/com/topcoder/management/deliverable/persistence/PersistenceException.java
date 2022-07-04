/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * The PersistenceException indicates that there was an error accessing or
 * updating a persisted resource store. This exception is used to wrap the
 * internal error that occurs when accessing the persistence store.
 * </p>
 * @author aubergineanode, TCSDEVELOPER
 * @version 1.0.2
 */
public class PersistenceException extends BaseException {
    /**
     * <p>
     * The serial version id.
     * </p>
     */
    private static final long serialVersionUID = -1416432981312993605L;

    /**
     * Creates a new PersistenceException.
     * @param message
     *            Explanation of error, can be null
     */
    public PersistenceException(String message) {
        super(message);
    }

    /**
     * Creates a new PersistenceException.
     * @param message
     *            Explanation of error, can be null
     * @param cause
     *            Underlying cause of error, can be null
     */
    public PersistenceException(String message, Exception cause) {
        super(message, cause);
    }
}
