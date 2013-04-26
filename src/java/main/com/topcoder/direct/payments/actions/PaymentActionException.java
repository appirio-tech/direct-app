/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.payments.actions;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>This exception is thrown while error occurs in this module.</p>
 *
 * <p>
 * <strong>Thread safety</strong>: This class is not thread-safe. However, it will be
 * used in a thread safe manner.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version  1.0 (System Assembly - TopCoder Direct Member Payments Dashboard v1.0)
 */
public class PaymentActionException extends BaseException {

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
    public PaymentActionException(String message) {
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
    public PaymentActionException(String message, Throwable cause) {
        super(message, cause);
    }
}
