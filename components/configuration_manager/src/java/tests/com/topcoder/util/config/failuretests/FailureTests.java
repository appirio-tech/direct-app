package com.topcoder.util.config.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all failure test cases.</p>
 *
 * @author TopCoder Software
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(ExceptionClassesTests.suite());
        suite.addTest(DefaultConfigManagerFailureTest.suite());
        suite.addTest(PropertyTests.suite());
        
        return suite;
    }

}
