/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence;

/**
 * <p>
 * The DeliverablePersistenceException indicates that there was an error
 * accessing or updating a persisted resource store. This exception is used to
 * wrap the internal error that occurs when accessing the persistence store. For
 * example, in the SqlDeliverablePersistence implementation it is used to wrap
 * SqlExceptions.
 * </p>
 * <p>
 * This exception is initially thrown in DeliverablePersistence implementations
 * and from there passes through DeliverableManager implementations and back to
 * the caller. It is also thrown directly by some DeliverableManager
 * implementations.
 * </p>
 * @author aubergineanode, TCSDEVELOPER
 * @version 1.0.2
 */
public class DeliverablePersistenceException extends PersistenceException {
    /**
     * <p>
     * The serial version id.
     * </p>
     */
    private static final long serialVersionUID = 5741139782950193016L;

    /**
     * Creates a new DeliverablePersistenceException.
     * @param message
     *            Explanation of error, can be null
     */
    public DeliverablePersistenceException(String message) {
        super(message);
    }

    /**
     * Creates a new DeliverablePersistenceException.
     * @param message
     *            Explanation of error, can be null
     * @param cause
     *            Underlying cause of error, can be null
     */
    public DeliverablePersistenceException(String message, Exception cause) {
        super(message, cause);
    }
}
