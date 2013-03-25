/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.contest.management;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>This test case aggregates all tests.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AllTests extends TestCase {
    /**
     * Returns the suite of all cases.
     *
     * @return all test cases
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // functional tests
        suite.addTestSuite(CreateContestsTests.class);
        suite.addTestSuite(ViewContestsTests.class);
        suite.addTestSuite(EditContestsTests.class);
        suite.addTestSuite(DetailsTabTests.class);
        suite.addTestSuite(SpecReviewTabTests.class);
        suite.addTestSuite(RegistraintsTabTests.class);
        suite.addTestSuite(SubmissionsTabTests.class);
        suite.addTestSuite(RecieptTabTests.class);

        return suite;
    }
}
