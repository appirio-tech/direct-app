/*
 * Copyright (C) 2011 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project.edit;

import com.topcoder.clients.model.Project;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.security.groups.model.Group;
import com.topcoder.service.permission.Permission;
import com.topcoder.service.project.ProjectCategory;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectType;

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
 * <p>
 *     Version 1.2 Release Assembly - TopCoder Cockpit Project Dashboard Project Type and Permission Notifications Integration
 *     - Add properties for project type, project category, project notifications, contest notifications and project permissions.
 * </p>
 *
 * <p>
 *     Version 1.3 (Release Assembly - TopCoder Cockpit Billing Account Project Association)
 *     - Add property {@link #billingAccounts} and its getter and setter
 * </p>
 *
 * <p>
 * Version 1.4 (Release Assembly - TopCoder Security Groups - Release 2) Change notes:
 *   <ol>
 *     <li>Added {@link #securityGroups} property.</li>
 *     <li>Added {@link #availableSecurityGroups} property.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.5 (Release Assembly - TopCoder Direct Cockpit Release Assembly Ten)
 * <ol>
 *     <li>Add {@link #tcAccountManagerIds} and its getter and setter</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.6 (TopCoder Direct - Add Appirio Manager)
 * <ul>
 *     <li>Added {@link #appirioManagerIds} and its getter and setter</li>
 * </ul>
 * </p>
 *
 * @version 1.6
 * @author GreatKevin, Veve
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
     * The TopCoder Account Manager ids of the project.
     *
     * @since 1.5
     */
    private Set<DirectProjectMetadata> tcAccountManagerIds = new LinkedHashSet<DirectProjectMetadata>();

    /**
     * The Appirio Managers ids of the project.
     *
     * @since 1.6
     */
    private Set<DirectProjectMetadata> appirioManagerIds = new LinkedHashSet<DirectProjectMetadata>();

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
     * The project types available.
     * @since 1.2
     */
    private List<ProjectType> projectTypes;

    /**
     * The project categories available
     * @since 1.2
     */
    private List<ProjectCategory> projectCategories;

    /**
     * The project user permissions.
     * @since 1.2
     */
    private List<Permission> projectPermissions;

    /**
     * The project forum notifications.
     * @since 1.2
     */
    private Map<Long, Boolean> projectForumNotifications;

    /**
     * Whether current user can access the permission notification section.
     * @since 1.2
     */
    private boolean canAccessPermissionNotification;

    /**
     * The billing accounts the project has.
     *
     * @since 1.3
     */
    private List<com.topcoder.clients.model.Project> billingAccounts;

    /**
     * <p>A <code>List<Group></code> providing the list of security groups associated with the project.</p>
     * 
     * @since 1.4
     */
    private List<Group> securityGroups;

    /**
     * <p>A <code>List<Group></code> providing the list of security groups available for association with the
     * project.</p>
     * 
     * @since 1.4
     */
    private List<Group> availableSecurityGroups;

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
     * Gets TopCoder Account Manager ids of the project
     *
     * @return TopCoder Account Manager ids of the project
     * @since 1.5
     */
    public Set<DirectProjectMetadata> getTcAccountManagerIds() {
        return tcAccountManagerIds;
    }

    /**
     * Sets TopCoder Account Manager ids of the project
     *
     * @param tcAccountManagerIds TopCoder Account Manager ids of the project
     * @since 1.5
     */
    public void setTcAccountManagerIds(Set<DirectProjectMetadata> tcAccountManagerIds) {
        this.tcAccountManagerIds = tcAccountManagerIds;
    }

    /**
     * Gets the Appirio Manager ids.
     *
     * @return the Appirio Manager ids.
     *
     * @since 1.6
     */
    public Set<DirectProjectMetadata> getAppirioManagerIds() {
        return appirioManagerIds;
    }

    /**
     * Sets the Appirio Manager ids.
     *
     * @param appirioManagerIds the Appirio Manager ids.
     * @since 1.6
     */
    public void setAppirioManagerIds(Set<DirectProjectMetadata> appirioManagerIds) {
        this.appirioManagerIds = appirioManagerIds;
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

    /**
     * Gets the project types.
     *
     * @return the project types.
     * @since 1.2
     */
    public List<ProjectType> getProjectTypes() {
        return projectTypes;
    }

    /**
     * Sets the project types.
     *
     * @param projectTypes the project types.
     * @since 1.2
     */
    public void setProjectTypes(List<ProjectType> projectTypes) {
        this.projectTypes = projectTypes;
    }

    /**
     * Gets the project categories.
     *
     * @return the project categories.
     * @since 1.2
     */
    public List<ProjectCategory> getProjectCategories() {
        return projectCategories;
    }

    /**
     * Sets the project categories.
     *
     * @param projectCategories the project categories.
     * @since 1.2
     */
    public void setProjectCategories(List<ProjectCategory> projectCategories) {
        this.projectCategories = projectCategories;
    }

    /**
     * Gets the project notifications.
     *
     * @return the project notifications.
     * @since 1.2
     */
    public Map<Long, Boolean> getProjectForumNotifications() {
        return projectForumNotifications;
    }

    /**
     * Sets the project notifications.
     *
     * @param projectForumNotifications the project forum notifications.
     * @since 1.2
     */
    public void setProjectForumNotifications(Map<Long, Boolean> projectForumNotifications) {
        this.projectForumNotifications = projectForumNotifications;
    }

    /**
     * Gets the project permissions.
     *
     * @return the project permissions.
     * @since 1.2
     */
    public List<Permission> getProjectPermissions() {
        return projectPermissions;
    }

    /**
     * Sets the project permissions.
     *
     * @param projectPermissions the project permissions.
     * @since 1.2
     */
    public void setProjectPermissions(List<Permission> projectPermissions) {
        this.projectPermissions = projectPermissions;
    }

    /**
     * Gets the flag - can access permission notification section.
     *
     * @return the flag - can access permission notification section.
     * @since 1.2
     */
    public boolean isCanAccessPermissionNotification() {
        return canAccessPermissionNotification;
    }

    /**
     * Sets the flag - can access permission notification section.
     *
     * @param canAccessPermissionNotification the flag - can access permission notification section.
     * @since 1.2
     */
    public void setCanAccessPermissionNotification(boolean canAccessPermissionNotification) {
        this.canAccessPermissionNotification = canAccessPermissionNotification;
    }

    /**
     * Gets the billing accounts the project has.
     *
     * @return the billing accounts the project has.
     * @since 1.3
     */
    public List<Project> getBillingAccounts() {
        return billingAccounts;
    }

    /**
     * Sets the billing accounts the project has.
     *
     * @param billingAccounts the billing accounts the project has.
     * @since 1.3
     */
    public void setBillingAccounts(List<Project> billingAccounts) {
        this.billingAccounts = billingAccounts;
    }

    /**
     * <p>Gets the list of security groups available for association with the project.</p>
     *
     * @return a <code>List<Group></code> providing the list of security groups available for association with the
     *         project.
     * @since 1.4
     */
    public List<Group> getAvailableSecurityGroups() {
        return this.availableSecurityGroups;
    }

    /**
     * <p>Sets the list of security groups available for association with the project.</p>
     *
     * @param availableSecurityGroups a <code>List<Group></code> providing the list of security groups available for
     *                                association with the project.
     * @since 1.4
     */
    public void setAvailableSecurityGroups(List<Group> availableSecurityGroups) {
        this.availableSecurityGroups = availableSecurityGroups;
    }

    /**
     * <p>Gets the list of security groups associated with the project.</p>
     *
     * @return a <code>List<Group></code> providing the list of security groups associated with the project.
     * @since 1.4
     */
    public List<Group> getSecurityGroups() {
        return this.securityGroups;
    }

    /**
     * <p>Sets the list of security groups associated with the project.</p>
     *
     * @param securityGroups a <code>List<Group></code> providing the list of security groups associated with the
     *                       project.
     * @since 1.4
     */
    public void setSecurityGroups(List<Group> securityGroups) {
        this.securityGroups = securityGroups;
    }
}
