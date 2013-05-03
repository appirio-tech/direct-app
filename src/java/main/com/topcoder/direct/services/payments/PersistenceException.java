/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.payments;

/**
 * <p>
 * This class stands for the exception thrown when persistence error occurs.
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
public class PersistenceException extends ServiceException {

    /**
     * <p>
     * The generated serial version uid.
     * </p>
     */
    private static final long serialVersionUID = -8557757109347699116L;

    /**
     * Constructor with error message.
     * 
     * @param message
     *            error message
     */
    public PersistenceException(String message) {
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
    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
