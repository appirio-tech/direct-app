/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown by LoggingEnabledService together with its subclasses when the class was not
 * initialized properly (e.g. while required properly is not specified or property value has invalid format).
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class CopilotServiceInitializationException extends BaseRuntimeException {
    /**
     * Creates a new instance of this exception with the given message.
     *
     * @param message the detailed error message of this exception
     */
    public CopilotServiceInitializationException(String message) {
        super(message);
    }

    /**
     * Creates a new instance of this exception with the given message and cause.
     *
     * @param message the detailed error message of this exception
     * @param cause the inner cause of this exception
     */
    public CopilotServiceInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new instance of this exception with the given message and exception data.
     *
     * @param message the detailed error message of this exception
     * @param data the exception data
     */
    public CopilotServiceInitializationException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Creates a new instance of this exception with the given message, cause and exception data.
     *
     * @param message the detailed error message of this exception
     * @param cause the inner cause of this exception
     * @param data the exception data
     */
    public CopilotServiceInitializationException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
