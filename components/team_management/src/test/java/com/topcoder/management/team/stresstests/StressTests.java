/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Stress test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * <p>
     * This test case aggregates all Stress test cases.
     * </p>
     *
     * @return all Stress test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(TeamManagementStressTests.suite());

        return suite;
    }
}
