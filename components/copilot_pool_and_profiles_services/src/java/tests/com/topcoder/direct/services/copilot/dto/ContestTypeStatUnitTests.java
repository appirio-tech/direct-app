/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link ContestTypeStat}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestTypeStatUnitTests {

    /**
     * Represents {@link ContestTypeStat} instance for testing.
     */
    private ContestTypeStat instance;

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new ContestTypeStat();
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
     * Accuracy test for {@link ContestTypeStat#ContestTypeStat()}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCtor() throws Exception {
        assertNotNull("Unable to instantiate this object", instance);
    }

    /**
     * Accuracy test for {@link ContestTypeStat#setProjectCategoryId(long)}. The projectCategoryId should be set
     * correctly.
     */
    @Test
    public void testSetProjectCategoryId() {
        instance.setProjectCategoryId(21l);
        assertEquals("Incorrect value after set to a new one.", 21l, instance.getProjectCategoryId());
    }

    /**
     * Accuracy test for {@link ContestTypeStat#getProjectCategoryId()}. The default projectCategoryId should be
     * returned correctly.
     */
    @Test
    public void testGetProjectCategoryId() {
        assertEquals("Incorrect default value", 0, instance.getProjectCategoryId());
    }

    /**
     * Accuracy test for {@link ContestTypeStat#setProjectCategoryName(String)}. The projectCategoryName should be
     * set correctly.
     */
    @Test
    public void testSetProjectCategoryName() {
        instance.setProjectCategoryName("b2");
        assertEquals("Incorrect value after set to a new one.", "b2", instance.getProjectCategoryName());
    }

    /**
     * Accuracy test for {@link ContestTypeStat#getProjectCategoryName()}. The default projectCategoryName should be
     * returned correctly.
     */
    @Test
    public void testGetProjectCategoryName() {
        assertNull("Incorrect default value", instance.getProjectCategoryName());
    }

    /**
     * Accuracy test for {@link ContestTypeStat#setPlannedContests(int)}. The plannedContests should be set
     * correctly.
     */
    @Test
    public void testSetPlannedContests() {
        instance.setPlannedContests(18);
        assertEquals("Incorrect value after set to a new one.", 18, instance.getPlannedContests());
    }

    /**
     * Accuracy test for {@link ContestTypeStat#getPlannedContests()}. The default plannedContests should be returned
     * correctly.
     */
    @Test
    public void testGetPlannedContests() {
        assertEquals("Incorrect default value", 0, instance.getPlannedContests());
    }

    /**
     * Accuracy test for {@link ContestTypeStat#setRealContests(int)}. The realContests should be set correctly.
     */
    @Test
    public void testSetRealContests() {
        instance.setRealContests(46);
        assertEquals("Incorrect value after set to a new one.", 46, instance.getRealContests());
    }

    /**
     * Accuracy test for {@link ContestTypeStat#getRealContests()}. The default realContests should be returned
     * correctly.
     */
    @Test
    public void testGetRealContests() {
        assertEquals("Incorrect default value", 0, instance.getRealContests());
    }

    /**
     * Accuracy test for {@link ContestTypeStat#setRepostedContests(int)}. The repostedContests should be set
     * correctly.
     */
    @Test
    public void testSetRepostedContests() {
        instance.setRepostedContests(5);
        assertEquals("Incorrect value after set to a new one.", 5, instance.getRepostedContests());
    }

    /**
     * Accuracy test for {@link ContestTypeStat#getRepostedContests()}. The default repostedContests should be
     * returned correctly.
     */
    @Test
    public void testGetRepostedContests() {
        assertEquals("Incorrect default value", 0, instance.getRepostedContests());
    }

    /**
     * Accuracy test for {@link ContestTypeStat#setFailedContests(int)}. The failedContests should be set correctly.
     */
    @Test
    public void testSetFailedContests() {
        instance.setFailedContests(87);
        assertEquals("Incorrect value after set to a new one.", 87, instance.getFailedContests());
    }

    /**
     * Accuracy test for {@link ContestTypeStat#getFailedContests()}. The default failedContests should be returned
     * correctly.
     */
    @Test
    public void testGetFailedContests() {
        assertEquals("Incorrect default value", 0, instance.getFailedContests());
    }

}
