/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.ajax;

/**
 * <p>
 * This is a post-processing exception which will alert the caller that the specific data object could not be
 * post-processed.
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
public class AJAXDataPostProcessingException extends Exception {
    /**
     * <p>
     * The generated serial version uid.
     * </p>
     */
    private static final long serialVersionUID = 5678978773444465687L;

    /**
     * <p>
     * Create a new AJAXDataPostProcessingException with error message.
     * </p>
     *
     * @param message
     *            the error message to set
     */
    public AJAXDataPostProcessingException(String message) {
        super(message);
    }

    /**
     * <p>
     * Create a new AJAXDataPostProcessingException with error message and root cause.
     * </p>
     *
     * @param message
     *            the error message to set
     * @param cause
     *            the chained cause exception
     */
    public AJAXDataPostProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
