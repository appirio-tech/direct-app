/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all stress test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * <p>
     * All stress test cases.
     * </p>
     *
     * @return The test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(DatabaseProjectPaymentAdjustmentPersistenceStressTests.class);
        suite.addTestSuite(DatabaseProjectPaymentPersistenceStressTests.class);
        suite.addTestSuite(ProjectPaymentManagerImplStressTests.class);
        return suite;
    }

}
