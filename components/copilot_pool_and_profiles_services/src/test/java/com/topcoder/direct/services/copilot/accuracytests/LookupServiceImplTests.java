/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.accuracytests;

import com.topcoder.direct.services.copilot.impl.LookupServiceImpl;

import org.junit.After;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


/**
 * Accuracy Tests for {@link LookupServiceImpl}.
 *
 * @author onsky
 * @version 1.0
 */
public class LookupServiceImplTests {
    /** {@link LookupServiceImpl} instance for unit test. */
    private static LookupServiceImpl instance;

    /**
     * Sets up the environment.
     *
     * @throws Exception to junit
     */
    @Before
    public void setUp() throws Exception {
        instance = new LookupServiceImpl();
        instance.setLookupDAO(new MockLookupDAO());
    }

    /**
     * Clears the environment.
     */
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Accuracy test for the constructor.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_ctor() throws Exception {
        assertNotNull("the object is not initialized", instance);
    }

    /**
     * Accuracy test for the getAllCopilotProfileStatuses, no exception is allowed.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_getAllCopilotProfileStatuses() throws Exception {
        assertTrue(instance.getAllCopilotProfileStatuses().size() == 1);
    }

    /**
     * Accuracy test for the getAllCopilotProjectStatuses, no exception is allowed.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_getAllCopilotProjectStatuses() throws Exception {
        assertTrue(instance.getAllCopilotProjectStatuses().size() == 1);
    }

    /**
     * Accuracy test for the getAllCopilotTypes, no exception is allowed.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_getAllCopilotTypes() throws Exception {
        assertTrue(instance.getAllCopilotTypes().size() == 1);
    }
}
