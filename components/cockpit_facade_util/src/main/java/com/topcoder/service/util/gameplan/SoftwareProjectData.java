/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.util.gameplan;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>This class is a container for game plan specific data of a single software project associated with some TC Direct
 * project.</p>
 *
 * <p>It is a simple JavaBean (POJO) that provides getters and setters for all private fields and performs no argument
 * validation in the setters.</p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TC Cockpit Enterprise Dashboard Project Pipeline and Project Completion Date Update)
 * <ol>
 *     <li>
 *      Add the property {@link #dependencyProjectTypeIds} and its getter/setter.
 *     </li>
 * </ol>
 * </p>
 *
 * <p><b>Thread Safety</b>: This class is mutable and not thread safe.</p>
 *
 * @author saarixx, FireIce, GreatKevin
 * @version 1.1
 */
public class SoftwareProjectData implements Serializable {
    /**
     * The ID of the TC Direct project. Can be any value. Has getter and setter.
     */
    private long tcDirectProjectId;

    /**
     * The ID of the software project. Can be any value. Has getter and setter.
     */
    private long projectId;

    /**
     * The project name. Can be any value. Has getter and setter.
     */
    private String projectName;

    /**
     * The start date of the contest. Can be any value. Has getter and setter.
     */
    private Date startDate;

    /**
     * The final review end date of the contest. Can be any value. Has getter and setter.
     */
    private Date finalReviewEndDate;

    /**
     * The end date of the contest. Can be any value. Has getter and setter.
     */
    private Date endDate;

    /**
     * The ID of the user who created the project. Can be any value. Has getter and setter.
     */
    private Long createUserId;

    /**
     * The IDs of the dependency projects. Can be any value. Has getter and setter.
     */
    private long[] dependencyProjectIds;

    /**
     * The dependency type ids of the dependency projects. Can be any value. Has getter and setter. It stores the
     * corresponding project dependency type ids of the <code>dependencyProjectIds</code>
     *
     * @since 1.1
     */
    private long[] dependencyProjectTypeIds;

    /**
     * The flag indicating whether this is a repost project. Can be any value. Has getter and setter.
     */
    private boolean repost;

    /**
     * The project status. Can be any value. Has getter and setter.
     */
    private String projectStatus;

    /**
     * The current phase of the project. Can be any value. Has getter and setter.
     */
    private String currentPhase;

    /**
     * The project type. Can be any value. Has getter and setter.
     */
    private String projectType;

    /**
     * The flag indicating whether the contest is started. Can be any value. Has getter and setter.
     */
    private boolean started;

    /**
     * The flag indicating whether the contest is finished. Can be any value. Has getter and setter.
     */
    private boolean finished;

    /**
     * Creates a <code>SoftwareProjectData</code> instance.
     */
    public SoftwareProjectData() {
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
     * Retrieves the ID of the software project.
     *
     * @return the ID of the software project
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Sets the ID of the software project.
     *
     * @param projectId the ID of the software project
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
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
     * Retrieves the final review end date of the contest.
     *
     * @return the final review
     */
    public Date getFinalReviewEndDate() {
        return finalReviewEndDate;
    }

    public void setFinalReviewEndDate(Date finalReviewEndDate) {
        this.finalReviewEndDate = finalReviewEndDate;
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
     * Retrieves the IDs of the dependency projects.
     *
     * @return the IDs of the dependency projects
     */
    public long[] getDependencyProjectIds() {
        return dependencyProjectIds;
    }

    /**
     * Sets the IDs of the dependency projects.
     *
     * @param dependencyProjectIds the IDs of the dependency projects
     */
    public void setDependencyProjectIds(long[] dependencyProjectIds) {
        this.dependencyProjectIds = dependencyProjectIds;
    }

    /**
     * Gets the dependency project type ids.
     *
     * @return the dependency project type ids.
     * @since 1.1
     */
    public long[] getDependencyProjectTypeIds() {
        return dependencyProjectTypeIds;
    }

    /**
     * Sets the dependency project type ids.
     *
     * @param dependencyProjectTypeIds the dependency project type ids.
     * @since 1.1
     */
    public void setDependencyProjectTypeIds(long[] dependencyProjectTypeIds) {
        this.dependencyProjectTypeIds = dependencyProjectTypeIds;
    }

    /**
     * Retrieves the flag indicating whether this is a repost project.
     *
     * @return the flag indicating whether this is a repost project
     */
    public boolean isRepost() {
        return repost;
    }

    /**
     * Sets the flag indicating whether this is a repost project.
     *
     * @param repost the flag indicating whether this is a repost project
     */
    public void setRepost(boolean repost) {
        this.repost = repost;
    }

    /**
     * Retrieves the project status.
     *
     * @return the project status
     */
    public String getProjectStatus() {
        return projectStatus;
    }

    /**
     * Sets the project status.
     *
     * @param projectStatus the project status
     */
    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    /**
     * Retrieves the current phase of the project.
     *
     * @return the current phase of the project
     */
    public String getCurrentPhase() {
        return currentPhase;
    }

    /**
     * Sets the current phase of the project.
     *
     * @param currentPhase the current phase of the project
     */
    public void setCurrentPhase(String currentPhase) {
        this.currentPhase = currentPhase;
    }

    /**
     * Retrieves the project type.
     *
     * @return the project type
     */
    public String getProjectType() {
        return projectType;
    }

    /**
     * Sets the project type.
     *
     * @param projectType the project type
     */
    public void setProjectType(String projectType) {
        this.projectType = projectType;
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

