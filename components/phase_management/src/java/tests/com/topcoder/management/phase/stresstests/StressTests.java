/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Stress test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * Get all the stress test suite.
     * @return the stress test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(DefaultPhaseManagerStressTest.suite());
        suite.addTest(DefaultPhaseValidatorStressTest.suite());
        suite.addTest(HandlerRegistryInfoStressTest.suite());
        return suite;
    }
}
