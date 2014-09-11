/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This is the base exception of this component. All other exceptions should extend from it.
 * </p>
 *
 * @author colau, haozhangr
 * @version 1.0
 */
public class ScreeningManagementException extends BaseException {

    /**
     * <p>
     * Creates the exception with the specified message.
     * </p>
     *
     *
     * @param message
     *            the message describing the exception
     */
    public ScreeningManagementException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates the exception with the specified message and cause.
     * </p>
     *
     *
     * @param message
     *            the message describing the exception
     * @param cause
     *            the cause of the exception
     */
    public ScreeningManagementException(String message, Throwable cause) {
        super(message, cause);
    }
}
