/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
 package com.topcoder.management.review.data.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.TestResult;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(CommentFailureTests.class);
        suite.addTestSuite(CommentTypeFailureTests.class);
        suite.addTestSuite(ItemFailureTests.class);
        suite.addTestSuite(ReviewEditorFailureTests.class);
        suite.addTestSuite(ReviewFailureTests.class);
        return suite;
    }

}
