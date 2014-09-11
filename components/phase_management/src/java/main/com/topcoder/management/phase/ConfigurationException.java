/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

import com.topcoder.util.errorhandling.BaseException;

/**
 * Thrown by the {@link DefaultPhaseManager#DefaultPhaseManager(String) DefaultPhaseManager(String namespace)}
 * constructor if a configuration parameter is missing or invalid.
 *
 * @author AleaActaEst, RachaelLCook
 * @version 1.0
 */
public class ConfigurationException extends BaseException {
    /**
     * Constructs a new exception with the specified message.
     *
     * @param message the reason for the exception
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified message and wrapped exception.
     *
     * @param message the reason for the exception
     * @param ex the wrapped exception
     */
    public ConfigurationException(String message, Throwable ex) {
        super(message, ex);
    }
}
