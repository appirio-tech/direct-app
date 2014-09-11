/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    /**
     * <p>
     * Get Tests to run.
     * </p>
     *
     * @return Instance of <code>Test</code>.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(SpecificationReviewAccuracyTest.class);
        suite.addTestSuite(SpecificationReviewServiceBeanAccuracyTest.class);
        suite.addTestSuite(SpecificationReviewServiceConfigurationExceptionAccuracyTest.class);
        suite.addTestSuite(SpecificationReviewServiceExceptionAccuracyTest.class);
        suite.addTestSuite(SpecificationReviewStatusAccuracyTest.class);

        return suite;
    }
}
