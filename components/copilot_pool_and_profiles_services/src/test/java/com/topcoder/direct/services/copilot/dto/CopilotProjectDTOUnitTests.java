/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.copilot.model.CopilotProject;

/**
 * Unit tests for {@link CopilotProjectDTO}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CopilotProjectDTOUnitTests {

    /**
     * Represents {@link CopilotProjectDTO} instance for testing.
     */
    private CopilotProjectDTO instance;

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new CopilotProjectDTO();
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
     * Accuracy test for {@link CopilotProjectDTO#CopilotProjectDTO()}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCtor() throws Exception {
        assertNotNull("Unable to instantiate this object", instance);
    }

    /**
     * Accuracy test for {@link CopilotProjectDTO#setCopilotProject(CopilotProject)}. The copilotProject should be
     * set correctly.
     */
    @Test
    public void testSetCopilotProject() {
        CopilotProject value = new CopilotProject();
        instance.setCopilotProject(value);
        assertTrue("Incorrect value after set to a new one.", value == instance.getCopilotProject());
    }

    /**
     * Accuracy test for {@link CopilotProjectDTO#getCopilotProject()}. The default copilotProject should be returned
     * correctly.
     */
    @Test
    public void testGetCopilotProject() {
        assertNull("Incorrect default value", instance.getCopilotProject());
    }

    /**
     * Accuracy test for {@link CopilotProjectDTO#setTotalPlannedContests(int)}. The totalPlannedContests should be
     * set correctly.
     */
    @Test
    public void testSetTotalPlannedContests() {
        instance.setTotalPlannedContests(7);
        assertEquals("Incorrect value after set to a new one.", 7, instance.getTotalPlannedContests());
    }

    /**
     * Accuracy test for {@link CopilotProjectDTO#getTotalPlannedContests()}. The default totalPlannedContests should
     * be returned correctly.
     */
    @Test
    public void testGetTotalPlannedContests() {
        assertEquals("Incorrect default value", 0, instance.getTotalPlannedContests());
    }

    /**
     * Accuracy test for {@link CopilotProjectDTO#setTotalRealContests(int)}. The totalRealContests should be set
     * correctly.
     */
    @Test
    public void testSetTotalRealContests() {
        instance.setTotalRealContests(34);
        assertEquals("Incorrect value after set to a new one.", 34, instance.getTotalRealContests());
    }

    /**
     * Accuracy test for {@link CopilotProjectDTO#getTotalRealContests()}. The default totalRealContests should be
     * returned correctly.
     */
    @Test
    public void testGetTotalRealContests() {
        assertEquals("Incorrect default value", 0, instance.getTotalRealContests());
    }

    /**
     * Accuracy test for {@link CopilotProjectDTO#setTotalRepostedContests(int)}. The totalRepostedContests should be
     * set correctly.
     */
    @Test
    public void testSetTotalRepostedContests() {
        instance.setTotalRepostedContests(11);
        assertEquals("Incorrect value after set to a new one.", 11, instance.getTotalRepostedContests());
    }

    /**
     * Accuracy test for {@link CopilotProjectDTO#getTotalRepostedContests()}. The default totalRepostedContests
     * should be returned correctly.
     */
    @Test
    public void testGetTotalRepostedContests() {
        assertEquals("Incorrect default value", 0, instance.getTotalRepostedContests());
    }

    /**
     * Accuracy test for {@link CopilotProjectDTO#setTotalFailedContests(int)}. The totalFailedContests should be set
     * correctly.
     */
    @Test
    public void testSetTotalFailedContests() {
        instance.setTotalFailedContests(27);
        assertEquals("Incorrect value after set to a new one.", 27, instance.getTotalFailedContests());
    }

    /**
     * Accuracy test for {@link CopilotProjectDTO#getTotalFailedContests()}. The default totalFailedContests should
     * be returned correctly.
     */
    @Test
    public void testGetTotalFailedContests() {
        assertEquals("Incorrect default value", 0, instance.getTotalFailedContests());
    }

    /**
     * Accuracy test for {@link CopilotProjectDTO#setPlannedDuration(int)}. The plannedDuration should be set
     * correctly.
     */
    @Test
    public void testSetPlannedDuration() {
        instance.setPlannedDuration(57);
        assertEquals("Incorrect value after set to a new one.", 57, instance.getPlannedDuration());
    }

    /**
     * Accuracy test for {@link CopilotProjectDTO#getPlannedDuration()}. The default plannedDuration should be
     * returned correctly.
     */
    @Test
    public void testGetPlannedDuration() {
        assertEquals("Incorrect default value", 0, instance.getPlannedDuration());
    }

    /**
     * Accuracy test for {@link CopilotProjectDTO#setRealDuration(int)}. The realDuration should be set correctly.
     */
    @Test
    public void testSetRealDuration() {
        instance.setRealDuration(17);
        assertEquals("Incorrect value after set to a new one.", 17, instance.getRealDuration());
    }

    /**
     * Accuracy test for {@link CopilotProjectDTO#getRealDuration()}. The default realDuration should be returned
     * correctly.
     */
    @Test
    public void testGetRealDuration() {
        assertEquals("Incorrect default value", 0, instance.getRealDuration());
    }

    /**
     * Accuracy test for {@link CopilotProjectDTO#setPlannedBugRaces(int)}. The plannedBugRaces should be set
     * correctly.
     */
    @Test
    public void testSetPlannedBugRaces() {
        instance.setPlannedBugRaces(15);
        assertEquals("Incorrect value after set to a new one.", 15, instance.getPlannedBugRaces());
    }

    /**
     * Accuracy test for {@link CopilotProjectDTO#getPlannedBugRaces()}. The default plannedBugRaces should be
     * returned correctly.
     */
    @Test
    public void testGetPlannedBugRaces() {
        assertEquals("Incorrect default value", 0, instance.getPlannedBugRaces());
    }

    /**
     * Accuracy test for {@link CopilotProjectDTO#setTotalRealBugRaces(int)}. The totalRealBugRaces should be set
     * correctly.
     */
    @Test
    public void testSetTotalRealBugRaces() {
        instance.setTotalRealBugRaces(56);
        assertEquals("Incorrect value after set to a new one.", 56, instance.getTotalRealBugRaces());
    }

    /**
     * Accuracy test for {@link CopilotProjectDTO#getTotalRealBugRaces()}. The default totalRealBugRaces should be
     * returned correctly.
     */
    @Test
    public void testGetTotalRealBugRaces() {
        assertEquals("Incorrect default value", 0, instance.getTotalRealBugRaces());
    }

    /**
     * Accuracy test for {@link CopilotProjectDTO#setContestTypeStats(Map)}. The contestTypeStats should be set
     * correctly.
     */
    @Test
    public void testSetContestTypeStats() {
        Map<String, ContestTypeStat> value = new HashMap<String, ContestTypeStat>();
        instance.setContestTypeStats(value);
        assertTrue("Incorrect value after set to a new one.", value == instance.getContestTypeStats());
    }

    /**
     * Accuracy test for {@link CopilotProjectDTO#getContestTypeStats()}. The default contestTypeStats should be
     * returned correctly.
     */
    @Test
    public void testGetContestTypeStats() {
        assertNull("Incorrect default value", instance.getContestTypeStats());
    }

}
