/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Unit tests for class <code>EntityNotFoundException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class EntityNotFoundExceptionTest {
    /**
     * <p>
     * Represents the <code>Throwable</code> instance used to test against.
     * </p>
     */
    private Throwable throwable;

    /**
     * <p>
     * Represents the <code>ExceptionData</code> instance used to test against.
     * </p>
     */
    private ExceptionData data;

    /**
     * Creates a test suite for unit tests in this test case.
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(EntityNotFoundExceptionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        throwable = new NullPointerException();
        data = new ExceptionData();
        data.setApplicationCode("AppCode");
    }

    /**
     * <p>
     * Tear down the test environment.
     * </p>
     */
    @After
    public void tearDown() {
        throwable = null;
        data = null;
    }

    /**
     * <p>
     * Inheritance test, verifies <code>EntityNotFoundException</code> subclasses should be correct.
     * </p>
     */
    @SuppressWarnings("cast")
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.",
            new EntityNotFoundException("test") instanceof ProjectMilestoneManagementException);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>EntityNotFoundException(String)</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        EntityNotFoundException exception = new EntityNotFoundException("test");
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>EntityNotFoundException(String, Throwable)</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor2() {
        EntityNotFoundException exception = new EntityNotFoundException("test", throwable);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
        assertEquals("Cause should be set correctly", throwable, exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>EntityNotFoundException(String, ExceptionData)</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor3() {
        EntityNotFoundException exception = new EntityNotFoundException("test", data);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
        assertEquals("Cause should be set correctly", "AppCode", exception.getApplicationCode());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>EntityNotFoundException(String, Throwable, ExceptionData)</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor4() {
        EntityNotFoundException exception = new EntityNotFoundException("test", throwable, data);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
        assertEquals("Cause should be set correctly", throwable, exception.getCause());
        assertEquals("Cause should be set correctly", "AppCode", exception.getApplicationCode());
    }

}
