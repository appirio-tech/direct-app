/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.accuracytests;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.topcoder.direct.services.copilot.model.CopilotProject;
import com.topcoder.direct.services.copilot.model.CopilotProjectInfo;
import com.topcoder.direct.services.copilot.model.CopilotProjectStatus;
import com.topcoder.direct.services.copilot.model.CopilotType;
import com.topcoder.direct.services.copilot.model.IdentifiableEntity;

import junit.framework.TestCase;

/**
 * Accuracy tests for <code>CopilotProject</code>.
 * 
 * @author morehappiness
 * @version 1.0
 */
public class CopilotProjectAccuracyTests extends TestCase {
    /**
     * Instance used for tests.
     */
    private CopilotProject instance;

    /**
     * Sets up the environment.
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        instance = new CopilotProject();
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
     * Tests for the copilotProfileId field.
     */
    public void test_copilotProfileId() {
        long copilotProfileId = 1;
        instance.setCopilotProfileId(copilotProfileId);
        assertEquals("Should be the same", copilotProfileId, instance.getCopilotProfileId());
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
     * Tests for the tcDirectProjectId field.
     */
    public void test_tcDirectProjectId() {
        long tcDirectProjectId = 1;
        instance.setTcDirectProjectId(tcDirectProjectId);
        assertEquals("Should be the same", tcDirectProjectId, instance.getTcDirectProjectId());
    }

    /**
     * Tests for the copilotType field.
     */
    public void test_copilotType() {
        CopilotType copilotType = new CopilotType();
        instance.setCopilotType(copilotType);
        assertEquals("Should be the same", copilotType, instance.getCopilotType());
    }

    /**
     * Tests for the status field.
     */
    public void test_status() {
        CopilotProjectStatus status = new CopilotProjectStatus();
        instance.setStatus(status);
        assertEquals("Should be the same", status, instance.getStatus());
    }

    /**
     * Tests for the customerFeedback field.
     */
    public void test_customerFeedback() {
        String customerFeedback = "customerFeedback";
        instance.setCustomerFeedback(customerFeedback);
        assertEquals("Should be the same", customerFeedback, instance.getCustomerFeedback());
    }

    /**
     * Tests for the customerRating field.
     */
    public void test_customerRating() {
        float customerRating = 1.1f;
        instance.setCustomerRating(customerRating);
        assertEquals("Should be the same", customerRating, instance.getCustomerRating());
    }

    /**
     * Tests for the pmFeedback field.
     */
    public void test_pmFeedback() {
        String pmFeedback = "pmFeedback";
        instance.setCustomerFeedback(pmFeedback);
        assertEquals("Should be the same", pmFeedback, instance.getCustomerFeedback());
    }

    /**
     * Tests for the pmRating field.
     */
    public void test_pmRating() {
        float pmRating = 1.1f;
        instance.setCustomerRating(pmRating);
        assertEquals("Should be the same", pmRating, instance.getCustomerRating());
    }

    /**
     * Tests for the privateProject field.
     */
    public void test_privateProject() {
        boolean privateProject = false;
        instance.setPrivateProject(privateProject);
        assertEquals("Should be the same", privateProject, instance.isPrivateProject());
    }

    /**
     * Tests for the projectInfos field.
     */
    public void test_projectInfos() {
        Set<CopilotProjectInfo> projectInfos = new HashSet<CopilotProjectInfo>();
        instance.setProjectInfos(projectInfos);
        assertEquals("Should be the same", projectInfos, instance.getProjectInfos());
    }

    /**
     * Tests for the completionDate field.
     */
    public void test_completionDate() {
        Date completionDate = new Date();
        instance.setCompletionDate(completionDate);
        assertEquals("Should be the same", completionDate, instance.getCompletionDate());
    }
}
