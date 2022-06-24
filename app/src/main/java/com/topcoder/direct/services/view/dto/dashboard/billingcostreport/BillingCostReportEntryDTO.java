/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.billingcostreport;

import com.topcoder.direct.services.view.dto.IdNamePair;

import java.io.Serializable;
import java.util.Date;

/**
 * <p> The DTO to store info the one entry of billing cost report data. </p>
 *
 * <p>
 * Version 1.1 (TC Accounting Tracking Invoiced Payments) change log:
 * <ol>
 *   <li>Added {@link #paymentId} and {@link #processed} fields. Also the setters/getters were added.</li>
 * </ol>
 * </p>
 * 
 * <p>
 * Version 1.2 (TC Accounting Tracking Invoiced Payments Part 2) change log:
 * <ol>
 *   <li>Added {@link #invoiceNumber}, {@link #invoiceId} and {@link #paymentDesc} fields.
 *   Also the setters/getters were added.</li>
 * </ol>
 * </p>
 * 
 * @author Blues, flexme
 * @version 1.2 (TC Cockpit Billing Cost Report Assembly v1.0)
 */
public class BillingCostReportEntryDTO implements Serializable {
    /**
     * The client data.
     */
    private IdNamePair client;

    /**
     * The billing data.
     */
    private IdNamePair billing;

    /**
     * The direct project data.
     */
    private IdNamePair project;

    /**
     * The contest data.
     */
    private IdNamePair contest;

    /**
     * The contest type/category data.
     */
    private IdNamePair contestType;

    /**
     * The status of the contest, could be 'Active', 'Scheduled' and 'Finished'
     */
    private String status;

    /**
     * The date the payment is processed.
     */
    private Date paymentDate;

    /**
     * The date the contest is launched.
     */
    private Date launchDate;

    /**
     * The date the contest is completed. If the contest is still scheduled or active, the date represents scheduled
     * completion date.
     */
    private Date completionDate;

    /**
     * The actual total member cost.
     */
    private double actualTotalMemberCost;

    /**
     * The payment amount of the payment entry.
     */
    private double paymentAmount;

    /**
     * The payment type name of the payment entry.
     */
    private String paymentType;
    
    /**
     * The ID of the payment.
     * 
     * @since 1.1
     */
    private long paymentId;

    /**
     * A flag indicates whether the invoice record of the payment has been processed.
     * 
     * @since 1.1
     */
    private boolean processed;

    /**
     * The invoice amount.
     */
    private double invoiceAmount;

    /**
     * The invoice date.
     */
    private Date invoiceDate;
    
    /**
     * The invoice number.
     * 
     * @since 1.2
     */
    private String invoiceNumber;
    
    /**
     * The id of the invoice.
     * 
     * @since 1.2
     */
    private long invoiceId;
    
    private long invoiceRecordId;
    
    /**
     * Flag indicates whether the contest payment is of type studio contest.
     */

    private boolean isStudio;

    /**
     * The reference id of the invoice.
     */
    private String referenceId;

    /**
     * The project filter value applied.
     */
    private String projectFilterValue;

    /**
     * The payment description.
     * 
     * @since 1.2
     */
    private String paymentDesc;
    
    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    /**
     * Gets the flag isStudio.
     *
     * @return the flag isStudio.
     */
    public boolean isStudio() {
        return isStudio;
    }

    /**
     * Sets the flag isStudio.
     *
     * @param studio whether the contest is a studio contest.
     */
    public void setStudio(boolean studio) {
        isStudio = studio;
    }


    /**
     * Gets the client
     *
     * @return the client.
     */
    public IdNamePair getClient() {
        return client;
    }

    /**
     * Sets the client.
     *
     * @param client the client to set.
     */
    public void setClient(IdNamePair client) {
        this.client = client;
    }

    /**
     * Gets the billing.
     *
     * @return the billing project.
     */
    public IdNamePair getBilling() {
        return billing;
    }

    /**
     * Sets the billing.
     *
     * @param billing the billing project to set.
     */
    public void setBilling(IdNamePair billing) {
        this.billing = billing;
    }

    /**
     * Gets the project.
     *
     * @return  the project.
     */
    public IdNamePair getProject() {
        return project;
    }

    /**
     * Sets the project.
     *
     * @param project the project to set.
     */
    public void setProject(IdNamePair project) {
        this.project = project;
    }

    /**
     * Gets the contest.
     *
     * @return the contest info.
     */
    public IdNamePair getContest() {
        return contest;
    }

    /**
     * Sets the contest.
     *
     * @param contest the contest to set.
     */
    public void setContest(IdNamePair contest) {
        this.contest = contest;
    }

    /**
     * Gets the contest type.
     *
     * @return the contest type.
     */
    public IdNamePair getContestType() {
        return contestType;
    }

    /**
     * Sets the contest type.
     *
     * @param contestType the contest type to set.
     */
    public void setContestType(IdNamePair contestType) {
        this.contestType = contestType;
    }

    /**
     * Gets the status.
     *
     * @return the status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status the status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the payment date.
     *
     * @return the payment date.
     */
    public Date getPaymentDate() {
        return paymentDate;
    }

    /**
     * Sets the payment date.
     *
     * @param paymentDate the payment date to set.
     */
    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * Gets the launch date.
     *
     * @return the launch date.
     */
    public Date getLaunchDate() {
        return launchDate;
    }

    /**
     * Sets the launch date.
     *
     * @param launchDate the launch date to set.
     */
    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    /**
     * Gets the completion date.
     *
     * @return the completion date.
     */
    public Date getCompletionDate() {
        return completionDate;
    }

    /**
     * Sets the completion date.
     *
     * @param completionDate the completion date to set.
     */
    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    /**
     * Gets the actual total member cost.
     *
     * @return the actual total member cost.
     */
    public double getActualTotalMemberCost() {
        return actualTotalMemberCost;
    }

    /**
     * Sets the actual total member cost.
     *
     * @param actualTotalMemberCost the actual total member cost to set.
     */
    public void setActualTotalMemberCost(double actualTotalMemberCost) {
        this.actualTotalMemberCost = actualTotalMemberCost;
    }

    /**
     * Gets the payment type.
     *
     * @return the payment type.
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * Sets the payment type.
     *
     * @param paymentType the payment type to set.
     */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * Gets the payment amount.
     *
     * @return the payment amount.
     */
    public double getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * Sets the payment amount.
     *
     * @param paymentAmount the payment amount to set.
     */
    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    /**
     * Gets the ID of the payment.
     * 
     * @return the ID of the payment.
     * @since 1.1
     */
    public long getPaymentId() {
        return paymentId;
    }

    /**
     * Sets the ID of the payment.
     * 
     * @param paymentId the ID of the payment.
     * @since 1.1
     */
    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * Gets the flag indicates whether the invoice record of the payment has been processed.
     * 
     * @return true if the invoice record of the payment has been processed, false otherwise.
     * @since 1.1
     */
    public boolean isProcessed() {
        return processed;
    }

    /**
     * Sets the flag indicates whether the invoice record of the payment has been processed.
     * 
     * @param processed true if the invoice record of the payment has been processed, false otherwise.
     * @since 1.1
     */
    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    /**
     * Gets the invoice amount.
     *
     * @return the invoice amount.
     */
    public double getInvoiceAmount() {
        return invoiceAmount;
    }

    /**
     * Sets the invoice amount.
     *
     * @param invoiceAmount the invoice amount to set.
     */
    public void setInvoiceAmount(double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }



    /**
     * Gets the invoice date.
     *
     * @return the invoice date.
     */
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * Sets the invoice date.
     *
     * @param invoiceDate the invoice date to set.
     */
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    /**
     * Gets the invoice number.
     * 
     * @return the invoice number.
     * @since 1.2
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * Sets the invoice number.
     * 
     * @param invoiceNumber the invoice number.
     * @since 1.2
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     * Gets the id of the invoice.
     * 
     * @return the id of the invoice.
     * @since 1.2
     */
    public long getInvoiceId() {
        return invoiceId;
    }

    /**
     * Sets the id of the invoice.
     * 
     * @param invoiceId the id of the invoice.
     * @since 1.2
     */
    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * Gets the payment description.
     * 
     * @return the payment description.
     * @since 1.2
     */
    public String getPaymentDesc() {
        return paymentDesc;
    }

    /**
     * Sets the payment description.
     * 
     * @param paymentDesc the payment description.
     * @since 1.2
     */
    public void setPaymentDesc(String paymentDesc) {
        this.paymentDesc = paymentDesc;
    }

    /**
     * Gets the invoice record id.
     * 
     * @return the invoice record id
     */
    public long getInvoiceRecordId() {
        return invoiceRecordId;
    }

    /**
     * Sets the invoice record id.
     * 
     * @param invoiceRecordId the invoice record id to set
     */
    public void setInvoiceRecordId(long invoiceRecordId) {
        this.invoiceRecordId = invoiceRecordId;
    }

    /**
     * Gets the project filter value.
     *
     * @return the project filter value applied.
     */
    public String getProjectFilterValue() {
        return projectFilterValue;
    }

    /**
     * Sets the project filter value.
     *
     * @param projectFilterValue the project filter value applied.
     */
    public void setProjectFilterValue(String projectFilterValue) {
        this.projectFilterValue = projectFilterValue;
    }
}
