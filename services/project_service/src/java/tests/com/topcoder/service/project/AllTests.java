/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.direct.services.project.entities.HibernateTestAll;
import com.topcoder.service.project.accuracytests.AccuracyTests;
import com.topcoder.service.project.failuretests.FailureTests;
import com.topcoder.service.project.stresstests.StressTests;

/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class AllTests extends TestCase {

    /**
     * <p>
     * Aggregates all tests.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // unit tests
        // ignored as unit test is not in the scope of this competition
        suite.addTest(UnitTests.suite());

        // failure tests
        suite.addTest(FailureTests.suite());

        // accuracy tests
        suite.addTest(AccuracyTests.suite());

        // stress tests
        suite.addTest(StressTests.suite());

        suite.addTest(HibernateTestAll.suite());
        return suite;
    }

}
