/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

import java.io.Serializable;

/**
 * <p>A form bean providing the form data submitted by user for getting the cost report.</p>
 *
 * <p>
 * Version 1.0.1 (TC Cockpit Cost Report Update Cost Breakdown Assembly) change note:
 * <ol>
 * <li>Added {@link #showBreakdown} property to indicate whether to export the cost break down,
 * also the getter/setter were added.</li>
 * </ol>
 * </p>
 *
 * @author flexme
 * @version 1.0.1 (TopCoder Cockpit - Cost Report Assembly)
 */
public class DashboardCostReportForm implements Serializable {

    /**
     * <p>A <code>long[]</code> providing the IDs for project categories to evaluate the cost report data for.</p>
     */
    private long[] projectCategoryIds;

    /**
     * <p>A <code>String</code> providing the start date for period of evaluation of cost data.</p>
     */
    private String startDate;

    /**
     * <p>A <code>String</code> providing the end date for period of evaluation of cost data.</p>
     */
    private String endDate;

    /**
     * <p>A <code>long</code> array providing the IDs for projects to get the statistics for.</p>
     */
    private long[] projectIds;

    /**
     * <p>A <code>long[]</code> providing the IDs for billing accounts to get statistics for.</p>
     */
    private long[] billingAccountIds;

    /**
     * <p>A <code>long[]</code> providing the IDs for customer accounts to get statistics for.</p>
     */
    private long[] customerIds;

    /**
     * <p>Represents the status ids of the cost report. 3 status are available: active, scheduled, completed.</p>
     */
    private long[] statusIds;

    /**
     * <p>A <code>boolean</code> flag indicating whether the retrieved data is expected to be converted into
     * <code>Excel</code> format or not.</p>
     */
    private boolean excel;

    /**
     * <p>A <code>boolean</code> providing the flag indicating whether the cost break down data should display in
     * the cost report.</p>
     * 
     * @since 1.0.1
     */
    private boolean showBreakdown;

    /**
     * <p>Constructs new <code>DashboardCostReportForm</code> instance. This implementation does nothing.</p>
     */
    public DashboardCostReportForm() {
    }

    /**
     * <p>Gets the IDs for project categories to evaluate the cost data for.</p>
     *
     * @return a <code>long[]</code> providing the IDs for project categories to evaluate the cost data for.
     */
    public long[] getProjectCategoryIds() {
        return this.projectCategoryIds;
    }

    /**
     * <p>Sets the IDs for project categories to evaluate the cost data for.</p>
     *
     * @param projectCategoryIds a <code>long[]</code> providing the IDs for project categories to evaluate the
     *                           cost data for.
     */
    public void setProjectCategoryIds(long[] projectCategoryIds) {
        this.projectCategoryIds = projectCategoryIds;
    }

    /**
     * <p>Gets the end date for period of evaluation of cost data.</p>
     *
     * @return a <code>String</code> providing the end date for period of evaluation of cost data.
     */
    public String getEndDate() {
        return this.endDate;
    }

    /**
     * <p>Sets the end date for period of evaluation of cost data.</p>
     *
     * @param endDate a <code>String</code> providing the end date for period of evaluation of cost data.
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * <p>Gets the start date for period of evaluation of cost data.</p>
     *
     * @return a <code>String</code> providing the start date for period of evaluation of cost data.
     */
    public String getStartDate() {
        return this.startDate;
    }

    /**
     * <p>Sets the start date for period of evaluation of cost data.</p>
     *
     * @param startDate a <code>String</code> providing the start date for period of evaluation of cost data.
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * <p>Gets the IDs for projects to get the statistics for.</p>
     *
     * @return a <code>long</code> array providing the IDs for projects to get the cost data for.
     */
    public long[] getProjectIds() {
        return this.projectIds;
    }

    /**
     * <p>Sets the IDs for projects to get the statistics for.</p>
     *
     * @param projectIds a <code>long</code> array providing the IDs for project to get the cost data for.
     */
    public void setProjectIds(long[] projectIds) {
        this.projectIds = projectIds;
    }

    /**
     * <p>Gets the IDs for customer accounts to get cost data for.</p>
     *
     * @return a <code>long[]</code> providing the IDs for customer accounts to get cost data for.
     */
    public long[] getCustomerIds() {
        return this.customerIds;
    }

    /**
     * <p>Sets the IDs for customer accounts to get cost data for.</p>
     *
     * @param customerIds a <code>long[]</code> providing the IDs for customer accounts to get cost data for.
     */
    public void setCustomerIds(long[] customerIds) {
        this.customerIds = customerIds;
    }

    /**
     * <p>Gets the IDs for billing accounts to get cost data for.</p>
     *
     * @return a <code>long[]</code> providing the IDs for billing accounts to get cost data for.
     */
    public long[] getBillingAccountIds() {
        return this.billingAccountIds;
    }

    /**
     * <p>Sets the IDs for billing accounts to get cost data for.</p>
     *
     * @param billingAccountIds a <code>long[]</code> providing the IDs for billing accounts to get cost data for.
     */
    public void setBillingAccountIds(long[] billingAccountIds) {
        this.billingAccountIds = billingAccountIds;
    }

    /**
     * <p>Gets the status ids used to filter the cost data.</p>
     *
     * @return a <code>long</code> array of status ids.
     */
    public long[] getStatusIds() {
        return statusIds;
    }

    /**
     * <p>Sets the status ids used to filter the cost report.</p>
     *
     * @param statusIds the status ids
     */
    public void setStatusIds(long[] statusIds) {
        this.statusIds = statusIds;
    }

    /**
     * <p>Gets whether the returned result is an excel file to download.</p>
     *
     * @return the flag to indicate whether the result is an excel file to download.
     */
    public boolean isExcel() {
        return excel;
    }

    /**
     * <p>Sets whether the returned result should be an excel file to download.</p>
     *
     * @param excel the flag to set.
     */
    public void setExcel(boolean excel) {
        this.excel = excel;
    }

    /**
     * Gets the flag indicating whether the cost breakdown data should display in the report.
     *
     * @return true if the cost breakdown data should display in the report, false otherwise.
     * @since 1.0.1
     */
    public boolean isShowBreakdown() {
        return showBreakdown;
    }

    /**
     * Sets the flag indicating whether the cost breakdown data should display in the report.
     * 
     * @param showBreakdown true if the cost breakdown data should display in the report, false otherwise.
     * @since 1.0.1
     */
    public void setShowBreakdown(boolean showBreakdown) {
        this.showBreakdown = showBreakdown;
    }
}
