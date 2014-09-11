/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.util.gameplan;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>This class is a container for game plan specific data of a single studio project associated with some TC Direct
 * project.</p>
 *
 * <p>It is a simple JavaBean (POJO) that provides getters and setters for all private fields and performs no argument
 * validation in the setters.</p>
 *
 * <p><b>Thread Safety</b>: This class is mutable and not thread safe.</p>
 *
 * @author saarixx, FireIce
 * @version 1.0
 */
public class StudioProjectData implements Serializable {
    /**
     * The ID of the TC Direct project. Can be any value. Has getter and setter.
     */
    private long tcDirectProjectId;

    /**
     * The ID of the studio project. Can be any value. Has getter and setter.
     */
    private Long projectId;

    /**
     * The ID of the contest. Can be any value. Has getter and setter.
     */
    private long contestId;

    /**
     * The project name. Can be any value. Has getter and setter.
     */
    private String projectName;

    /**
     * The contest name. Can be any value. Has getter and setter.
     */
    private String contestName;

    /**
     * The start date of the contest. Can be any value. Has getter and setter.
     */
    private Date startDate;

    /**
     * The end date of the contest. Can be any value. Has getter and setter.
     */
    private Date endDate;

    /**
     * The ID of the user who created the project. Can be any value. Has getter and setter.
     */
    private Long createUserId;

    /**
     * The status of the contest. Can be any value. Has getter and setter.
     */
    private String contestStatus;

    /**
     * The contest type. Can be any value. Has getter and setter.
     */
    private String contestType;

    /**
     * The flag indicating whether the contest is started. Can be any value. Has getter and setter.
     */
    private boolean started;

    /**
     * The flag indicating whether the contest is finished. Can be any value. Has getter and setter.
     */
    private boolean finished;

    /**
     * Creates a <code>StudioProjectData</code> instance.
     */
    public StudioProjectData() {
    }

    /**
     * Retrieves the ID of the TC Direct project.
     *
     * @return the ID of the TC Direct project
     */
    public long getTcDirectProjectId() {
        return tcDirectProjectId;
    }

    /**
     * Sets the ID of the TC Direct project.
     *
     * @param tcDirectProjectId the ID of the TC Direct project
     */
    public void setTcDirectProjectId(long tcDirectProjectId) {
        this.tcDirectProjectId = tcDirectProjectId;
    }

    /**
     * Retrieves the ID of the studio project.
     *
     * @return the ID of the studio project
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * Sets the ID of the studio project.
     *
     * @param projectId the ID of the studio project
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * Retrieves the ID of the contest.
     *
     * @return the ID of the contest
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Sets the ID of the contest.
     *
     * @param contestId the ID of the contest
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * Retrieves the project name.
     *
     * @return the project name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Sets the project name.
     *
     * @param projectName the project name
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Retrieves the contest name.
     *
     * @return the contest name
     */
    public String getContestName() {
        return contestName;
    }

    /**
     * Sets the contest name.
     *
     * @param contestName the contest name
     */
    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    /**
     * Retrieves the start date of the contest.
     *
     * @return the start date of the contest
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the contest.
     *
     * @param startDate the start date of the contest
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Retrieves the end date of the contest.
     *
     * @return the end date of the contest
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the contest.
     *
     * @param endDate the end date of the contest
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Retrieves the ID of the user who created the project.
     *
     * @return the ID of the user who created the project
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * Sets the ID of the user who created the project.
     *
     * @param createUserId the ID of the user who created the project
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * Retrieves the status of the contest.
     *
     * @return the status of the contest
     */
    public String getContestStatus() {
        return contestStatus;
    }

    /**
     * Sets the status of the contest.
     *
     * @param contestStatus the status of the contest
     */
    public void setContestStatus(String contestStatus) {
        this.contestStatus = contestStatus;
    }

    /**
     * Retrieves the contest type.
     *
     * @return the contest type
     */
    public String getContestType() {
        return contestType;
    }

    /**
     * Sets the contest type.
     *
     * @param contestType the contest type
     */
    public void setContestType(String contestType) {
        this.contestType = contestType;
    }

    /**
     * Retrieves the flag indicating whether the contest is started.
     *
     * @return the flag indicating whether the contest is started
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Sets the flag indicating whether the contest is started.
     *
     * @param started the flag indicating whether the contest is started
     */
    public void setStarted(boolean started) {
        this.started = started;
    }

    /**
     * Retrieves the flag indicating whether the contest is finished.
     *
     * @return the flag indicating whether the contest is finished
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Sets the flag indicating whether the contest is finished.
     *
     * @param finished the flag indicating whether the contest is finished
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}

