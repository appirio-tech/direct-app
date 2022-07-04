/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases;

import com.topcoder.project.phases.failuretests.FailureTests;
import com.topcoder.project.phases.accuracytests.AccuracyTests;
import com.topcoder.project.phases.stresstests.StressTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This test case aggregates all test cases.
 *
 * @author littlebull
 * @version 2.0
 */
public class AllTests extends TestCase {
    /**
     * Test all the tests of this component.
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        //failure tests
        suite.addTest(FailureTests.suite());

        //accuracy tests
        suite.addTest(AccuracyTests.suite());

        //stress tests
        suite.addTest(StressTests.suite());

        //unit tests
        suite.addTest(UnitTests.suite());

        return suite;
    }

}
