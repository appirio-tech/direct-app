/*
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.scorecard.data.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all accuracy test cases.
 * </p>
 *
 * @author crackme
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * aggregates all accuracy test cases.
     *
     * @return Test instance
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(TestGroup.class);
        suite.addTestSuite(TestWeightedScorecardStructure.class);
        suite.addTestSuite(TestSection.class);
        suite.addTestSuite(TestScorecardType.class);
        suite.addTestSuite(TestScorecardStatus.class);
        suite.addTestSuite(TestScorecardEditor.class);
        suite.addTestSuite(TestScorecard.class);
        suite.addTestSuite(TestQuestionType.class);
        suite.addTestSuite(TestQuestion.class);
        suite.addTestSuite(TestNamedScorecardStructure.class);

        return suite;
    }
}
