/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence.sql.logging;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>Unit test for SubmissionType.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.1
 */
public class LogMessageTest extends TestCase {

    /**
     * <p>Represents type of message.</p>
     */
    private static final String TYPE = "type";

    /**
     * <p>Represents id of message, used for testing.</p>
     */
    private static final Long ID = new Long(1L);

    /**
     * <p>Represents operator used for testing.</p>
     */
    private static final String OPERATOR = "operator";

    /**
     * <p>Represents error message for testing.</p>
     */
    private static final String MESSAGE = "message";

    /**
     * <p>Represents error using for testing.</p>
     */
    private static final Throwable ERROR = new Exception();

    /**
     * <p>Instance of tested class.</p>
     */
    private LogMessage instance;

    /**
     * <p>Sets up test environment.</p>
     */
    protected void setUp() {

        instance = new LogMessage(TYPE, ID, OPERATOR, MESSAGE, ERROR);
    }

    /**
     * Aggregates all tests in this class.
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(LogMessageTest.class);
    }

    /**
     * <p>Test <strong>getId</strong> method.</p>
     */
    public void testGetId() {

        Assert.assertEquals("Getter getId returned invalid value.", ID, instance.getId());
    }

    /**
     * <p>Test <strong>getType</strong> method.</p>
     */
    public void testGetType() {

        Assert.assertEquals("Getter getType returned invalid value.", TYPE, instance.getType());
    }

    /**
     * <p>Test <strong>getMessage</strong> method.</p>
     */
    public void testGetMessage() {

        Assert.assertEquals("Getter getMessage returned invalid value.", MESSAGE, instance.getMessage());
    }

    /**
     * <p>Test <strong>getError</strong> method.</p>
     */
    public void testGetError() {

        Assert.assertEquals("Getter getError returned invalid value.", ERROR, instance.getError());
    }

    /**
     * <p>Tests returned value from <strong>getLogMessage</strong> method.</p>
     */
    public void testGetLogMessage1() {

        instance = new LogMessage(TYPE, ID, OPERATOR, MESSAGE);

        String msg = instance.getLogMessage();
        String expectedValue = "type: type id: 1 operator:operator - message";


        Assert.assertNotNull("Getter getLogMessage was not supposed to return null.", msg);
        Assert.assertEquals("Getter getLogMessage returned invalid value.", escapeSpecialCharacters(expectedValue)
                , escapeSpecialCharacters(msg));
    }

    /**
     * <p>Tests returned value from <strong>getLogMessage</strong> method when all field are set to null.</p>
     */
    public void testGetLogMessage2() {

        instance = new LogMessage(null, null, null, null, null);

        String msg = instance.getLogMessage();
        String expectedValue = "type: Unknown id: Unknown operator:Unknown - null";

        Assert.assertNotNull("Getter getLogMessage was not supposed to return null.", msg);
        Assert.assertEquals("Getter getLogMessage returned invalid value.", escapeSpecialCharacters(expectedValue)
                , escapeSpecialCharacters(msg));
    }

    /**
     * <p>Removes special characters from passed string.</p>
     *
     * @param text text to remove special characters
     *
     * @return text with removed special characters
     */
    private String escapeSpecialCharacters(String text) {

        text = text.replaceAll("\t", "");
        text = text.replaceAll("\n", "");
        text = text.replaceAll("\r", "");

        return text;
    }

    /**
     * <p>Test <strong>getLogMessage</strong> method.</p>
     */
    public void testGetLogMessage3() {

        String msg1 = instance.getLogMessage();
        String msg2 = instance.getLogMessage();

        Assert.assertEquals("Getter getLogMessage returned invalid value.", msg1, msg2);
    }

    /**
     * <p>Test <strong>getExceptionStackTrace</strong> method.</p>
     */
    public void testGetExceptionStackTrace() {

        Assert.assertNotNull("Getter getExceptionStackTrace returned invalid value.",
                LogMessage.getExceptionStackTrace(ERROR));
    }

    /**
     * <p>Test <strong>getExceptionStackTrace</strong> method.</p>
     *
     * <p>IllegalArgumentException is expected to be thrown by this method.</p>
     *
     * @throws Exception if any error occurs
     */
    public void testGetExceptionStackTraceNull() throws Exception {

        try {
            LogMessage.getExceptionStackTrace(null);

            Assert.fail("IllegalArgumentException was expected.");
        } catch (IllegalArgumentException exc) {
            // test passed
        }
    }

    /**
     * <p>Test <strong>testToString</strong> method.</p>
     */
    public void testToString() {

        String msg = instance.getLogMessage();

        Assert.assertEquals("Method toString returned invalid value.",
                msg, instance.toString());
    }
}
