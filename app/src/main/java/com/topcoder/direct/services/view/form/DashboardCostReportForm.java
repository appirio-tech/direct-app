/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;


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
 * <p>
 * Version 1.1 (TC Cockpit Permission and Report Update One) change log:
 * <ol>
 *   <li>This class has been refactoring. It's extended from <code>DashboardReportForm</code>.</li>
 * </ol>
 * </p>
 * 
 * @author flexme, TCSASSEMBER
 * @version 1.1 (TopCoder Cockpit - Cost Report Assembly)
 */
public class DashboardCostReportForm extends DashboardReportForm {
    /**
     * <p>A <code>boolean</code> flag indicating whether the retrieved data is expected to be converted into
     * <code>Excel</code> format or not.</p>
     */
    private boolean excel;

    /**
     * <p>A <code>String</code> providing the contest id to filter the result.</p>
     */
    private String contestId;

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
        super();
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

    /**
     * Gets the contest id to filter the result.
     * 
     * @return the contest id to filter the result.
     */
    public String getContestId() {
        return contestId;
    }

    /**
     * Sets the contest id to filter the result.
     * 
     * @param contestId the contest id to filter the result.
     */
    public void setContestId(String contestId) {
        this.contestId = contestId;
    }
}
