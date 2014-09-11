/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.topcoder.management.scorecard.accuracytests.AccuracyTests;
import com.topcoder.management.scorecard.failuretests.FailureTests;
import com.topcoder.management.scorecard.stresstests.StressTests;

/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 * @author zhuzeyuan
 * @version 1.0
 */
public class AllTests extends TestCase {
    /**
     * Aggragates all tests in this class.
     *
     * @return Test suite aggragating all tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // unit tests
        suite.addTest(UnitTests.suite());

        // accuracy tests
        suite.addTest(AccuracyTests.suite());

        // failure tests
        suite.addTest(FailureTests.suite());

        // stress tests
        suite.addTest(StressTests.suite());

        return suite;
    }

}
