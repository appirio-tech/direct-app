/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.review.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author wiedzmin
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(new TestSuite(ChainFilterAccuracyTest.class));
        suite.addTest(new TestSuite(DefaultReviewManagerAccuracyTest.class));
        suite.addTest(new TestSuite(ReviewManagementExceptionAccuracyTest.class));
        suite.addTest(new TestSuite(ConfigurationExceptionAccuracyTest.class));
        suite.addTest(new TestSuite(ReviewPersistenceExceptionAccuracyTest.class));
        suite.addTest(new TestSuite(DuplicateReviewEntityExceptionAccuracyTest.class));
        suite.addTest(new TestSuite(ReviewEntityNotFoundExceptionAccuracyTest.class));
        return suite;
    }

}
