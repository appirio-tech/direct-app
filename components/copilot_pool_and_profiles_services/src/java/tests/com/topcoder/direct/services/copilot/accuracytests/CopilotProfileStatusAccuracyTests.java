/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.accuracytests;

import com.topcoder.direct.services.copilot.model.CopilotProfileStatus;
import com.topcoder.direct.services.copilot.model.LookupEntity;

import junit.framework.TestCase;

/**
 * Accuracy tests for <code>CopilotProfileStatus</code>.
 * 
 * @author morehappiness
 * @version 1.0
 */
public class CopilotProfileStatusAccuracyTests extends TestCase {
    /**
     * Instance used for tests.
     */
    private CopilotProfileStatus instance;

    /**
     * Sets up the environment.
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        instance = new CopilotProfileStatus();
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
        assertTrue("Should be true", LookupEntity.class.isInstance(instance));
    }
}
