/**
 *
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
package com.cronos.onlinereview.services.uploads;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.onlinereview.services.uploads.impl.DefaultManagersProviderTest;
import com.cronos.onlinereview.services.uploads.impl.DefaultUploadExternalServicesTest;
import com.cronos.onlinereview.services.uploads.impl.DefaultUploadServicesTest;
import com.cronos.onlinereview.services.uploads.impl.HelperTest;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * Adds all test suites to the unit test suite and returns the unit test suite.
     *
     * @return the unit test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(ConfigurationExceptionTest.suite());
        suite.addTest(InvalidProjectExceptionTest.suite());
        suite.addTest(InvalidProjectPhaseExceptionTest.suite());
        suite.addTest(InvalidSubmissionExceptionTest.suite());
        suite.addTest(InvalidSubmissionStatusExceptionTest.suite());
        suite.addTest(InvalidUserExceptionTest.suite());
        suite.addTest(PersistenceExceptionTest.suite());

        suite.addTest(DefaultManagersProviderTest.suite());
        suite.addTest(DefaultUploadExternalServicesTest.suite());
        suite.addTest(DefaultUploadServicesTest.suite());
        suite.addTest(HelperTest.suite());
        suite.addTest(Demo.suite());
        return suite;
    }

}
