/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

/**
 * <p>
 * Extends TeamManagerException. Thrown by the TeamManager addPosition and updatePosition methods if the position
 * violates business rules. These would involve, in the TeamManagerImpl, names and descriptions of invalid length, or
 * the percentage in the context of all other positions in the team overflowing 100.
 * </p>
 * <p>
 * Thread-Safety: exceptions are safe because they are immutable.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class InvalidPositionException extends TeamManagerException {

    /**
     * Creates a new exception instance with this error message.
     * @param msg
     *            error message
     */
    public InvalidPositionException(String msg) {
        super(msg);
    }

    /**
     * Creates a new exception instance with this error message and cause of error.
     * @param msg
     *            error message
     * @param cause
     *            cause of error.
     */
    public InvalidPositionException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
