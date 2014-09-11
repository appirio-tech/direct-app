/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author TopCoder, GavinWang
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * <p>
     * Aggregates all failure tests.
     * </p>
     *
     * @return all failure tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ResponseSeverityFailureTests.class);
        suite.addTestSuite(ScreeningResponseFailureTests.class);
        suite.addTestSuite(ScreeningResultFailureTests.class);
        suite.addTestSuite(ScreeningStatusFailureTests.class);
        suite.addTestSuite(ScreeningTaskFailureTests.class);
        suite.addTestSuite(ScreeningManagerFactoryFailureTests.class);
        suite.addTestSuite(DefaultDbScreeningManagerFailureTests.class);

        return suite;
    }
}
