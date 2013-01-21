/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.search;

/**
 * <p>
 * The DTO to store the search result for project.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TopCoder Cockpit Instant Search）
 */
public class ProjectSearchResult {

    /**
     * The project id.
     */
    private long projectId;

    /**
     * The project name.
     */
    private String projectName;

    /**
     * The project type id.
     */
    private long projectTypeId;

    /**
     * The project category id.
     */
    private long projectCategoryId;

    /**
     * The project type name.
     */
    private String projectTypeName;

    /**
     * The project category name.
     */
    private String projectCategoryName;

    /**
     * The client id.
     */
    private long clientId;

    /**
     * The client name.
     */
    private String clientName;

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
     * Gets the project type id.
     *
     * @return the project type id.
     */
    public long getProjectTypeId() {
        return projectTypeId;
    }

    /**
     * Sets the project type id.
     *
     * @param projectTypeId the project type id.
     */
    public void setProjectTypeId(long projectTypeId) {
        this.projectTypeId = projectTypeId;
    }

    /**
     * Gets the project category id.
     *
     * @return the project category id.
     */
    public long getProjectCategoryId() {
        return projectCategoryId;
    }

    /**
     * Sets the project category id.
     *
     * @param projectCategoryId the project category id.
     */
    public void setProjectCategoryId(long projectCategoryId) {
        this.projectCategoryId = projectCategoryId;
    }

    /**
     * Gets the project type name.
     *
     * @return the project type name.
     */
    public String getProjectTypeName() {
        return projectTypeName;
    }

    /**
     * Sets the project type name.
     *
     * @param projectTypeName the project type name.
     */
    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName;
    }

    /**
     * Gets the project category name.
     *
     * @return the project category name.
     */
    public String getProjectCategoryName() {
        return projectCategoryName;
    }

    /**
     * Sets the project category name.
     *
     * @param projectCategoryName the project category name.
     */
    public void setProjectCategoryName(String projectCategoryName) {
        this.projectCategoryName = projectCategoryName;
    }

    /**
     * Gets the client id.
     *
     * @return the client id.
     */
    public long getClientId() {
        return clientId;
    }

    /**
     * Sets the client id.
     *
     * @param clientId the client id.
     */
    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    /**
     * Gets the client name.
     *
     * @return the client name.
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Sets the client name.
     *
     * @param clientName the client name.
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * Gets the project type label.
     *
     * @return the project type label.
     */
    public String getProjectTypeLabel() {
        String projectType;
        if(getProjectTypeName() == null) {
            projectType = "None";
        } else {
            projectType = getProjectTypeName() + (getProjectCategoryName() != null ? "-" + getProjectCategoryName() : "");
        }
        return projectType;
    }
}
