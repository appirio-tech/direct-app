/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.dao;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>This exception is thrown by BaseDAO when the class was not initialized properly (e.g. while required properly is
 * not specified or property value has invalid format).</p>
 *
 * <p><strong>Thread safety:</strong> This class is not thread safe because its base class is not thread safe.</p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class CopilotDAOInitializationException extends BaseRuntimeException {

    /**
     * <p>Creates new instance of <code>{@link CopilotDAOInitializationException}</code> class with given error
     * message.</p>
     *
     * @param message the detailed error message of this exception
     */
    public CopilotDAOInitializationException(String message) {
        super(message);
    }

    /**
     * <p>Creates new instance of <code>{@link CopilotDAOInitializationException}</code> class with error message and
     * inner cause.</p>
     *
     * @param message the detailed error message of this exception
     * @param cause   the inner cause of this exception
     */
    public CopilotDAOInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>Creates new instance of <code>{@link CopilotDAOInitializationException}</code> class with given error message
     * and exception data.</p>
     *
     * @param message the detailed error message of this exception
     * @param data    the exception data
     */
    public CopilotDAOInitializationException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * <p>Creates new instance of <code>{@link CopilotDAOInitializationException}</code> class with given error message,
     * inner cause and exception data.</p>
     *
     * @param message the detailed error message of this exception
     * @param cause   the inner cause of this exception
     * @param data    the exception data
     */
    public CopilotDAOInitializationException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}

