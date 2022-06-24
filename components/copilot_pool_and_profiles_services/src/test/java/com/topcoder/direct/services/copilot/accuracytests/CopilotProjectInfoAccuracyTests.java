/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.accuracytests;

import com.topcoder.direct.services.copilot.model.CopilotProjectInfo;
import com.topcoder.direct.services.copilot.model.CopilotProjectInfoType;
import com.topcoder.direct.services.copilot.model.IdentifiableEntity;

import junit.framework.TestCase;

/**
 * Accuracy tests for <code>CopilotProjectInfo</code>.
 * 
 * @author morehappiness
 * @version 1.0
 */
public class CopilotProjectInfoAccuracyTests extends TestCase {
    /**
     * Instance used for tests.
     */
    private CopilotProjectInfo instance;

    /**
     * Sets up the environment.
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        instance = new CopilotProjectInfo();
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
     * Tests for the value field.
     */
    public void test_value() {
        String value = "value";
        instance.setValue(value);
        assertEquals("Should be the same", value, instance.getValue());
    }

    /**
     * Tests for the infoType field.
     */
    public void test_infoType() {
        CopilotProjectInfoType infoType = new CopilotProjectInfoType();
        instance.setInfoType(infoType);
        assertEquals("Should be the same", infoType, instance.getInfoType());
    }

    /**
     * Tests for the copilotProjectId field.
     */
    public void test_copilotProjectId() {
        long copilotProjectId = 1;
        instance.setCopilotProjectId(copilotProjectId);
        assertEquals("Should be the same", copilotProjectId, instance.getCopilotProjectId());
    }
}
