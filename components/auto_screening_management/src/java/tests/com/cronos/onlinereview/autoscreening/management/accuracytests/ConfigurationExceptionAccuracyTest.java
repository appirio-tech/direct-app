/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management.accuracytests;

import com.cronos.onlinereview.autoscreening.management.ConfigurationException;
import com.cronos.onlinereview.autoscreening.management.ScreeningManagementException;

import junit.framework.TestCase;

/**
 * <p>The accuracy test cases for ConfigurationException class.</p>
 *
 * @author oodinary
 * @version 1.0
 */
public class ConfigurationExceptionAccuracyTest extends TestCase {

	/**
     * <p>The Default Exception Message.</p>
     */
    private final String defaultExceptionMessage = "DefaultExceptionMessage";

    /**
     * <p>The Default Throwable Message.</p>
     */
    private final String defaultThrowableMessage = "DefaultThrowableMessage";

    /**
     * <p>An ConfigException instance for testing.</p>
     */
    private ConfigurationException defaultException = null;

    /**
     * <p>Initialization.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        defaultException = new ConfigurationException(defaultExceptionMessage);
    }

    /**
     * <p>Set defaultException to null.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        defaultException = null;
    }

    /**
     * <p>Tests the ctor(String).</p>
     * <p>The ConfigurationException instance should be created successfully.</p>
     */
    public void testCtor_String_Accuracy() {

        assertNotNull("ConfigurationException should be accurately created.", defaultException);
        assertTrue("defaultException should be instance of ConfigurationException.",
                defaultException instanceof ConfigurationException);
        assertTrue("defaultException should be instance of ScreeningManagementException.",
                defaultException instanceof ScreeningManagementException);
        assertTrue("ConfigurationException should be accurately created with the same Exception message.",
                defaultException.getMessage().indexOf(defaultExceptionMessage) >= 0);
    }

    /**
     * <p>Tests the ctor(String, Throwable).</p>
     * <p>The ConfigurationException instance should be created successfully.</p>
     */
    public void testCtor_StringThrowable_Accuracy() {

        defaultException = new ConfigurationException(defaultExceptionMessage,
                new Throwable(defaultThrowableMessage));

        assertNotNull("ConfigurationException should be accurately created.", defaultException);
        assertTrue("defaultException should be instance of ConfigurationException.",
                defaultException instanceof ConfigurationException);
        assertTrue("defaultException should be instance of ScreeningManagementException.",
                defaultException instanceof ScreeningManagementException);
        assertTrue("ConfigurationException should be accurately created with the same Exception message.",
                defaultException.getMessage().indexOf(defaultExceptionMessage) >= 0);
        assertTrue("ConfigurationException should be accurately created with the same Throwable message.",
                defaultException.getMessage().indexOf(defaultThrowableMessage) >= 0);
    }
}
