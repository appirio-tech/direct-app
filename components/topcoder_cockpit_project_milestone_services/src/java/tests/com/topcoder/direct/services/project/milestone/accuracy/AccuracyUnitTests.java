/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone.accuracy;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all accuracy unit test cases.
 * </p>
 *
 * @author hesibo
 * @version 1.0
 */
public class AccuracyUnitTests extends TestCase {

    /**
     * The suite of all tests.
     *
     * @return a test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(ResponsiblePersonServiceImplUnitTests.suite());
        suite.addTest(MilestoneServiceImplUnitTests.suite());
        return suite;
    }
}
