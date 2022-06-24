/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.accuracytests;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.direct.services.copilot.model.CopilotProfileInfo;
import com.topcoder.direct.services.copilot.model.CopilotProfileStatus;
import com.topcoder.direct.services.copilot.model.IdentifiableEntity;

import junit.framework.TestCase;

/**
 * Accuracy tests for <code>CopilotProfile</code>.
 * 
 * @author morehappiness
 * @version 1.0
 */
public class CopilotProfileAccuracyTests extends TestCase {
    /**
     * Instance used for tests.
     */
    private CopilotProfile instance;

    /**
     * Sets up the environment.
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        instance = new CopilotProfile();
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
     * Tests for the userId field.
     */
    public void test_userId() {
        long userId = 1;
        instance.setUserId(userId);
        assertEquals("Should be the same", userId, instance.getUserId());
    }

    /**
     * Tests for the status field.
     */
    public void test_status() {
        CopilotProfileStatus status = new CopilotProfileStatus();
        instance.setStatus(status);
        assertEquals("Should be the same", status, instance.getStatus());
    }

    /**
     * Tests for the suspensionCount field.
     */
    public void test_suspensionCount() {
        int suspensionCount = 1;
        instance.setSuspensionCount(suspensionCount);
        assertEquals("Should be the same", suspensionCount, instance.getSuspensionCount());
    }

    /**
     * Tests for the reliability field.
     */
    public void test_reliability() {
        float reliability = 1;
        instance.setReliability(reliability);
        assertEquals("Should be the same", reliability, instance.getReliability());
    }

    /**
     * Tests for the activationDate field.
     */
    public void test_activationDate() {
        Date activationDate = new Date();
        instance.setActivationDate(activationDate);
        assertEquals("Should be the same", activationDate, instance.getActivationDate());
    }

    /**
     * Tests for the showCopilotEarnings field.
     */
    public void test_showCopilotEarnings() {
        boolean showCopilotEarnings = true;
        instance.setShowCopilotEarnings(showCopilotEarnings);
        assertEquals("Should be the same", showCopilotEarnings, instance.isShowCopilotEarnings());
    }

    /**
     * Tests for the profileInfos field.
     */
    public void test_profileInfos() {
        Set<CopilotProfileInfo> profileInfos = new HashSet<CopilotProfileInfo>();
        instance.setProfileInfos(profileInfos);
        assertEquals("Should be the same", profileInfos, instance.getProfileInfos());
    }
}
