/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

import java.io.Serializable;

/**
 * <p>A form bean providing the form data submitted by user for getting the participation metrics report.</p>
 *
 * @author TCSASSEMBER
 * @version 1.0 (TC Cockpit Participation Metrics Report Part One Assembly 1 )
 */
public class DashboardParticipationMetricsReportForm implements Serializable {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 8123239262266251226L;

    /**
     * <p>A <code>long[]</code> providing the IDs for project categories to evaluate the participation metrics report data for.</p>
     */
    private long[] projectCategoryIds;

    /**
     * <p>A <code>String</code> providing the start date for period of evaluation of participation metrics data.</p>
     */
    private String startDate;

    /**
     * <p>A <code>String</code> providing the end date for period of evaluation of participation metrics data.</p>
     */
    private String endDate;

    /**
     * <p>A <code>long</code> providing the ID for project to get the statistics for.</p>
     */
    private long projectId;

    /**
     * <p>A <code>long</code> providing the IDs for billing accounts to get statistics for.</p>
     */
    private long billingAccountId;

    /**
     * <p>A <code>long</code> providing the ID for customer accounts to get statistics for.</p>
     */
    private long customerId;

    /**
     * <p>Represents the status ids of the participation metrics report. 3 status are available: active, scheduled, completed.</p>
     */
    private long[] statusIds;

    /**
     * Emptry constructor.
     */
    public DashboardParticipationMetricsReportForm() {
        
    }
    
    /**
     * <p>Gets the IDs for project categories to evaluate the participation metrics data for.</p>
     *
     * @return a <code>long[]</code> providing the IDs for project categories to evaluate the participation metrics data for.
     */
    public long[] getProjectCategoryIds() {
        return this.projectCategoryIds;
    }

    /**
     * <p>Sets the IDs for project categories to evaluate the participation metrics data for.</p>
     *
     * @param projectCategoryIds a <code>long[]</code> providing the IDs for project categories to evaluate the
     *                           participation metrics data for.
     */
    public void setProjectCategoryIds(long[] projectCategoryIds) {
        this.projectCategoryIds = projectCategoryIds;
    }

    /**
     * <p>Gets the end date for period of evaluation of participation metrics data.</p>
     *
     * @return a <code>String</code> providing the end date for period of evaluation of participation metrics data.
     */
    public String getEndDate() {
        return this.endDate;
    }

    /**
     * <p>Sets the end date for period of evaluation of participation metrics data.</p>
     *
     * @param endDate a <code>String</code> providing the end date for period of evaluation of participation metrics data.
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * <p>Gets the start date for period of evaluation of participation metrics data.</p>
     *
     * @return a <code>String</code> providing the start date for period of evaluation of participation metrics data.
     */
    public String getStartDate() {
        return this.startDate;
    }

    /**
     * <p>Sets the start date for period of evaluation of participation metrics data.</p>
     *
     * @param startDate a <code>String</code> providing the start date for period of evaluation of participation metrics data.
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * <p>Gets the ID for project to get the statistics for.</p>
     *
     * @return a <code>long</code> providing the ID for projects to get the participation metrics data for.
     */
    public long getProjectId() {
        return this.projectId;
    }

    /**
     * <p>Sets the ID for project to get the statistics for.</p>
     *
     * @param projectId a <code>long</code> providing the ID for project to get the participation metrics data for.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * <p>Gets the ID for customer accounts to get participation metrics data for.</p>
     *
     * @return a <code>long</code> providing the ID for customer accounts to get participation metrics data for.
     */
    public long getCustomerId() {
        return this.customerId;
    }

    /**
     * <p>Sets the ID for customer accounts to get participation metrics data for.</p>
     *
     * @param customerId a <code>long</code> providing the ID for customer accounts to get participation metrics data for.
     */
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    /**
     * <p>Gets the ID for billing account to get participation metrics data for.</p>
     *
     * @return a <code>long</code> providing the ID for billing accounts to get participation metrics data for.
     */
    public long getBillingAccountId() {
        return this.billingAccountId;
    }

    /**
     * <p>Sets the ID for billing account to get participation metrics data for.</p>
     *
     * @param billingAccountId a <code>long</code> providing the IDs for billing accounts to get participation metrics data for.
     */
    public void setBillingAccountId(long billingAccountId) {
        this.billingAccountId = billingAccountId;
    }

    /**
     * <p>Gets the status ids used to filter the participation metrics data.</p>
     *
     * @return a <code>long</code> array of status ids.
     */
    public long[] getStatusIds() {
        return statusIds;
    }

    /**
     * <p>Sets the status ids used to filter the participation metrics report.</p>
     *
     * @param statusIds the status ids
     */
    public void setStatusIds(long[] statusIds) {
        this.statusIds = statusIds;
    }    
}
