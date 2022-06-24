/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.management.review.application.impl.ReviewApplicationManagerImplUnitTests;
import com.topcoder.management.review.application.impl.ReviewApplicationPersistenceExceptionUnitTests;
import com.topcoder.management.review.application.impl.ReviewAuctionManagerImplUnitTests;
import com.topcoder.management.review.application.impl.ReviewAuctionPersistenceExceptionUnitTests;
import com.topcoder.management.review.application.impl.persistence.BaseDatabasePersistenceUnitTests;
import com.topcoder.management.review.application.impl.persistence.DatabaseReviewApplicationPersistenceUnitTests;
import com.topcoder.management.review.application.impl.persistence.DatabaseReviewAuctionPersistenceUnitTests;
import com.topcoder.management.review.application.search.ReviewApplicationFilterBuilderUnitTests;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author sparemax
 * @version 1.0.6
 */
public class UnitTests extends TestCase {
    /**
     * <p>
     * All unit test cases.
     * </p>
     *
     * @return The test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(Demo.suite());
        suite.addTest(HelperUnitTests.suite());

        suite.addTest(BaseLookupEntityUnitTests.suite());
        suite.addTest(ReviewApplicationResourceRoleUnitTests.suite());
        suite.addTest(ReviewApplicationRoleUnitTests.suite());
        suite.addTest(ReviewApplicationStatusUnitTests.suite());
        suite.addTest(ReviewApplicationUnitTests.suite());
        suite.addTest(ReviewAuctionCategoryUnitTests.suite());
        suite.addTest(ReviewAuctionTypeUnitTests.suite());
        suite.addTest(ReviewAuctionUnitTests.suite());

        suite.addTest(ReviewApplicationManagerImplUnitTests.suite());
        suite.addTest(ReviewAuctionManagerImplUnitTests.suite());

        suite.addTest(DatabaseReviewApplicationPersistenceUnitTests.suite());
        suite.addTest(DatabaseReviewAuctionPersistenceUnitTests.suite());
        suite.addTest(BaseDatabasePersistenceUnitTests.suite());

        suite.addTest(ReviewApplicationFilterBuilderUnitTests.suite());

        // Exceptions
        suite.addTest(ReviewApplicationManagementConfigurationExceptionUnitTests.suite());
        suite.addTest(ReviewApplicationManagementExceptionUnitTests.suite());
        suite.addTest(ReviewApplicationManagerExceptionUnitTests.suite());
        suite.addTest(ReviewAuctionManagerExceptionUnitTests.suite());
        suite.addTest(ReviewApplicationPersistenceExceptionUnitTests.suite());
        suite.addTest(ReviewAuctionPersistenceExceptionUnitTests.suite());

        return suite;
    }

}
