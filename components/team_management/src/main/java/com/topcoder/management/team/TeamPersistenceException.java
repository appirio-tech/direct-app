/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * <p>
 * Extends BaseRuntimeException. It is thrown by all TeamManager and TeamPersistence methods if an unexpected system
 * error occurs. In InformixTeamPersistence, this would include any connection failures, or unexpected violations of
 * table or column constraints. And in TeamManagerImpl it could be an error with key generation.
 * </p>
 * <p>
 * Thread-Safety: exceptions are safe because they are immutable.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class TeamPersistenceException extends BaseRuntimeException {

    /**
     * Creates a new exception instance with this error message.
     * @param msg
     *            error message
     */
    public TeamPersistenceException(String msg) {
        super(msg);
    }

    /**
     * Creates a new exception instance with this error message and cause of error.
     * @param msg
     *            error message
     * @param cause
     *            cause of error.
     */
    public TeamPersistenceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
