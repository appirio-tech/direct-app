/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification;

import com.topcoder.service.review.specification.ejb.SpecificationReviewServiceBeanTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * Adds all test suites to the unit test suite and returns the unit test suite.
     *
     * @return the unit test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(SpecificationReviewServiceConfigurationExceptionTests.class);
        suite.addTestSuite(SpecificationReviewServiceExceptionTests.class);
        suite.addTestSuite(SpecificationReviewServiceBeanTests.class);
        suite.addTestSuite(SpecificationReviewStatusTests.class);
        suite.addTestSuite(SpecificationReviewTests.class);

        suite.addTestSuite(Demo.class);
        return suite;
    }
}
