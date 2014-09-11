/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task;

/**
 * <p>
 * This exception is thrown in modifiable methods if the user isn't permitted to access the task/task list.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Exception is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public class PermissionException extends TaskManagementException {
    /**
     * <p>
     * The serial version id.
     * </p>
     */
    private static final long serialVersionUID = 6378965077902743498L;

    /**
     * <p>
     * Constructs a new <code>PermissionException</code> instance.
     * </p>
     */
    public PermissionException() {
        // empty
    }

    /**
     * <p>
     * Constructs a new <code>PermissionException</code> instance with error message.
     * </p>
     *
     * @param message
     *            the error message.
     */
    public PermissionException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructs a new <code>PermissionException</code> instance with inner cause.
     * </p>
     *
     * @param cause
     *            the inner cause.
     */
    public PermissionException(Throwable cause) {
        super(cause);
    }

    /**
     * <p>
     * Constructs a new <code>PermissionException</code> instance with error message and inner cause.
     * </p>
     *
     * @param message
     *            the error message.
     * @param cause
     *            the inner cause.
     */
    public PermissionException(String message, Throwable cause) {
        super(message, cause);
    }
}
