/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of <code>{@link PersistenceException}</code> class.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class PersistenceExceptionTest extends TestCase {
    /**
     * <p>
     * Integrates all tests in this class.
     * </p>
     *
     * @return Test suite of all tests of <code>PersistenceExceptionTest</code>.
     */
    public static Test suite() {
        return new TestSuite(PersistenceExceptionTest.class);
    }

    /**
     * <p>
     * Accuracy test of <code>{@link PersistenceException#PersistenceException(String message)}</code>
     * constructor. Creates an instance and get its attributes for test.
     * </p>
     */
    public void testPersistenceException_accuracy_1() {
        PersistenceException exception = new PersistenceException(TestHelper.EXCEPTION_MESSAGE);
        assertEquals("Failed to create PersistenceException", TestHelper.EXCEPTION_MESSAGE, exception
                .getMessage());
    }

    /**
     * <p>
     * Accuracy test of
     * <code>{@link PersistenceException#PersistenceException(String message, Throwable cause)}</code>
     * constructor. Creates an instance and get its attributes for test.
     * </p>
     */
    public void testPersistenceException_accuracy_2() {
        PersistenceException exception = new PersistenceException(TestHelper.EXCEPTION_MESSAGE,
                new Exception());
        assertTrue("Failed to create PersistenceException", exception.getMessage().contains(
                TestHelper.EXCEPTION_MESSAGE));
        assertNotNull("Failed to create PersistenceException", exception.getCause());
    }
}
