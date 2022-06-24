/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibility.accuracytests;

import com.topcoder.service.contest.eligibility.dao.ContestEligibilityPersistenceException;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for <code>ContestEligibilityPersistenceException</code>.
 * </p>
 * 
 * @author fivestarwy
 * @version 1.0
 */
public class ContestEligibilityPersistenceExceptionTest extends TestCase {

    /**
     * <p>
     * Represents the <code>Throwable</code> instance used to test against.
     * </p>
     */
    private Throwable cause;

    /**
     * <p>
     * Sets up the env.
     * </p>
     * 
     * @throws Exception if any error occurs.
     */
    public void setUp() throws Exception {
        cause = new Exception();
    }

    /**
     * <p>
     * Tears down the fixture. This method is called after a test is executed.
     * </p>
     * 
     * @throws Exception if any error occurs.
     */
    public void tearDown() throws Exception {
        cause = null;
    }

    /**
     * <p>
     * Accuracy test for the constructor
     * <code>CustomValidatorException(String)</code>.
     * </p>
     * <p>
     * Instance should be created successfully.
     * </p>
     */
    public void testCtor1() {
        ContestEligibilityPersistenceException exception = new ContestEligibilityPersistenceException(
                "test");
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception
                .getMessage());
    }

    /**
     * <p>
     * Accuracy test for the constructor
     * <code>ContestEligibilityPersistenceException(String,Throwable)</code>.
     * </p>
     * <p>
     * Instance should be created successfully.
     * </p>
     */
    public void testCtor2() {
        ContestEligibilityPersistenceException exception = new ContestEligibilityPersistenceException(
                "test", cause);
        assertEquals("Return value should be 'test'", "test", exception
                .getMessage());
        assertEquals("Cause should be set correctly", cause, exception
                .getCause());
    }

}
