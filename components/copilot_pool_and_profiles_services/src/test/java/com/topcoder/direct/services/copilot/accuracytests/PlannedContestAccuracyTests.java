/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.accuracytests;

import java.util.Date;

import com.topcoder.direct.services.copilot.model.IdentifiableEntity;
import com.topcoder.direct.services.copilot.model.PlannedContest;

import junit.framework.TestCase;

/**
 * Accuracy tests for <code>PlannedContest</code>.
 * 
 * @author morehappiness
 * @version 1.0
 */
public class PlannedContestAccuracyTests extends TestCase {
    /**
     * Instance used for tests.
     */
    private PlannedContest instance;

    /**
     * Sets up the environment.
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        instance = new PlannedContest();
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
     * Tests for the name field.
     */
    public void test_name() {
        String name = "name";
        instance.setName(name);
        assertEquals("Should be the same", name, instance.getName());
    }

    /**
     * Tests for the description field.
     */
    public void test_description() {
        String description = "description";
        instance.setDescription(description);
        assertEquals("Should be the same", description, instance.getDescription());
    }

    /**
     * Tests for the projectCategoryId field.
     */
    public void test_projectCategoryId() {
        long projectCategoryId = 1;
        instance.setProjectCategoryId(projectCategoryId);
        assertEquals("Should be the same", projectCategoryId, instance.getProjectCategoryId());
    }

    /**
     * Tests for the copilotProjectPlanId field.
     */
    public void test_copilotProjectPlanId() {
        long copilotProjectPlanId = 1;
        instance.setCopilotProjectPlanId(copilotProjectPlanId);
        assertEquals("Should be the same", copilotProjectPlanId, instance.getCopilotProjectPlanId());
    }

    /**
     * Tests for the startDate field.
     */
    public void test_startDate() {
        Date startDate = new Date();
        instance.setStartDate(startDate);
        assertEquals("Should be the same", startDate, instance.getStartDate());
    }

    /**
     * Tests for the endDate field.
     */
    public void test_endDate() {
        Date endDate = new Date();
        instance.setStartDate(endDate);
        assertEquals("Should be the same", endDate, instance.getStartDate());
    }
}
