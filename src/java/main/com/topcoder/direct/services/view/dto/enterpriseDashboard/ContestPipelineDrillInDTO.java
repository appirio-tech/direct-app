/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.enterpriseDashboard;

import java.io.Serializable;
import java.util.Date;

/**
 * The DTO for the enterprise dashboard contests pipeline drill-in.
 *
 * @author GreatKevin
 * @version 1.0 (Release Assembly - Cockpit Enterprise Dashboard Chart Drill-In)
 */
public class ContestPipelineDrillInDTO implements Serializable {

    /**
     * The direct project id.
     */
    private long directProjectId;

    /**
     * The direct project name.
     */
    private String directProjectName;

    /**
     * The contest status.
     */
    private String contestStatus;

    /**
     * The contest id.
     */
    private long contestId;

    /**
     * The contest name.
     */
    private String contestName;

    /**
     * The contest start date.
     */
    private Date startDate;

    /**
     * The contest end date.
     */
    private Date endDate;

    /**
     * The copilot handle.
     */
    private String copilotHandle;

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
     * Gets the contest status.
     *
     * @return the contest status.
     */
    public String getContestStatus() {
        return contestStatus;
    }

    /**
     * Sets the contest status
     *
     * @param contestStatus the contest status.
     */
    public void setContestStatus(String contestStatus) {
        this.contestStatus = contestStatus;
    }

    /**
     * Gets the contest id.
     *
     * @return the contest id.
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Sets the contest id.
     *
     * @param contestId the contest id.
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * Gets the contest name.
     *
     * @return the contest name.
     */
    public String getContestName() {
        return contestName;
    }

    /**
     * Sets the contest name.
     *
     * @param contestName the contest name.
     */
    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    /**
     * Gets the start date.
     *
     * @return the start date.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date.
     *
     * @param startDate the start date.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date.
     *
     * @return the end date.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date.
     *
     * @param endDate the end date.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the copilot handle.
     *
     * @return the copilot handle.
     */
    public String getCopilotHandle() {
        return copilotHandle;
    }

    /**
     * Sets the copilot handle.
     *
     * @param copilotHandle the copilot handle.
     */
    public void setCopilotHandle(String copilotHandle) {
        this.copilotHandle = copilotHandle;
    }
}
