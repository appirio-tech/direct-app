/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved
 */
package com.topcoder.service.contest.eligibilityvalidation;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * Aggregates all Unit test cases.
     *
     * @return test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(ContestEligibilityValidationManagerBeanTests.suite());
        suite.addTest(ContestEligibilityValidationManagerConfigurationExceptionTests.suite());
        suite.addTest(ContestEligibilityValidationManagerExceptionTests.suite());
        suite.addTest(GroupEligibilityValidatorTests.suite());
        suite.addTest(UnsupportedContestEligibilityValidatiorExceptionTests.suite());
        suite.addTest(ContestEligibilityValidatorExceptionTests.suite());
        suite.addTest(DemoTest.suite());
        return suite;
    }
}