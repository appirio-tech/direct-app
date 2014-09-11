/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 * @author Thinfox
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * <p>
     * Aggragates all accuracy tests.
     * </p>
     * @return test suite aggragating all accuracy tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(DefaultPhaseManagerTests.class);
        suite.addTestSuite(DefaultPhaseValidatorTests.class);
        suite.addTestSuite(HandleRegistryInfoTests.class);
        suite.addTestSuite(PhaseOperationEnumTests.class);
        suite.addTestSuite(PhaseStatusEnumTests.class);

        suite.addTestSuite(ConfigurationExceptionTests.class);
        suite.addTestSuite(PhaseValidationExceptionTests.class);
        suite.addTestSuite(PhasePersistenceExceptionTests.class);
        suite.addTestSuite(PhaseManagementExceptionTests.class);

        return suite;
    }
}