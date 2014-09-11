/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.accuracytests;

import java.util.HashSet;
import java.util.Set;

import com.topcoder.direct.services.copilot.model.CopilotProjectPlan;
import com.topcoder.direct.services.copilot.model.IdentifiableEntity;
import com.topcoder.direct.services.copilot.model.PlannedContest;

import junit.framework.TestCase;

/**
 * Accuracy tests for <code>CopilotProjectPlan</code>.
 * 
 * @author morehappiness
 * @version 1.0
 */
public class CopilotProjectPlanAccuracyTests extends TestCase {
    /**
     * Instance used for tests.
     */
    private CopilotProjectPlan instance;

    /**
     * Sets up the environment.
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        instance = new CopilotProjectPlan();
    }

    /**
     * Clears down the environment.
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
    }

    /**
     * Tests for the constructor.
     */
    public void test_ctor() {
        assertNotNull("Should not be null", instance);
        assertTrue("Should be true", IdentifiableEntity.class.isInstance(instance));
    }

    /**
     * Tests for the plannedDuration field.
     */
    public void test_plannedDuration() {
        int plannedDuration = 1;
        instance.setPlannedDuration(plannedDuration);
        assertEquals("Should be the same", plannedDuration, instance.getPlannedDuration());
    }

    /**
     * Tests for the plannedBugRaces field.
     */
    public void test_plannedBugRaces() {
        int plannedBugRaces = 1;
        instance.setPlannedBugRaces(plannedBugRaces);
        assertEquals("Should be the same", plannedBugRaces, instance.getPlannedBugRaces());
    }

    /**
     * Tests for the copilotProjectId field.
     */
    public void test_copilotProjectId() {
        long copilotProjectId = 1;
        instance.setCopilotProjectId(copilotProjectId);
        assertEquals("Should be the same", copilotProjectId, instance.getCopilotProjectId());
    }

    /**
     * Tests for the plannedContests field.
     */
    public void test_plannedContests() {
        Set<PlannedContest> plannedContests = new HashSet<PlannedContest>();
        instance.setPlannedContests(plannedContests);
        assertEquals("Should be the same", plannedContests, instance.getPlannedContests());
    }

}
