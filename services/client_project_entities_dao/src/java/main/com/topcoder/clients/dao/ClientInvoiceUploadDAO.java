/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao;

import com.topcoder.clients.model.ClientInvoiceUpload;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * This interface defines the contracts for the DAO of the <code>ClientInvoiceUpload</code>
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TopCoder Cockpit - Billing Management)
 */
public interface ClientInvoiceUploadDAO  {

    /**
     * Creates the new <code>ClientInvoiceUpload</code> record.
     *
     * @param invoiceUpload the <code>ClientInvoiceUpload</code> instance to insert
     * @throws ClientInvoiceUploadPersistenceException if there is error with the persistence
     */
    void create(ClientInvoiceUpload invoiceUpload) throws ClientInvoiceUploadPersistenceException;

    /**
     * Updates the <code>ClientInvoiceUpload</code>.
     *
     * @param invoiceUpload the <code>ClientInvoiceUpload</code> to update
     * @throws ClientInvoiceUploadEntityNotFoundException if the entity to update does not exist
     * @throws ClientInvoiceUploadPersistenceException if there is any error with persistence.
     */
    void update(ClientInvoiceUpload invoiceUpload) throws ClientInvoiceUploadEntityNotFoundException, ClientInvoiceUploadPersistenceException;

    /**
     * Deletes the invoice upload record with the specified <code>invoiceUploadId</code>
     *
     * @param invoiceUploadId the id of the invoice upload record
     * @throws ClientInvoiceUploadEntityNotFoundException if the invoice upload record does not exist
     * @throws ClientInvoiceUploadPersistenceException if there is any error with the persistence.
     */
    void delete(long invoiceUploadId) throws ClientInvoiceUploadEntityNotFoundException, ClientInvoiceUploadPersistenceException;

    /**
     * Gets the <code>ClientInvoiceUpload</code> from persistence with the given <code>invoiceUploadId</code>
     *
     * @param invoiceUploadId the id of the invoice upload record
     * @return the <code>ClientInvoiceUpload</code> instance
     * @throws ClientInvoiceUploadEntityNotFoundException if the entity is not found with the given id
     * @throws ClientInvoiceUploadPersistenceException if there is any error with the persistence
     */
    ClientInvoiceUpload get(long invoiceUploadId) throws ClientInvoiceUploadEntityNotFoundException, ClientInvoiceUploadPersistenceException;

    /**
     * Gets all the <code>ClientInvoiceUpload</code> belong to the given <code>clientId</code>
     *
     * @param clientId the id of the client
     * @return a list of <code>ClientInvoiceUpload</code>
     * @throws ClientInvoiceUploadPersistenceException if there is any error with the persistence.
     */
    List<ClientInvoiceUpload> getAllByClientId(long clientId) throws ClientInvoiceUploadPersistenceException;

    /**
     * Gets all the <code>ClientInvoiceUpload</code> of the given client and filtered
     * by the given startDate and endDate.
     *
     * @param clientId the id of the client
     * @param startDate the start date used to filter
     * @param endDate the end date used to filter
     * @return a list of <code>ClientInvoiceUpload</code> instances
     * @throws ClientInvoiceUploadPersistenceException if there is any error with the persistence
     */
    public List<ClientInvoiceUpload> getAllByClientId(long clientId, Date startDate,
                                                      Date endDate) throws ClientInvoiceUploadPersistenceException;

}
