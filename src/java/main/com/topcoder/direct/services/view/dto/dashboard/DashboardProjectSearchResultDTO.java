/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard;

import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectStatusType;
import com.topcoder.service.facade.contest.ProjectSummaryData;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
 * <p>
  * Version 2.2 (Release Assembly - TC Cockpit All Projects Management Page Update) changes:
  * - Add the property {@link #projectsMetadataMap} and its getter and setter.
  * </p>
 *
 * @author isv, BeBetter, GreatKevin
 * @version 2.2
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
     * Stores the mapping of metadata of the searched project. The key is the metadata key, the value
     * is a list of metadata.
     * </p>
     * @since 2.2
     */
    private Map<DirectProjectMetadataKey, List<DirectProjectMetadata>> projectsMetadataMap;
    
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

    /**
     * Gets the map of project metadata.
     *
     * @return the map of project metadata.
     * @since 2.2
     */
    public Map<DirectProjectMetadataKey, List<DirectProjectMetadata>> getProjectsMetadataMap() {
        return projectsMetadataMap;
    }

    /**
     * Sets the map of project metadata.
     *
     * @param projectsMetadataMap the map of project metadata.
     * @since 2.2
     */
    public void setProjectsMetadataMap(Map<DirectProjectMetadataKey, List<DirectProjectMetadata>> projectsMetadataMap) {
        this.projectsMetadataMap = projectsMetadataMap;
    }
}
