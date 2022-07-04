/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all accuracy test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * Returns the test suite for all accuracy tests.
     *
     * @return Test the test suite for all accuracy tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(new TestSuite(AccuracyTestDefaultScorecardValidator.class));
        suite.addTest(new TestSuite(AccuracyTestScorecardManagementImpl.class));
        suite.addTest(new TestSuite(AccuracyTestScorecardSearchBundle.class));

        return suite;
    }
}
