/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.fees.services;

import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * This exception is thrown if some configuration specific error occurs.
 * 
 * Thread safety: The class is mutable and not thread safe.
 * 
 * @author winstips, TCSDEVELOPER
 * @version 1.0
 */
public class ContestFeeConfigurationException extends BaseRuntimeException {
    /**
     * Creates a new instance of this exception with the given message.
     * 
     * @param message
     *            - the detailed error message of this exception
     */
    public ContestFeeConfigurationException(String message) {
        super(message);
    }

    /**
     * Creates a new instance of this exception with the given message and cause.
     * 
     * @param message
     *            - the detailed error message of this exception
     * @param cause
     *            - the inner cause of this exception
     */
    public ContestFeeConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
