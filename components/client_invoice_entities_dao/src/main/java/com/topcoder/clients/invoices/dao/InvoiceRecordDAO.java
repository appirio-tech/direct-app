/*
 * Copyright (C) 2011 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.invoices.dao;

import com.topcoder.clients.invoices.model.InvoiceRecord;

/**
 * <p>This interface represents a <code>InvoiceRecord</code> DAO. It extends GenericDAO&lt;InvoiceRecord&gt; and provides an
 * additional methods for retrieving <code>InvoiceRecord</code> by contest id and invoice type or by payment id.</p>
 *
 * <p><strong>Thread safety:</strong> Implementations of this interface must be thread safe when entities passed to them
 * are used by the caller in thread safe manner.</p>
 * 
 * <p>
 * Versions 1.1: (Module Assembly - Contest Fee Based on Percentage of Member Cost Cockpit Pages Update):
 * <ul>
 *   <li>Added {@link #getByPaymentAndInvoiceType(long, long)} get by payment id and invoice type id.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Versions 1.2: (Release Assembly - TC Cockpit Bug Race Cost and Fees Part 1):
 * <ul>
 *   <li>Added {@link #getByJiraIssueAndInvoiceType(String, long)} get record by JIRA issue id and invoice type id.</li>
 * </ul>
 * </p>
 *
 * @author flexme, minhu, TCSASSEMBLER
 * @version 1.2
 */
public interface InvoiceRecordDAO extends GenericDAO<InvoiceRecord> {
    
    /**
     * <p>Gets the <code>InvoiceRecord</code> by contest id and invoice type. Returns null if no result found.</p>
     * 
     * @param contestId the contest id.
     * @param invoiceTypeId the id of the invoice type.
     * @return the <code>InvoiceRecord</code> for the specified contest id and invoice type. Null if not found.
     * @throws IllegalArgumentException if contest id is not positive, or invoice type id is not positive.
     * @throws InvoiceDAOException if some other error occurred.
     */
    InvoiceRecord getByContestAndInvoiceType(long contestId, long invoiceTypeId) throws InvoiceDAOException;

    /**
     * <p>Gets the <code>InvoiceRecord</code> by JIRA issue id and invoice type. Returns null if no result found.</p>
     *
     * @param jiraIssueId the jira issue id.
     * @param invoiceTypeId the id of the invoice type.
     * @return the <code>InvoiceRecord</code> for the specified contest id and invoice type. Null if not found.
     * @throws IllegalArgumentException if contest id is not positive, or invoice type id is not positive.
     * @throws InvoiceDAOException if some other error occurred.
     * @since 1.2
     */
    InvoiceRecord getByJiraIssueAndInvoiceType(String jiraIssueId, long invoiceTypeId) throws InvoiceDAOException;

    /**
     * <p>Gets the <code>InvoiceRecord</code> by payment id. Returns null if no result found.</p>
     * 
     * @param paymentId the payment id.
     * @return the <code>InvoiceRecord</code> for the specified payment id. Null if not found.
     * @throws IllegalArgumentException if payment id is not positive.
     * @throws InvoiceDAOException if some other error occurred.
     */
    InvoiceRecord getByPayment(long paymentId) throws InvoiceDAOException;
    
    /**
     * <p>Gets the <code>InvoiceRecord</code> by payment id. Returns null if no result found.</p>
     * 
     * @param paymentId the payment id.
     * @param invoiceTypeId the id of the invoice type.
     * @return the <code>InvoiceRecord</code> for the specified payment id. Null if not found.
     * @throws IllegalArgumentException if payment id is not positive.
     * @throws InvoiceDAOException if some other error occurred.
     * @since 1.1
     */
    InvoiceRecord getByPaymentAndInvoiceType(long paymentId, long invoiceTypeId) throws InvoiceDAOException;    
    
    /**
     * <p>Get the number of invoice records of the specified invoice.</p>
     * 
     * @param invoiceId the id of the specified invoice.
     * @return the number of invoice records of the specified invoice.
     * @throws IllegalArgumentException if invoice id is not positive.
     * @throws InvoiceDAOException if any other error occurred.
     */
    int countByInvoice(long invoiceId) throws InvoiceDAOException;
}
