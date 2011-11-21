/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.fees.services;

/**
 * The ContestFeeEntityNotFoundException will be thrown from the update and delete methods if the entity is not found in
 * the persistence.
 * 
 * 
 * Thread safety: The class is mutable and not thread safe.
 * 
 * @author winstips, TCSDEVELOPER
 * @version 1.0
 */
public class ContestFeeEntityNotFoundException extends ContestFeeServiceException {
    /**
     * Creates a new instance of this exception with the given message.
     * 
     * @param message
     *            - the detailed error message of this exception
     */
    public ContestFeeEntityNotFoundException(String message) {
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
    public ContestFeeEntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
