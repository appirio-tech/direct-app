/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project.edit;

import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.service.project.ProjectData;

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 *     DTO for data in edit project page.
 * </p>
 *
 * @version 1.0 (Module Assembly - TopCoder Cockpit Project Dashboard Edit Project version 1.0)
 */
public class EditCockpitProjectDTO extends CommonDTO implements Serializable {

    /**
     * The project data.
     */
    private ProjectData project;

    /**
     * The client id of the project.
     */
    private Long clientId;

    /**
     * The client managers Ids of the project.
     */
    private Set<DirectProjectMetadata> clientManagerIds = new LinkedHashSet<DirectProjectMetadata>();

    /**
     * The TopCoder Project Manager ids of the project.
     */
    private Set<DirectProjectMetadata> tcManagerIds = new LinkedHashSet<DirectProjectMetadata>();

    /**
     * The SVN URL of the project.
     */
    private DirectProjectMetadata svnURL;

    /**
     * The JIRA URL of the project.
     */
    private DirectProjectMetadata jiraURL;

    /**
     * The planned duration of the project.
     */
    private DirectProjectMetadata duration;

    /**
     * The planned budget of the project.
     */
    private DirectProjectMetadata budget;

    /**
     * The privacy setting of the project.
     */
    private DirectProjectMetadata privacy;

    /**
     * All the custom metadata of the project.
     */
    private Map<DirectProjectMetadataKey, List<DirectProjectMetadata>> customMetadata = new LinkedHashMap<DirectProjectMetadataKey, List<DirectProjectMetadata>>();

    /**
     * Gets the project data.
     *
     * @return the project data.
     */
    public ProjectData getProject() {
        return project;
    }

    /**
     * Sets the project data.
     *
     * @param project the project data.
     */
    public void setProject(ProjectData project) {
        this.project = project;
    }

    /**
     * Gets the client id.
     *
     * @return the client id of the project.
     */
    public Long getClientId() {
        return clientId;
    }

    /**
     * Sets the client id.
     *
     * @param clientId the client id of the project.
     */
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    /**
     * Gets client manager ids of the project.
     *
     * @return client manager ids of the project.
     */
    public Set<DirectProjectMetadata> getClientManagerIds() {
        return clientManagerIds;
    }

    /**
     * Sets client manager ids of the project.
     *
     * @param clientManagerIds client manager ids of the project.
     */
    public void setClientManagerIds(Set<DirectProjectMetadata> clientManagerIds) {
        this.clientManagerIds = clientManagerIds;
    }

    /**
     * Gets TopCoder manager ids of the project.
     *
     * @return TopCoder manager ids of the project.
     */
    public Set<DirectProjectMetadata> getTcManagerIds() {
        return tcManagerIds;
    }

    /**
     * Sets TopCoder manager ids of the project.
     *
     * @param tcManagerIds TopCoder manager ids of the project.
     */
    public void setTcManagerIds(Set<DirectProjectMetadata> tcManagerIds) {
        this.tcManagerIds = tcManagerIds;
    }

    /**
     * Gets the SVN URL of the project.
     *
     * @return the SVN URL of the project.
     */
    public DirectProjectMetadata getSvnURL() {
        return svnURL;
    }

    /**
     * Sets the SVN URL of the project.
     *
     * @param svnURL the SVN URL of the project.
     */
    public void setSvnURL(DirectProjectMetadata svnURL) {
        this.svnURL = svnURL;
    }

    /**
     * Gets the JIRA URL of the project.
     *
     * @return the JIRA URL of the project.
     */
    public DirectProjectMetadata getJiraURL() {
        return jiraURL;
    }

    /**
     * Sets the JIRA URL of the project.
     *
     * @param jiraURL the JIRA URL of the project.
     */
    public void setJiraURL(DirectProjectMetadata jiraURL) {
        this.jiraURL = jiraURL;
    }

    /**
     * Gets the planned duration of the project.
     *
     * @return the planned duration of the project.
     */
    public DirectProjectMetadata getDuration() {
        return duration;
    }

    /**
     * Sets the planned duration of the project.
     *
     * @param duration the planned duration of the project.
     */
    public void setDuration(DirectProjectMetadata duration) {
        this.duration = duration;
    }

    /**
     * Gets the budget of the project.
     *
     * @return the budget of the project.
     */
    public DirectProjectMetadata getBudget() {
        return budget;
    }

    /**
     * Sets the budget of the project.
     *
     * @param budget the budget of the project.
     */
    public void setBudget(DirectProjectMetadata budget) {
        this.budget = budget;
    }

    /**
     * Gets the privacy setting of the project.
     *
     * @return the privacy setting of the project.
     */
    public DirectProjectMetadata getPrivacy() {
        return privacy;
    }

    /**
     * Sets the privacy setting of the project.
     *
     * @param privacy the privacy setting of the project.
     */
    public void setPrivacy(DirectProjectMetadata privacy) {
        this.privacy = privacy;
    }

    /**
     * Gets all custom metadata of the project.
     *
     * @return all custom metadata of the project.
     */
    public Map<DirectProjectMetadataKey, List<DirectProjectMetadata>> getCustomMetadata() {
        return customMetadata;
    }

    /**
     * Sets all custom metadata of the project.
     *
     * @param customMetadata all custom metadata of the project.
     */
    public void setCustomMetadata(Map<DirectProjectMetadataKey, List<DirectProjectMetadata>> customMetadata) {
        this.customMetadata = customMetadata;
    }
}
