/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard;

import java.io.Serializable;

import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.service.facade.contest.ProjectSummaryData;

/**
 * <p>
 * A DTO class providing the results for project search to be displayed by dashboard view.
 * </p>
 * <p>
 * Version 2.0 - use summary data as internal data
 * </p>
 *
 * @author isv, BeBetter
 * @version 2.0
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

}
