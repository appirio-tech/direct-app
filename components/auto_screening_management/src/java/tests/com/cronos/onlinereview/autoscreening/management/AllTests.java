/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.onlinereview.autoscreening.management;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.onlinereview.autoscreening.management.accuracytests.AccuracyTests;
import com.cronos.onlinereview.autoscreening.management.failuretests.FailureTests;
import com.cronos.onlinereview.autoscreening.management.stresstests.StressTests;

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
        
        //failure tests
        suite.addTest(FailureTests.suite());
        
        //stress tests
        suite.addTest(StressTests.suite());
        
        return suite;
    }

}
