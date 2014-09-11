/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task;


/**
 * <p>
 * This exception indicates error with notification sending.
 * </p>
 * <p>
 * <b>Thread-Safety:</b>It's not thread-safe because its base class is not thread-safe.
 * </p>
 *
 * @author Mozgastik, TCSDEVELOPER
 * @version 1.0
 */
public class NotificationException extends TaskManagementException {

    /**
     * <p>
     * Constructs a new <code>NotificationException</code> instance.
     * </p>
     */
    public NotificationException() {
        // does nothing
    }

    /**
     * <p>
     * Constructs a new <code>NotificationException</code> instance with error message.
     * </p>
     *
     * @param message
     *            the error message.
     */
    public NotificationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructs a new <code>NotificationException</code> instance with inner cause.
     * </p>
     *
     * @param cause
     *            the inner cause.
     */
    public NotificationException(Throwable cause) {
        super(cause);
    }

    /**
     * <p>
     * Constructs a new <code>NotificationException</code> instance with error message and inner cause.
     * </p>
     *
     * @param message
     *            the error message.
     * @param cause
     *            the inner cause.
     */
    public NotificationException(String message, Throwable cause) {
        super(message, cause);
    }
}

