/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

/**
 * <p>
 * Extends TeamManagerException. Thrown by the TeamManager removePosition and removeTeam methods if the positioned and
 * teamId are unknown, respectively.
 * </p>
 * <p>
 * Thread-Safety: exceptions are safe because they are immutable.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class UnknownEntityException extends TeamManagerException {

    /**
     * Creates a new exception instance with this error message.
     * @param msg
     *            error message
     */
    public UnknownEntityException(String msg) {
        super(msg);
    }

    /**
     * Creates a new exception instance with this error message and cause of error.
     * @param msg
     *            error message
     * @param cause
     *            cause of error.
     */
    public UnknownEntityException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
