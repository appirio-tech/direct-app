/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.impl;

import javax.annotation.PostConstruct;

import com.topcoder.direct.services.copilot.CopilotServiceInitializationException;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This is the base class for all service implementations that can perform logging of errors and debug information.
 * It holds a Logging Wrapper logger to be used by subclasses. It's assumed that subclasses of this class will be
 * initialized using Spring IoC.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class has mutable attributes, thus it's not thread safe. But it's assumed that its
 * subclasses will be initialized via Spring IoC before calling any business method, this way it's always used in
 * thread safe manner.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public abstract class LoggingEnabledService {
    /**
     * The name of the logger to be used for logging errors and debug information. Cannot be empty after
     * initialization (validation is performed in checkInit() method). If null, the logging is not performed.
     * Initialized by Spring setter injection. Has a setter. Is used in checkInit().
     */
    private String loggerName;

    /**
     * The logger to be used by subclasses for logging errors and debug information. Is initialized in checkInit().
     * If null, logging is not performed. Has a protected getter to be used by subclasses.
     */
    private Log log;

    /**
     * Creates an instance of LoggingEnabledService.
     */
    protected LoggingEnabledService() {
        // Do nothing
    }

    /**
     * Checks whether this class was initialized by Spring properly. Initializes any additional properties if
     * required.
     *
     * @throws CopilotServiceInitializationException if the loggerName is empty
     */
    @PostConstruct
    protected void checkInit() {
        if (loggerName == null) {
            return;
        }
        if (loggerName.trim().length() == 0) {
            throw new CopilotServiceInitializationException("loggerName should not be empty.");
        }
        log = LogManager.getLog(loggerName);
    }

    /**
     * Sets the name of the logger to be used by this class.
     *
     * @param loggerName the name of the logger to be used by this class
     */
    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    /**
     * Retrieves the logger to be used by subclasses for logging errors and debug information.
     *
     * @return the logger to be used by subclasses for logging errors and debug information
     */
    protected Log getLog() {
        return log;
    }
}
