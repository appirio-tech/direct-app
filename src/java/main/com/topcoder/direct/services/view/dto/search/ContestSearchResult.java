/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.search;

import java.io.Serializable;

/**
 * <p>
 * The DTO to store the search result for contest.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0
 */
public class ContestSearchResult implements Serializable {
    /**
     * The contest id.
     */
    private long contestId;

    /**
     * The contest name.
     */
    private String contestName;

    /**
     * The project id.
     */
    private long projectId;

    /**
     * The project name.
     */
    private String projectName;

    /**
     * The contest type id.
     */
    private long contestTypeId;

    /**
     * The contest type name.
     */
    private String contestTypeName;

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
     * Gets the project id.
     *
     * @return the project id.
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Sets the project id.
     *
     * @param projectId the project id.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the project name.
     *
     * @return the project name.
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Sets the project name.
     *
     * @param projectName the project name.
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Gets the contest type id.
     *
     * @return the contest type id.
     */
    public long getContestTypeId() {
        return contestTypeId;
    }

    /**
     * Sets the contest type id.
     *
     * @param contestTypeId the contest type id.
     */
    public void setContestTypeId(long contestTypeId) {
        this.contestTypeId = contestTypeId;
    }

    /**
     * Gets the contest type name.
     *
     * @return the contest type name.
     */
    public String getContestTypeName() {
        return contestTypeName;
    }

    /**
     * Sets the contest type name.
     *
     * @param contestTypeName the contest type name.
     */
    public void setContestTypeName(String contestTypeName) {
        this.contestTypeName = contestTypeName;
    }
}
