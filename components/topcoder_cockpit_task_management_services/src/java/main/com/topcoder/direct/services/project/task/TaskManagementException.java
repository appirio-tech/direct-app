/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task;

/**
 * <p>
 * This is the base exception for checked exceptions of this application.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Exception is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public class TaskManagementException extends Exception {
    /**
     * <p>
     * The serial version id.
     * </p>
     */
    private static final long serialVersionUID = 7125152408644361137L;

    /**
     * <p>
     * Constructs a new <code>TaskManagementException</code> instance.
     * </p>
     */
    public TaskManagementException() {
        // empty
    }

    /**
     * <p>
     * Constructs a new <code>TaskManagementException</code> instance with error message.
     * </p>
     *
     * @param message
     *            the error message.
     */
    public TaskManagementException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructs a new <code>TaskManagementException</code> instance with inner cause.
     * </p>
     *
     * @param cause
     *            the inner cause.
     */
    public TaskManagementException(Throwable cause) {
        super(cause);
    }

    /**
     * <p>
     * Constructs a new <code>TaskManagementException</code> instance with error message and inner cause.
     * </p>
     *
     * @param message
     *            the error message.
     * @param cause
     *            the inner cause.
     */
    public TaskManagementException(String message, Throwable cause) {
        super(message, cause);
    }
}
