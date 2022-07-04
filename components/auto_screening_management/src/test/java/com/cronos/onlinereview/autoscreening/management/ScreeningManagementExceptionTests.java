/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

import com.topcoder.util.errorhandling.BaseException;

import junit.framework.TestCase;

/**
 * <p>
 * Test cases of ScreeningManagementException. Tests the class for proper saving the provided
 * arguments.
 * </p>
 *
 * @author haozhangr
 * @version 1.0
 */
public class ScreeningManagementExceptionTests extends TestCase {
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
     * An instance of <code>ScreeningManagementException</code> which is tested.
     * </p>
     */
    private ScreeningManagementException target = null;

    /**
     * <p>
     * setUp() routine. Creates test ScreeningManagementException instance.
     * </p>
     */
    protected void setUp() {
        this.target = new ScreeningManagementException(MESSAGE);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies ScreeningManagementException subclasses BaseException.
     * </p>
     */
    public void testInheritance() {
        assertTrue("ScreeningManagementException does not subclass BaseException.",
                target instanceof BaseException);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>ScreeningManagementException(String)</code> Create for
     * proper behavior. Verifies that the message is saved as is.
     * </p>
     */
    public void testCreate_String_1_accuracy() {
        assertEquals("The message should be saved as is", MESSAGE, target.getMessage());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>ScreeningManagementException(String)</code> Create for
     * proper behavior. Verifies that the NULL message is also saved without causing any errors.
     * </p>
     */
    public void testCreate_String_2_accuracy() {
        target = new ScreeningManagementException(null);
        assertNull("The NULL message should be saved as is", target.getMessage());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>ScreeningManagementException(String, Throwable)</code>
     * Create for proper behavior. Verifies that the message is saved as is.
     * </p>
     */
    public void testCreate_String_Throwable_1_accuracy() {
        target = new ScreeningManagementException(MESSAGE, CAUSE);
        assertTrue("The message should be saved as is", target.getMessage().indexOf(MESSAGE) >= 0);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>ScreeningManagementException(String, Throwable)</code>
     * Create for proper behavior. Verifies that the cause is saved as is.
     * </p>
     */
    public void testCreate_String_Throwable_2_accuracy() {
        target = new ScreeningManagementException(MESSAGE, CAUSE);
        assertSame("The cause should be saved as is", CAUSE, target.getCause());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>ScreeningManagementException(String, Throwable)</code>
     * Create for proper behavior. Verifies that the NULL message is also saved as is without
     * causing any errors.
     * </p>
     */
    public void testCreate_String_Throwable_3_accuracy() {
        target = new ScreeningManagementException(null, CAUSE);
        assertTrue("The NULL message should be saved as is",
                target.getMessage().indexOf("null") >= 0);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>ScreeningManagementException(String, Throwable)</code>
     * Create for proper behavior. Verifies that the NULL cause is saved as is without causing any
     * errors.
     * </p>
     */
    public void testCreate_String_Throwable_4_accuracy() {
        target = new ScreeningManagementException(MESSAGE, null);
        assertNull("The NULL cause should be saved as is", target.getCause());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>ScreeningManagementException(String, Throwable)</code>
     * Create for proper behavior. Verifies that the NULL cause and NULL message are saved as is
     * without causing any errors.
     * </p>
     */
    public void testCreate_String_Throwable_5_accuracy() {
        target = new ScreeningManagementException(null, null);
        assertNull("The NULL message should be saved as is", target.getMessage());
        assertNull("The NULL cause should be saved as is", target.getCause());
    }
}
