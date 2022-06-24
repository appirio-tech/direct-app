/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 * @author zhuzeyuan
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * Aggragates all tests in this class.
     *
     * @return Test suite aggragating all tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(ConfigurationExceptionTest.suite());
        suite.addTest(ValidationExceptionTest.suite());
        suite.addTest(ScorecardSearchBundleTest.suite());
        suite.addTest(DefaultScorecardValidatorTest.suite());
        suite.addTest(ScorecardManagerImplTest.suite());
        suite.addTest(DemoTest.suite());
        return suite;
    }

}
