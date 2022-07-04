/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.catalog.service.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        /*suite.addTestSuite(CatalogServiceImplAccuracyTest11.class);
        
        
        suite.addTestSuite(AssetDTOAccuracyTest.class); */
        suite.addTestSuite(CatalogServiceImplAccuracyTest.class);
 /*       suite.addTestSuite(EntityNotFoundExceptionAccuracyTest.class);
        suite.addTestSuite(PersistenceExceptionAccuracyTest.class);
        suite.addTestSuite(SearchCriteriaAccuracyTest.class);
        */

        return suite;
    }
}
