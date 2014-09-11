/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 * @author Thinfox
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * Aggregates stress test cases.
     * @return Stress test fixtures.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(DefaulUploadServicesStressTests.class);
        suite.addTestSuite(DefaulUploadExternalServicesStressTests.class);

        return suite;
    }
}
