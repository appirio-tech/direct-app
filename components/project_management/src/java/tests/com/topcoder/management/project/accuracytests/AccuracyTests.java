/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.accuracytests;

import com.topcoder.management.project.accuracytests.validation.DefaultProjectValidatorAccuracyTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * @author skatou
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * Aggregates all Accuracy test cases.
     *
     * @return the test suite aggregates all Accuracy test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ConfigurationExceptionAccuracyTests.class);
        suite.addTestSuite(ValidationExceptionAccuracyTests.class);
        suite.addTestSuite(PersistenceExceptionAccuracyTests.class);

        suite.addTestSuite(ProjectTypeAccuracyTests.class);
        suite.addTestSuite(ProjectCategoryAccuracyTests.class);
        suite.addTestSuite(ProjectStatusAccuracyTests.class);
        suite.addTestSuite(ProjectPropertyTypeAccuracyTests.class);
        suite.addTestSuite(ProjectAccuracyTests.class);

        suite.addTestSuite(DefaultProjectValidatorAccuracyTests.class);

        suite.addTestSuite(ProjectManagerImplDelegateAccuracyTests.class);
        suite.addTestSuite(ProjectManagerImplSearchFunctionsAccuracyTests.class);

        return suite;
    }
}
