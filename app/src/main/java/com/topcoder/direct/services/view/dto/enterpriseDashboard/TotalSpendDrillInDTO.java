/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.enterpriseDashboard;

/**
 * The DTO for the enterprise dashboard total spend chart drill-in.
 *
 * <p>
 * Version 1.1 (Release Assembly - TC Cockpit New Enterprise Dashboard Release 2)
 * <ul>
 *     <li>Add property {@link #yearMonthLabel} and its getter and setter</li>
 * </ul>
 * </p>
 *
 * @author GreatKevin
 * @version 1.1
 * @since 1.0 (Release Assembly - Cockpit Enterprise Dashboard Chart Drill-In)
 */
public class TotalSpendDrillInDTO extends EnterpriseDashboardTotalSpendDTO {
    /**
     * The direct project id.
     */
    private long directProjectId;

    /**
     * The direct project name.
     */
    private String directProjectName;

    /**
     * The year and month label representing a month in a year.
     *
     * @since 1.1
     */
    private String yearMonthLabel;

    /**
     * Gets the direct project id.
     *
     * @return the direct project id.
     */
    public long getDirectProjectId() {
        return directProjectId;
    }

    /**
     * Sets the direct project id.
     *
     * @param directProjectId the direct project id.
     */
    public void setDirectProjectId(long directProjectId) {
        this.directProjectId = directProjectId;
    }

    /**
     * Gets the direct project name.
     *
     * @return the direct project name.
     */
    public String getDirectProjectName() {
        return directProjectName;
    }

    /**
     * Sets the direct project name.
     *
     * @param directProjectName the direct project name.
     */
    public void setDirectProjectName(String directProjectName) {
        this.directProjectName = directProjectName;
    }

    /**
     * Gets the year month label.
     *
     * @return the year month label.
     * @since 1.1
     */
    public String getYearMonthLabel() {
        return yearMonthLabel;
    }

    /**
     * Sets the year month label.
     *
     * @param yearMonthLabel the year month label.
     * @since 1.1
     */
    public void setYearMonthLabel(String yearMonthLabel) {
        this.yearMonthLabel = yearMonthLabel;
    }
}
