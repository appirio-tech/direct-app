/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment;

import java.util.List;

import com.topcoder.management.payment.impl.ProjectPaymentManagementPersistenceException;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This interface represents a project payment manager. It defines CRUD and search methods.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Implementations of this interface are required to be thread safe when entities
 * passed to them are used by the caller in thread safe manner.
 * </p>
 *
 * @author maksimilian, sparemax
 * @version 1.0.1
 */
public interface ProjectPaymentManager {
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
     *             if projectPayment's id is not null, projectPayment's type is null, projectPayment's resourceId is
     *             null, projectPayment's amount is null or negative, projectPayment's createDate is null.
     * @throws ProjectPaymentManagementDataIntegrityException
     *             if project payment integrity is broken, for example if project payment is being persisted with
     *             payment type that is not present in DB.
     * @throws ProjectPaymentManagementException
     *             if some other error occurred when accessing the persistence for example.
     */
    public ProjectPayment create(ProjectPayment projectPayment, String operator) throws ProjectPaymentManagementException;

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
     *             if projectPayment's id is null or negative, projectPayment's resourceId is null, projectPayment's
     *             amount is null or negative, projectPayment's createDate is null.
     * @throws ProjectPaymentNotFoundException
     *             if project payment with ID equal to projectPayment.getProjectPaymentId() doesn't exist in
     *             persistence
     * @throws ProjectPaymentManagementDataIntegrityException
     *             if project payment integrity is broken, for example if project payment is being persisted with
     *             payment type that is not present in DB.
     * @throws ProjectPaymentManagementException
     *             if some other error occurred when accessing the persistence for example.
     */
    public void update(ProjectPayment projectPayment, String operator) throws ProjectPaymentManagementException;

    /**
     * Retrieves the given project payment from persistence by its id.
     *
     * @param projectPaymentId
     *            the id of the project payment
     *
     * @return the project payment, null if nothing was found
     *
     * @throws ProjectPaymentManagementException
     *             if some error occurred when accessing the persistence for example.
     */
    public ProjectPayment retrieve(long projectPaymentId) throws ProjectPaymentManagementException;

    /**
     * Deletes the given project payment from persistence.
     *
     * @param projectPaymentId
     *            the id of project payment to delete
     *
     * @return the flag indicating if project payment was deleted
     *
     * @throws ProjectPaymentManagementException
     *             if some error occurred when accessing the persistence for example.
     */
    public boolean delete(long projectPaymentId) throws ProjectPaymentManagementException;

    /**
     * Searches for all project payments that are matched with the given filter. Returns an empty list if none found.
     *
     * @param filter
     *            the filter
     *
     * @return the list with found project payments that are matched with the given filter (not null, doesn't contain
     *         null)
     *
     * @throws ProjectPaymentManagementPersistenceException
     *             if some error occurred when accessing the persistence
     * @throws ProjectPaymentManagementException
     *             if some other error occurred
     */
    public List<ProjectPayment> search(Filter filter) throws ProjectPaymentManagementException;
}
