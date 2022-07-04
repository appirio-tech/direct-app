/**
 *
 * Copyright (c) 2012, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.review.application;

import com.topcoder.management.review.application.accuracytests.AccuracyTests;
import com.topcoder.management.review.application.failuretests.FailureTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AllTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        //unit tests
        suite.addTest(AccuracyTests.suite());
        suite.addTest(FailureTests.suite());
        suite.addTest(UnitTests.suite());

        return suite;
    }

}
