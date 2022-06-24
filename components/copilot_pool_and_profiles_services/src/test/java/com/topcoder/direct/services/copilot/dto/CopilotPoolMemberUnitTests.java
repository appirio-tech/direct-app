/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.copilot.model.CopilotProfile;

/**
 * Unit tests for {@link CopilotPoolMember}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CopilotPoolMemberUnitTests {

    /**
     * Represents {@link CopilotPoolMember} instance for testing.
     */
    private CopilotPoolMember instance;

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new CopilotPoolMember();
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
     * Accuracy test for {@link CopilotPoolMember#CopilotPoolMember()}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCtor() throws Exception {
        assertNotNull("Unable to instantiate this object", instance);
    }

    /**
     * Accuracy test for {@link CopilotPoolMember#setCopilotProfile(CopilotProfile)}. The copilotProfile should be
     * set correctly.
     */
    @Test
    public void testSetCopilotProfile() {
        CopilotProfile value = new CopilotProfile();
        instance.setCopilotProfile(value);
        assertTrue("Incorrect value after set to a new one.", value == instance.getCopilotProfile());
    }

    /**
     * Accuracy test for {@link CopilotPoolMember#getCopilotProfile()}. The default copilotProfile should be returned
     * correctly.
     */
    @Test
    public void testGetCopilotProfile() {
        assertNull("Incorrect default value", instance.getCopilotProfile());
    }

    /**
     * Accuracy test for {@link CopilotPoolMember#setTotalProjects(int)}. The totalProjects should be set correctly.
     */
    @Test
    public void testSetTotalProjects() {
        instance.setTotalProjects(28);
        assertEquals("Incorrect value after set to a new one.", 28, instance.getTotalProjects());
    }

    /**
     * Accuracy test for {@link CopilotPoolMember#getTotalProjects()}. The default totalProjects should be returned
     * correctly.
     */
    @Test
    public void testGetTotalProjects() {
        assertEquals("Incorrect default value", 0, instance.getTotalProjects());
    }

    /**
     * Accuracy test for {@link CopilotPoolMember#setTotalContests(int)}. The totalContests should be set correctly.
     */
    @Test
    public void testSetTotalContests() {
        instance.setTotalContests(30);
        assertEquals("Incorrect value after set to a new one.", 30, instance.getTotalContests());
    }

    /**
     * Accuracy test for {@link CopilotPoolMember#getTotalContests()}. The default totalContests should be returned
     * correctly.
     */
    @Test
    public void testGetTotalContests() {
        assertEquals("Incorrect default value", 0, instance.getTotalContests());
    }

    /**
     * Accuracy test for {@link CopilotPoolMember#setTotalRepostedContests(int)}. The totalRepostedContests should be
     * set correctly.
     */
    @Test
    public void testSetTotalRepostedContests() {
        instance.setTotalRepostedContests(45);
        assertEquals("Incorrect value after set to a new one.", 45, instance.getTotalRepostedContests());
    }

    /**
     * Accuracy test for {@link CopilotPoolMember#getTotalRepostedContests()}. The default totalRepostedContests
     * should be returned correctly.
     */
    @Test
    public void testGetTotalRepostedContests() {
        assertEquals("Incorrect default value", 0, instance.getTotalRepostedContests());
    }

    /**
     * Accuracy test for {@link CopilotPoolMember#setTotalFailedContests(int)}. The totalFailedContests should be set
     * correctly.
     */
    @Test
    public void testSetTotalFailedContests() {
        instance.setTotalFailedContests(44);
        assertEquals("Incorrect value after set to a new one.", 44, instance.getTotalFailedContests());
    }

    /**
     * Accuracy test for {@link CopilotPoolMember#getTotalFailedContests()}. The default totalFailedContests should
     * be returned correctly.
     */
    @Test
    public void testGetTotalFailedContests() {
        assertEquals("Incorrect default value", 0, instance.getTotalFailedContests());
    }

    /**
     * Accuracy test for {@link CopilotPoolMember#setTotalBugRaces(int)}. The totalBugRaces should be set correctly.
     */
    @Test
    public void testSetTotalBugRaces() {
        instance.setTotalBugRaces(38);
        assertEquals("Incorrect value after set to a new one.", 38, instance.getTotalBugRaces());
    }

    /**
     * Accuracy test for {@link CopilotPoolMember#getTotalBugRaces()}. The default totalBugRaces should be returned
     * correctly.
     */
    @Test
    public void testGetTotalBugRaces() {
        assertEquals("Incorrect default value", 0, instance.getTotalBugRaces());
    }

    /**
     * Accuracy test for {@link CopilotPoolMember#setCurrentProjects(int)}. The currentProjects should be set
     * correctly.
     */
    @Test
    public void testSetCurrentProjects() {
        instance.setCurrentProjects(59);
        assertEquals("Incorrect value after set to a new one.", 59, instance.getCurrentProjects());
    }

    /**
     * Accuracy test for {@link CopilotPoolMember#getCurrentProjects()}. The default currentProjects should be
     * returned correctly.
     */
    @Test
    public void testGetCurrentProjects() {
        assertEquals("Incorrect default value", 0, instance.getCurrentProjects());
    }

    /**
     * Accuracy test for {@link CopilotPoolMember#setCurrentContests(int)}. The currentContests should be set
     * correctly.
     */
    @Test
    public void testSetCurrentContests() {
        instance.setCurrentContests(41);
        assertEquals("Incorrect value after set to a new one.", 41, instance.getCurrentContests());
    }

    /**
     * Accuracy test for {@link CopilotPoolMember#getCurrentContests()}. The default currentContests should be
     * returned correctly.
     */
    @Test
    public void testGetCurrentContests() {
        assertEquals("Incorrect default value", 0, instance.getCurrentContests());
    }

}
