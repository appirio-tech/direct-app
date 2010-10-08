/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

import java.io.Serializable;

/**
 * <p>A form bean providing the data submitted by user for evaluating the data for <code>Enterprise Dashboard</code>
 * view.</p>
 *
 * @author isv
 * @version 1.0 (Direct Enterprise Dashboard Assembly 1.0)
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
     * <p>A <code>long</code> providing the ID for project to get the statistics for.</p>
     */
    private long projectId;

    /**
     * <p>Constructs new <code>EnterpriseDashboardForm</code> instance. This implementation does nothing.</p>
     */
    public EnterpriseDashboardForm() {
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
     * <p>Gets the ID for project to get the statistics for.</p>
     *
     * @return a <code>long</code> providing the ID for project to get the statistics for.
     */
    public long getProjectId() {
        return this.projectId;
    }

    /**
     * <p>Sets the ID for project to get the statistics for.</p>
     *
     * @param projectId a <code>long</code> providing the ID for project to get the statistics for.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }
}
