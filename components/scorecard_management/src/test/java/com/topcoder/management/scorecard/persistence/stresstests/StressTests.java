/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.persistence.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * suite.
     *
     * @return suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(ScorecardManagementPersistenceStressTest.class);
        return suite;
    }
}
