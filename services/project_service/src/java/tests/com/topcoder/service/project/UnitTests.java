/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.project.impl.ProjectServiceUnitTests;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author FireIce
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * Aggregates all unit tests.
     * </p>
     *
     * @return test suite aggregating all unit tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(ConfigurationExceptionUnitTests.suite());

        suite.addTest(ProjectServiceFaultUnitTests.suite());
        suite.addTest(AuthorizationFailedFaultUnitTests.suite());
        suite.addTest(IllegalArgumentFaultUnitTests.suite());
        suite.addTest(PersistenceFaultUnitTests.suite());
        suite.addTest(ProjectHasCompetitionsFaultUnitTests.suite());
        suite.addTest(ProjectNotFoundFaultUnitTests.suite());
        suite.addTest(UserNotFoundFaultUnitTests.suite());

        suite.addTest(CompetitionUnitTests.suite());
        suite.addTest(ProjectDataUnitTests.suite());
        suite.addTest(ProjectUnitTests.suite());

        suite.addTest(ProjectServiceUnitTests.suite());

        suite.addTestSuite(Demo.class);

        // Save the Cobertura data on server side
        suite.addTestSuite(SaveTest.class);

        return suite;
    }
}
