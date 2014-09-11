/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.1
 */
public class StressTests extends TestCase {
    /**
     * <p>
     * Aggregates all stress tests in this class.
     * </p>
     * @return Test suite aggregating all stress tests.
     */
    public static Test suite() {

        final TestSuite suite = new TestSuite();
        suite.addTestSuite(CatalogServiceImplStressTest.class);
        suite.addTestSuite(CatalogServicesStressTests.class);
        return suite;
    }
}