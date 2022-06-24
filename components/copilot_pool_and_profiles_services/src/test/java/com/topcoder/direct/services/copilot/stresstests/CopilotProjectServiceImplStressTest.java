/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.stresstests;

import junit.framework.TestCase;

import com.topcoder.direct.services.copilot.impl.CopilotProjectServiceImpl;

/**
 * <p>
 * Stress Test cases of the <code>CopilotProjectServiceImpl</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CopilotProjectServiceImplStressTest extends TestCase {

    /**
     * <p>
     * Executing number count.
     * </p>
     */
    private static final int COUNT = 100;

    /**
     * <p>
     * The <code>CopilotProjectServiceImpl</code> instance to be tested.
     * </p>
     */
    private CopilotProjectServiceImpl obj;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Override
    protected void setUp() throws Exception {
        obj = new CopilotProjectServiceImpl();
        obj.setGenericDAO(new MockCopilotProjectDAO());
        obj.setCopilotProjectPlanDAO(new MockCopilotProjectPlanDAO());
        obj.setUtilityDAO(new MockUtilityDAO());
        obj.setProjectManager(new MockProjectManager());
        obj.setCopilotProfileDAO(new MockCopilotProfileDAO());
    }

    /**
     * <p>
     * Stress test case for getCopilotProjects.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void test_getCopilotProjectPlan() throws Exception {
        long time = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            obj.getCopilotProjects(10);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Total getCopilotProjects time: " + time + "ms for running " + COUNT + " times.");
    }

    /**
     * <p>
     * Stress test case for getCopilotProjectDTO.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void test_getCopilotProjectDTO() throws Exception {
        long time = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            obj.getCopilotProjectDTO(10);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Total getCopilotProjectDTO time: " + time + "ms for running " + COUNT + " times.");
    }
}