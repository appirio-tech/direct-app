/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

import com.topcoder.util.errorhandling.BaseException;

/**
 * Thrown by {@link PhasePersistence PhasePersistence} instances when an error occurs related to the persistent
 * store.
 *
 * @author AleaActaEst, RachaelLCook
 * @version 1.0
 */

public class PhasePersistenceException extends BaseException {
    /**
     * Constructs a new exception with the specified message and cause.
     *
     * @param message the reason for the exception
     * @param cause the internal exception that caused this exception
     */
    public PhasePersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified message.
     *
     * @param message the reason for the exception
     */
    public PhasePersistenceException(String message) {
        super(message);
    }
}
