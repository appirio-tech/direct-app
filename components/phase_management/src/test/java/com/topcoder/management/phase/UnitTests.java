/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.management.phase.validation.DefaultPhaseValidatorTests;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author RachaelLCook
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * Returns the master test suite.
     *
     * @return the master test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(DefaultPhaseManagerTests.class);
        suite.addTestSuite(HandleRegistryInfoTests.class);
        suite.addTestSuite(PhaseValidationExceptionTests.class);
        suite.addTestSuite(PhasePersistenceExceptionTests.class);
        suite.addTestSuite(PhaseManagementExceptionTests.class);
        suite.addTestSuite(ConfigurationExceptionTests.class);
        suite.addTestSuite(DefaultPhaseValidatorTests.class);
        suite.addTestSuite(Demo.class);
        return suite;
    }

}
