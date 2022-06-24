/*
 * Copyright (c) 2004, TopCoder, Inc. All rights reserved
 */
package com.topcoder.date.workdays;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(DefaultWorkdaysFactoryTest.class);
        suite.addTestSuite(WorkdaysUnitOfTimeTest.class);
        suite.addTestSuite(DefaultWorkdaysTest.class);
        suite.addTestSuite(DefaultWorkdaysFunctionalityTest.class);
        suite.addTestSuite(ConfigurationFileExceptionTest.class);
        suite.addTestSuite(Demo.class);

        return suite;
    }
}
