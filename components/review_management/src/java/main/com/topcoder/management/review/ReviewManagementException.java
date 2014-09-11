/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review;

import com.topcoder.util.errorhandling.BaseException;

/**
 * This exception will be thrown by ReviewManager, ReviewPersistence and their
 * implementations if any error occurs during the management of review entities.
 * @author woodjhon, urtks
 * @version 1.0
 */
public class ReviewManagementException extends BaseException {

    /**
     * Create the instance with error message.
     * @param msg
     *            the error message
     */
    public ReviewManagementException(String msg) {
        super(msg);
    }

    /**
     * Create the instance with error message and cause exception.
     * @param msg
     *            the error message
     * @param cause
     *            the cause exception
     */
    public ReviewManagementException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
