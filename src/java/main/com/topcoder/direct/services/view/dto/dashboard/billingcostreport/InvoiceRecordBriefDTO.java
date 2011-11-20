/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.billingcostreport;

/**
 * <p>The DTO to store info the one invoice record brief data.</p>
 * 
 * @author flexme
 * @version 1.0 (TC Accounting Tracking Invoiced Payments)
 */
public class InvoiceRecordBriefDTO {
    /**
     * <p>The id of the contest.</p>
     */
    private long contestId;
    
    /**
     * <p>The id of the billing account.</p>
     */
    private long billingAccountId;
    
    /**
     * <p>The name of the invoice type.</p>
     */
    private String invoiceType;
    
    /**
     * <p>Construct a new {@link #InvoiceRecordBriefDTO()} instance.</p>
     */
    public InvoiceRecordBriefDTO() {
        
    }

    /**
     * <p>Gets the id of the contest.</p>
     * 
     * @return the id of the contest.
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * <p>Sets the id of the contest.</p>
     * 
     * @param contestId the id of the contest.
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * <p>Gets the id of the billing account.</p>
     * 
     * @return the id of the billing account.
     */
    public long getBillingAccountId() {
        return billingAccountId;
    }

    /**
     * <p>Sets the id of the billing account.</p>
     * 
     * @param billingAccountId the id of the billing account.
     */
    public void setBillingAccountId(long billingAccountId) {
        this.billingAccountId = billingAccountId;
    }

    /**
     * <p>Gets the name of the invoice type.</p>
     * 
     * @return the name of the invoice type.
     */
    public String getInvoiceType() {
        return invoiceType;
    }

    /**
     * <p>Sets the name of the invoice type.</p>
     * 
     * @param invoiceType the name of the invoice type.
     */
    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }
}
