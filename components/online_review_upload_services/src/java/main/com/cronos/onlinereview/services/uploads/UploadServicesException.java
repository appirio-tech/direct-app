/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.onlinereview.services.uploads;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This is the parent exception of the component. It's thrown when some error occurs and is not covered from
 * the other custom exceptions.
 * </p>
 *
 * <p>
 * Thread safety: This class is thread safe since it is immutable.
 * </p>
 *
 * @author fabrizyo, cyberjag
 * @version 1.0
 */
public class UploadServicesException extends BaseException {

    /**
     * <p>
     * Constructs the exception with error message.
     * </p>
     *
     * @param message the error message
     */
    public UploadServicesException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructs the exception with error message and inner cause.
     * </p>
     *
     * @param message the error message
     * @param cause   the cause of this exception
     */
    public UploadServicesException(String message, Throwable cause) {
        super(message, cause);
    }
}
