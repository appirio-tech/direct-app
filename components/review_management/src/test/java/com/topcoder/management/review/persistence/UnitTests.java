/**
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.review.persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(InformixReviewPersistenceTest.suite());
        suite.addTest(DemoTest.suite());
        return suite;
    }

}
