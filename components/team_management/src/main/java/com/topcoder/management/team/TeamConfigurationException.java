/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * <p>
 * Extends BaseRuntimeException. Constitutes a general configuration exception that all implementations that require
 * such configuration can throw it something goes wrong during that configuration. Called by the TeamManagerImpl and
 * InformixTeamPersistence namespace-backed constructors, if a configuration-related error occurs, such as a namespace
 * not being recognized by Config Manager, or missing required values, etc.
 * </p>
 * <p>
 * Thread-Safety: exceptions are safe because they are immutable.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class TeamConfigurationException extends BaseRuntimeException {

    /**
     * Creates a new exception instance with this error message.
     * @param msg
     *            error message
     */
    public TeamConfigurationException(String msg) {
        super(msg);
    }

    /**
     * Creates a new exception instance with this error message and cause of error.
     * @param msg
     *            error message
     * @param cause
     *            cause of error.
     */
    public TeamConfigurationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
