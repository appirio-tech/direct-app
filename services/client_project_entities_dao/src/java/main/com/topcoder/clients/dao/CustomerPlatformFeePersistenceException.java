/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao;

/**
 * The CustomerPlatformFeePersistenceException will be thrown is any persistence related error occurs.
 * 
 * Thread safety: The class is mutable and not thread safe.
 * 
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - Add Monthly Platform Fee Feature to Admin Page)
 */
public class CustomerPlatformFeePersistenceException extends CustomerPlatformFeeServiceException {
    /**
     * Creates a new instance of this exception with the given message.
     * 
     * @param message
     *            - the detailed error message of this exception
     */
    public CustomerPlatformFeePersistenceException(String message) {
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
    public CustomerPlatformFeePersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
