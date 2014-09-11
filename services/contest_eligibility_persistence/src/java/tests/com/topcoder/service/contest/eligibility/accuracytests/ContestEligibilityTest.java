/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibility.accuracytests;

import com.topcoder.service.contest.eligibility.ContestEligibility;
import com.topcoder.service.contest.eligibility.GroupContestEligibility;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for <code>ContestEligibility</code> class.
 * </p>
 *
 * @author fivestarwy
 * @version 1.0
 */
public class ContestEligibilityTest extends TestCase {
    /**
     * <p>
     * Represent the ContestEligibility instance to test.
     * </p>
     */
    private ContestEligibility instance;

    /**
     * <p>
     * Set the test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception {
        instance = new GroupContestEligibility();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ContestEligibility()</code>.
     * Instance should be created successfully.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("The instance should be created successfully", instance);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getId()</code>. 
     * </p>
     */
    public void testGetId() {
        long expectValue = 10;
        instance.setId(expectValue);
        assertEquals("The return value should be same as expect value",
                expectValue, instance.getId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setId(long)</code>. Expects no
     * error occurs.
     * </p>
     */
    public void testSetId() {
        long expectValue = 10;
        instance.setId(expectValue);
        assertEquals("The return value should be same as expect value",
                expectValue, instance.getId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getContestId()</code>. Expects no
     * error occurs.
     * </p>
     */
    public void testGetContestId() {
        long expectValue = 10;
        instance.setContestId(expectValue);
        assertEquals("The return value should be same as expect value",
                expectValue, instance.getContestId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setContestId(long)</code>. Expects
     * no error occurs.
     * </p>
     */
    public void testSetContestId() {
        long expectValue = 10;
        instance.setContestId(expectValue);
        assertEquals("The return value should be same as expect value",
                expectValue, instance.getContestId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>isStudio()</code>. Expects no error
     * occurs.
     * </p>
     */
    public void testIsStudio() {
        instance.setStudio(true);
        assertTrue("The return value should be true", instance.isStudio());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setStudio(boolean)</code>. Expects
     * no error occurs.
     * </p>
     */
    public void testSetStudio() {
        instance.setStudio(true);
        assertTrue("The return value should be true", instance.isStudio());
    }

    /**
     * <p>
     * Accuracy test for the method <code>isDeleted()</code>. Expects no
     * error occurs.
     * </p>
     */
    public void testIsDeleted() {
        instance.setDeleted(true);
        assertTrue("The return value should be true", instance.isDeleted());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setDeleted(boolean)</code>. Expects
     * no error occurs.
     * </p>
     */
    public void testSetDeleted() {
        instance.setDeleted(true);
        assertTrue("The return value should be true", instance.isDeleted());
    }

}
