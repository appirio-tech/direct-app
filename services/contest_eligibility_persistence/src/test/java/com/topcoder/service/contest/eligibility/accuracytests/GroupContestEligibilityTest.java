/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibility.accuracytests;

import junit.framework.TestCase;

import com.topcoder.service.contest.eligibility.GroupContestEligibility;

/**
 * <p>
 * Unit tests for class <code>GroupContestEligibility</code>.
 * </p>
 *
 * @author fivestarwy
 * @version 1.0
 */
public class GroupContestEligibilityTest extends TestCase {
    /**
     * <p>
     * Represent the GroupContestEligibility instance to test.
     * </p>
     */
    private GroupContestEligibility instance;

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
     * Accuracy test for the constructor <code>GroupContestEligibility()</code>.
     * Instance should be created successfully.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("The instance should be created successfully", instance);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getGroupId()</code>. Expects no
     * error occurs.
     * </p>
     */
    public void testGetGroupId() {
        long expectValue = 10;
        instance.setGroupId(expectValue);
        assertEquals("The return value should be same as expect value",
                expectValue, instance.getGroupId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setGroupId(long)</code>. Expects no
     * error occurs.
     * </p>
     */
    public void testSetGroupId() {
        long expectValue = 10;
        instance.setGroupId(expectValue);
        assertEquals("The return value should be same as expect value",
                expectValue, instance.getGroupId());
    }

}
