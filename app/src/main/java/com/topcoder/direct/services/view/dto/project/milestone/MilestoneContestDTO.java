/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project.milestone;

import java.io.Serializable;

/**
 * The DTO to represent a contest which is associated to a milestone.
 *
 * @author TCSASSEMBLER
 * @version 1.0
 * @since 1.0 (Module Assembly - TC Cockpit Contest Milestone Association Milestone Page Update)
 */
public class MilestoneContestDTO implements Serializable {
    /**
     * The contest id.
     */
    private long contestId;

    /**
     * The contest name.
     */
    private String contestName;

    /**
     * The short representation of the contest status.
     */
    private String contestShortStatus;

    /**
     * The contest status.
     */
    private String contestStatus;

    /**
     * The start date of the contest.
     */
    private String startDate;

    /**
     * The end date of the contest.
     */
    private String endDate;

    /**
     * The type of the contest.
     */
    private String contestType;

    /**
     * The milestone id.
     */
    private long milestoneId;

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
     * @return
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
     * Gets the short representation of the contest status.
     *
     * @return the short representation of the contest status.
     */
    public String getContestShortStatus() {
        return contestShortStatus;
    }

    /**
     * Sets the short representation of the contest status.
     *
     * @param contestShortStatus the short representation of the contest status.
     */
    public void setContestShortStatus(String contestShortStatus) {
        this.contestShortStatus = contestShortStatus;
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
     * Sets the contest status.
     *
     * @param contestStatus the contest status.
     */
    public void setContestStatus(String contestStatus) {
        this.contestStatus = contestStatus;
    }

    /**
     * Gets the start date.
     *
     * @return the start date.
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date.
     *
     * @param startDate the start date.
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date.
     *
     * @return the end date.
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date.
     *
     * @param endDate the end date.
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the contest type.
     *
     * @return the contest type.
     */
    public String getContestType() {
        return contestType;
    }

    /**
     * Sets the contest type.
     *
     * @param contestType the contest type.
     */
    public void setContestType(String contestType) {
        this.contestType = contestType;
    }

    /**
     * Gets the milestone id.
     *
     * @return the milestone id.
     */
    public long getMilestoneId() {
        return milestoneId;
    }

    /**
     * Sets the milestone id.
     *
     * @param milestoneId the milestone id.
     */
    public void setMilestoneId(long milestoneId) {
        this.milestoneId = milestoneId;
    }
}
