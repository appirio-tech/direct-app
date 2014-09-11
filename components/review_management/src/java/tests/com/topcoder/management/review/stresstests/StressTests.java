/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Stress test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * **
     * Adds all the stress test suite.
     * @return the stress test case suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(new TestSuite(ChainFilterStressTest.class));
        suite.addTest(new TestSuite(DefaultReviewManagerStressTest.class));
        return suite;
    }
}
