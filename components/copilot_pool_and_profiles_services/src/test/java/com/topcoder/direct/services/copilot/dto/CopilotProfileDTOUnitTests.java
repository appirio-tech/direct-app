/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.dto;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link CopilotProfileDTO}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CopilotProfileDTOUnitTests {

    /**
     * Represents {@link CopilotProfileDTO} instance for testing.
     */
    private CopilotProfileDTO instance;

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new CopilotProfileDTO();
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
     * Accuracy test for {@link CopilotProfileDTO#CopilotProfileDTO()}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCtor() throws Exception {
        assertNotNull("Unable to instantiate this object", instance);
    }

    /**
     * Accuracy test for {@link CopilotProfileDTO#setContestTypeStats(Map)}. The contestTypeStats should be set
     * correctly.
     */
    @Test
    public void testSetContestTypeStats() {
        Map<String, ContestTypeStat> value = new HashMap<String, ContestTypeStat>();
        instance.setContestTypeStats(value);
        assertTrue("Incorrect value after set to a new one.", value == instance.getContestTypeStats());
    }

    /**
     * Accuracy test for {@link CopilotProfileDTO#getContestTypeStats()}. The default contestTypeStats should be
     * returned correctly.
     */
    @Test
    public void testGetContestTypeStats() {
        assertNull("Incorrect default value", instance.getContestTypeStats());
    }

    /**
     * Accuracy test for {@link CopilotProfileDTO#setEarnings(double)}. The earnings should be set correctly.
     */
    @Test
    public void testSetEarnings() {
        instance.setEarnings(14);
        assertTrue("Incorrect value after set to a new one.", 14 == instance.getEarnings());
    }

    /**
     * Accuracy test for {@link CopilotProfileDTO#getEarnings()}. The default earnings should be returned correctly.
     */
    @Test
    public void testGetEarnings() {
        assertTrue("Incorrect default value", 0.0 == instance.getEarnings());
    }

}
