/**
 *
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
package com.topcoder.catalog.entity;

import com.topcoder.catalog.entity.accuracytests.AccuracyTests;
import com.topcoder.catalog.entity.failuretests.FailureTests;
import com.topcoder.catalog.entity.stresstests.StressTests;
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
        suite.addTest(UnitTests.suite());

        //accuracy tests
        suite.addTest(AccuracyTests.suite());

        //stress tests
        suite.addTest(StressTests.suite());

        //failure tests
        suite.addTest(FailureTests.suite());

        return suite;
    }

}
