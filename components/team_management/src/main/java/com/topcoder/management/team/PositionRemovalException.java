/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

/**
 * <p>
 * Extends TeamManagerException. Thrown by the TeamManager removePosition and method if the attempt to remove a
 * position from a team violates some business rules. These would involve, in the TeamManagerImpl, the position being
 * published or filled, or the team being finalized.
 * </p>
 * <p>
 * Thread-Safety: exceptions are safe because they are immutable.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class PositionRemovalException extends TeamManagerException {

    /**
     * Creates a new exception instance with this error message.
     * @param msg
     *            error message
     */
    public PositionRemovalException(String msg) {
        super(msg);
    }

    /**
     * Creates a new exception instance with this error message and cause of error.
     * @param msg
     *            error message
     * @param cause
     *            cause of error.
     */
    public PositionRemovalException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
