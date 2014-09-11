/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.gameplan;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

import javax.ejb.ApplicationException;

/**
 * <p>This exception is thrown by implementations of <code>GamePlanService</code> when some implementation specific
 * error occurred. Also this exception is used as a base class for other specific custom exceptions.</p>
 *
 * <p><b>Thread Safety</b>: This class is not thread safe because its base class is not thread safe.</p>
 *
 * @author saarixx, FireIce
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class GamePlanServiceException extends BaseCriticalException {
    /**
     * Creates a <code>GamePlanServiceException</code> instance with the given message.
     *
     * @param message the detailed error message of this exception
     */
    public GamePlanServiceException(String message) {
        super(message);
    }

    /**
     * Creates a <code>GamePlanServiceException</code> instance with the given message and cause.
     *
     * @param message the detailed error message of this exception
     * @param cause   the inner cause of this exception
     */
    public GamePlanServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a <code>GamePlanServiceException</code> instance with the given message and exception data.
     *
     * @param message the detailed error message of this exception
     * @param data    the exception data
     */
    public GamePlanServiceException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Creates a <code>GamePlanServiceException</code> instance with the given message, cause and exception data.
     *
     * @param message the detailed error message of this exception
     * @param cause   the inner cause of this exception
     * @param data    the exception data
     */
    public GamePlanServiceException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}

