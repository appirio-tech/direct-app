/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
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
 * <p>
 *     Version 1.1 Release Assembly - TC Cockpit Edit Project and Project General Info Update change nots:
 *     - Add 5 new properties for 5 project ratings.
 * </p>
 *
 * @version 1.1
 * @author GreatKevin
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
     * The business impact rating of the project.
     * @since 1.1
     */
    private DirectProjectMetadata businessImpactRating;

    /**
     * The risk level rating of the project.
     * @since 1.1
     */
    private DirectProjectMetadata riskLevelRating;

    /**
     * The cost level rating of the project.
     * @since 1.1
     */
    private DirectProjectMetadata costLevelRating;

    /**
     * The difficulty rating of the project.
     * @since 1.1
     */
    private DirectProjectMetadata difficultyRating;

    /**
     * The ROI rating of the project.
     * @since 1.1
     */
    private DirectProjectMetadata roiRating;

    /**
     * All the custom metadata of the project.
     */
    private Map<DirectProjectMetadataKey, List<DirectProjectMetadata>> customMetadata = new LinkedHashMap<DirectProjectMetadataKey, List<DirectProjectMetadata>>();

    /**
     * Whether the user has full permission on the project to edit. Only when true, the user can edit the managers of
     * the project.
     */
    private boolean hasFullPermission;

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

    /**
     * Gets the business impact rating.
     *
     * @return the business impact rating.
     * @since 1.1
     */
    public DirectProjectMetadata getBusinessImpactRating() {
        return businessImpactRating;
    }

    /**
     * Sets the business impact rating.
     *
     * @param businessImpactRating the business impact rating.
     * @since 1.1
     */
    public void setBusinessImpactRating(DirectProjectMetadata businessImpactRating) {
        this.businessImpactRating = businessImpactRating;
    }

    /**
     * Gets the risk level rating.
     *
     * @return the risk level rating.
     * @since 1.1
     */
    public DirectProjectMetadata getRiskLevelRating() {
        return riskLevelRating;
    }

    /**
     * Sets the risk level rating.
     *
     * @param riskLevelRating the risk level rating.
     * @since 1.1
     */
    public void setRiskLevelRating(DirectProjectMetadata riskLevelRating) {
        this.riskLevelRating = riskLevelRating;
    }

    /**
     * Gets the cost level rating.
     *
     * @return the cost level rating.
     * @since 1.1
     */
    public DirectProjectMetadata getCostLevelRating() {
        return costLevelRating;
    }

    /**
     * Sets the cost level rating.
     *
     * @param costLevelRating the cost level rating.
     * @since 1.1
     */
    public void setCostLevelRating(DirectProjectMetadata costLevelRating) {
        this.costLevelRating = costLevelRating;
    }

    /**
     * Gets the difficulty rating.
     *
     * @return the difficulty rating.
     * @since 1.1
     */
    public DirectProjectMetadata getDifficultyRating() {
        return difficultyRating;
    }

    /**
     * Sets the difficulty rating.
     *
     * @param difficultyRating the difficulty rating.
     * @since 1.1
     */
    public void setDifficultyRating(DirectProjectMetadata difficultyRating) {
        this.difficultyRating = difficultyRating;
    }

    /**
     * Gets the ROI rating.
     *
     * @return the ROI rating.
     * @since 1.1
     */
    public DirectProjectMetadata getRoiRating() {
        return roiRating;
    }

    /**
     * Sets the ROI rating.
     *
     * @param roiRating the roi rating.
     * @since 1.1
     */
    public void setRoiRating(DirectProjectMetadata roiRating) {
        this.roiRating = roiRating;
    }

    /**
     * Gets has full permission flag.
     *
     * @return full permission flag
     */
    public boolean getHasFullPermission() {
        return hasFullPermission;
    }

    /**
     * Sets has full permission flag.
     *
     * @param hasFullPermission full permission flag.
     */
    public void setHasFullPermission(boolean hasFullPermission) {
        this.hasFullPermission = hasFullPermission;
    }
}
