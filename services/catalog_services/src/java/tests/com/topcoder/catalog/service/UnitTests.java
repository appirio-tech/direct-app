/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>This test case aggregates all unit tests for the component.</p>
     *
     * @return the test suite for the component
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        // exceptions
        suite.addTestSuite(PersistenceExceptionTest.class);
        suite.addTestSuite(EntityNotFoundExceptionTest.class);
        suite.addTestSuite(AssetDTOTest.class);
        suite.addTestSuite(SearchCriteriaTest.class);
        // CatalogServiceImpl
        suite.addTestSuite(CatalogServiceImplTest.class);
        suite.addTestSuite(CatalogServiceImplFailTest.class);
        suite.addTestSuite(CatalogServiceImplCreateAssetIllegalArgumentsTest.class);
        suite.addTestSuite(CatalogServiceImplCreateVersionIllegalArgumentsTest.class);
        suite.addTestSuite(CatalogServiceImplUpdateComponentIllegalArgumentsTest.class);
        // demo
        suite.addTestSuite(DemoTest.class);

        suite.addTestSuite(BugFixTest.class);
        return suite;
    }

}
