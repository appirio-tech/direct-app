/*
 * Copyright (C) 2011 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.invoices.model;


/**
 * <p>This class is a container for information about a single invoice record. It is a simple JavaBean (POJO) that
 * provides getters and setters for all private attributes and performs no argument validation in the setters.</p>
 *
 * <p><strong>Thread safety:</strong> This class is mutable and not thread safe.</p>
 *
 * <p>
 * Version 1.1 (TC Accounting Tracking Invoiced Payments Part 2) change log:
 * <ol>
 *   <li>Added field {@link #invoice}. Also the getter/setter were added.</li>
 * </ol>
 * </p>
 * 
 * <p>
 * Version 1.2 (Module Assembly - TC Cockpit Invoice History Page Update) changes:
 * <ol>
 *   <li>Update filed {@link #contestId} and also the getter/setter to allow for null value.</li>
 *   <li>Added field {@link #cockpitProjectId}. Also the getter/setter were added.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.3 (Release Assembly - TC Cockpit Bug Race Cost and Fees Part 1) changes:
 * <ol>
 *   <li>Added field {@link #jiraIssueId}. Also the getter/setter were added.</li>
 * </ol>
 * </p>
 *
 * @author flexme, notpad, TCSASSEMBLER
 * @version 1.3
 */
public class InvoiceRecord extends IdentifiableEntity {
    
    /**
     * <p>The ID of the billing account. Can be any value. Has getter and setter.</p>
     */
    private long billingAccountId;

    /**
     * <p>The ID of JIRA issue. Can be any value. Has getter and setter.</p>
     *
     * <p>Currently this field is used when the invoice record is for JIRA bug race fee.</p>
     *
     * @since 1.3
     */
    private String jiraIssueId;

    /**
     * <p>The ID of the contest. Can be any value. Has getter and setter.</p>
     */
    private Long contestId;
    
    /**
     * <p>The ID of the invoice type. Can be any value. Has getter and setter.</p>
     */
    private InvoiceType invoiceType;
    
    /**
     * <p>The ID of the payment. Can be any value. Has getter and setter.</p>
     */
    private Long paymentId;

    /**
     * <p>The invoice amount. Can be any value. Has getter and setter.</p>
     */
    private Double invoiceAmount;

    /**
     * <p>The flag indicates whether the record has been processed. Can be any value. Has getter and setter.</p>
     */
    private boolean processed;
    
    /**
     * <p>The invoice this invoice record belongs to. Can be any value. Has getter and setter.</p>
     * 
     * @since 1.1
     */
    private Invoice invoice;
    
    /**
     * <p>The ID of the reference invoice record for credit. Can be any value. Has getter and setter.</p>
     */
    private Long referenceId;
    
    /**
     * <p>The ID of the cockpit project. Can be any value. Has getter and setter.</p>
     * 
     * @since 1.2
     */
    private Long cockpitProjectId;
    
    /**
     * <p>Creates new instance of <code>{@link InvoiceRecord}</code> class.</p>
     */
    public InvoiceRecord() {
     // empty constructor
    }

    /**
     * <p>Gets the ID of the billing account.</p>
     * 
     * @return the ID of the billing account.
     */
    public long getBillingAccountId() {
        return billingAccountId;
    }

    /**
     * <p>Sets the ID of the billing account.</p>
     * 
     * @param billingAccountId the ID of the billing account.
     */
    public void setBillingAccountId(long billingAccountId) {
        this.billingAccountId = billingAccountId;
    }

    /**
     * <p>Gets the ID of the contest.</p>
     * 
     * @return the ID of the contest.
     */
    public Long getContestId() {
        return contestId;
    }

    /**
     * <p>Sets the ID of the contest.</p>
     * 
     * @param contestId the ID of the contest.
     */
    public void setContestId(Long contestId) {
        this.contestId = contestId;
    }

    /**
     * <p>Gets the ID of the invoice type.</p>
     * 
     * @return the ID of the invoice type.
     */
    public InvoiceType getInvoiceType() {
        return invoiceType;
    }

    /**
     * <p>Sets the ID of the invoice type.</p>
     * 
     * @param invoiceType the ID of the invoice type.
     */
    public void setInvoiceType(InvoiceType invoiceType) {
        this.invoiceType = invoiceType;
    }

    /**
     * <p>Gets the ID of the payment.</p>
     * 
     * @return the ID of the payment.
     */
    public Long getPaymentId() {
        return paymentId;
    }

    /**
     * <p>Sets the ID of the payment.</p>
     * 
     * @param paymentId the ID of the payment.
     */
    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * <p>Gets the flag indicates whether the record has been processed.</p>
     * 
     * @return the ID of the payment.
     */
    public boolean isProcessed() {
        return processed;
    }

    /**
     * <p>Sets the flag indicates whether the record has been processed.</p>
     * 
     * @param processed the flag indicates whether the record has been processed.
     */
    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    /**
     * <p>Gets the invoice amount.</p>
     *
     * @return the invoice amount.
     */
    public Double getInvoiceAmount() {
        return invoiceAmount;
    }

    /**
     * <p>Sets the invoice amount..</p>
     *
     * @param invoiceAmount the invoice amount.
     */
    public void setInvoiceAmount(Double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    /**
     * <p>Gets the invoice this invoice record belongs to.</p>
     * 
     * @return the invoice this invoice record belongs to.
     * @since 1.1
     */
    public Invoice getInvoice() {
        return invoice;
    }

    /**
     * <p>Sets the invoice this invoice record belongs to.</p>
     * 
     * @param invoice the invoice this invoice record belongs to.
     * @since 1.1
     */
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
    
    /**
     * <p>Gets the ID of the reference invoice record for credit.</p>
     * 
     * @return the ID of the reference invoice record for credit.
     */
    public Long getReferenceId() {
        return referenceId;
    }

    /**
     * <p>Sets the ID of the reference invoice record for credit.</p>
     * 
     * @param referenceId the ID of the reference invoice record fore credit.
     */
    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }
    
    /**
     * <p>Gets the ID of the cockpit project.</p>
     * 
     * @return the ID of the cockpit project.
     * @since 1.2
     */
    public Long getCockpitProjectId() {
        return cockpitProjectId;
    }

    /**
     * <p>Sets the ID of the cockpit project.</p>
     * 
     * @param cockpitProjectId the ID of the cockpit project.
     * @since 1.2
     */
    public void setCockpitProjectId(Long cockpitProjectId) {
        this.cockpitProjectId = cockpitProjectId;
    }

    /**
     * <p>Gets the JIRA issue ID.</p>
     *
     * @return the JIRA issue ID.
     * @since 1.3
     */
    public String getJiraIssueId() {
        return jiraIssueId;
    }

    /**
     * <p>Gets the JIRA issue ID.</p>
     *
     * @param jiraIssueId the JIRA issue ID.
     * @since 1.3
     */
    public void setJiraIssueId(String jiraIssueId) {
        this.jiraIssueId = jiraIssueId;
    }
}
