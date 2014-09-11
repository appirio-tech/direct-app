/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service;

/**
 * <p>
 * This class extends <code>ProjectServicesException</code>. Called by the
 * <code>ProjectServicesImpl</code> constructors if a configuration-related error occurs, such as
 * a namespace not being recognized by <b>Config Manager</b>, or missing required values, or errors
 * while constructing the managers and services.
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
public class ConfigurationException extends ProjectServicesException {

    /**
     * <p>
     * Creates a new exception instance with this error message.
     * </p>
     *
     * @param msg
     *            the error message
     */
    public ConfigurationException(String msg) {
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
    public ConfigurationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
