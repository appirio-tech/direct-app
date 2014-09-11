/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.management.project.persistence.accuracytests.AccuracyTests;
import com.topcoder.management.project.persistence.failuretests.FailureTests;
import com.topcoder.management.project.persistence.stresstests.StressTests;

/**
 * <p>
 * This test case aggregates all test cases of this component.
 * </p>
 * 
 * @author kshatriyan
 * @version 1.1
 */
public class AllTests extends TestCase {

    /**
     * Aggregates all the test cases.
     * 
     * @return the suite containing all the test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // unit tests
        suite.addTest(UnitTests.suite());

        // accuracy tests
        suite.addTest(AccuracyTests.suite());

        // failure tests
        suite.addTest(FailureTests.suite());

        // stress tests
        suite.addTest(StressTests.suite());

        return suite;
    }

}
