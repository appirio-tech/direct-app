/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown by implementations of LookupService and GenericService together with its subclasses when
 * some not expected error occurred. Also this exception is used as a base class for other specific custom
 * exceptions.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class CopilotServiceException extends BaseCriticalException {
    /**
     * Creates a new instance of this exception with the given message.
     *
     * @param message the detailed error message of this exception
     */
    public CopilotServiceException(String message) {
        super(message);
    }

    /**
     * Creates a new instance of this exception with the given message and cause.
     *
     * @param message the detailed error message of this exception
     * @param cause the inner cause of this exception
     */
    public CopilotServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new instance of this exception with the given message and exception data.
     *
     * @param message the detailed error message of this exception
     * @param data the exception data
     */
    public CopilotServiceException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Creates a new instance of this exception with the given message, cause and exception data.
     *
     * @param message the detailed error message of this exception
     * @param cause the inner cause of this exception
     * @param data the exception data
     */
    public CopilotServiceException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
