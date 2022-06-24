/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.scorecard.data.stresstests;

import junit.framework.TestCase;


/**
 * This is the base class for all stress test classes, it's intended
 * to simplify time recording.
 *
 * @author Wendell
 * @version 1.0
 */
public class AbstractTest extends TestCase {
    /** Number of times a method will be executed. */
    protected static final int TIMES = 1000;

    /** The delta used to compare floats. */
    protected static final float DELTA = 1e-9f;

    /** The start time of the test. */
    long start;

    /** The end time of the test. */
    long end;

    /**
     * Starts a stress test.
     */
    public void begin() {
        start = System.currentTimeMillis();
    }

    /**
     * Ends a stress test.
     *
     * @param methodName the name of the method being tested.
     */
    public void end(String methodName) {
        end = System.currentTimeMillis();
        System.out.println("Execute " + methodName + " " + TIMES + " times takes "
            + (end - start) + "ms");
    }
}
