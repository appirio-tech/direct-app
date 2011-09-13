/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project;

/**
 * <p>
 *     Enumeration to represent the cockpit project status.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TopCoder Cockpit Project Status Management)
 */
public enum ProjectStatusType {

    /**
     * The active project status.
     */
    ACTIVE(1L, "Active"),

    /**
     * The archived project status.
     */
    ARCHIVED(2L, "Archived"),

    /**
     * The deleted project status.
     */
    DELETED(3L, "Deleted"),

    /**
     * The completed project status.
     */
    COMPLETED(4L, "Completed");

    /**
     * The id of the project status.
     */
    private long projectStatusId;

    /**
     * The name of the project status.
     */
    private String projectStatusName;

    /**
     * Private constructor for enum type initialization.
     *
     * @param projectStatusId the id of the project status.
     * @param projectStatusName the name of the project status.
     */
    private ProjectStatusType(long projectStatusId, String projectStatusName) {
        this.projectStatusId = projectStatusId;
        this.projectStatusName = projectStatusName;
    }

    /**
     * Gets the project status id.
     *
     * @return the project status id.
     */
    public long getProjectStatusId() {
        return projectStatusId;
    }

    /**
     * Sets the project status id.
     *
     * @param projectStatusId the project status id.
     */
    public void setProjectStatusId(long projectStatusId) {
        this.projectStatusId = projectStatusId;
    }

    /**
     * Gets the project status name.
     *
     * @return the name of the project status.
     */
    public String getProjectStatusName() {
        return projectStatusName;
    }

    /**
     * Sets the project status name.
     *
     * @param projectStatusName the name of the project status.
     */
    public void setProjectStatusName(String projectStatusName) {
        this.projectStatusName = projectStatusName;
    }

    /**
     * <p>
     *     Static helper method to get the ProjectStatusType enum by project status id.
     * </p>
     *
     * @param projectStatusId the project status id
     * @return the ProjectStatusType matches the id.
     */
    public static ProjectStatusType getProjectStatusType(long projectStatusId) {
        ProjectStatusType[] values = ProjectStatusType.values();

        for (ProjectStatusType type : values) {
            if (type.getProjectStatusId() == projectStatusId) {
                return type;
            }
        }

        return null;
    }
}
