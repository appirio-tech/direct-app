/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.review.application.impl.ReviewApplicationManagerImpl;
import com.topcoder.management.review.application.impl.ReviewAuctionManagerImpl;
import com.topcoder.management.review.application.search.ReviewApplicationFilterBuilder;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * Shows usage for the component.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public class Demo extends BaseUnitTests {
    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(Demo.class);
    }

    /**
     * <p>
     * Demo API usage of <code>ReviewApplicationManager</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unused")
    @Test
    public void testDemoReviewApplicationManager() throws Exception {
        // Create an instance of ReviewAuctionManagerImpl using default configuration
        ReviewAuctionManager reviewAuctionManager = new ReviewAuctionManagerImpl();
        // Get Review Auction Types
        List<ReviewAuctionType> types = reviewAuctionManager.getAuctionTypes();

        // Create Auction
        ReviewAuction auction = new ReviewAuction();
        auction.setProjectId(100000);
        auction.setAuctionType(types.get(0));

        auction = reviewAuctionManager.createAuction(auction);
        long auctionId = auction.getId();

        // Create an instance of ReviewApplicationManagerImpl using default configuration
        ReviewApplicationManager reviewApplicationManager = new ReviewApplicationManagerImpl();

        // Create an instance of ReviewApplicationManagerImpl using custom configuration
        reviewApplicationManager = new ReviewApplicationManagerImpl(
            ReviewApplicationManagerImpl.DEFAULT_CONFIG_FILENAME,
            ReviewApplicationManagerImpl.DEFAULT_CONFIG_NAMESPACE);

        // Create an instance of ReviewApplicationManagerImpl using custom configuration
        ConfigurationObject configuration = getConfig(ReviewApplicationManagerImpl.DEFAULT_CONFIG_NAMESPACE);
        reviewApplicationManager = new ReviewApplicationManagerImpl(configuration);

        // Get Review Application Statuses
        List<ReviewApplicationStatus> statuses = reviewApplicationManager.getApplicationStatuses();

        // Create Review Application
        ReviewApplication application = new ReviewApplication();
        application.setUserId(123);
        application.setAuctionId(auctionId);
        application.setApplicationRoleId(4);
        application.setStatus(statuses.get(0));
        application.setCreateDate(new Date());

        application = reviewApplicationManager.createApplication(application);

        long applicationId = application.getId();

        // Update Review Application
        application.setStatus(statuses.get(1));

        reviewApplicationManager.updateApplication(application);

        // Search by auction ID
        Filter auctionIdFilter = ReviewApplicationFilterBuilder.createAuctionIdFilter(auctionId);
        List<ReviewApplication> applications = reviewApplicationManager.searchApplications(auctionIdFilter);

        // Search by user ID
        Filter userIdFilter = ReviewApplicationFilterBuilder.createUserIdFilter(123);
        applications = reviewApplicationManager.searchApplications(userIdFilter);

        // Search by application status ID
        Filter applicationStatusIdFilter =
            ReviewApplicationFilterBuilder.createApplicationStatusIdFilter(statuses.get(1).getId());
        applications = reviewApplicationManager.searchApplications(applicationStatusIdFilter);

        // Search by combination of filters
        Filter filter = new AndFilter(auctionIdFilter, new AndFilter(userIdFilter, applicationStatusIdFilter));
        applications = reviewApplicationManager.searchApplications(filter);
    }

    /**
     * <p>
     * Demo API usage of <code>ReviewAuctionManager</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unused")
    @Test
    public void testDemoReviewAuctionManager() throws Exception {
        // Create an instance of ReviewAuctionManagerImpl using default configuration
        ReviewAuctionManager reviewAuctionManager = new ReviewAuctionManagerImpl();

        // Create an instance of ReviewApplicationManagerImpl using custom configuration
        reviewAuctionManager = new ReviewAuctionManagerImpl(ReviewAuctionManagerImpl.DEFAULT_CONFIG_FILENAME,
            ReviewAuctionManagerImpl.DEFAULT_CONFIG_NAMESPACE);

        // Create an instance of ReviewAuctionManagerImpl using custom configuration
        ConfigurationObject configuration = getConfig(ReviewAuctionManagerImpl.DEFAULT_CONFIG_NAMESPACE);
        reviewAuctionManager = new ReviewAuctionManagerImpl(configuration);

        // Get Review Auction Categories
        List<ReviewAuctionCategory> categories = reviewAuctionManager.getAuctionCategories();

        // Get Review Auction Types
        List<ReviewAuctionType> types = reviewAuctionManager.getAuctionTypes();

        // Create Auction
        ReviewAuction auction = new ReviewAuction();
        auction.setProjectId(100000);
        auction.setAuctionType(types.get(0));

        auction = reviewAuctionManager.createAuction(auction);

        long auctionId = auction.getId();

        // Search Auctions by auction category ID
        List<ReviewAuction> auctions = reviewAuctionManager.searchOpenAuctions(1);

        // Search Auctions by aution category ID and project category IDs
        auctions = reviewAuctionManager.searchOpenAuctions(1, Arrays.asList(1L, 2L, 3L));

        // Get Auction
        auction = reviewAuctionManager.getAuction(auctionId);
    }
}
