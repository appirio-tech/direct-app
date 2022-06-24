/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This test case aggregates all Unit test cases.
 *
 * @author littlebull
 * @version 2.0
 */
public class UnitTests extends TestCase {
    /**
     * Test all the unit tests of this component.
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // Demo
        suite.addTestSuite(Demo.class);

        // com.topcoder.project.phases
        suite.addTestSuite(AttributableObjectUnitTests.class);
        suite.addTestSuite(CyclicDependencyExceptionUnitTests.class);
        suite.addTestSuite(DependencyUnitTests.class);
        suite.addTestSuite(PhaseDateComparatorUnitTests.class);
        suite.addTestSuite(PhaseStatusUnitTests.class);
        suite.addTestSuite(PhaseTypeUnitTests.class);
        suite.addTestSuite(ProjectPhaseHelperUnitTests.class);

        // Unit tests of Project
        suite.addTestSuite(ProjectBasicUnitTests.class);
        suite.addTestSuite(ProjectPhaseUnitTests.class);
        suite.addTestSuite(ProjectDateUnitTests.class);

        // Unit tests of Phase
        suite.addTestSuite(PhaseBasicUnitTests.class);
        suite.addTestSuite(PhaseDependencyUnitTests.class);
        suite.addTestSuite(PhaseDateUnitTests.class);

        return suite;
    }

}
