/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.phase.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * This test case aggregates all Unit test cases.
 *
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(DefaultPhaseManagerTest.class);
        suite.addTestSuite(DefaultPhaseValidatorTest.class);
        suite.addTestSuite(HandlerRegistryInfoTest.class);

        return suite;
    }
}
