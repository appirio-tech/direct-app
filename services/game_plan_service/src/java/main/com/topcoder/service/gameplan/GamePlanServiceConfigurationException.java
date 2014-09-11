/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.gameplan;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>This exception is thrown by <code>GamePlanServiceBean</code> when error occurs while initializing this bean.</p>
 *
 * <p><code>Thread Safety</code>: This class is not thread safe because its base class is not thread safe.</p>
 *
 * @author saarixx, FireIce
 * @version 1.0
 */
public class GamePlanServiceConfigurationException extends BaseRuntimeException {
    /**
     * Creates a <code>GamePlanServiceConfigurationException</code> instance with the given message.
     *
     * @param message the detailed error message of this exception
     */
    public GamePlanServiceConfigurationException(String message) {
        super(message);
    }

    /**
     * Creates a <code>GamePlanServiceConfigurationException</code> instance with the given message and cause.
     *
     * @param message the detailed error message of this exception
     * @param cause   the inner cause of this exception
     */
    public GamePlanServiceConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a <code>GamePlanServiceConfigurationException</code> instance with the given message and exception data.
     *
     * @param message the detailed error message of this exception
     * @param data    the exception data
     */
    public GamePlanServiceConfigurationException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Creates a <code>GamePlanServiceConfigurationException</code> instance with the given message, cause and exception
     * data.
     *
     * @param message the detailed error message of this exception
     * @param cause   the inner cause of this exception
     * @param data    the exception data
     */
    public GamePlanServiceConfigurationException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}

