/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.contest.management;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>This test case aggregates all tests.</p>
 *
 * @author gjw99
 * @version 1.1
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
        suite.addTest(CreateContestsTests.suite());
        suite.addTest(EditContestsTests.suite());
//        suite.addTestSuite(DetailsTabTests.class);
//        suite.addTestSuite(SpecReviewTabTests.class);
//        suite.addTestSuite(RegistraintsTabTests.class);
//        suite.addTestSuite(SubmissionsTabTests.class);
//        suite.addTestSuite(RecieptTabTests.class);

        return suite;
    }
}
