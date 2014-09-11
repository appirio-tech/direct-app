/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.cloudvm.service;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

import javax.ejb.ApplicationException;

/**
 * This exception extends the BaseCriticalException and it's thrown from CloudVMService if any error occurs.
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException
public class CloudVMServiceException extends BaseCriticalException {
    /**
     * Constructor with error message.
     *
     * @param message the error message
     */
    public CloudVMServiceException(String message) {
        super(message);
    }

    /**
     * Constructor with error message and inner cause.
     *
     * @param message the error message.
     * @param cause   the inner cause.
     */
    public CloudVMServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with error message & exception data.
     *
     * @param message the error message.
     * @param data    the exception data.
     */
    public CloudVMServiceException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Constructor with error message, inner cause and exception data.
     *
     * @param message the error message.
     * @param cause   the inner cause.
     * @param data    the exception data.
     */
    public CloudVMServiceException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}

