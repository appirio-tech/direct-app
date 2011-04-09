/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util.jira;

import com.topcoder.util.errorhandling.BaseException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Exception used to indicate an authentication error with the Jira RPC service. It will be thrown by the methods
 * of class <code>JiraRpcServiceWrapper</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0 (TC Cockpit Bug Tracking R1 Contest Tracking assembly)
 */
public class JiraRpcServiceAuthenticationException extends BaseException {
     /**
     * Creates a new exception with the given message.
     *
     * @param message error message
     */
    public JiraRpcServiceAuthenticationException(String message) {
        super(message);
    }

    /**
     * Creates a new exception with the given message and cause.
     *
     * @param message error message
     * @param cause cause of error
     */
    public JiraRpcServiceAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new exception with the given message and exception data.
     *
     * @param message error message
     * @param exceptionData additional data to attach to the exception
     */
    public JiraRpcServiceAuthenticationException(String message, ExceptionData exceptionData) {
        super(message, exceptionData);
    }

    /**
     * Creates a new exception with the given message, cause and exception data.
     *
     * @param message error message
     * @param cause cause of error
     * @param exceptionData additional data to attach to the exception
     */
    public JiraRpcServiceAuthenticationException(String message, Throwable cause, ExceptionData exceptionData) {
        super(message, cause, exceptionData);
    }
}
