/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task;

import com.topcoder.direct.services.project.task.failuretests.FailureTests;
import com.topcoder.direct.services.project.task.stresstests.StressTests;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.direct.services.project.task.accuracytests.AccuracyTests;

/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AllTests extends TestCase {
    /**
     * <p>
     * The test suite.
     * </p>
     * 
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // unit tests
        suite.addTest(UnitTests.suite());
        suite.addTest(AccuracyTests.suite());
        suite.addTest(FailureTests.suite());
        suite.addTest(StressTests.suite());

        return suite;
    }
}
