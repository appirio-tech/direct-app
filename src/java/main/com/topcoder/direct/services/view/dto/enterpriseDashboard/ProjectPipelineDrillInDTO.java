/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.enterpriseDashboard;

import java.io.Serializable;
import java.util.Date;

/**
 * The DTO for the enterprise dashboard projects pipeline drill-in.
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
public class ProjectPipelineDrillInDTO implements Serializable {

    /**
     * The direct project id.
     */
    private long directProjectId;

    /**
     * The direct project name.
     */
    private String directProjectName;

    /**
     * The direct project status.
     */
    private String directProjectStatus;

    /**
     * The project start date.
     */
    private Date projectStartDate;

    /**
     * The project completion date.
     */
    private Date projectCompletionDate;

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
     * Gets the project status.
     *
     * @return the project status.
     */
    public String getDirectProjectStatus() {
        return directProjectStatus;
    }

    /**
     * Sets the project status
     *
     * @param directProjectStatus the project status.
     */
    public void setDirectProjectStatus(String directProjectStatus) {
        this.directProjectStatus = directProjectStatus;
    }

    /**
     * Gets the start date.
     *
     * @return the start date.
     */
    public Date getProjectStartDate() {
        return projectStartDate;
    }

    /**
     * Sets the start date.
     *
     * @param projectStartDate the start date.
     */
    public void setProjectStartDate(Date projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    /**
     * Gets the end date.
     *
     * @return the end date.
     */
    public Date getProjectCompletionDate() {
        return projectCompletionDate;
    }

    /**
     * Sets the end date.
     *
     * @param projectCompletionDate the end date.
     */
    public void setProjectCompletionDate(Date projectCompletionDate) {
        this.projectCompletionDate = projectCompletionDate;
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
