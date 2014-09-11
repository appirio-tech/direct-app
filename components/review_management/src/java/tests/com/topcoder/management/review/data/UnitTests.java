/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This test case aggregates all Unit test cases.
 *
 * @author vividmxx
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * Aggregates all Unit test cases.
     *
     * @return the aggregated Unit test cases
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(CommentTypeUnitTests.class);
        suite.addTestSuite(CommentUnitTests.class);
        suite.addTestSuite(ItemUnitTests.class);
        suite.addTestSuite(ReviewUnitTests.class);
        suite.addTestSuite(ReviewEditorUnitTests.class);
        suite.addTestSuite(DemoTests.class);
        return suite;
    }
}
