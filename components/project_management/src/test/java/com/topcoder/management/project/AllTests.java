/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.management.project.accuracytests.AccuracyTests;
import com.topcoder.management.project.failuretests.FailureTests;
import com.topcoder.management.project.stresstests.StressTests;

/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AllTests extends TestCase {

    /**
     * Aggregates all test cases.
     *
     * @return the aggregated test case
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // unit tests
        suite.addTest(UnitTests.suite());

        // failure tests
        suite.addTest(FailureTests.suite());

        suite.addTest(AccuracyTests.suite());

        suite.addTest(StressTests.suite());
        return suite;
    }

}
