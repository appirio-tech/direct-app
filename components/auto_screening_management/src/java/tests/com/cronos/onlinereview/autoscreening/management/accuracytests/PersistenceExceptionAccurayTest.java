/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management.accuracytests;

import com.cronos.onlinereview.autoscreening.management.PersistenceException;
import com.cronos.onlinereview.autoscreening.management.ScreeningManagementException;

import junit.framework.TestCase;

/**
 * <p>The accuracy test cases for PersistenceException class.</p>
 *
 * @author oodinary
 * @version 1.0
 */
public class PersistenceExceptionAccurayTest extends TestCase {

	/**
     * <p>The Default Exception Message.</p>
     */
    private final String defaultExceptionMessage = "DefaultExceptionMessage";

    /**
     * <p>The Default Throwable Message.</p>
     */
    private final String defaultThrowableMessage = "DefaultThrowableMessage";

    /**
     * <p>An instance for testing.</p>
     */
    private PersistenceException defaultException = null;

    /**
     * <p>Initialization.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        defaultException = new PersistenceException(defaultExceptionMessage);
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
     * <p>The PersistenceException instance should be created successfully.</p>
     */
    public void testCtor_String_Accuracy() {

        assertNotNull("PersistenceException should be accurately created.", defaultException);
        assertTrue("defaultException should be instance of PersistenceException.",
                defaultException instanceof PersistenceException);
        assertTrue("defaultException should be instance of ScreeningManagementException.",
                defaultException instanceof ScreeningManagementException);
        assertTrue("PersistenceException should be accurately created with the same Exception message.",
                defaultException.getMessage().indexOf(defaultExceptionMessage) >= 0);
    }

    /**
     * <p>Tests the ctor(String, Throwable).</p>
     * <p>The PersistenceException instance should be created successfully.</p>
     */
    public void testCtor_StringThrowable_Accuracy() {

        defaultException = new PersistenceException(defaultExceptionMessage,
                new Throwable(defaultThrowableMessage));

        assertNotNull("PersistenceException should be accurately created.", defaultException);
        assertTrue("defaultException should be instance of PersistenceException.",
                defaultException instanceof PersistenceException);
        assertTrue("defaultException should be instance of ScreeningManagementException.",
                defaultException instanceof ScreeningManagementException);
        assertTrue("PersistenceException should be accurately created with the same Exception message.",
                defaultException.getMessage().indexOf(defaultExceptionMessage) >= 0);
        assertTrue("PersistenceException should be accurately created with the same Throwable message.",
                defaultException.getMessage().indexOf(defaultThrowableMessage) >= 0);
    }
}
