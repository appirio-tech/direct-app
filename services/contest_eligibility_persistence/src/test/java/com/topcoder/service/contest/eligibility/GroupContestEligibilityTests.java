/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibility;

import java.io.Serializable;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * UnitTest cases of the <code>GroupContestEligibility</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GroupContestEligibilityTests extends TestCase {

    /**
     * <p>
     * Represents the GroupContestEligibility instance to test against.
     * </p>
     */
    private GroupContestEligibility entity;

    /**
     * <p>
     * Creates a test suite for the tests.
     * </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(GroupContestEligibilityTests.class);
        return suite;
    }

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    protected void setUp() throws Exception {
        entity = new GroupContestEligibility();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    protected void tearDown() throws Exception {
        entity = null;
    }

    /**
     * <p>
     * Accuracy test for constructor <code>GroupContestEligibility()</code>. It verifies the new instance is
     * created and it should implements the interface Serializable.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("Unable to instantiate Entity", entity);
        assertTrue("Entity should implements the interface Serializable.", entity instanceof Serializable);
    }

    /**
     * <p>
     * Test method for getGroupId. It verifies the returned value is correct.
     * </p>
     */
    public void testGetGroupId() {
        assertEquals("Incorrect default value for id", 0, entity.getGroupId());
        entity.setGroupId(1);
        assertEquals("Incorrect id after set to a new one", 1, entity.getGroupId());
    }

    /**
     * <p>
     * Test method for setGroupId. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetGroupId() {
        entity.setGroupId(2);
        assertEquals("Incorrect id after set to a new one", 2, entity.getGroupId());
    }
}