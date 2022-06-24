/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * The ResourcePersistenceException indicates that there was an error accessing
 * or updating a persisted resource store. This exception is used to wrap the
 * internal error that occurs when accessing the persistence store. For example,
 * in the SqlResourcePersistence implementation it is used to wrap
 * SqlExceptions.
 * </p>
 *
 * <p>
 * This exception is initially thrown in ResourcePersistence implementations and
 * from there passes through ResourceManager implementations and back to the
 * caller. It is also thrown directly by some ResourceManager implementations.
 * </p>
 *
 * @author aubergineanode, kinfkong
 * @version 1.0
 */
public class ResourcePersistenceException extends BaseException {

    /**
     * <p>
     * Creates a ResourcePersistenceException with the given message providing the details of
     * the error occurred.
     * </p>
     *
     * @param message the message for this exception providing the details of
     *        the error occurred
     */
    public ResourcePersistenceException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a ResourcePersistenceException with the given message and cause.
     * </p>
     *
     * @param message the message for this exception providing the details of
     *        the error occurred
     * @param cause an exception representing the original cause of this exception
     */
    public ResourcePersistenceException(String message, Exception cause) {
        super(message, cause);
    }
}
