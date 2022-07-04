/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * @author kshatriyan
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    /**
     * Suite containing all accuracy test cases.
     *
     * @return the accuracy test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(ConfigurationExceptionTest.suite());
        suite.addTest(FullProjectDataTest.suite());
        suite.addTest(ProjectServicesExceptionTest.suite());
        suite.addTest(ProjectServicesImplTest.suite());
        return suite;
    }

}
