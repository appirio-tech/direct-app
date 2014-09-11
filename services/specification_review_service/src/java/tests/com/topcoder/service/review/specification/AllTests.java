/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification;

import com.topcoder.service.review.specification.ejb.SpecificationReviewServiceBeanTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.review.specification.failuretests.FailureTests;
import com.topcoder.service.review.specification.accuracytests.AccuracyTests;
import com.topcoder.service.review.specification.stresstests.StressTests;


/**
 * <p>This test case aggregates all test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AllTests extends TestCase {
    /**
     * Adds all test suites to the unit test suite and returns the unit test suite.
     *
     * @return the unit test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
		suite.addTest(FailureTests.suite());
        suite.addTest(StressTests.suite());
		suite.addTest(UnitTests.suite());
		suite.addTest(AccuracyTests.suite());
        return suite;
    }
}
