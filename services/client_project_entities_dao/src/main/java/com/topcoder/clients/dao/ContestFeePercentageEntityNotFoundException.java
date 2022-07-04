/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao;

/**
 * The ContestFeePercentageEntityNotFoundException will be thrown from the update and delete methods
 * if the entity is not found in the persistence.
 * 
 * 
 * Thread safety: The class is mutable and not thread safe.
 * 
 * @author minhu
 * @version 1.0 (Module Assembly - Contest Fee Based on % of Member Cost Admin Part)
 */
public class ContestFeePercentageEntityNotFoundException extends ContestFeePercentageServiceException {
    /**
     * Creates a new instance of this exception with the given message.
     * 
     * @param message
     *            - the detailed error message of this exception
     */
    public ContestFeePercentageEntityNotFoundException(String message) {
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
    public ContestFeePercentageEntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
