/*
 * Copyright (C) 2006 TopCoder Inc., All rights reserved.
 */
package com.topcoder.project.phases.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(AttributableObjectFailureTest.class);
        suite.addTestSuite(DependencyFailureTest.class);
        suite.addTestSuite(PhaseDateComparatorFailureTest.class);
        suite.addTestSuite(PhaseFailureTest.class);
        suite.addTestSuite(PhaseStatusFailureTest.class);
        suite.addTestSuite(PhaseTypeFailureTest.class);
        suite.addTestSuite(ProjectFailureTest.class);

        return suite;
    }

}
