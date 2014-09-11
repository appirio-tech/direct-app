/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.impl;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.copilot.CopilotServiceInitializationException;

/**
 * Failure tests for {@link LoggingEnabledService}.
 * 
 * @author morehappiness
 * @version 1.0
 */
public class LoggingEnabledServiceFailureTests {

    /**
     * LoggingEnabledService instance for testing.
     */
    private LoggingEnabledService instance;

    /**
     * Sets up the test environment.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new LoggingEnabledService() {
        };
    }

    /**
     * Failure test for checkInit.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceInitializationException.class)
    public void test_checkInit_failure1() throws Exception {
        instance.setLoggerName("");
        instance.checkInit();
    }

}
