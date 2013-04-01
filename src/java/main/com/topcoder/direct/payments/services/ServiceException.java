/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.payments.services;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception is thrown while error occurs in this module.
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
public class ServiceException extends BaseException {

    /**
     * <p>
     * The generated serial version uid.
     * </p>
     */
    private static final long serialVersionUID = -511549519296296759L;

    /**
     * Constructor with error message.
     * 
     * @param message
     *            error message
     */
    public ServiceException(String message) {
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
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
