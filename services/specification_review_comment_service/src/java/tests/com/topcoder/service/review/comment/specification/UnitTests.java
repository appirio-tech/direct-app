/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.comment.specification;

import com.topcoder.service.review.comment.specification.ejb.SpecReviewCommentServiceBeanUnitTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class UnitTests {

    /**
     * <p>
     * Aggregates the unit tests.
     * </p>
     * 
     * @return a <code>Test</code> providing the test suite aggregating all unit
     *         tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(SpecReviewCommentServiceBeanUnitTest.suite());
        return suite;
    }
}
