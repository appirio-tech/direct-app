/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task;

/**
 * <p>
 * This exception is thrown for any configuration error of this application.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Exception is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public class TaskManagementConfigurationException extends RuntimeException {
    /**
     * <p>
     * The serial version id.
     * </p>
     */
    private static final long serialVersionUID = -1777545754399960549L;

    /**
     * <p>
     * Constructs a new <code>TaskManagementConfigurationException</code> instance.
     * </p>
     */
    public TaskManagementConfigurationException() {
        // empty
    }

    /**
     * <p>
     * Constructs a new <code>TaskManagementConfigurationException</code> instance with error message.
     * </p>
     *
     * @param message
     *            the error message.
     */
    public TaskManagementConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructs a new <code>TaskManagementConfigurationException</code> instance with inner cause.
     * </p>
     *
     * @param cause
     *            the inner cause.
     */
    public TaskManagementConfigurationException(Throwable cause) {
        super(cause);
    }

    /**
     * <p>
     * Constructs a new <code>TaskManagementConfigurationException</code> instance with error message and inner cause.
     * </p>
     *
     * @param message
     *            the error message.
     * @param cause
     *            the inner cause.
     */
    public TaskManagementConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
