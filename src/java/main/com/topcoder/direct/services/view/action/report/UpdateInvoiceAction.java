/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.report;

import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.topcoder.clients.invoices.dao.InvoiceDAO;
import com.topcoder.clients.invoices.dao.InvoiceRecordDAO;
import com.topcoder.clients.invoices.model.Invoice;
import com.topcoder.clients.invoices.model.InvoiceRecord;
import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;

/**
 * <p>A <code>Struts</code> action to be used for handling the requests for updating the invoice.</p>
 * 
 * @author TCSASSEMBER
 * @version 1.0 (TC Accounting Tracking Invoiced Payments Part 2)
 */
public class UpdateInvoiceAction extends BaseDirectStrutsAction {

    /**
     * <p>A <code>long</code> providing the id of the invoice to be updated.</p>
     */
    private long invoiceId;
    
    /**
     * <p>A <code>boolean</code> indicates that whether we are going to update the invoice for
     * the single invoice record.</p>
     */
    private boolean updateSingle;
    
    /**
     * <p>A <code>long</code> providing the id of the single invoice record. It will be only used
     * when {@link #updateSingle} is true.</p> 
     */
    private long invoiceRecordId;
    
    /**
     * <p>A <code>boolean</code> indicates whether we need to check the invoice number is already
     * exists.</p>
     */
    private boolean checkInvoiceNumber;
    
    /**
     * <p>A <code>String</code> providing the new invoice number.</p>
     */
    private String invoiceNumber;
    
    /**
     * <p>A <code>String</code> providing the new invoice date.</p>
     */
    private String invoiceDate;
    
    /**
     * <p>The instance of <code>InvoiceDAO</code>. Used to retrieve <code>Invoice</code> data. Will
     * be injected by Spring IoC.</p>
     */
    private InvoiceDAO invoiceDAO;
    
    /**
     * <p>The instance of <code>InvoiceRecordDAO</code>. Used to retrieve <code>InvoiceRecord</code> data. Will
     * be injected by Spring IoC.</p>
     */
    private InvoiceRecordDAO invoiceRecordDAO;
    
    /**
     * <p>The Spring transaction manager instance. Will be injected by Spring IoC.</p>
     */
    PlatformTransactionManager transactionManager;
    
    /**
     * <p>Handles the incoming request. It will update the invoice.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        // start a new transaction
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName(this.getClass().getName() + ".executeAction");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        
        try {
            TCSubject tcSubject = DirectStrutsActionsHelper.getTCSubjectFromSession();
            String userId = String.valueOf(tcSubject.getUserId());
            // check permission
            if (!DirectUtils.canPerformInvoiceRecords(tcSubject)) {
                throw new DirectException("Has no permission to perform the operation");
            }
            
            long newInvoiceId;
            if (!updateSingle) {
                Invoice invoice = invoiceDAO.getByInvoiceNumber(invoiceNumber);
                if (invoice != null && invoice.getId() != invoiceId) {
                    // the invoice number already exists
                    Map<String, Boolean> result = new HashMap<String, Boolean>();
                    result.put("invoiceNumberExists", Boolean.TRUE);
                    setResult(result);
                    transactionManager.commit(status);
                    return;
                }
                
                invoice = invoiceDAO.retrieve(invoiceId);
                invoice.setInvoiceNumber(invoiceNumber);
                invoice.setInvoiceDate(DirectUtils.getDate(invoiceDate));
                invoiceDAO.update(invoice);
                newInvoiceId = invoice.getId();
            } else {
                // update invoice for the single invoice record
                Invoice invoice = invoiceDAO.getByInvoiceNumber(invoiceNumber);
                InvoiceRecord invoiceRecord = invoiceRecordDAO.retrieve(invoiceRecordId);
                if (checkInvoiceNumber && invoice != null && invoice.getId() != invoiceRecord.getInvoice().getId()) {
                    // the invoice number already exists
                    Map<String, Boolean> result = new HashMap<String, Boolean>();
                    result.put("invoiceNumberExists", Boolean.TRUE);
                    setResult(result);
                    transactionManager.commit(status);
                    return;
                }
                
                long oldInvoiceId = invoiceRecord.getInvoice().getId();
                if (invoice == null) {
                    // create a new invoice
                    invoice = new Invoice();
                    invoice.setInvoiceNumber(invoiceNumber);
                    invoice.setInvoiceDate(DirectUtils.getDate(invoiceDate));
                    invoice.setCreateUser(userId);
                    invoice.setModifyUser(userId);
                    invoiceDAO.create(invoice);
                } else {
                    invoice.setInvoiceDate(DirectUtils.getDate(invoiceDate));
                    invoiceDAO.update(invoice);
                }
                invoiceRecord.setInvoice(invoice);
                invoiceRecordDAO.update(invoiceRecord);
                
                int count = invoiceRecordDAO.countByInvoice(oldInvoiceId);
                if (count == 0) {
                    invoiceDAO.delete(oldInvoiceId);
                }
                newInvoiceId = invoice.getId();
            }
            Map<String, Long> result = new HashMap<String, Long>();
            result.put("invoiceId", new Long(newInvoiceId));
            setResult(result);
            transactionManager.commit(status);
        } catch (Exception e) {
            if (!status.isCompleted()) {
                transactionManager.rollback(status);
            }
            throw e;
        }
    }

    /**
     * <p>Sets the id of the invoice to be updated.</p>
     * 
     * @param invoiceId the id of the invoice to be updated
     */
    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * <p>Sets the new invoice number.</p>
     * 
     * @param invoiceNumber the new invoice number
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     * <p>Sets the new invoice date.</p>
     * 
     * @param invoiceDate the new invoice date
     */
    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    /**
     * @param updateSingle the updateSingle to set
     */
    public void setUpdateSingle(boolean updateSingle) {
        this.updateSingle = updateSingle;
    }

    /**
     * @param invoiceRecordId the invoiceRecordId to set
     */
    public void setInvoiceRecordId(long invoiceRecordId) {
        this.invoiceRecordId = invoiceRecordId;
    }

    /**
     * @param checkInvoiceNumber the checkInvoiceNumber to set
     */
    public void setCheckInvoiceNumber(boolean checkInvoiceNumber) {
        this.checkInvoiceNumber = checkInvoiceNumber;
    }

    /**
     * <p>Sets the instance of <code>InvoiceDAO</code>.</p>
     * 
     * @param invoiceDAO the instance of <code>InvoiceDAO</code>
     */
    public void setInvoiceDAO(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }
    
    /**
     * <p>Sets the instance of <code>InvoiceRecordDAO</code>.</p>
     * 
     * @param invoiceRecordDAO the instance of <code>InvoiceRecordDAO</code>
     */
    public void setInvoiceRecordDAO(InvoiceRecordDAO invoiceRecordDAO) {
        this.invoiceRecordDAO = invoiceRecordDAO;
    }

    /**
     * <p>Sets the Spring transaction manager instance.</p>
     * 
     * @param transactionManager the Spring transaction manager instance
     */
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}
