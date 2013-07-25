/*
 * Copyright (C) 2011-2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.topcoder.clients.invoices.dao.InvoiceDAO;
import com.topcoder.clients.invoices.dao.InvoiceRecordDAO;
import com.topcoder.clients.invoices.dao.LookupDAO;
import com.topcoder.clients.invoices.model.Invoice;
import com.topcoder.clients.invoices.model.InvoiceRecord;
import com.topcoder.clients.invoices.model.InvoiceType;
import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.dto.dashboard.billingcostreport.BillingCostReportEntryDTO;
import com.topcoder.direct.services.view.dto.dashboard.billingcostreport.InvoiceRecordBriefDTO;
import com.topcoder.direct.services.view.dto.dashboard.billingcostreport.PaymentType;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;

/**
 * <p>A <code>Struts</code> action to be used for handling the requests for updating the invoice records.</p>
 * 
 * <p>
 * Version 1.1 (TC Accounting Tracking Invoiced Payments Part 2) change notes:
 * <ol>
 *   <li>Added fields {@link #invoiceDAO}, {@link #invoiceNumber}, {@link #invoiceDate}, {@link #checkInvoiceNumber},
 *   {@link #transactionManager}. Also the setters were added.</li>
 *   <li>Updated {@link #executeAction()} to use the programmatic transaction management and update the logic to
 *   create/update invoice record.</li>
 * </ol>
 * </p>
 * <p>
 * Versions 1.2: (Module Assembly - Contest Fee Based on Percentage of Member Cost Cockpit Pages Update):
 * <ol>
 *   <li>Updated {@link #executeAction()} to support % based contest fee.</li>
 * </ol>
 * </p>
 * 
 * <p>
 * Version 1.3: (Module Assembly - Add Monthly Platform Fee Feature to Admin Page) change notes:
 * <ol>
 *   <li>Updated method {@link #executeAction()} to support customer Platform Fee records. </li>
 * </ol>
 * </p>
 * 
 * <p>
 * Version 1.4: (Module Assembly - TC Cockpit Invoice History Page Update) changes:
 * <ol>
 *   <li>Updated method {@link #executeAction()} to support records related to a second installment.
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.5: (Release Assembly - TC Cockpit Bug Race Cost and Fees Part 1) changes:
 * <ol>
 *   <li>Added field {@link #jiraIssueIds}. Also the setter was added.</li>
 *   <li>Updated method {@link #executeAction()} to support JIRA bug race contest fee.</li>
 * </ol>
 * </p>
 *
 * @author flexme, minhu, TCSASSEMBLER, notpad
 * @version 1.5
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
     * <p>The instance of <code>InvoiceDAO</code>. Used to retrieve <code>Invoice</code> data. Will
     * be injected by Spring IoC.</p>
     * 
     * @since 1.1
     */
    private InvoiceDAO invoiceDAO;
    
    /**
     * <p>A <code>List</code> providing the payment IDs of the invoice records which needs to be updated.</p>
     */
    private List<Long> paymentIds;
    
    /**
     * <p>A <code>List</code> providing the contest IDs of the invoice records which needs to be updated.</p>
     */
    private List<Long> contestIds;

    /**
     * <p>A <code>String</code> providing the JIRA issue IDs of the invoice records which needs to be updated.</p>
     *
     * @since 1.5
     */
    private List<String> jiraIssueIds;
    
    /**
     * <p>A <code>List</code> providing the reference IDs of the credit invoice records which needs to be updated.</p>
     */
    private List<Long> referenceIds;
    
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
     * <p>A <code>String</code> providing the invoice number.</p>
     * 
     * @since 1.1
     */
    private String invoiceNumber;
    
    /**
     * <p>A <code>String</code> providing the invoice date.</p>
     * 
     * @since 1.1
     */
    private String invoiceDate;
    
    /**
     * <p>Represents the flag whether to check the invoice number exists.</p>
     * 
     * @since 1.1
     */
    private boolean checkInvoiceNumber;
    
    /**
     * <p>The Spring transaction manager instance. Will be injected by Spring IoC.</p>
     * 
     * @since 1.1
     */
    PlatformTransactionManager transactionManager;
    
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
        // !!! For paymentId > 0, we should NOT get contest_id, jira_issue_id, billing_account_id, payment_type from
        // request parameters because paymentId can unique determine contest_id, jira_issue_id, billing_account_id,
        // payment_type. We need to get contest_id, jira_issue_id, billingaccount_id, payment_type from
        // database by payment_id.
        // For paymentId = 0 and jiveIssueId is not empty, we need to get contest_id, billing_account_id
        // from database by jiveIssueId.
        // For paymentId = 0 and contestId > 0, we need to get billing_account_id from database by contest_id.
        // For platform fee records, its contest_id = customer_platform_fee_id and its billing_account_id = 0.
        List<InvoiceRecordBriefDTO> recordDatas = DataProvider.getInvoiceRecordRelatedData(
            jiraIssueIds, contestIds, paymentIds, invoiceTypeNames);
        Map<Long, BillingCostReportEntryDTO> secondInstallments = DataProvider.getRelatedSecondInstallment(
            paymentIds, invoiceTypeNames);
        
        TCSubject tcSubject = DirectStrutsActionsHelper.getTCSubjectFromSession();
        String userId = String.valueOf(tcSubject.getUserId());
        // check permission
        if (!DirectUtils.canPerformInvoiceRecords(tcSubject)) {
            throw new DirectException("Has no permission to perform the operation");
        }
        
        // start a new transaction
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName(this.getClass().getName() + ".executeAction");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        
        Invoice invoice = invoiceDAO.getByInvoiceNumber(invoiceNumber);
        if (checkInvoiceNumber && invoice != null) {
            // the invoice number already exists
            Map<String, Boolean> result = new HashMap<String, Boolean>();
            result.put("invoiceNumberExists", Boolean.TRUE);
            setResult(result);
            transactionManager.commit(status);
            return;
        }
        
        try {
            if (invoice != null) {
                // update the invoice date
                invoice.setInvoiceDate(DirectUtils.getDate(invoiceDate));
            }
            List<Long> invoiceRecordIds = new ArrayList<Long>();
            for (int i = 0; i < contestIds.size(); i++) {
                InvoiceRecord record = null;
                InvoiceRecord record2 = null;
                String invoiceTypeName = invoiceTypeNames.get(i).trim();
                InvoiceType invoiceType = DirectUtils.getInvoiceType(invoiceTypeName, invoiceTypes);
                if (invoiceType == null) {
                    throw new DirectException("Can't find the invoice type:" + invoiceTypeName);
                }
                // payment ids are only used to get related data when credit, after that, set it to zero
                if (PaymentType.CREDIT.getDescription().equalsIgnoreCase(invoiceTypeName)) {
                    paymentIds.set(i, 0L);
                }
                if (paymentIds.get(i) > 0) {
                    // payment_id > 0, get invoice record by payment_id and invoice_type_id
                    record = invoiceRecordDAO.getByPaymentAndInvoiceType(paymentIds.get(i), invoiceType.getId());
                } else {
                    if (!PaymentType.CREDIT.getDescription().equalsIgnoreCase(invoiceTypeName)) {
                        // payment_id = 0, get invoice record by contest_id and invoice_type_id
                        if (jiraIssueIds!= null && jiraIssueIds.get(i) != null && jiraIssueIds.get(i).length() > 0) {
                            record = invoiceRecordDAO.getByJiraIssueAndInvoiceType(jiraIssueIds.get(i),
                                    invoiceType.getId());
                        } else {
                            record = invoiceRecordDAO.getByContestAndInvoiceType(contestIds.get(i),
                                    invoiceType.getId());
                        }
                        if (record != null && record.getPaymentId() != null) {
                            throw new DirectException("Payment Id should be zero.");
                        }
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
					if (jiraIssueIds != null && jiraIssueIds.get(i) != null && !jiraIssueIds.get(i).equals(""))
					{
						record.setJiraIssueId(jiraIssueIds.get(i));
					}
                    record.setCockpitProjectId(recordData.getCockpitProjectId());
                    if (PaymentType.CREDIT.getDescription().equalsIgnoreCase(invoiceTypeName)) {
                        record.setReferenceId(referenceIds.get(i));
                    }
                    if (paymentIds.get(i) > 0) {
                        record.setPaymentId(paymentIds.get(i));
                        //record.setInvoiceType(DirectUtils.getInvoiceType(recordData.getInvoiceType().trim(), invoiceTypes));
                        record.setInvoiceType(invoiceType);
                    } else {
                        record.setInvoiceType(invoiceType);
                    }
                    record.setProcessed(processeds.get(i));
                    record.setInvoiceAmount(invoiceAmounts.get(i));
                    record.setCreateUser(userId);
                    record.setModifyUser(userId);
                    
                    if (invoice == null) {
                        // create a new invoice
                        invoice = new Invoice();
                        invoice.setInvoiceDate(DirectUtils.getDate(invoiceDate));
                        invoice.setInvoiceNumber(invoiceNumber);
                        invoice.setCreateUser(userId);
                        invoice.setModifyUser(userId);
                        invoiceDAO.create(invoice);
                    }
                    record.setInvoice(invoice);
                    
                    // check if there is a second installment for this payment
                    if (paymentIds.get(i) > 0 && secondInstallments.containsKey(paymentIds.get(i))) {
                        record2 = new InvoiceRecord();
                        BillingCostReportEntryDTO costDTO = secondInstallments.get(paymentIds.get(i));
                        long paymentId2 = costDTO.getPaymentId();
                        double invoiceAmount2 = costDTO.getInvoiceAmount();
                        record2.setBillingAccountId(recordData.getBillingAccountId());
                        record2.setContestId(recordData.getContestId());
                        record2.setCockpitProjectId(recordData.getCockpitProjectId());
                        record2.setPaymentId(paymentId2);
                        record2.setInvoiceType(invoiceType);
                        record2.setProcessed(processeds.get(i));
                        record2.setInvoiceAmount(invoiceAmount2);
                        record2.setCreateUser(userId);
                        record2.setModifyUser(userId);
                        record2.setInvoice(invoice);
                    } 
                    
                    invoiceRecordDAO.create(record);
                    if (record2 != null) {
                        invoiceRecordDAO.create(record2);
                    }
                }
                invoiceRecordIds.add(record.getId());
            }
            transactionManager.commit(status);
            
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("invoiceId", new Long(invoice.getId()));
            result.put("invoiceRecordIds", invoiceRecordIds);
            setResult(result);
        } catch (Exception e) {
            if (!status.isCompleted()) {
                transactionManager.rollback(status);
            }
            throw e;
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
     * <p>Sets the instance of <code>InvoiceDAO</code>.</p>
     * 
     * @param invoiceDAO the instance of <code>InvoiceDAO</code>.
     * @since 1.1
     */
    public void setInvoiceDAO(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
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
     * <p>Sets the reference IDs of the credit invoice records which needs to be updated.</p>
     * 
     * @param referenceIds A <code>List</code> providing the reference IDs of the credit invoice records which needs to be updated.
     */
    public void setReferenceIds(List<Long> referenceIds) {
        this.referenceIds = referenceIds;
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

    /**
     * <p>Sets the invoice number.</p>
     * 
     * @param invoiceNumber the invoice number
     * @since 1.1
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     * <p>Sets the invoice date.</p>
     * 
     * @param invoiceDate the invoice date
     * @since 1.1
     */
    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    /**
     * <p>Sets the flag whether to check the invoice number exists.</p>
     * 
     * @param checkInvoiceNumber the flag whether to check the invoice number exists.
     * @since 1.1
     */
    public void setCheckInvoiceNumber(boolean checkInvoiceNumber) {
        this.checkInvoiceNumber = checkInvoiceNumber;
    }

    /**
     * <p>Sets the Spring transaction manager instance.</p>
     * 
     * @param transactionManager the Spring transaction manager instance
     * @since 1.1
     */
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    /**
     * <p>Sets the JIRA issue ids of the invoice records which needs to be updated.</p>
     *
     * @param jiraIssueIds A <code>String</code> providing the JIRA issue IDs of the invoice records which
     *                     needs to be updated.
     * @since 1.5
     */
    public void setJiraIssueIds(List<String> jiraIssueIds) {
        this.jiraIssueIds = jiraIssueIds;
    }
}
