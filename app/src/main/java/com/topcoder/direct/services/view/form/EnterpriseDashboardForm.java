/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

import java.io.Serializable;

/**
 * <p>A form bean providing the data submitted by user for evaluating the data for <code>Enterprise Dashboard</code>
 * view.</p>
 *
 * <p>
 * Version 1.0.1 (Cockpit - Enterprise Dashboard 2 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #billingAccountIds} property with respective accessor/mutator methods.</li>
 *     <li>Added {@link #customerIds} property with respective accessor/mutator methods.</li>
 *     <li>Refactored existing <code>projectId</code> property into {@link #projectIds} property with respective 
 *     accessor/mutator methods.</li>
 *   </ol>
 * </p>
 *
 * <p>
 *     Version 1.1 (Release Assembly - TC Cockpit Enterprise Dashboard Volume View Assembly) change notes:
 *     <ol>
 *         <li>Add the form data contestStatus.</li>
 *     </ol>
 * </p>
 *
 * @author isv, TCSASSEMBLER
 * @version 1.1
 */
public class EnterpriseDashboardForm implements Serializable {

    /**
     * <p>A <code>long[]</code> providing the IDs for project categories to evaluate the statistical data for.</p>
     */
    private long[] projectCategoryIds;

    /**
     * <p>A <code>String</code> providing the start date for period of evaluation of statistical data.</p>
     */
    private String startDate;

    /**
     * <p>A <code>String</code> providing the end date for period of evaluation of statistical data.</p>
     */
    private String endDate;

    /**
     * <p>A <code>long</code> array providing the IDs for projects to get the statistics for.</p>
     */
    private long[] projectIds;

    /**
     * <p>A <code>long[]</code> providing the IDs for billing accounts to get statistics for.</p>
     * 
     * @since 1.0.1
     */
    private long[] billingAccountIds;

    /**
     * <p>A <code>long[]</code> providing the IDs for customer accounts to get statistics for.</p>
     * 
     * @since 1.0.1
     */
    private long[] customerIds;

    /**
     * The contest status ids of the volume view.
     * @since 1.1
     */
    private long[] contestStatus;

    /**
     * <p>Constructs new <code>EnterpriseDashboardForm</code> instance. This implementation does nothing.</p>
     */
    public EnterpriseDashboardForm() {
    }

    /**
     * Gets the contest status ids.
     *
     * @return the contest status ids.
     * @since 1.1
     */
    public long[] getContestStatus() {
        return contestStatus;
    }

    /**
     * Sets the contest status ids.
     *
     * @param contestStatus the contest status ids.
     * @since 1.1
     */
    public void setContestStatus(long[] contestStatus) {
        this.contestStatus = contestStatus;
    }

    /**
     * <p>Gets the IDs for project categories to evaluate the statistical data for.</p>
     *
     * @return a <code>long[]</code> providing the IDs for project categories to evaluate the statistical data for.
     */
    public long[] getProjectCategoryIds() {
        return this.projectCategoryIds;
    }

    /**
     * <p>Sets the IDs for project categories to evaluate the statistical data for.</p>
     *
     * @param projectCategoryIds a <code>long[]</code> providing the IDs for project categories to evaluate the
     *                           statistical data for.
     */
    public void setProjectCategoryIds(long[] projectCategoryIds) {
        this.projectCategoryIds = projectCategoryIds;
    }

    /**
     * <p>Gets the end date for period of evaluation of statistical data.</p>
     *
     * @return a <code>String</code> providing the end date for period of evaluation of statistical data.
     */
    public String getEndDate() {
        return this.endDate;
    }

    /**
     * <p>Sets the end date for period of evaluation of statistical data.</p>
     *
     * @param endDate a <code>String</code> providing the end date for period of evaluation of statistical data.
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * <p>Gets the start date for period of evaluation of statistical data.</p>
     *
     * @return a <code>String</code> providing the start date for period of evaluation of statistical data.
     */
    public String getStartDate() {
        return this.startDate;
    }

    /**
     * <p>Sets the start date for period of evaluation of statistical data.</p>
     *
     * @param startDate a <code>String</code> providing the start date for period of evaluation of statistical data.
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * <p>Gets the IDs for projects to get the statistics for.</p>
     *
     * @return a <code>long</code> array providing the IDs for projects to get the statistics for.
     */
    public long[] getProjectIds() {
        return this.projectIds;
    }

    /**
     * <p>Sets the IDs for projects to get the statistics for.</p>
     *
     * @param projectIds a <code>long</code> array providing the IDs for project to get the statistics for.
     */
    public void setProjectIds(long[] projectIds) {
        this.projectIds = projectIds;
    }

    /**
     * <p>Gets the IDs for customer accounts to get statistics for.</p>
     *
     * @return a <code>long[]</code> providing the IDs for customer accounts to get statistics for.
     * @since 1.0.1
     */
    public long[] getCustomerIds() {
        return this.customerIds;
    }

    /**
     * <p>Sets the IDs for customer accounts to get statistics for.</p>
     *
     * @param customerIds a <code>long[]</code> providing the IDs for customer accounts to get statistics for.
     * @since 1.0.1
     */
    public void setCustomerIds(long[] customerIds) {
        this.customerIds = customerIds;
    }

    /**
     * <p>Gets the IDs for billing accounts to get statistics for.</p>
     *
     * @return a <code>long[]</code> providing the IDs for billing accounts to get statistics for.
     * @since 1.0.1
     */
    public long[] getBillingAccountIds() {
        return this.billingAccountIds;
    }

    /**
     * <p>Sets the IDs for billing accounts to get statistics for.</p>
     *
     * @param billingAccountIds a <code>long[]</code> providing the IDs for billing accounts to get statistics for.
     * @since 1.0.1
     */
    public void setBillingAccountIds(long[] billingAccountIds) {
        this.billingAccountIds = billingAccountIds;
    }
}
