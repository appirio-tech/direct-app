/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all accuracy test cases.
 * </p>
 *
 * @author icyriver
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * <p>
     * Initialize the AccuracyTests to test.
     * </p>
     *
     * @return a TestSuite for AccuracyTests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // add each accuracy test here.
        suite.addTest(CommentTypeAccuracyTest.suite());
        suite.addTest(CommentAccuracyTest.suite());
        suite.addTest(ItemAccuracyTest.suite());
        suite.addTest(ReviewAccuracyTest.suite());
        suite.addTest(ReviewEditorAccuracyTest.suite());
        return suite;
    }
}
