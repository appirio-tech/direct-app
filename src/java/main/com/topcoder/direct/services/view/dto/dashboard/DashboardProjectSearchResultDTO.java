/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard;

import java.io.Serializable;

import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectStatusType;
import com.topcoder.service.facade.contest.ProjectSummaryData;

/**
 * <p>
 * A DTO class providing the results for project search to be displayed by dashboard view.
 * </p>
 * <p>
 * Version 2.0 - use summary data as internal data
 * </p>
 * <p>
 * Version 2.1 (Release Assembly - TopCoder Cockpit Project Status Management) changes:
 * - Add the getter method {@link #getProjectStatusType()} to get the project status.
 * </p>
 *
 * @author isv, BeBetter, GreatKevin
 * @version 2.1
 */
public class DashboardProjectSearchResultDTO implements Serializable {
    /**
     * <p>
     * The project summary data.
     * </p>
     */
    private ProjectSummaryData data;

    /**
     * <p>
     * Sets the <code>data</code> field value.
     * </p>
     *
     * @param data the value to set
     */
    public void setData(ProjectSummaryData data) {
        this.data = data;
    }

    /**
     * <p>
     * Gets the <code>data</code> field value.
     * </p>
     *
     * @return the <code>data</code> field value
     */
    public ProjectSummaryData getData() {
        return this.data;
    }

    /**
     * Gets the project DTO.
     *
     * @return the <code>ProjectBriefDTO</code> data
     */
    public ProjectBriefDTO getProject() {
        if (data == null) {
            return null;
        }

        ProjectBriefDTO project = new ProjectBriefDTO();
        project.setId(data.getProjectId());
        project.setName(data.getProjectName());
        return project;
    }

    /**
     * Gets the project status type (enum) of the project.
     *
     * @return the project status type.
     * @since 2.1
     */
    public ProjectStatusType getProjectStatusType() {
        return ProjectStatusType.getProjectStatusType(data.getDirectProjectStatusId());
    }
}
