/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class AccuracyTests extends TestCase {

    /**
     * <p>Returns an accuracy test suite.</p>
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(AttributableObjectAccuracyTest.class);
        suite.addTestSuite(CyclicDependencyExceptionAccuracyTest.class);
        suite.addTestSuite(DependencyAccuracyTest.class);
        suite.addTestSuite(PhaseAccuracyTest.class);
        suite.addTestSuite(PhaseDateComparatorAccuracyTest.class);
        suite.addTestSuite(PhaseStatusAccuracyTest.class);
        suite.addTestSuite(PhaseTypeAccuracyTest.class);
        suite.addTestSuite(ProjectAccuracyTest.class);

        return suite;
    }

}
