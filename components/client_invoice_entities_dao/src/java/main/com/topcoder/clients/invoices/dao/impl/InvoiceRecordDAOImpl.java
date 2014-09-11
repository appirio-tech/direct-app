/*
 * Copyright (C) 2011 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.invoices.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.HibernateException;

import com.topcoder.clients.invoices.dao.InvoiceDAOException;
import com.topcoder.clients.invoices.dao.InvoiceRecordDAO;
import com.topcoder.clients.invoices.model.InvoiceRecord;

/**
 * <p>This class is an implementation of InvoiceRecordDAO that uses Hibernate session to access entities in
 * persistence. It extends GenericDAOImpl&lt;InvoiceRecord&gt;. This class
 * uses Logging Wrapper logger to log errors and debug information.</p>
 *
 * <p><strong>Configuration: </strong>This class will be used with Spring IoC and will be configured using Spring xml
 * file.</p>
 * 
 * <p><strong>Thread safety:</strong> This class has mutable attributes, thus it's not thread safe. But it's assumed
 * that it will be initialized via Spring IoC before calling any business method, this way it's always used in thread
 * safe manner. It uses thread safe SessionFactory, Session and Log instances.</p>
 * 
 * <p>
 * Version 1.1 (TC Accounting Tracking Invoiced Payments Part 2) change log:
 * <ol>
 *   <li>Remove the Spring annotation transaction.</li>
 * </ol>
 * </p>
 * <p>
 * Versions 1.2: (Module Assembly - Contest Fee Based on Percentage of Member Cost Cockpit Pages Update):
 * <ul>
 *   <li>Added {@link #getByPaymentAndInvoiceType(long, long)} get by payment id and invoice type id.</li>
 *   <li>Changed QUERY_BY_CONTEST_INVOICE_TYPE to {@link #QUERY_BY_CONTEST_INVOICE_TYPE_WITH_NULL_PAYMENT_ID}
 *   to add criteria "paymentId is null".</li>
 * </ul>
 * </p>
 *
 * <p>
 * Versions 1.3: (Release Assembly - TC Cockpit Bug Race Cost and Fees Part 1):
 * <ul>
 *   <li>Added constant {@link #QUERY_BY_JIRA_ISSUE_INVOICE_TYPE_WITH_NULL_PAYMENT_ID}.</li>
 *   <li>Added {@link #getByJiraIssueAndInvoiceType(String, long)} get record by JIRA issue id and invoice type id.</li>
 * </ul>
 * </p>
 *
 * @author flexme, minhu, TCSASSEMBLER
 * @version 1.3
 */
public class InvoiceRecordDAOImpl extends GenericDAOImpl<InvoiceRecord> implements InvoiceRecordDAO {
    
    /**
     * <p>Represents class name.</p>
     */
    private static final String CLASS_NAME = "InvoiceRecordDAOImpl";
    
    /**
     * <p>Represents a hql query used for retrieving {@link InvoiceRecord} by contest id and invoice type id.</p>
     * @since 1.1
     */
    private static final String QUERY_BY_CONTEST_INVOICE_TYPE_WITH_NULL_PAYMENT_ID =
        "from InvoiceRecord where contestId=:contestId and invoiceType.id=:invoiceTypeId and paymentId is null";

    /**
     * <p>Represents a hql query used for retrieving {@link InvoiceRecord} by JIRA issue id and invoice type id.</p>
     * @since 1.3
     */
    private static final String QUERY_BY_JIRA_ISSUE_INVOICE_TYPE_WITH_NULL_PAYMENT_ID =
            "from InvoiceRecord where jiraIssueId=:jiraIssueId and invoiceType.id=:invoiceTypeId and paymentId is null";

    /**
     * <p>Represents a hql query used for retrieving {@link InvoiceRecord} by payment id.</p>
     */
    private static final String QUERY_BY_PAYMENT = "from InvoiceRecord where paymentId=:paymentId";
    
    /**
     * <p>Represents a hql query used for retrieving {@link InvoiceRecord} by payment id and invoice type id.</p>
     * @since 1.1
     */
    private static final String QUERY_BY_PAYMENT_INVOICE_TYPE =
        "from InvoiceRecord where paymentId=:paymentId and invoiceType.id=:invoiceTypeId";
    
    /**
     * <p>Represents a hql query used for retrieving the count of {@link InvoiceRecord} by invoice id.</p>
     */
    private static final String QUERY_COUNT_BY_INVOICE_STRING = "select count(*) from InvoiceRecord where invoice.id=:invoiceId";
    
    /**
     * <p>Creates new instance of <code>{@link InvoiceRecordDAOImpl}</code> class.</p>
     */
    public InvoiceRecordDAOImpl() {
        // empty constructor
    }

    /**
     * <p>Gets the <code>InvoiceRecord</code> by contest id and invoice type. Returns null if no result found.</p>
     * 
     * @param contestId the contest id.
     * @param invoiceTypeId the id of the invoice type.
     * @return the <code>InvoiceRecord</code> for the specified contest id and invoice type. Null if not found.
     * @throws IllegalArgumentException if contest id is not positive, or invoice type id is not positive.
     * @throws InvoiceDAOException if some other error occurred.
     */
    @SuppressWarnings("unchecked")
    public InvoiceRecord getByContestAndInvoiceType(long contestId, long invoiceTypeId) throws InvoiceDAOException {
        final String methodName = "getByContestAndInvoiceType";
        final long executionStart = System.currentTimeMillis();
        Helper.logMethodEntered(getLog(), CLASS_NAME, methodName);

        Helper.checkIsPositive(getLog(), contestId, "contestId", CLASS_NAME, methodName);
        Helper.checkIsPositive(getLog(), invoiceTypeId, "invoiceTypeId", CLASS_NAME, methodName);
        
        try {
            List<InvoiceRecord> list = getSession().createQuery(QUERY_BY_CONTEST_INVOICE_TYPE_WITH_NULL_PAYMENT_ID)
                .setParameter("contestId", contestId)
                .setParameter("invoiceTypeId", invoiceTypeId).list();
            
            // log method exit
            Helper.logMethodExited(getLog(), CLASS_NAME, methodName, executionStart);
            
            if (list.size() == 0) {
                return null;
            } else {
                return list.get(0);
            }
        } catch (HibernateException e) {
            InvoiceDAOException ex = new InvoiceDAOException(MessageFormat.format(
                    "Error occurred when retrieving entities of {0}", "InvoiceRecord"), e);
            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, CLASS_NAME, methodName), ex);

            throw ex;
        }
    }

    /**
     * <p>Gets the <code>InvoiceRecord</code> by JIRA issue id and invoice type. Returns null if no result found.</p>
     *
     * @param jiraIssueId the jira issue id.
     * @param invoiceTypeId the id of the invoice type.
     * @return the <code>InvoiceRecord</code> for the specified contest id and invoice type. Null if not found.
     * @throws IllegalArgumentException if contest id is not positive, or invoice type id is not positive.
     * @throws InvoiceDAOException if some other error occurred.
     * @since 1.3
     */
    public InvoiceRecord getByJiraIssueAndInvoiceType(String jiraIssueId, long invoiceTypeId) throws InvoiceDAOException {
        final String methodName = "getByJiraIssueAndInvoiceType";
        final long executionStart = System.currentTimeMillis();
        Helper.logMethodEntered(getLog(), CLASS_NAME, methodName);

        Helper.checkString(getLog(), jiraIssueId, "jiraIssueId", CLASS_NAME, methodName);
        Helper.checkIsPositive(getLog(), invoiceTypeId, "invoiceTypeId", CLASS_NAME, methodName);

        try {
            List<InvoiceRecord> list = getSession().createQuery(QUERY_BY_JIRA_ISSUE_INVOICE_TYPE_WITH_NULL_PAYMENT_ID)
                    .setParameter("jiraIssueId", jiraIssueId)
                    .setParameter("invoiceTypeId", invoiceTypeId).list();

            // log method exit
            Helper.logMethodExited(getLog(), CLASS_NAME, methodName, executionStart);

            if (list.size() == 0) {
                return null;
            } else {
                return list.get(0);
            }
        } catch (HibernateException e) {
            InvoiceDAOException ex = new InvoiceDAOException(MessageFormat.format(
                    "Error occurred when retrieving entities of {0}", "InvoiceRecord"), e);
            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, CLASS_NAME, methodName), ex);

            throw ex;
        }
    }

    /**
     * <p>Gets the <code>InvoiceRecord</code> by payment id. Returns null if no result found.</p>
     * 
     * @param paymentId the payment id.
     * @return the <code>InvoiceRecord</code> for the specified payment id. Null if not found.
     * @throws IllegalArgumentException if payment id is not positive.
     * @throws InvoiceDAOException if some other error occurred.
     */
    @SuppressWarnings("unchecked")
    public InvoiceRecord getByPayment(long paymentId) throws InvoiceDAOException {
        final String methodName = "getByPayment";
        final long executionStart = System.currentTimeMillis();
        Helper.logMethodEntered(getLog(), CLASS_NAME, methodName);

        Helper.checkIsPositive(getLog(), paymentId, "paymentId", CLASS_NAME, methodName);
        
        try {
            List<InvoiceRecord> list = getSession().createQuery(QUERY_BY_PAYMENT)
                .setParameter("paymentId", paymentId).list();
            
            // log method exit
            Helper.logMethodExited(getLog(), CLASS_NAME, methodName, executionStart);
            
            if (list.size() == 0) {
                return null;
            } else {
                return list.get(0);
            }
        } catch (HibernateException e) {
            InvoiceDAOException ex = new InvoiceDAOException(MessageFormat.format(
                    "Error occurred when retrieving entities of {0}", "InvoiceRecord"), e);
            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, CLASS_NAME, methodName), ex);

            throw ex;
        }
    }
    
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
    @SuppressWarnings("unchecked")
    public InvoiceRecord getByPaymentAndInvoiceType(long paymentId, long invoiceTypeId) throws InvoiceDAOException {
        final String methodName = "getByPaymentAndInvoiceType";
        final long executionStart = System.currentTimeMillis();
        Helper.logMethodEntered(getLog(), CLASS_NAME, methodName);

        Helper.checkIsPositive(getLog(), paymentId, "paymentId", CLASS_NAME, methodName);
        Helper.checkIsPositive(getLog(), invoiceTypeId, "invoiceTypeId", CLASS_NAME, methodName);
        
        try {
            List<InvoiceRecord> list = getSession().createQuery(QUERY_BY_PAYMENT_INVOICE_TYPE)
                .setParameter("paymentId", paymentId).setParameter("invoiceTypeId", invoiceTypeId).list();
            
            // log method exit
            Helper.logMethodExited(getLog(), CLASS_NAME, methodName, executionStart);
            
            if (list.size() == 0) {
                return null;
            } else {
                return list.get(0);
            }
        } catch (HibernateException e) {
            InvoiceDAOException ex = new InvoiceDAOException(MessageFormat.format(
                    "Error occurred when retrieving entities of {0}", "InvoiceRecord"), e);
            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, CLASS_NAME, methodName), ex);

            throw ex;
        }        
    }
    
    /**
     * <p>Get the number of invoice records of the specified invoice.</p>
     * 
     * @param invoiceId the id of the specified invoice.
     * @return the number of invoice records of the specified invoice.
     * @throws IllegalArgumentException if invoice id is not positive.
     * @throws InvoiceDAOException if any other error occurred.
     */
    public int countByInvoice(long invoiceId) throws InvoiceDAOException {
        final String methodName = "countByInvoice";
        final long executionStart = System.currentTimeMillis();
        Helper.logMethodEntered(getLog(), CLASS_NAME, methodName);
        
        Helper.checkIsPositive(getLog(), invoiceId, "invoiceId", CLASS_NAME, methodName);
        
        try {
            int count = ((Long) getSession().createQuery(QUERY_COUNT_BY_INVOICE_STRING)
                .setParameter("invoiceId", invoiceId).uniqueResult()).intValue();
            
            // log method exit
            Helper.logMethodExited(getLog(), CLASS_NAME, methodName, executionStart);
            return count;
        } catch (HibernateException e) {
            InvoiceDAOException ex = new InvoiceDAOException(MessageFormat.format(
                    "Error occurred when retrieving entities of {0}", "InvoiceRecord"), e);
            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, CLASS_NAME, methodName), ex);

            throw ex;
        }
    }
}
