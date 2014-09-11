/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.dao;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>This exception is thrown by BaseDAO, implementations of LookupDAO, UtilityDAO and GenericDAO when some unexpected
 * error occurred. Also this exception is used as a base class for other specific custom exceptions.</p>
 *
 * <p><strong>Thread safety:</strong> This class is not thread safe because its base class is not thread safe.</p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class CopilotDAOException extends BaseCriticalException {

    /**
     * <p>Creates new instance of <code>{@link CopilotDAOException}</code> class with given error message.</p>
     *
     * @param message the detailed error message of this exception
     */
    public CopilotDAOException(String message) {
        super(message);
    }

    /**
     * <p>Creates new instance of <code>{@link CopilotDAOException}</code> class with error message and inner
     * cause.</p>
     *
     * @param message the detailed error message of this exception
     * @param cause   the inner cause of this exception
     */
    public CopilotDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>Creates new instance of <code>{@link CopilotDAOException}</code> class with given error message and exception
     * data.</p>
     *
     * @param message the detailed error message of this exception
     * @param data    the exception data
     */
    public CopilotDAOException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * <p>Creates new instance of <code>{@link CopilotDAOException}</code> class with given error message, inner cause
     * and exception data.</p>
     *
     * @param message the detailed error message of this exception
     * @param cause   the inner cause of this exception
     * @param data    the exception data
     */
    public CopilotDAOException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}

