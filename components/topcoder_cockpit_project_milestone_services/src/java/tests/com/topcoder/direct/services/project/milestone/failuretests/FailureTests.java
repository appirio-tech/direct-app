/*
 * Copyright (c) 2011, TopCoder, Inc. All rights reserved
 */
package com.topcoder.direct.services.project.milestone.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FailureTests extends TestCase {

    /**
     * The suite of all tests.
     *
     * @return a test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(HibernateMilestoneServiceTest.suite());
        suite.addTest(HibernateResponsiblePersonServiceTest.suite());
        return suite;
    }

}
