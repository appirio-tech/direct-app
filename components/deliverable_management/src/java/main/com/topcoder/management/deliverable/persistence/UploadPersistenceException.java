/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence;

/**
 * <p>
 * The UploadPersistenceException indicates that there was an error accessing or
 * updating a persisted resource store. This exception is used to wrap the
 * internal error that occurs when accessing the persistence store. For example,
 * in the SqlUploadPersistence implementation it is used to wrap SqlExceptions.
 * </p>
 * <p>
 * This exception is initially thrown in UploadPersistence implementations and
 * from there passes through UploadManager implementations and back to the
 * caller. It is also thrown directly by some UploadManager implementations.
 * </p>
 * @author aubergineanode, TCSDEVELOPER
 * @version 1.0.2
 */
public class UploadPersistenceException extends PersistenceException {
    /**
     * <p>
     * The serial version id.
     * </p>
     */
    private static final long serialVersionUID = 839767261408861168L;

    /**
     * Creates a new UploadPersistenceException.
     * @param message
     *            Explanation of error, can be null
     */
    public UploadPersistenceException(String message) {
        super(message);
    }

    /**
     * Creates a new UploadPersistenceException.
     * @param message
     *            Explanation of error, can be null
     * @param cause
     *            Underlying cause of error, can be null
     */
    public UploadPersistenceException(String message, Exception cause) {
        super(message, cause);
    }
}
