/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.accuracytests;


import com.topcoder.direct.services.copilot.model.IdentifiableEntity;
import com.topcoder.direct.services.copilot.model.LookupEntity;

import junit.framework.TestCase;

/**
 * Accuracy tests for <code>LookupEntity</code>.
 * 
 * @author morehappiness
 * @version 1.0
 */
public class LookupEntityAccuracyTests extends TestCase {
    /**
     * Instance used for tests.
     */
    private LookupEntity instance;

    /**
     * Sets up the environment.
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        instance = new LookupEntity() {
        };
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
}
