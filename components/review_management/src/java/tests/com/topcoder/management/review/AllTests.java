/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review;

import com.topcoder.management.review.accuracytests.AccuracyTests;
import com.topcoder.management.review.failuretests.FailureTests;
import com.topcoder.management.review.stresstests.StressTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 *
 * @author icyriver
 * @version 1.0
 */
public class AllTests extends TestCase {
    /**
     * <p>
     * Creates a test suite for all tests.
     * </p>
     *
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        //add all tests here.
        suite.addTest(UnitTests.suite());
        suite.addTest(AccuracyTests.suite());
        suite.addTest(FailureTests.suite());
        suite.addTest(StressTests.suite());

        return suite;
    }
}
