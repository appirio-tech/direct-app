/*
 * Copyright (C) 2011 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.billingcostreport;

/**
 * <p>The DTO to store info the one invoice record brief data.</p>
 * 
 * <p>
 * Version 1.1 (Module Assembly - TC Cockpit Invoice History Page Update) changes:
 * <ol>
 *   <li>Update {@link #contestId} field and its getter and setter to allow for null value.</li>
 *   <li>Added {@link #cockpitProjectId} field and its getter and setter.</li> 
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TC Cockpit Bug Race Cost and Fees Part 1) changes:
 * <ol>
 *   <li>Added field {@link #jiraIssueId}. Also the getter/setter were added.</li>
 * </ol>
 * </p>
 *
 * @author flexme, notpad, TCSASSEMBLER
 * @version 1.2
 */
public class InvoiceRecordBriefDTO {
    /**
     * <p>The id of the contest.</p>
     */
    private Long contestId;

    /**
     * <p>The ID of JIRA issue. Can be any value. Has getter and setter.</p>
     *
     * @since 1.2
     */
    private String jiraIssueId;
    
    /**
     * <p>The id of the cockpict project.</p>
     * 
     * @since 1.1
     */
    private Long cockpitProjectId;
    
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
    public Long getContestId() {
        return contestId;
    }

    /**
     * <p>Sets the id of the cockpict project.</p>
     * 
     * @param cockpitProjectId the id of the cockpict project.
     * @since 1.1
     */
    public void setCockpitProjectId(Long cockpitProjectId) {
        this.cockpitProjectId = cockpitProjectId;
    }
    
    /**
     * <p>Gets the id of the cockpict project.</p>
     * 
     * @return the id of the cockpict project.
     * @since 1.1
     */
    public Long getCockpitProjectId() {
        return cockpitProjectId;
    }

    /**
     * <p>Sets the id of the contest.</p>
     * 
     * @param contestId the id of the contest.
     */
    public void setContestId(Long contestId) {
        this.contestId = contestId;
    }

    /**
     * <p>Gets the JIRA issue ID.</p>
     *
     * @return the JIRA issue ID.
     * @since 1.2
     */
    public String getJiraIssueId() {
        return jiraIssueId;
    }

    /**
     * <p>Gets the JIRA issue ID.</p>
     *
     * @param jiraIssueId the JIRA issue ID.
     * @since 1.2
     */
    public void setJiraIssueId(String jiraIssueId) {
        this.jiraIssueId = jiraIssueId;
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
