/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author kaqi072821
 * @version 1.0
 */
public class FailureTests extends TestCase {

    /**
     * The suite to aggregates all failure tests.
     *
     * @return The suite to aggregates all failure tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(CatalogServiceImplArgumentFailureTest.class);
        suite.addTestSuite(CatalogServiceImplFailureTest.class);
        suite.addTestSuite(SearchCriteriaFailureTest.class);
        return suite;
    }

}