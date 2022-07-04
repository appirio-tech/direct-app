/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

/**
 * <p>
 * Extends TeamManagerException. Thrown by the TeamManager createTeam and updateTeam methods if the team violates
 * business rules. These would involve, in the TeamManagerImpl, names and descriptions of invalid length, or being
 * created as finalized.
 * </p>
 * <p>
 * Thread-Safety: exceptions are safe because they are immutable.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class InvalidTeamException extends TeamManagerException {

    /**
     * Creates a new exception instance with this error message.
     * @param msg
     *            error message
     */
    public InvalidTeamException(String msg) {
        super(msg);
    }

    /**
     * Creates a new exception instance with this error message and cause of error.
     * @param msg
     *            error message
     * @param cause
     *            cause of error.
     */
    public InvalidTeamException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
