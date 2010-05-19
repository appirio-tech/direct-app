/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.ajax;

/**
 * <p>
 * THis is a pre-processing exception which will alert the caller that the specific data object could not be
 * pre-processed.
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
public class AJAXDataPreProcessingException extends Exception {
    /**
     * <p>
     * The generated serial version uid.
     * </p>
     */
    private static final long serialVersionUID = -3540108031220322464L;

    /**
     * <p>
     * Create a new AJAXDataPreProcessingException with error message.
     * </p>
     *
     * @param message
     *            the error message to set
     */
    public AJAXDataPreProcessingException(String message) {
        super(message);
    }

    /**
     * <p>
     * Create a new AJAXDataPreProcessingException with error message and root cause.
     * </p>
     *
     * @param message
     *            the error message to set
     * @param cause
     *            the chained cause exception
     */
    public AJAXDataPreProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
