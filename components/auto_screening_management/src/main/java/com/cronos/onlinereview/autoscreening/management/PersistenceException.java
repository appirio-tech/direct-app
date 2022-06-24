/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

/**
 * <p>
 * This exception indicates any error related to persistence. It is used to wrap exceptions such as
 * SQLException and IOException.
 * </p>
 * <p>
 * It is thrown from the ScreeningManager methods.
 * </p>
 *
 * @author colau, haozhangr
 * @version 1.0
 */
public class PersistenceException extends ScreeningManagementException {

    /**
     * <p>
     * Creates the exception with the specified message.
     * </p>
     *
     *
     * @param message
     *            the message describing the exception
     */
    public PersistenceException(String message) {
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
    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
