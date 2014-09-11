/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(HelperUnitTest.suite());

        suite.addTest(InvalidPositionExceptionUnitTest.suite());
        suite.addTest(InvalidTeamExceptionUnitTest.suite());
        suite.addTest(PositionRemovalExceptionUnitTest.suite());
        suite.addTest(TeamConfigurationExceptionUnitTest.suite());
        suite.addTest(TeamManagerExceptionUnitTest.suite());
        suite.addTest(TeamPersistenceExceptionUnitTest.suite());
        suite.addTest(UnknownEntityExceptionUnitTest.suite());

        suite.addTest(TeamHeaderUnitTest.suite());
        suite.addTest(TeamPositionUnitTest.suite());

        suite.addTest(PositionFilterFactoryUnitTest.suite());
        suite.addTest(TeamFilterFactoryUnitTest.suite());
        suite.addTest(UtilityFilterFactoryUnitTest.suite());

        suite.addTest(TeamImplUnitTest.suite());

        suite.addTest(TeamManagerImplUnitTest1Constructors.suite());
        suite.addTest(TeamManagerImplUnitTest2Teams.suite());
        suite.addTest(TeamManagerImplUnitTest3Positions.suite());

        suite.addTest(DemoUnitTest.suite());
        return suite;
    }

}
