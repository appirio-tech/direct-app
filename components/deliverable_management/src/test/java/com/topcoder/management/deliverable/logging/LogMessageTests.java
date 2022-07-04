/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.logging;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for <code>{@link LogMessage}</code> class.
 * </p>
 *
 * @author sparemax
 * @version 1.1
 * @since 1.1
 */
public class LogMessageTests extends TestCase {
    /**
     * <p>
     * Represents the <code>LogMessage</code> instance used in tests.
     * </p>
     */
    private LogMessage instance;

    /**
     * <p>
     * Represents the type used in tests.
     * </p>
     */
    private String type = "type";

    /**
     * <p>
     * Represents the id used in tests.
     * </p>
     */
    private Long id = new Long(1);

    /**
     * <p>
     * Represents the operator used in tests.
     * </p>
     */
    private String operator = "operator";

    /**
     * <p>
     * Represents the message used in tests.
     * </p>
     */
    private String message = "message";

    /**
     * <p>
     * Represents the error used in tests.
     * </p>
     */
    private Throwable error = new Exception();

    /**
     * <p>
     * Sets up the tests.
     * </p>
     */
    public void setUp() {
        instance = new LogMessage(type, id, operator, message, error);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>LogMessage(String type, Long id, String operator,
     * String message, Throwable error)</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    public void testCtor1() {
        instance = new LogMessage(type, id, operator, message, error);

        assertEquals("'type' should be correct.", type, instance.getType());
        assertEquals("'id' should be correct.", id, instance.getId());
        assertEquals("'message' should be correct.", message, instance.getMessage());
        assertSame("'error' should be correct.", error, instance.getError());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>LogMessage(String type, Long id, String operator,
     *  String message)</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    public void testCtor2() {
        instance = new LogMessage(type, id, operator, message);

        assertEquals("'type' should be correct.", type, instance.getType());
        assertEquals("'id' should be correct.", id, instance.getId());
        assertEquals("'message' should be correct.", message, instance.getMessage());
        assertNull("'error' should be correct.", instance.getError());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getType()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    public void test_getType() {
        assertEquals("'type' value should be properly retrieved.", type, instance.getType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    public void test_getId() {
        assertEquals("'id' value should be properly retrieved.", id, instance.getId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getMessage()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    public void test_getMessage() {
        assertEquals("'message' value should be properly retrieved.", message, instance.getMessage());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getError()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    public void test_getError() {
        assertSame("'error' value should be properly retrieved.", error, instance.getError());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getLogMessage()</code>.<br>
     * The result should be correct.
     * </p>
     */
    public void test_getLogMessage_1() {
        String logMessage = instance.getLogMessage();

        assertTrue("'getLogMessage' should be correct.",
            logMessage.indexOf("type: type id: 1 operator:operator - message") != -1);
        assertTrue("'getLogMessage' should be correct.", logMessage.indexOf("java.lang.Exception") != -1);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getLogMessage()</code>.<br>
     * The result should be correct.
     * </p>
     */
    public void test_getLogMessage_2() {
        instance = new LogMessage(null, null, null, null);

        String logMessage = instance.getLogMessage();

        assertTrue("'getLogMessage' should be correct.",
            logMessage.indexOf("type: Unknown id: Unknown operator:Unknown - null") != -1);
        assertFalse("'getLogMessage' should be correct.", logMessage.indexOf("java.lang.Exception") != -1);
    }

    /**
     * <p>
     * Accuracy test for the method <code>toString()</code>.<br>
     * The result should be correct.
     * </p>
     */
    public void test_toString() {
        String logMessage = instance.getLogMessage();

        assertEquals("'toString' should be correct.", logMessage, instance.toString());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getExceptionStackTrace(Throwable cause)</code>.<br>
     * The result should be correct.
     * </p>
     */
    public void test_getExceptionStackTrace() {
        String stackTrace = LogMessage.getExceptionStackTrace(error);

        assertTrue("'getExceptionStackTrace' should be correct.", stackTrace.indexOf("java.lang.Exception") != -1);
    }

    /**
     * <p>
     * Failure test for the method <code>getExceptionStackTrace(Throwable cause)</code> with cause is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void test_getExceptionStackTrace_causeNull() {
        error = null;

        try {
            LogMessage.getExceptionStackTrace(error);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }
}
