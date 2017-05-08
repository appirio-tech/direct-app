/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.impl;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.payment.ProjectPayment;
import com.topcoder.management.payment.ProjectPaymentManagementConfigurationException;
import com.topcoder.management.payment.ProjectPaymentManagementDataIntegrityException;
import com.topcoder.management.payment.ProjectPaymentNotFoundException;
import com.topcoder.management.payment.ProjectPaymentValidationException;

/**
 * <p>
 * This interface represents a project payment persistence. Currently it defines CRUD methods for project payment.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Implementations of this interface are required to be thread safe when configure()
 * method is called just once right after instantiation and entities passed to them are used by the caller in thread
 * safe manner.
 * </p>
 *
 * @author maksimilian, sparemax
 * @version 1.0.1
 */
public interface ProjectPaymentPersistence {
    /**
     * Creates the given project payment in persistence.
     *
     * @param projectPayment
     *            the project payment with updated data
     * @param operator
     *            ID of the user doing the operation
     *
     * @return the inserted project payment with generated id.
     *
     * @throws IllegalArgumentException
     *             if projectPayment is null or if operator is null or empty string
     * @throws ProjectPaymentValidationException
     *             if projectPayment's id is not null, projectPayment's type is null, projectPaymentType's
     *             projectPaymentTypeId is null, projectPayment's resourceId is null, projectPayment's amount is null
     *             or negative, projectPayment's createDate is null.
     * @throws ProjectPaymentManagementDataIntegrityException
     *             if project payment integrity is broken, for example if project payment is being persisted with
     *             payment type that is not present in DB.
     * @throws ProjectPaymentManagementPersistenceException
     *             if some other error occurred when accessing the persistence
     * @throws IllegalStateException
     *             if persistence was not configured properly
     */
    public ProjectPayment create(ProjectPayment projectPayment, String operator) throws ProjectPaymentValidationException,
        ProjectPaymentManagementDataIntegrityException, ProjectPaymentManagementPersistenceException;

    /**
     * Updates the given project payment in persistence.
     *
     * @param projectPayment
     *            the project payment with updated data
     * @param operator
     *            ID of the user doing the operation
     *
     * @throws IllegalArgumentException
     *             if projectPayment is null or if operator is null or empty string
     * @throws ProjectPaymentValidationException
     *             if projectPayment's id is null or negative, projectPayment's type is null, projectPaymentType's
     *             projectPaymentTypeId is null, projectPayment's resourceId is null, projectPayment's amount is null
     *             or negative, projectPayment's createDate is null.
     * @throws ProjectPaymentNotFoundException
     *             if project payment with ID equal to projectPayment.getProjectPaymentId() doesn't exist in
     *             persistence
     * @throws ProjectPaymentManagementDataIntegrityException
     *             if project payment integrity is broken, for example if project payment is being persisted with
     *             payment type that is not present in DB.
     * @throws ProjectPaymentManagementPersistenceException
     *             if some other error occurred when accessing the persistence
     * @throws IllegalStateException
     *             if persistence was not configured properly
     */
    public void update(ProjectPayment projectPayment, String operator) throws ProjectPaymentValidationException,
        ProjectPaymentNotFoundException, ProjectPaymentManagementDataIntegrityException,
        ProjectPaymentManagementPersistenceException;

    /**
     * Retrieves the given project payment from persistence by its id.
     *
     * @param projectPaymentId
     *            the id of the project payment
     *
     * @return the project payment, null if nothing was found
     *
     * @throws ProjectPaymentManagementPersistenceException
     *             if some error occurred when accessing the persistence
     * @throws IllegalStateException
     *             if persistence was not configured properly
     */
    public ProjectPayment retrieve(long projectPaymentId) throws ProjectPaymentManagementPersistenceException;

    /**
     * Deletes the given project payment from persistence.
     *
     * @param projectPaymentId
     *            the id of project payment to delete
     *
     * @return the flag indicating if project payment was deleted
     *
     * @throws ProjectPaymentManagementPersistenceException
     *             if some error occurred when accessing the persistence
     * @throws IllegalStateException
     *             if persistence was not configured properly
     */
    public boolean delete(long projectPaymentId) throws ProjectPaymentManagementPersistenceException;

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
