/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.impl;

import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.payment.ProjectPaymentAdjustment;
import com.topcoder.management.payment.ProjectPaymentAdjustmentValidationException;
import com.topcoder.management.payment.ProjectPaymentManagementConfigurationException;

/**
 * <p>
 * This interface represents a project payment adjustment persistence. Currently it defines create/update and retrieve
 * by project id methods for project payment adjustment.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Implementations of this interface are required to be thread safe when configure()
 * method is called just once right after instantiation and entities passed to them are used by the caller in thread
 * safe manner.
 * </p>
 *
 * @author maksimilian, sparemax
 * @version 1.0
 */
public interface ProjectPaymentAdjustmentPersistence {
    /**
     * Creates or updates the given project payment adjustment in persistence.
     *
     * @param projectPaymentAdjustment
     *            the project payment adjustment to create or update
     *
     * @throws IllegalArgumentException
     *             if projectPaymentAdjustment is null
     * @throws ProjectPaymentAdjustmentValidationException
     *             if projectPaymentAdjustment's project id is null, projectPaymentAdjustment's resource role id is
     *             null, projectPaymentAdjustment's fixed amount is negative, projectPaymentAdjustment's multiplier is
     *             negative, if both fixed amount and multiplier are non-null at the same time.
     * @throws ProjectPaymentManagementPersistenceException
     *             if some other error occurred when accessing the persistence
     * @throws IllegalStateException
     *             if persistence was not configured properly
     */
    public void save(ProjectPaymentAdjustment projectPaymentAdjustment)
        throws ProjectPaymentAdjustmentValidationException, ProjectPaymentManagementPersistenceException;

    /**
     * Retrieves the given project payment adjustments from persistence by project id.
     *
     * @param projectId
     *            the project id of the project payment adjustment
     *
     * @return the list or project payment adjustments matching the criteria, empty list if nothing was found.
     *
     * @throws ProjectPaymentManagementPersistenceException
     *             if some error occurred when accessing the persistence
     * @throws IllegalStateException
     *             if persistence was not configured properly
     */
    public List<ProjectPaymentAdjustment> retrieveByProjectId(long projectId)
        throws ProjectPaymentManagementPersistenceException;

    /**
     * Configures the instance with use of the given configuration object.
     *
     * @param configuration
     *            the configuration object
     *
     * @throws IllegalArgumentException
     *             if configuration is null (is not thrown by implementations that don't use any configuration
     *             parameters)
     * @throws ProjectPaymentManagementConfigurationException
     *             if some error occurred when initializing an instance using the given configuration (is not thrown
     *             by implementations that don't use any configuration parameters)
     */
    public void configure(ConfigurationObject configuration);
}
