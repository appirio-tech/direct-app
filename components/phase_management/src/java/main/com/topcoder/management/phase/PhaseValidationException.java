/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

import com.topcoder.util.errorhandling.BaseException;

/**
 * Thrown by {@link PhaseValidator#validate PhaseValidator.validate()} when an invalid phase is encountered.
 *
 * @author AleaActaEst, RachaelLCook
 * @version 1.0
 */

public class PhaseValidationException extends BaseException {
    /**
     * Constructs a new exception with the specified message and cause.
     *
     * @param message the reason for the exception
     * @param cause the internal exception that caused this exception
     */
    public PhaseValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified message.
     *
     * @param message the reason for the exception
     */
    public PhaseValidationException(String message) {
        super(message);
    }
}
