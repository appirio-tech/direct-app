/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author lqz
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * Test suite for the failure tests.
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(DatabaseProjectPaymentAdjustmentPersistenceFailureTests.suite());
        suite.addTest(DatabaseProjectPaymentPersistenceFailureTests.suite());
        suite.addTest(ProjectPaymentAdjustmentManagerImplFailureTests.suite());
        suite.addTest(ProjectPaymentManagerImplFailureTests.suite());

        return suite;
    }

}
