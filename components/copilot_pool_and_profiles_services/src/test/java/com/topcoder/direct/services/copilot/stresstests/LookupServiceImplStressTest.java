/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.stresstests;

import com.topcoder.direct.services.copilot.impl.LookupServiceImpl;
import junit.framework.TestCase;

/**
 * <p>
 * Stress Test cases of the <code>LookupServiceImpl</code> class.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class LookupServiceImplStressTest extends TestCase {

    /**
     * <p>
     * Executing number count.
     * </p>
     */
    private static final int COUNT = 100;

    /**
     * <p>
     * The <code>LookupServiceImpl</code> instance to be tested.
     * </p>
     */
    private LookupServiceImpl obj;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * 
     * @throws Exception to JUnit
     */
    @Override
    protected void setUp() throws Exception {
        obj = new LookupServiceImpl();
        obj.setLookupDAO(new MockLookupDAO());
    }

    /**
     * <p>
     * Stress test case for getAllCopilotProfileStatuses.
     * </p>
     * 
     * @throws Exception to JUnit
     */
    public void test_getAllCopilotProfileStatuses() throws Exception {
        long time = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            obj.getAllCopilotProfileStatuses();
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Total getAllCopilotProfileStatuses time: " + time + "ms for running " + COUNT
            + " times.");
    }

    /**
     * <p>
     * Stress test case for getAllCopilotTypes.
     * </p>
     * 
     * @throws Exception to JUnit
     */
    public void test_getAllCopilotTypes() throws Exception {
        long time = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            obj.getAllCopilotTypes();
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Total getAllCopilotTypes time: " + time + "ms for running " + COUNT + " times.");
    }

    /**
     * <p>
     * Stress test case for getAllCopilotProjectStatuses.
     * </p>
     * 
     * @throws Exception to JUnit
     */
    public void test_getAllCopilotProjectStatuses() throws Exception {
        long time = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            obj.getAllCopilotProjectStatuses();
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Total getAllCopilotProjectStatuses time: " + time + "ms for running " + COUNT
            + " times.");
    }
}