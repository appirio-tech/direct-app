/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibilityvalidation.failuretests;

import com.topcoder.service.contest.eligibilityvalidation.ContestEligibilityValidationManagerBeanUnitTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all accuracy test cases.
 * </p>
 *
 * @author fivestarwy
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * <p>
     * All unit test cases.
     * </p>
     *
     * @return The test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(GroupEligibilityValidatorTests.class);
        suite.addTestSuite(ContestEligibilityValidationManagerBeanUnitTests.class);
        return suite;
    }

}
