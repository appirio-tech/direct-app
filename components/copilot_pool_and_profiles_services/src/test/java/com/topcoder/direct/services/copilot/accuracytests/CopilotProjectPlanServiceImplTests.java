/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.accuracytests;

import com.topcoder.direct.services.copilot.impl.CopilotProjectPlanServiceImpl;

import org.junit.After;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;


/**
 * Accuracy Tests for {@link CopilotProjectPlanServiceImpl}.
 *
 * @author onsky
 * @version 1.0
 */
public class CopilotProjectPlanServiceImplTests {
    /** {@link CopilotProjectPlanServiceImpl} instance for unit test. */
    private static CopilotProjectPlanServiceImpl instance;

    /**
     * Sets up the environment.
     *
     * @throws Exception to junit
     */
    @Before
    public void setUp() throws Exception {
        instance = new CopilotProjectPlanServiceImpl();
        instance.setGenericDAO(new MockCopilotProjectPlanDAO());
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
     * Accuracy test for the getCopilotProjectPlan, no exception is allowed.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_getCopilotProjectPlan() throws Exception {
        assertNotNull(instance.getCopilotProjectPlan(1));
    }
}
