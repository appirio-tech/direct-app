/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service;

import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * <p>
 * This class extends <code>BaseRuntimeException</code>. Represents a base exception for all
 * other exceptions defined in this component. It is thrown by all <code>ProjectServices</code>
 * methods.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong> This class is not thread safe since the parent is not thread
 * safe, but the thread safety is not necessary for the exceptions.
 * </p>
 *
 * @author argolite, moonli
 * @author fabrizyo, znyyddf
 * @version 1.1
 * @since 1.0
 */
@SuppressWarnings("serial")
public class ProjectServicesException extends BaseRuntimeException {
    /**
     * <p>
     * Creates a new exception instance with this error message.
     * </p>
     *
     * @param msg
     *            the error message
     */
    public ProjectServicesException(String msg) {
        super(msg);
    }

    /**
     * <p>
     * Creates a new exception instance with this error message and cause of error.
     * </p>
     *
     * @param msg
     *            the error message
     * @param cause
     *            cause of error
     */
    public ProjectServicesException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
