/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.team.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all accuracy test cases for the <i>Team Management</i> component.</p>
 *
 * @author RachaelLCook
 * @version 1.0
 */

public class AccuracyTests extends TestCase {
    /**
     * Returns the accuracy test suite for the <i>Team Management</i> component.
     *
     * @return the accuracy test suite for the <i>Team Management</i> component
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(PositionFilterFactoryTests.class);
        suite.addTestSuite(TeamFilterFactoryTests.class);
        suite.addTestSuite(TeamHeaderTests.class);
        suite.addTestSuite(TeamImplTests.class);
        suite.addTestSuite(TeamManagerImplTests.class);
        suite.addTestSuite(TeamPositionTests.class);
        suite.addTestSuite(UtilityFilterFactoryTests.class);

        return suite;
    }
}
