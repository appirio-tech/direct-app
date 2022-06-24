/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.stresstests;

import junit.framework.TestCase;

import com.topcoder.direct.services.copilot.impl.CopilotProfileServiceImpl;

/**
 * <p>
 * Stress Test cases of the <code>CopilotProfileServiceImpl</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CopilotProfileServiceImplStressTest extends TestCase {

    /**
     * <p>
     * Executing number count.
     * </p>
     */
    private static final int COUNT = 100;

    /**
     * <p>
     * The <code>CopilotProfileServiceImpl</code> instance to be tested.
     * </p>
     */
    private CopilotProfileServiceImpl obj;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Override
    protected void setUp() throws Exception {
        obj = new CopilotProfileServiceImpl();
        obj.setGenericDAO(new MockCopilotProfileDAO());
        obj.setCopilotProjectPlanDAO(new MockCopilotProjectPlanDAO());
        obj.setUtilityDAO(new MockUtilityDAO());
        obj.setProjectManager(new MockProjectManager());
        obj.setCopilotProjectDAO(new MockCopilotProjectDAO());
    }

    /**
     * <p>
     * Stress test case for getCopilotPoolMembers.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void test_getCopilotPoolMembers() throws Exception {
        long time = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            obj.getCopilotPoolMembers();
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Total getCopilotPoolMembers time: " + time + "ms for running " + COUNT + " times.");
    }

    /**
     * <p>
     * Stress test case for getCopilotProfileDTO.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void test_getCopilotProfileDTO() throws Exception {
        long time = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            obj.getCopilotProfileDTO(2);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Total getCopilotProfileDTO time: " + time + "ms for running " + COUNT + " times.");
    }

    /**
     * <p>
     * Stress test case for getCopilotProfile.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void test_getCopilotProfile() throws Exception {
        long time = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            obj.getCopilotProfile(2);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Total getCopilotProfile time: " + time + "ms for running " + COUNT + " times.");
    }
}