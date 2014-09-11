/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao;

/**
 * The ContestFeePercentagePersistenceException will be thrown is any persistence related error occurs.
 * 
 * Thread safety: The class is mutable and not thread safe.
 * 
 * @author minhu
 * @version 1.0 (Module Assembly - Contest Fee Based on % of Member Cost Admin Part)
 */
public class ContestFeePercentagePersistenceException extends ContestFeePercentageServiceException {
    /**
     * Creates a new instance of this exception with the given message.
     * 
     * @param message
     *            - the detailed error message of this exception
     */
    public ContestFeePercentagePersistenceException(String message) {
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
    public ContestFeePercentagePersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
