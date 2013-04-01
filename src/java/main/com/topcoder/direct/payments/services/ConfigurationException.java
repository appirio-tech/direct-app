/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.payments.services;

import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * <p>
 * This exception class stands for the configuration exception.
 * </p>
 * 
 * <p>
 * <strong>Thread Safety: </strong> Exceptions are not thread safe and they are
 * not expected to be used concurrently.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TopCoder Direct Member Payments Dashboard
 *          Backend Assembly)
 * @since 1.0
 */
public class ConfigurationException extends BaseRuntimeException {

    /**
     * <p>
     * The generated serial version uid.
     * </p>
     */
    private static final long serialVersionUID = -4797647036835531198L;

    /**
     * Constructor with exception message.
     * 
     * @param message
     *            exception message
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructor with error message and cause.
     * 
     * @param message
     *            error message
     * @param cause
     *            exception cause
     */
    public ConfigurationException(String message, Throwable cause) {
    }
}
