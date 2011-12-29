/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.copilot.management;

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
        suite.addTestSuite(GetACopilotTests.class);
        suite.addTestSuite(GetACopilotStep1Tests.class);
        suite.addTestSuite(GetACopilotStep2Tests.class);
        suite.addTestSuite(GetACopilotStep3Tests.class);
        suite.addTestSuite(GetACopilotSummaryTests.class);
        suite.addTestSuite(GetACopilotConfirmation1Tests.class);
        suite.addTestSuite(CopilotPoolGridTests.class);
        suite.addTestSuite(CopilotPoolListTests.class);
        suite.addTestSuite(CopilotSelectedTests.class);
        suite.addTestSuite(GetACopilotConfirmation2Tests.class);
        suite.addTestSuite(MyCopilotPostingTests.class);
        suite.addTestSuite(MyCopilotPostingRegistrantsTests.class);
        suite.addTestSuite(MyCopilotPostingSubmissionsTests.class);
        suite.addTestSuite(ManageCopilotsTests.class);

        return suite;
    }
}
