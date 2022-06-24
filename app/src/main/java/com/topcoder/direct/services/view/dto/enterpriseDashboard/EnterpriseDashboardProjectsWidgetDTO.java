/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.enterpriseDashboard;

import java.io.Serializable;
import java.util.Date;

/**
 * The DTO used for enterprise dashboard projects widget.
 *
 * @author TCSASSEMBLER
 * @version 1.0
 */
public class EnterpriseDashboardProjectsWidgetDTO implements Serializable {
    /**
     * The direct project id.
     */
    private long directProjectId;

    /**
     * The direct project name.
     */
    private String directProjectName;

    /**
     * The active contest number of the project.
     */
    private int activeContestsNumber;

    /**
     * The earliest incomplete milestone due date.
     */
    private Date milestoneDueDate;

    /**
     * The milestone name of the earliest incomplete milestone due date
     */
    private String milestoneName;

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
     * Gets the active contests number.
     *
     * @return the active contests number.
     */
    public int getActiveContestsNumber() {
        return activeContestsNumber;
    }

    /**
     * Sets the active contests number.
     *
     * @param activeContestsNumber the active contests number.
     */
    public void setActiveContestsNumber(int activeContestsNumber) {
        this.activeContestsNumber = activeContestsNumber;
    }

    /**
     * Gets the milestone due date.
     *
     * @return the milestone due date.
     */
    public Date getMilestoneDueDate() {
        return milestoneDueDate;
    }

    /**
     * Sets the milestone due date.
     *
     * @param milestoneDueDate the milestone due date.
     */
    public void setMilestoneDueDate(Date milestoneDueDate) {
        this.milestoneDueDate = milestoneDueDate;
    }

    /**
     * Gets the direct project name
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
     * Gets the milestone name.
     *
     * @return the milestone name.
     */
    public String getMilestoneName() {
        return milestoneName;
    }

    /**
     * Sets the milestone name.
     *
     * @param milestoneName the milestone name.
     */
    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
    }
}
