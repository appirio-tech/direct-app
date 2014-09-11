/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

import junit.framework.TestCase;

/**
 * <p>
 * Test cases of ConfigurationException. Tests the class for proper saving the provided arguments.
 * </p>
 *
 * @author haozhangr
 * @version 1.0
 */
public class ConfigurationExceptionTests extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String MESSAGE = "test error message";

    /**
     * <p>
     * A <code>Throwable</code> used for testing.
     * </p>
     */
    private static final Throwable CAUSE = new Throwable();

    /**
     * <p>
     * An instance of <code>ConfigurationException</code> which is tested.
     * </p>
     */
    private ConfigurationException target = null;

    /**
     * <p>
     * setUp() routine. Creates test ConfigurationException instance.
     * </p>
     */
    protected void setUp() {
        this.target = new ConfigurationException(MESSAGE);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies ConfigurationException subclasses ScreeningManagementException.
     * </p>
     */
    public void testInheritance() {
        assertTrue("ConfigurationException does not subclass ScreeningManagementException.",
                target instanceof ScreeningManagementException);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>ConfigurationException(String)</code> Create for proper
     * behavior. Verifies that the message is saved as is.
     * </p>
     */
    public void testCreate_String_1_accuracy() {
        assertEquals("The message should be saved as is", MESSAGE, target.getMessage());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>ConfigurationException(String)</code> Create for proper
     * behavior. Verifies that the NULL message is also saved without causing any errors.
     * </p>
     */
    public void testCreate_String_2_accuracy() {
        target = new ConfigurationException(null);
        assertNull("The NULL message should be saved as is", target.getMessage());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>ConfigurationException(String, Throwable)</code> Create for
     * proper behavior. Verifies that the message is saved as is.
     * </p>
     */
    public void testCreate_String_Throwable_1_accuracy() {
        target = new ConfigurationException(MESSAGE, CAUSE);
        assertTrue("The message should be saved as is", target.getMessage().indexOf(MESSAGE) >= 0);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>ConfigurationException(String, Throwable)</code> Create for
     * proper behavior. Verifies that the cause is saved as is.
     * </p>
     */
    public void testCreate_String_Throwable_2_accuracy() {
        target = new ConfigurationException(MESSAGE, CAUSE);
        assertSame("The cause should be saved as is", CAUSE, target.getCause());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>ConfigurationException(String, Throwable)</code> Create for
     * proper behavior. Verifies that the NULL message is also saved as is without causing any
     * errors.
     * </p>
     */
    public void testCreate_String_Throwable_3_accuracy() {
        target = new ConfigurationException(null, CAUSE);
        assertTrue("The NULL message should be saved as is",
                target.getMessage().indexOf("null") >= 0);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>ConfigurationException(String, Throwable)</code> Create for
     * proper behavior. Verifies that the NULL cause is saved as is without causing any errors.
     * </p>
     */
    public void testCreate_String_Throwable_4_accuracy() {
        target = new ConfigurationException(MESSAGE, null);
        assertNull("The NULL cause should be saved as is", target.getCause());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>ConfigurationException(String, Throwable)</code> Create for
     * proper behavior. Verifies that the NULL cause and NULL message are saved as is without
     * causing any errors.
     * </p>
     */
    public void testCreate_String_Throwable_5_accuracy() {
        target = new ConfigurationException(null, null);
        assertNull("The NULL message should be saved as is", target.getMessage());
        assertNull("The NULL cause should be saved as is", target.getCause());
    }
}
