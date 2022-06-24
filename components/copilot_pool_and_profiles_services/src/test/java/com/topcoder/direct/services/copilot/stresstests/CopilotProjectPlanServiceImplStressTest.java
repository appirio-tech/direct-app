/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.stresstests;

import junit.framework.TestCase;

import com.topcoder.direct.services.copilot.impl.CopilotProjectPlanServiceImpl;

/**
 * <p>
 * Stress Test cases of the <code>CopilotProjectPlanServiceImpl</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CopilotProjectPlanServiceImplStressTest extends TestCase {

    /**
     * <p>
     * Executing number count.
     * </p>
     */
    private static final int COUNT = 100;

    /**
     * <p>
     * The <code>CopilotProjectPlanServiceImpl</code> instance to be tested.
     * </p>
     */
    private CopilotProjectPlanServiceImpl obj;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Override
    protected void setUp() throws Exception {
        obj = new CopilotProjectPlanServiceImpl();
        obj.setGenericDAO(new MockCopilotProjectPlanDAO());
    }

    /**
     * <p>
     * Stress test case for getCopilotProjectPlan.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void test_getCopilotProjectPlan() throws Exception {
        long time = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            obj.getCopilotProjectPlan(10);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Total getCopilotProjectPlan time: " + time + "ms for running " + COUNT + " times.");
    }
}