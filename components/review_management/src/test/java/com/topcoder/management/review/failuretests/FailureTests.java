/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.review.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author TChenhong
 * @version 1.0
 */
public class FailureTests extends TestCase {

    /**
     * Aggregates all failure test cases
     *
     * @return Test
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(TestChainFilterFailure.class);
        suite.addTestSuite(TestDefaultReviewManagerFailure.class);

        return suite;
    }

}