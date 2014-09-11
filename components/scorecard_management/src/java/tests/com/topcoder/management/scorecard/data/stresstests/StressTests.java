/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.scorecard.data.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 *
 * @author Wendell
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * <p>
     * Returns the test suite of all stress test cases.
     * </p>
     *
     * @return the test suite of all stress test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(GroupStressTest.class);
        suite.addTestSuite(QuestionStressTest.class);
        suite.addTestSuite(QuestionTypeStressTest.class);
        suite.addTestSuite(ScorecardStatusStressTest.class);
        suite.addTestSuite(ScorecardStressTest.class);
        suite.addTestSuite(ScorecardTypeStressTest.class);
        suite.addTestSuite(SectionStressTest.class);

        return suite;
    }
}
