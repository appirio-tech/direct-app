/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task;

/**
 * <p>
 * This exception is thrown in update / delete method if given entity is not found.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Exception is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public class EntityNotFoundException extends PersistenceException {
    /**
     * <p>
     * The serial version id.
     * </p>
     */
    private static final long serialVersionUID = 4314341295609435285L;

    /**
     * <p>
     * Constructs a new <code>EntityNotFoundException</code> instance.
     * </p>
     */
    public EntityNotFoundException() {
        // empty
    }

    /**
     * <p>
     * Constructs a new <code>EntityNotFoundException</code> instance with error message.
     * </p>
     *
     * @param message
     *            the error message.
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructs a new <code>EntityNotFoundException</code> instance with inner cause.
     * </p>
     *
     * @param cause
     *            the inner cause.
     */
    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * <p>
     * Constructs a new <code>EntityNotFoundException</code> instance with error message and inner cause.
     * </p>
     *
     * @param message
     *            the error message.
     * @param cause
     *            the inner cause.
     */
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
