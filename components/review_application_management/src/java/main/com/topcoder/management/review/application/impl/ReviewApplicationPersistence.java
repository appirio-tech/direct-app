/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.impl;

import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.review.application.ReviewApplication;
import com.topcoder.management.review.application.ReviewApplicationManagementConfigurationException;
import com.topcoder.management.review.application.ReviewApplicationStatus;

/**
 * <p>
 * This interface defines contract for accessing review application related data from persistence.
 * </p>
 *
 * <p>
 * It provides methods to create review application, update review application and get review application statuses.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Implementations of this interface are required to be thread safe when configure()
 * method is called just once right after instantiation and entities passed to them are used by the caller in thread
 * safe manner.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public interface ReviewApplicationPersistence {
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
     * @throws ReviewApplicationPersistenceException
     *             if any other error occurred during the operation
     */
    public ReviewApplication createApplication(ReviewApplication application)
        throws ReviewApplicationPersistenceException;

    /**
     * Update a review application.
     *
     * @param application
     *            the review application to update
     *
     * @throws IllegalArgumentException
     *             if application is null
     * @throws ReviewApplicationPersistenceException
     *             if any other error occurred during the operation
     */
    public void updateApplication(ReviewApplication application)
        throws ReviewApplicationPersistenceException;

    /**
     * Returns all application statuses.
     *
     * @return all application statuses, empty list will be returned if there's no application statuses defined
     *
     * @throws ReviewApplicationPersistenceException
     *             if any other error occurred during the operation in the system (normally won't happen)
     */
    public List<ReviewApplicationStatus> getApplicationStatuses()
        throws ReviewApplicationPersistenceException;

    /**
     * Configures this instance with use of the given configuration object.
     *
     * @param configuration
     *            the configuration object
     *
     * @throws IllegalArgumentException
     *             if configuration is null (is not thrown by implementations that don't use any configuration
     *             parameters)
     * @throws ReviewApplicationManagementConfigurationException
     *             if some error occurred when initializing an instance using the given configuration (is not thrown by
     *             implementations that don't use any configuration parameters)
     */
    public void configure(ConfigurationObject configuration);
}
