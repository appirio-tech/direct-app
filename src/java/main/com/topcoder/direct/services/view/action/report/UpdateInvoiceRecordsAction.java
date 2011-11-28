/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.report;

import java.util.List;

import com.topcoder.clients.invoices.dao.InvoiceRecordDAO;
import com.topcoder.clients.invoices.dao.LookupDAO;
import com.topcoder.clients.invoices.model.InvoiceRecord;
import com.topcoder.clients.invoices.model.InvoiceType;
import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.dto.dashboard.billingcostreport.InvoiceRecordBriefDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;

/**
 * <p>A <code>Struts</code> action to be used for handling the requests for updating the invoice records.</p>
 * 
 * @author flexme
 * @version 1.0 (TC Accounting Tracking Invoiced Payments)
 */
public class UpdateInvoiceRecordsAction extends BaseDirectStrutsAction {

    /**
     * <p>The instance of <code>InvoiceRecordDAO</code>. Used to retrieve <code>InvoiceRecord</code> data. Will
     * be injected by Spring IoC.</p>
     */
    private InvoiceRecordDAO invoiceRecordDAO;
    
    /**
     * <p>The instance of <code>LookupDAO</code>. Used to retrieve <code>InvoiceType</code> data. Will
     * be injected by Spring IoC.</p>
     */
    private LookupDAO lookupDAO;
    
    /**
     * <p>A <code>List</code> providing the payment IDs of the invoice records which needs to be updated.</p>
     */
    private List<Long> paymentIds;
    
    /**
     * <p>A <code>List</code> providing the contest IDs of the invoice records which needs to be updated.</p>
     */
    private List<Long> contestIds;
    
    /**
     * <p>A <code>List</code> providing the invoice type names of the invoice records which needs to be updated.</p>
     */
    private List<String> invoiceTypeNames;

    /**
     * <p>A <code>List</code> providing the invoice amounts of the invoice records which needs to be updated.</p>
     */
    private List<Double> invoiceAmounts;
    
    /**
     * <p>A <code>List</code> providing the processed flag of the invoice records which needs to be updated.</p>
     */
    private List<Boolean> processeds;
    
    /**
     * <p>Constructs new <code>UpdateInvoiceRecordsAction</code> instance</p>
     */
    public UpdateInvoiceRecordsAction() {
        
    }
    
    /**
     * <p>Handles the incoming request. It will update the invoice records.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        List<InvoiceType> invoiceTypes = lookupDAO.getAllInvoiceTypes();
        // !!! For paymentId > 0, we should NOT get contest_id, billing_account_id, payment_type from request parameters because
        // paymentId can unique determine contest_id, billing_account_id, payment_type. We need to get contest_id,
        // billingaccount_id, payment_type from database by payment_id.
        // For paymentId = 0 and contestId > 0, we need to get billing_account_id from database by contest_id.
        List<InvoiceRecordBriefDTO> recordDatas = DataProvider.getInvoiceRecordRelatedData(contestIds, paymentIds);
        
        TCSubject tcSubject = DirectStrutsActionsHelper.getTCSubjectFromSession();
        String userId = String.valueOf(tcSubject.getUserId());
        // check permission
        if (!DirectUtils.canPerformInvoiceRecords(tcSubject)) {
            throw new DirectException("Has no permission to perform the operation");
        }
        
        for (int i = 0; i < contestIds.size(); i++) {
            InvoiceRecord record;
            String invoiceTypeName = invoiceTypeNames.get(i).trim();
            InvoiceType invoiceType = DirectUtils.getInvoiceType(invoiceTypeName, invoiceTypes);
            if (invoiceType == null) {
                throw new DirectException("Can't find the invoice type:" + invoiceTypeName);
            }
            if (paymentIds.get(i) > 0) {
                // payment_id > 0, get invoice record by payment_id
                record = invoiceRecordDAO.getByPayment(paymentIds.get(i));
            } else {
                // payment_id = 0, get invoice record by contest_id and invoice_type_id
                record = invoiceRecordDAO.getByContestAndInvoiceType(contestIds.get(i), invoiceType.getId());
                if (record != null && record.getPaymentId() != null) {
                    throw new DirectException("Payment Id should be zero.");
                }
            }
            
            if (record != null) {
                // InvoiceRecord exists, update the exists record
                record.setModifyUser(userId);
                record.setInvoiceAmount(invoiceAmounts.get(i));
                record.setProcessed(processeds.get(i));
                invoiceRecordDAO.update(record);
            } else {
                // InvoiceRecord didn't exists, insert a new one into database
                InvoiceRecordBriefDTO recordData = recordDatas.get(i);
                if (recordData == null) {
                    throw new DirectException("Can't find the payment data.");
                }
                record = new InvoiceRecord();
                record.setBillingAccountId(recordData.getBillingAccountId());
                record.setContestId(recordData.getContestId());
                if (paymentIds.get(i) > 0) {
                    record.setPaymentId(paymentIds.get(i));
                    record.setInvoiceType(DirectUtils.getInvoiceType(recordData.getInvoiceType().trim(), invoiceTypes));
                } else {
                    record.setInvoiceType(invoiceType);
                }
                record.setProcessed(processeds.get(i));
                record.setInvoiceAmount(invoiceAmounts.get(i));
                record.setCreateUser(userId);
                record.setModifyUser(userId);
                invoiceRecordDAO.create(record);
            }
        }
    }

    /**
     * <p>Sets the instance of <code>InvoiceRecordDAO</code>.</p>
     * 
     * @param invoiceRecordDAO the instance of <code>InvoiceRecordDAO</code>.
     */
    public void setInvoiceRecordDAO(InvoiceRecordDAO invoiceRecordDAO) {
        this.invoiceRecordDAO = invoiceRecordDAO;
    }

    /**
     * <p>Sets the instance of <code>LookupDAO</code>.</p>
     * 
     * @param lookupDAO the instance of <code>LookupDAO</code>.
     */
    public void setLookupDAO(LookupDAO lookupDAO) {
        this.lookupDAO = lookupDAO;
    }

    /**
     * <p>Sets the payment IDs of the invoice records which needs to be updated.</p>
     * 
     * @param paymentIds A <code>List</code> providing the payment IDs of the invoice records which needs to be updated.
     */
    public void setPaymentIds(List<Long> paymentIds) {
        this.paymentIds = paymentIds;
    }

    /**
     * <p>Sets the contest IDs of the invoice records which needs to be updated.</p>
     * 
     * @param contestIds A <code>List</code> providing the contest IDs of the invoice records which needs to be updated.
     */
    public void setContestIds(List<Long> contestIds) {
        this.contestIds = contestIds;
    }

    /**
     * <p>Sets the invoice type names of the invoice records which needs to be updated.</p>
     * 
     * @param invoiceTypeNames A <code>List</code> providing the invoice type names of the invoice records which needs to be updated.
     */
    public void setInvoiceTypeNames(List<String> invoiceTypeNames) {
        this.invoiceTypeNames = invoiceTypeNames;
    }

        /**
     * <p>Sets the invoice amounts of the invoice records which needs to be updated.</p>
     *
     * @param invoiceTypeNames A <code>List</code> providing the invoice amounts of the invoice records which needs to be updated.
     */
    public void setInvoiceAmounts(List<Double> invoiceAmounts) {
        this.invoiceAmounts = invoiceAmounts;
    }

    /**
     * <p>Sets the processed flag of the invoice records which needs to be updated.</p>
     * 
     * @param processeds A <code>List</code> providing the processed flag of the invoice records which needs to be updated.
     */
    public void setProcesseds(List<Boolean> processeds) {
        this.processeds = processeds;
    }
}
