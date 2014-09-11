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
 * UnitTest cases of the <code>ContestEligibility</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestEligibilityTests extends TestCase {

    /**
     * <p>
     * Represents the ContestEligibility instance to test against.
     * </p>
     */
    private ContestEligibility entity;

    /**
     * <p>
     * Creates a test suite for the tests.
     * </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ContestEligibilityTests.class);
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
        entity = new MockContestEligibility();
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
     * Accuracy test for constructor <code>ContestEligibility()</code>. It verifies the new instance is
     * created and it should implements the interface Serializable.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("Unable to instantiate Entity", entity);
        assertTrue("Entity should implements the interface Serializable.", entity instanceof Serializable);
    }

    /**
     * <p>
     * Test method for getId. It verifies the returned value is correct.
     * </p>
     */
    public void testGetId() {
        assertEquals("Incorrect default value for id", 0, entity.getId());
        entity.setId(1);
        assertEquals("Incorrect id after set to a new one", 1, entity.getId());
    }

    /**
     * <p>
     * Test method for setId. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetId() {
        entity.setId(2);
        assertEquals("Incorrect id after set to a new one", 2, entity.getId());
    }

    /**
     * <p>
     * Test method for getId. It verifies the returned value is correct.
     * </p>
     */
    public void testGetContestId() {
        assertEquals("Incorrect default value for id", 0, entity.getContestId());
        entity.setContestId(1);
        assertEquals("Incorrect id after set to a new one", 1, entity.getContestId());
    }

    /**
     * <p>
     * Test method for setContestId. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetContestId() {
        entity.setContestId(2);
        assertEquals("Incorrect id after set to a new one", 2, entity.getContestId());
    }

    /**
     * <p>
     * Test method for isDeleted. It verifies the returned value is correct.
     * </p>
     */
    public void testIsDeleted() {
        assertEquals("Incorrect default value for deleted", false, entity.isDeleted());
        entity.setDeleted(true);
        assertEquals("Incorrect deleted after set to a new one", true, entity.isDeleted());
    }

    /**
     * <p>
     * Test method for setDeleted. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetDeleted() {
        entity.setDeleted(true);
        assertEquals("Incorrect deleted after set to a new one", true, entity.isDeleted());
    }

    /**
     * <p>
     * Test method for IsStudio. It verifies the returned value is correct.
     * </p>
     */
    public void testIsStudio() {
        assertEquals("Incorrect default value for deleted", false, entity.isStudio());
        entity.setStudio(true);
        assertEquals("Incorrect deleted after set to a new one", true, entity.isStudio());
    }

    /**
     * <p>
     * Test method for setStudio. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetStudio() {
        entity.setStudio(true);
        assertEquals("Incorrect deleted after set to a new one", true, entity.isStudio());
    }
}