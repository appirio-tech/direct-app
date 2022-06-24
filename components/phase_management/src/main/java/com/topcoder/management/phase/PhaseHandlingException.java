/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

/**
 * Thrown by various {@link PhaseManager PhaseManager} methods when an error occurs. If the error was the result of
 * an internal exception (such as a persistence problem), the <code>PhaseManagementException</code> will have an
 * associated wrapped exception.
 *
 * @author RachaelLCook
 * @version 1.0
 */

public class PhaseHandlingException extends PhaseManagementException {
    /**
     * Constructs a new exception with the specified message.
     *
     * @param message the reason for the exception
     */
    public PhaseHandlingException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified message and wrapped exception.
     *
     * @param message the reason for the exception
     * @param ex the wrapped exception
     */
    public PhaseHandlingException(String message, Throwable ex) {
        super(message, ex);
    }
}
