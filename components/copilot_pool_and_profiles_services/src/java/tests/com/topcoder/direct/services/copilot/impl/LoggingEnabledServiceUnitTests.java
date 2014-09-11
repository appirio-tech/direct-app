/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.copilot.CopilotServiceInitializationException;
import com.topcoder.direct.services.copilot.TestHelper;

/**
 * Unit tests for {@link LoggingEnabledService}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class LoggingEnabledServiceUnitTests {

    /**
     * Represents {@link LoggingEnabledService} instance for testing.
     */
    private LoggingEnabledService instance;

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new LoggingEnabledService() {
        };
    }

    /**
     * Tears down the test environment.
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Accuracy test for {@link LoggingEnabledService#LoggingEnabledService()}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCtor() throws Exception {
        assertNotNull("Unable to instantiate this object", instance);
    }

    /**
     * Accuracy test for {@link LoggingEnabledService#checkInit}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCheckInit() throws Exception {
        instance.setLoggerName("d");
        try {
            instance.checkInit();
        } catch (Exception e) {
            fail("should not stand here.");
        }
    }

    /**
     * Failure test for {@link LoggingEnabledService#checkInit}.Expected CopilotServiceInitializationException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceInitializationException.class)
    public void testCheckInitFailure() throws Exception {
        instance.setLoggerName("  ");
        instance.checkInit();
    }

    /**
     * Accuracy test for {@link LoggingEnabledService#setLoggerName(String)}. The loggerName should be set correctly.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testSetLoggerName() throws Exception {
        instance.setLoggerName("73");
        assertEquals("Incorrect value after set to a new one.", "73", TestHelper.getField(
            LoggingEnabledService.class, instance, "loggerName"));
    }

    /**
     * Accuracy test for {@link LoggingEnabledService#getLog()}. The default log should be returned correctly.
     */
    @Test
    public void testGetLog() {
        assertNull("Incorrect default value", instance.getLog());
    }

}
