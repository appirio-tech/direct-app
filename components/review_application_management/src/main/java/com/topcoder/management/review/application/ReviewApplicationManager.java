/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application;

import java.util.List;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This interface defines the contract to manage review applications.
 * </p>
 *
 * <p>
 * It provides methods to create review application, update review application, get review application statuses and
 * search review applications.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Implementations of this interface are required to be thread safe when entities
 * passed to them are used by the caller in thread safe manner.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public interface ReviewApplicationManager {
    /**
     * Create a review application.
     *
     * @param application
     *            the review application to create
     *
     * @return the created ReviewApplication
     *
     * @throws IllegalArgumentException
     *             if application is null
     * @throws ReviewApplicationManagerException
     *             if any other error occurred during the operation
     */
    public ReviewApplication createApplication(ReviewApplication application) throws ReviewApplicationManagerException;

    /**
     * Update a review application.
     *
     * @param application
     *            the review application to update
     *
     * @throws IllegalArgumentException
     *             if application is null
     * @throws ReviewApplicationManagerException
     *             if any other error occurred during the operation
     */
    public void updateApplication(ReviewApplication application) throws ReviewApplicationManagerException;

    /**
     * Search review applications.
     *
     * @param filter
     *            the filter for searching review applications (null if all review applications need to be retrieved).
     *
     * @return the list of ReviewApplication's matching the search filter, empty list will be returned if there's no
     *         matching ReviewApplication.
     *
     * @throws ReviewApplicationManagerException
     *             if any other error occurred during the operation
     */
    public List<ReviewApplication> searchApplications(Filter filter) throws ReviewApplicationManagerException;

    /**
     * Returns all application statuses.
     *
     * @return all application statuses, empty list will be returned if there's no application statuses defined in the
     *         system (normally won't happen)
     *
     * @throws ReviewApplicationManagerException
     *             if any other error occurred during the operation
     */
    public List<ReviewApplicationStatus> getApplicationStatuses() throws ReviewApplicationManagerException;
}
