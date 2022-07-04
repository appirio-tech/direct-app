/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.enterpriseDashboard;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *     The DTO to represent the enterprise dashboard projects pipeline data of a single month.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TC Cockpit Enterprise Dashboard Project Pipeline and Project Completion Date Update)
 */
public class EnterpriseDashboardMonthProjectPipelineDTO implements Serializable {

    /**
     * The total started project number in the month.
     */
    private int totalStartedProjects;

    /**
     * The total completed project number in the month.
     */
    private int totalCompletedProjects;

    /**
     * The date object representing the month.
     */
    private Date date;

    /**
     * Gets the total started projects number.
     *
     * @return the total started projects number.
     */
    public int getTotalStartedProjects() {
        return totalStartedProjects;
    }

    /**
     * Sets the total started projects number.
     *
     * @param totalStartedProjects the total started projects number.
     */
    public void setTotalStartedProjects(int totalStartedProjects) {
        this.totalStartedProjects = totalStartedProjects;
    }

    /**
     * Gets the total completed projects number.
     *
     * @return the total completed projects number.
     */
    public int getTotalCompletedProjects() {
        return totalCompletedProjects;
    }

    /**
     * Sets the total completed projects number.
     *
     * @param totalCompletedProjects the total completed projects number.
     */
    public void setTotalCompletedProjects(int totalCompletedProjects) {
        this.totalCompletedProjects = totalCompletedProjects;
    }

    /**
     * Gets the date of the month.
     *
     * @return the date of the month.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date of the month.
     *
     * @param date the date of the month.
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
