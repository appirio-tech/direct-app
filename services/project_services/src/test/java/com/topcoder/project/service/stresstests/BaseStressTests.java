/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.stresstests;

import junit.framework.TestCase;


/**
 * <p>
 * Base class for all stress test classes.
 * </p>
 *
 * @author woodatxc
 * @version 1.0
 */
class BaseStressTests extends TestCase {
    /**
     * <p>
     * The times that a method will be called.
     * </p>
     */
    protected static final int RUN_TIMES = 50;

    /**
     * <p>
     * The maximum time allowed.
     * </p>
     */
    protected static final long MAX_TIME = 10000;

    /**
     * <p>
     * The start time of the stress test calling this helper.
     * </p>
     */
    private long start;

    /**
     * <p>
     * The end time of the stress test calling this helper.
     * </p>
     */
    private long end;

    /**
     * <p>
     * Protected constructor to prevent this class being instantiated externally.
     * </p>
     */
    protected BaseStressTests() {
        // do nothing
    }

    /**
     * <p>
     * Begins a stress test.
     * <p>
     */
    public void beginTest() {
        start = System.currentTimeMillis();
    }

    /**
     * <p>
     * Ends a stress test.
     * </p>
     *
     * @param exeMessage the execution message.
     * @param maxTime the maximum time allowed.
     */
    public void endTest(String exeMessage, long maxTime) {
        end = System.currentTimeMillis();

        long took = end - start;
        System.out.println("Executing " + exeMessage + " " + RUN_TIMES + " times takes " + (end - start) + "ms");
        assertTrue(took <= maxTime);
    }
}
