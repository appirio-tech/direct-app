/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.management.review.application.accuracytests.impl.ReviewApplicationManagerImplAccuracyTests;
import com.topcoder.management.review.application.accuracytests.impl.ReviewAuctionManagerImplAccuracyTests;
import com.topcoder.management.review.application.accuracytests.impl.persistence.DatabaseReviewApplicationPersistenceAccuracyTests;
import com.topcoder.management.review.application.accuracytests.impl.persistence.DatabaseReviewAuctionPersistenceAccuracyTests;
import com.topcoder.management.review.application.accuracytests.search.ReviewApplicationFilterBuilderAccuracyTests;

/**
 * <p>
 * This test case aggregates all accuracy test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0.6
 */
public class AccuracyTests extends TestCase {
    /**
     * <p>
     * All unit test cases.
     * </p>
     *
     * @return The test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(DatabaseReviewAuctionPersistenceAccuracyTests.suite());
        suite.addTest(DatabaseReviewApplicationPersistenceAccuracyTests.suite());

        suite.addTest(ReviewApplicationManagerImplAccuracyTests.suite());
        suite.addTest(ReviewAuctionManagerImplAccuracyTests.suite());

        suite.addTest(ReviewApplicationFilterBuilderAccuracyTests.suite());

        return suite;
    }
}