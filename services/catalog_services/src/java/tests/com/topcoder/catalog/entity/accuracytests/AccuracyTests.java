/**
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
package com.topcoder.catalog.entity.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * Aggregates all Accuracy test cases.
     *
     * @return Test
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(TestCategoryAccuracy.class);
        suite.addTestSuite(TestPhaseAccuracy.class);
        suite.addTestSuite(TestTechnologyAccuracy.class);
        suite.addTestSuite(TestCompForumAccuracy.class);
        suite.addTestSuite(TestCompLinkAccuracy.class);

        suite.addTestSuite(TestCompUserAccuracy.class);

        suite.addTestSuite(TestCompClientAccuracy.class);

        suite.addTestSuite(TestCompVersionAccuracy.class);
	// Test for BUGR-110
        suite.addTestSuite(BugFixTest.class);
        suite.addTestSuite(TestCompVersionDatesAccuracy.class);
        suite.addTestSuite(TestComponentAccuracy.class);

        return suite;
    }
}
