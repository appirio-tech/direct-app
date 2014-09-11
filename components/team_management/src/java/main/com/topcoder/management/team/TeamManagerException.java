/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * Extends BaseException. Represents a base exception for all other business-related management exceptions defined in
 * this component. This would include invalid team and position field values, as well as an illegal attempt to remove
 * a position. These are presented by the InvalidTeamException, InvalidPositionException, and
 * PositionRemovalException, classes respectively.
 * </p>
 * <p>
 * Thread-Safety: exceptions are safe because they are immutable.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class TeamManagerException extends BaseException {

    /**
     * Creates a new exception instance with this error message.
     * @param msg
     *            error message
     */
    public TeamManagerException(String msg) {
        super(msg);
    }

    /**
     * Creates a new exception instance with this error message and cause of error.
     * @param msg
     *            error message
     * @param cause
     *            cause of error.
     */
    public TeamManagerException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
