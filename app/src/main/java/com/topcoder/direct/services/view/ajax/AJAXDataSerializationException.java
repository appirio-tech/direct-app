/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.ajax;

/**
 *<p>
 * This is a serialization exception which alerts the caller that specific data could not be serialized for
 * AJAX-based processing.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Exceptions are not thread safe and they are not expected to be used
 * concurrently.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
public class AJAXDataSerializationException extends Exception {
    /**
     * <p>
     * The generated serial version uid.
     * </p>
     */
    private static final long serialVersionUID = 4949334359084715720L;

    /**
     * <p>
     *
     * Create a new AJAXDataSerializationException with error message.
     * </p>
     *
     * @param message
     *            the error message to set
     */
    public AJAXDataSerializationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Create a new AJAXDataSerializationException with error message and root cause.
     * </p>
     *
     * @param message
     *            the error message to set
     * @param cause
     *            the chained cause exception
     */
    public AJAXDataSerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
