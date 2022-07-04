/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.specreview;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is created and used by <code>StartSpecificationReviewAction</code> if any error occurs during action
 * execution. It is not thrown by the action, but just stored in the model map as required by Struts Framework
 * component.
 * </p>
 *
 * <p>
 * It extends <code>SpecificationReviewActionException</code>.
 * </p>
 *
 * <p>
 * Thread-safety: This class is not thread safe since the parent class is not thread safe.
 * </p>
 *
 * @author caru, TCSDEVELOPER
 * @version 1.0
 */
public class StartSpecificationReviewActionException extends SpecificationReviewActionException {

    /**
     * Creates a new exception with the given message.
     *
     * @param message error message
     */
    public StartSpecificationReviewActionException(String message) {
        super(message);
    }

    /**
     * Creates a new exception with the given message and cause.
     *
     * @param message error message
     * @param cause cause of error
     */
    public StartSpecificationReviewActionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new exception with the given message and exception data.
     *
     * @param message error message
     * @param exceptionData additional data to attach to the exception
     */
    public StartSpecificationReviewActionException(String message, ExceptionData exceptionData) {
        super(message, exceptionData);
    }

    /**
     * Creates a new exception with the given message, cause and exception data.
     *
     * @param message error message
     * @param cause cause of error
     * @param exceptionData additional data to attach to the exception
     */
    public StartSpecificationReviewActionException(String message, Throwable cause, ExceptionData exceptionData) {
        super(message, cause, exceptionData);
    }
}
