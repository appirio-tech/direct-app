/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all unit test cases.
 * </p>
 *
 * @author icyriver
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // add exceptions unit tests here.
        suite.addTest(ReviewManagementExceptionTest.suite());
        suite.addTest(ConfigurationExceptionTest.suite());
        suite.addTest(ReviewPersistenceExceptionTest.suite());
        suite.addTest(DuplicateReviewEntityExceptionTest.suite());
        suite.addTest(ReviewEntityNotFoundExceptionTest.suite());

        // add ChainFilter unit test here.
        suite.addTest(ChainFilterTest.suite());

        // add review manager unit test here.
        suite.addTest(DefaultReviewManagerTest.suite());

        // add demo test here.
        suite.addTest(DemoTest.suite());

        return suite;
    }
}
