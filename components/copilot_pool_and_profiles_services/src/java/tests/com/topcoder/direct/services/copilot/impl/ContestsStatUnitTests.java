/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link ContestsStat}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestsStatUnitTests {

    /**
     * Represents {@link ContestsStat} instance for testing.
     */
    private ContestsStat instance;

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new ContestsStat();
    }

    /**
     * Tears down the test environment.
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Accuracy test for {@link ContestsStat#ContestsStat()}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCtor() throws Exception {
        assertNotNull("Unable to instantiate this object", instance);
    }

    /**
     * Accuracy test for {@link ContestsStat#setTotalContests(int)}. The totalContests should be set correctly.
     */
    @Test
    public void testSetTotalContests() {
        instance.setTotalContests(22);
        assertEquals("Incorrect value after set to a new one.", 22, instance.getTotalContests());
    }

    /**
     * Accuracy test for {@link ContestsStat#getTotalContests()}. The default totalContests should be returned
     * correctly.
     */
    @Test
    public void testGetTotalContests() {
        assertEquals("Incorrect default value", 0, instance.getTotalContests());
    }

    /**
     * Accuracy test for {@link ContestsStat#setTotalRepostedContests(int)}. The totalRepostedContests should be set
     * correctly.
     */
    @Test
    public void testSetTotalRepostedContests() {
        instance.setTotalRepostedContests(68);
        assertEquals("Incorrect value after set to a new one.", 68, instance.getTotalRepostedContests());
    }

    /**
     * Accuracy test for {@link ContestsStat#getTotalRepostedContests()}. The default totalRepostedContests should be
     * returned correctly.
     */
    @Test
    public void testGetTotalRepostedContests() {
        assertEquals("Incorrect default value", 0, instance.getTotalRepostedContests());
    }

    /**
     * Accuracy test for {@link ContestsStat#setTotalFailedContests(int)}. The totalFailedContests should be set
     * correctly.
     */
    @Test
    public void testSetTotalFailedContests() {
        instance.setTotalFailedContests(88);
        assertEquals("Incorrect value after set to a new one.", 88, instance.getTotalFailedContests());
    }

    /**
     * Accuracy test for {@link ContestsStat#getTotalFailedContests()}. The default totalFailedContests should be
     * returned correctly.
     */
    @Test
    public void testGetTotalFailedContests() {
        assertEquals("Incorrect default value", 0, instance.getTotalFailedContests());
    }

    /**
     * Accuracy test for {@link ContestsStat#setTotalBugRaces(int)}. The totalBugRaces should be set correctly.
     */
    @Test
    public void testSetTotalBugRaces() {
        instance.setTotalBugRaces(63);
        assertEquals("Incorrect value after set to a new one.", 63, instance.getTotalBugRaces());
    }

    /**
     * Accuracy test for {@link ContestsStat#getTotalBugRaces()}. The default totalBugRaces should be returned
     * correctly.
     */
    @Test
    public void testGetTotalBugRaces() {
        assertEquals("Incorrect default value", 0, instance.getTotalBugRaces());
    }

    /**
     * Accuracy test for {@link ContestsStat#setCurrentContests(int)}. The currentContests should be set correctly.
     */
    @Test
    public void testSetCurrentContests() {
        instance.setCurrentContests(47);
        assertEquals("Incorrect value after set to a new one.", 47, instance.getCurrentContests());
    }

    /**
     * Accuracy test for {@link ContestsStat#getCurrentContests()}. The default currentContests should be returned
     * correctly.
     */
    @Test
    public void testGetCurrentContests() {
        assertEquals("Incorrect default value", 0, instance.getCurrentContests());
    }

}
