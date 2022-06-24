/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of <code>{@link InvalidUserException}</code> class.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class InvalidUserExceptionTest extends TestCase {
    /**
     * <p>
     * Integrates all tests in this class.
     * </p>
     *
     * @return Test suite of all tests of <code>InvalidUserExceptionTest</code>.
     */
    public static Test suite() {
        return new TestSuite(InvalidUserExceptionTest.class);
    }

    /**
     * <p>
     * Accuracy test of
     * <code>{@link InvalidUserException#InvalidUserException(String message, long userId)}</code>
     * constructor. Creates an instance and get its attributes for test.
     * </p>
     */
    public void testInvalidUserException_accuracy_1() {
        InvalidUserException exception = new InvalidUserException(TestHelper.EXCEPTION_MESSAGE,
                TestHelper.PROJECT_PHASE_ID);
        assertEquals("Failed to create InvalidUserException", TestHelper.EXCEPTION_MESSAGE, exception
                .getMessage());
    }

    /**
     * <p>
     * Accuracy test of
     * <code>{@link InvalidUserException#InvalidUserException(String message, Throwable cause, long userId)}</code>
     * constructor. Creates an instance and get its attributes for test.
     * </p>
     */
    public void testInvalidUserException_accuracy_2() {
        InvalidUserException exception = new InvalidUserException(TestHelper.EXCEPTION_MESSAGE,
                new Exception(), TestHelper.USER_ID);
        assertTrue("Failed to create InvalidUserException", exception.getMessage().contains(
                TestHelper.EXCEPTION_MESSAGE));
        assertNotNull("Failed to create InvalidUserException", exception.getCause());
    }

    /**
     * <p>
     * Accuracy test of <code>{@link InvalidUserException#getUserId()}</code> method.
     * </p>
     *
     * <p>
     * Expects the same which is set.
     * </p>
     *
     */
    public void testGetUserId_accuracy() {
        InvalidUserException exception = new InvalidUserException(TestHelper.EXCEPTION_MESSAGE,
                TestHelper.USER_ID);
        assertEquals("Failed to get user id", TestHelper.USER_ID, exception.getUserId());
    }
}
