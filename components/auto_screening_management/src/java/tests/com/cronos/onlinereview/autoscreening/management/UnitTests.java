/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.onlinereview.autoscreening.management;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // exception tests
        suite.addTestSuite(ConfigurationExceptionTests.class);
        suite.addTestSuite(PersistenceExceptionTests.class);
        suite.addTestSuite(ScreeningManagementExceptionTests.class);
        suite.addTestSuite(ScreeningTaskAlreadyExistsExceptionTests.class);
        suite.addTestSuite(ScreeningTaskDoesNotExistExceptionTests.class);

        suite.addTestSuite(DbScreeningManagerTests.class);
        suite.addTestSuite(DefaultDbScreeningManagerTests.class);
        suite.addTestSuite(ResponseSeverityTests.class);
        suite.addTestSuite(ScreeningManagerFactoryTests.class);
        suite.addTestSuite(ScreeningResponseTests.class);
        suite.addTestSuite(ScreeningResultTests.class);
        suite.addTestSuite(ScreeningStatusTests.class);
        suite.addTestSuite(ScreeningTaskTests.class);
        suite.addTestSuite(ScreeningTaskDoesNotExistExceptionTests.class);

        // Helper tests
        suite.addTestSuite(ManagementHelperTests.class);
        suite.addTestSuite(DbHelperTests.class);

        suite.addTestSuite(Demo.class);
        return suite;
    }

}
