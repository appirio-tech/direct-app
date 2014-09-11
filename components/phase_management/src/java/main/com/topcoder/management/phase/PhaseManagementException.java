/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

import com.topcoder.util.errorhandling.BaseException;

/**
 * Thrown by various {@link PhaseManager PhaseManager} methods when an error occurs. If the error was the result of
 * an internal exception (such as a persistence problem), the <code>PhaseManagementException</code> will have an
 * associated wrapped exception.
 *
 * @author AleaActaEst, RachaelLCook
 * @version 1.0
 */

public class PhaseManagementException extends BaseException {
    /**
     * Constructs a new exception with the specified message.
     *
     * @param message the reason for the exception
     */
    public PhaseManagementException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified message and wrapped exception.
     *
     * @param message the reason for the exception
     * @param ex the wrapped exception
     */
    public PhaseManagementException(String message, Throwable ex) {
        super(message, ex);
    }
}
