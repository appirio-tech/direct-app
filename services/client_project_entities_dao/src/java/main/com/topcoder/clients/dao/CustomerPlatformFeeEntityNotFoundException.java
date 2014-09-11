/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao;

/**
 * The CustomerPlatformFeeEntityNotFoundException will be thrown from the update and delete methods
 * if the entity is not found in the persistence.
 * 
 * 
 * Thread safety: The class is mutable and not thread safe.
 * 
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - Add Monthly Platform Fee Feature to Admin Page)
 */
public class CustomerPlatformFeeEntityNotFoundException extends CustomerPlatformFeeServiceException {
    /**
     * Creates a new instance of this exception with the given message.
     * 
     * @param message
     *            - the detailed error message of this exception
     */
    public CustomerPlatformFeeEntityNotFoundException(String message) {
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
    public CustomerPlatformFeeEntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
