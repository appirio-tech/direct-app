package com.topcoder.util.config.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all accuracy test cases.</p>
 *
 * @author TopCoder Software
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        //suite.addTest(XXX.suite());
        suite.addTest(new TestSuite(AccTestConfigManager.class));
        suite.addTest(new TestSuite(EscapeTest.class));

        return suite;
    }

}
