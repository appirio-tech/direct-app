/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.accounting;

import com.topcoder.direct.services.exception.DirectException;

/**
 * <p>
 * This is a custom exception to be used in methods of class CustomerPlatformFeeAction.
 * It is thrown when the parameters validation fails.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Exceptions are not thread safe and they are not expected to be used concurrently.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - Add Monthly Platform Fee Feature to Admin Page)
 */
public class CustomerPlatformFeeValidationException extends DirectException {
    /**
     * <p>
     * The generated serial version uid.
     * </p>
     */
    private static final long serialVersionUID = 5354260583912791692L;

    /**
     * <p>
     * Create a new CustomerPlatformFeeValidationException with error message.
     * </p>
     *
     * @param message the error message to set
     */
    public CustomerPlatformFeeValidationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Create a new CustomerPlatformFeeValidationException with error message and root cause.
     * </p>
     *
     * @param message the error message to set
     * @param cause the chained cause exception
     */
    public CustomerPlatformFeeValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
