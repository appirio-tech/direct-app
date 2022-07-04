/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */



package com.topcoder.management.deliverable.persistence.accuracytests;

import com.topcoder.management.deliverable.logging.LogMessage;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy test for <code>LogMessage</code>
 * </p>
 *
 * @author morehappiness
 * @version 1.1
 */
public class LogMessageAccuracyTests extends TestCase {
    /**
     * LogMessage instance used for tests.
     */
    private LogMessage instance;

    /**
     * <p>
     * Test for <code>LogMessage(type:String,id:Long,operator:String,message:String,error:Throwable)</code> method.
     * </p>
     */
    public void test_ctor1() {
        assertNotNull("Failed to create instance", instance);
    }

    /**
     * <p>
     * Test for <code>LogMessage(type:String,id:Long,operator:String,message:String)</code> method.
     * </p>
     */
    public void test_ctor2() {
        instance = new LogMessage("type", new Long(1), "operator", "message");
        assertNotNull("Failed to create instance", instance);
    }

    /**
     * <p>
     * Test for <code>getId()</code> method.
     * </p>
     */
    public void test_getId() {
        assertEquals("Should be equal", new Long(1), instance.getId());
    }

    /**
     * <p>
     * Test for <code>getType()</code> method.
     * </p>
     */
    public void test_getType() {
        assertEquals("Should be equal", "type", instance.getType());
    }

    /**
     * <p>
     * Test for <code>getMessage()</code> method.
     * </p>
     */
    public void test_getMessage() {
        assertEquals("Should be equal", "message", instance.getMessage());
    }

    /**
     * <p>
     * Test for <code>getError()</code> method.
     * </p>
     */
    public void test_getError() {
        assertEquals("Should be equal", "exception", instance.getError().getMessage());
    }

    /**
     * <p>
     * Test for <code>getLogMessage()</code> method.
     * </p>
     */
    public void test_getLogMessage() {
        assertTrue("Should have the expected message", instance.getLogMessage().indexOf("exception") >= 0);
        assertTrue("Should have the expected message", instance.getLogMessage().indexOf("message") >= 0);
        assertTrue("Should have the expected message", instance.getLogMessage().indexOf("operator") >= 0);
    }

    /**
     * <p>
     * Test for <code>getExceptionStackTrace(Throwable)</code> method.
     * </p>
     */
    public void test_getExceptionStackTrace() {
        Exception e = new IllegalArgumentException("IAE");

        assertTrue("Should have the expected message", instance.getExceptionStackTrace(e).indexOf("IAE") >= 0);
    }

    /**
     * <p>
     * Test for <code>toString()</code> method.
     * </p>
     */
    public void test_toString() {
        assertTrue("Should have the expected message", instance.getLogMessage().indexOf("exception") >= 0);
        assertTrue("Should have the expected message", instance.getLogMessage().indexOf("message") >= 0);
        assertTrue("Should have the expected message", instance.getLogMessage().indexOf("operator") >= 0);
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        instance = new LogMessage("type", new Long(1), "operator", "message", new Exception("exception"));
    }
}
