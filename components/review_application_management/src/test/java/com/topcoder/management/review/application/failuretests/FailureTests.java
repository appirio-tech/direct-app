/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.management.review.application.failuretests.impl.ReviewApplicationManagerImplFailureTests;
import com.topcoder.management.review.application.failuretests.impl.ReviewAuctionManagerImplFailureTests;
import com.topcoder.management.review.application.failuretests.impl.persistence.DatabaseReviewApplicationPersistenceFailureTests;
import com.topcoder.management.review.application.failuretests.impl.persistence.DatabaseReviewAuctionPersistenceFailureTests;
import com.topcoder.management.review.application.failuretests.search.ReviewApplicationFilterBuilderFailureTests;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0.6
 */
public class FailureTests extends TestCase {
    /**
     * <p>
     * All unit test cases.
     * </p>
     *
     * @return The test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(ReviewApplicationFilterBuilderFailureTests.suite());

        suite.addTest(ReviewApplicationManagerImplFailureTests.suite());
        suite.addTest(ReviewAuctionManagerImplFailureTests.suite());

        suite.addTest(DatabaseReviewAuctionPersistenceFailureTests.suite());
        suite.addTest(DatabaseReviewApplicationPersistenceFailureTests.suite());

        return suite;
    }
}