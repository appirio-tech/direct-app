/**
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved
 */
package com.topcoder.service.contest.eligibilityvalidation;

import com.topcoder.service.contest.eligibilityvalidation.accuracytests.AccuracyTests;
import com.topcoder.service.contest.eligibilityvalidation.failuretests.FailureTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 * 
 * @author TopCoder
 * @version 1.0
 */
public class AllTests extends TestCase {

    /**
     * Aggregates all Unit test cases.
     * 
     * @return test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // failure tests
        suite.addTest(FailureTests.suite());

        // accuracy tests
        suite.addTest(AccuracyTests.suite());

        // stress tests
        suite.addTestSuite(GroupEligibilityValidatorStressTests.class);

//        // unit tests
//        suite.addTest(UnitTests.suite());

        return suite;
    }
}