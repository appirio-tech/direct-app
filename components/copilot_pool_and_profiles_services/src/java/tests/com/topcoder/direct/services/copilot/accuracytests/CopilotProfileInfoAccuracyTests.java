/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.accuracytests;

import com.topcoder.direct.services.copilot.model.CopilotProfileInfo;
import com.topcoder.direct.services.copilot.model.CopilotProfileInfoType;
import com.topcoder.direct.services.copilot.model.IdentifiableEntity;

import junit.framework.TestCase;

/**
 * Accuracy tests for <code>CopilotProfileInfo</code>.
 * 
 * @author morehappiness
 * @version 1.0
 */
public class CopilotProfileInfoAccuracyTests extends TestCase {
    /**
     * Instance used for tests.
     */
    private CopilotProfileInfo instance;

    /**
     * Sets up the environment.
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        instance = new CopilotProfileInfo();
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
        CopilotProfileInfoType infoType = new CopilotProfileInfoType();
        instance.setInfoType(infoType);
        assertEquals("Should be the same", infoType, instance.getInfoType());
    }

    /**
     * Tests for the copilotProfileId field.
     */
    public void test_copilotProfileId() {
        long copilotProfileId = 1;
        instance.setCopilotProfileId(copilotProfileId);
        assertEquals("Should be the same", copilotProfileId, instance.getCopilotProfileId());
    }
}
