/**
 *
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
package com.topcoder.catalog.entity.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 * 
 * @author TopCoder
 * @version 1.0
 */
public class StressTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(CategoryTest.class);
        suite.addTestSuite(ComponentTest.class);
        suite.addTestSuite(PhaseTest.class);
        return suite;
    }
}
