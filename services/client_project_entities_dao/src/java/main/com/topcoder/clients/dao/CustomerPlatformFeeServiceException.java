/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao;

import com.topcoder.util.errorhandling.BaseCriticalException;

/**
 * This exception is a base class for all other custom checked exceptions related to CustomerPlatformFee.
 * It is never thrown directly, subclasses are used instead.
 * 
 * Thread safety: The class is mutable and not thread safe.
 * 
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - Add Monthly Platform Fee Feature to Admin Page)
 */
public class CustomerPlatformFeeServiceException extends BaseCriticalException {
    /**
     * Creates a new instance of this exception with the given message.
     * 
     * @param message
     *            - the detailed error message of this exception
     */
    public CustomerPlatformFeeServiceException(String message) {
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
    public CustomerPlatformFeeServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
