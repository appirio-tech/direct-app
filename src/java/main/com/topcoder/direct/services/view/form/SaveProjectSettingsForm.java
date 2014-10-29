/*
 * Copyright (C) 2011 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.view.dto.project.edit.ProjectMetadataOperation;
import com.topcoder.direct.services.view.dto.project.edit.ProjectNotificationSetting;
import com.topcoder.service.permission.ProjectPermission;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *     The form data for the save operation and all the ajax operations in Edit Project Settings page.
 * </p>
 *
 * <p>
 *     Version 1.1 (Release Assembly - TC Cockpit Edit Project and Project General Info Update) change notes:
 *     - Add new form property projectRatings for saving project ratings in Edit Project Page.
 * </p>
 *
 * <p>
 *     Version 1.2 (Release Assembly - TopCoder Cockpit Project Dashboard Project Type and Permission Notifications Integration)
 *     - Add new form properties: project type id, project category id, project permissions, project notifications and contest
 *     timeline / forum notifications and user id.
 * </p>
 *
 * <p>
 *     Version 1.3 (Release Assembly - TopCoder Cockpit Billing Account Project Association)
 *     - Add the property {@link #projectBillingAccountId}
 * </p>
 *
 * <p>
 *     Version 1.3 (Release Assembly - TopCoder Direct Cockpit Release Assembly Ten)
 *     - Add the property {@link #accountManagers}
 * </p>
 *
 * <p>
 *     Version 1.4 (TopCoder Direct - Add Appirio Manager)
 *     - Add {@link #appirioManagers} and its getter and setter.
 * </p>
 *
 * @author GreatKevin, Veve
 * @version 1.4
 */
public class SaveProjectSettingsForm extends ProjectIdForm {

    /**
     * The name of the project.
     */
    private String projectName;

    /**
     * The description of the project.
     */
    private String projectDescription;

    /**
     * The status id of the project.
     */
    private long projectStatusId;

    /**
     * The client managers of the project.
     */
    private List<ProjectMetadataOperation> clientManagers;

    /**
     * The TopCoder platform specialists of the project.
     */
    private List<ProjectMetadataOperation> projectManagers;

    /**
     * The TopCoder account managers.
     *
     * @since 1.3
     */
    private List<ProjectMetadataOperation> accountManagers;

    /**
     * The Appirio Managers.
     *
     * @since 1.4
     */
    private List<ProjectMetadataOperation> appirioManagers;

    /**
     * The budget of the project.
     */
    private ProjectMetadataOperation budget;

    /**
     * The planned duration of the project.
     */
    private ProjectMetadataOperation duration;

    /**
     * The svn address of the project.
     */
    private ProjectMetadataOperation svn;

    /**
     * The jira address of the project.
     */
    private ProjectMetadataOperation jira;

    /**
     * All the custom project metadata values.
     */
    private List<ProjectMetadataOperation> customMetadataValues;

    /**
     * The newly created custom metadata key.
     */
    private DirectProjectMetadataKey newCustomKey;
    
    /**
     * The privacy setting of the project.
     */
    private ProjectMetadataOperation privacy;

    /**
     * The project ratings of the project.
     * @since 1.1
     */
    private List<ProjectMetadataOperation> projectRatings;

    /**
     * The project type id.
     *
     * @since 1.2
     */
    private long projectTypeId;

    /**
     * The project category id.
     * @since 1.2
     */
    private long projectCategoryId;

    /**
     * The project permissions.
     * @since 1.2
     */
    private List<ProjectPermission> projectPermissions;

    /**
     * The project nofications.
     * @since 1.2
     */
    private List<ProjectNotificationSetting> projectNotifications;

    /**
     * The contests of which the timeline is turned on.
     * @since 1.2
     */
    private List<Long> contestsTimeline;

    /**
     * The contests of which the forum notification is turned on.
     * @since 1.2
     */
    private List<Long> contestsNotification;

    /**
     * The completion  date of the milestone.
     */
    private Date completionDate;

    /**
     * The user id.
     * @since 1.2
     */
    private long userId;

    /**
     * The billing account id to set.
     * @since 1.3
     */
    private long projectBillingAccountId;

    /**
     * Gets the name of the project.
     *
     * @return the name of the project.
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Sets the name of the project.
     *
     * @param projectName
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Gets the project description.
     *
     * @return the description of the project.
     */
    public String getProjectDescription() {
        return projectDescription;
    }

    /**
     * Sets the description of the project.
     *
     * @param projectDescription the description of the project.
     */
    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    /**
     * Gets the status id of the project.
     *
     * @return the status id of the project.
     */
    public long getProjectStatusId() {
        return projectStatusId;
    }

    /**
     * Sets the status id of the project.
     *
     * @param projectStatusId the status id of the project.
     */
    public void setProjectStatusId(long projectStatusId) {
        this.projectStatusId = projectStatusId;
    }

    /**
     * Gets the client managers of the project.
     *
     * @return the client managers of the project.
     */
    public List<ProjectMetadataOperation> getClientManagers() {
        return clientManagers;
    }

    /**
     * Sets the client managers of the project.
     *
     * @param clientManagers the client managers of the project.
     */
    public void setClientManagers(List<ProjectMetadataOperation> clientManagers) {
        this.clientManagers = clientManagers;
    }

    /**
     * Gets the TopCoder project managers of the project.
     *
     * @return the TopCoder project managers of the project.
     */
    public List<ProjectMetadataOperation> getProjectManagers() {
        return projectManagers;
    }

    /**
     * Sets the TopCoder project managers of the project.
     *
     * @param projectManagers the TopCoder project managers of the project.
     */
    public void setProjectManagers(List<ProjectMetadataOperation> projectManagers) {
        this.projectManagers = projectManagers;
    }

    /**
     * Gets TopCoder account managers.
     *
     * @return TopCoder account managers.
     * @since 1.3
     */
    public List<ProjectMetadataOperation> getAccountManagers() {
        return accountManagers;
    }

    /**
     * Sets TopCoder account managers.
     *
     * @param accountManagers TopCoder account managers.
     * @since 1.3
     */
    public void setAccountManagers(List<ProjectMetadataOperation> accountManagers) {
        this.accountManagers = accountManagers;
    }

    /**
     * Gets the Appirio Managers.
     *
     * @return the Appirio Managers.
     * @since 1.4
     */
    public List<ProjectMetadataOperation> getAppirioManagers() {
        return appirioManagers;
    }

    /**
     * Sets the Appirio Managers
     *
     * @param appirioManagers the Appirio Managers.
     * @since 1.4
     */
    public void setAppirioManagers(List<ProjectMetadataOperation> appirioManagers) {
        this.appirioManagers = appirioManagers;
    }

    /**
     * Gets the budget.
     *
     * @return the budget of the project.
     */
    public ProjectMetadataOperation getBudget() {
        return budget;
    }

    /**
     * Sets the budget.
     *
     * @param budget the budget of the project.
     */
    public void setBudget(ProjectMetadataOperation budget) {
        this.budget = budget;
    }

    /**
     * Gets the planned duration of the project.
     *
     * @return the planned duration of the project.
     */
    public ProjectMetadataOperation getDuration() {
        return duration;
    }

    /**
     * Sets the planned duration of the project.
     *
     * @param duration the planned duration of the project.
     */
    public void setDuration(ProjectMetadataOperation duration) {
        this.duration = duration;
    }

    /**
     * Gets the svn address of the project.
     *
     * @return the svn address of the project.
     */
    public ProjectMetadataOperation getSvn() {
        return svn;
    }

    /**
     * Sets the svn address of the project.
     *
     * @param svn the svn address of the project.
     */
    public void setSvn(ProjectMetadataOperation svn) {
        this.svn = svn;
    }

    /**
     * Gets the JIRA address of the project.
     *
     * @return the jira address of the project.
     */
    public ProjectMetadataOperation getJira() {
        return jira;
    }

    /**
     * Sets the JIRA address of the project.
     *
     * @param jira the jira address of the project.
     */
    public void setJira(ProjectMetadataOperation jira) {
        this.jira = jira;
    }

    /**
     * Gets the privacy setting of the project.
     *
     * @return the privacy setting of the project.
     */
    public ProjectMetadataOperation getPrivacy() {
        return privacy;
    }

    /**
     * Sets the privacy setting of the project.
     *
     * @param privacy the privacy setting of the project.
     */
    public void setPrivacy(ProjectMetadataOperation privacy) {
        this.privacy = privacy;
    }

    /**
     * Gets the new created custom metadata key.
     *
     * @return the new created custom metadata key.
     */
    public DirectProjectMetadataKey getNewCustomKey() {
        return newCustomKey;
    }

    /**
     * Sets the new created custom metadata key.
     *
     * @param newCustomKey the new created custom metadata key.
     */
    public void setNewCustomKey(DirectProjectMetadataKey newCustomKey) {
        this.newCustomKey = newCustomKey;
    }

    /**
     * Gets the custom metadata values.
     *
     * @return the custom metadata values.
     */
    public List<ProjectMetadataOperation> getCustomMetadataValues() {
        return customMetadataValues;
    }

    /**
     * Sets the custom metadata values.
     *
     * @param customMetadataValues the custom metadata values.
     */
    public void setCustomMetadataValues(List<ProjectMetadataOperation> customMetadataValues) {
        this.customMetadataValues = customMetadataValues;
    }

    /**
     * Gets the project ratings of the project.
     *
     * @return the project ratings of the project.
     * @since 1.1
     */
    public List<ProjectMetadataOperation> getProjectRatings() {
        return projectRatings;
    }

    /**
     * Sets the project ratings of the project.
     *
     * @param projectRatings the project ratings of the project.
     * @since 1.1
     */
    public void setProjectRatings(List<ProjectMetadataOperation> projectRatings) {
        this.projectRatings = projectRatings;
    }

    /**
     * Gets the project type id.
     *
     * @return the project type id.
     * @since 1.2
     */
    public long getProjectTypeId() {
        return projectTypeId;
    }

    /**
     * Sets the project type id.
     *
     * @param projectTypeId the project type id.
     * @since 1.2
     */
    public void setProjectTypeId(long projectTypeId) {
        this.projectTypeId = projectTypeId;
    }

    /**
     * Gets the project category id.
     *
     * @return the project category id.
     * @since 1.2
     */
    public long getProjectCategoryId() {
        return projectCategoryId;
    }

    /**
     * Sets the project category id.
     *
     * @param projectCategoryId the project category id.
     * @since 1.2
     */
    public void setProjectCategoryId(long projectCategoryId) {
        this.projectCategoryId = projectCategoryId;
    }

    /**
     * Gets the project permissions.
     *
     * @return the project permissions.
     * @since 1.2
     */
    public List<ProjectPermission> getProjectPermissions() {
        return projectPermissions;
    }

    /**
     * Sets the project permission.
     *
     * @param projectPermissions the project permissions.
     * @since 1.2
     */
    public void setProjectPermissions(List<ProjectPermission> projectPermissions) {
        this.projectPermissions = projectPermissions;
    }

    /**
     * Gets the project notifications.
     *
     * @return the project notifications.
     * @since 1.2
     */
    public List<ProjectNotificationSetting> getProjectNotifications() {
        return projectNotifications;
    }

    /**
     * Sets the project notifications.
     *
     * @param projectNotifications the project notifications.
     * @since 1.2
     */
    public void setProjectNotifications(List<ProjectNotificationSetting> projectNotifications) {
        this.projectNotifications = projectNotifications;
    }

    /**
     * Gets the contest ids of which the timeline notification is turned on.
     *
     * @return the contest ids of which the timeline notification is turned on.
     * @since 1.2
     */
    public List<Long> getContestsTimeline() {
        return contestsTimeline;
    }

    /**
     * Sets the contest ids of which the timeline notification is turned on.
     *
     * @param contestsTimeline the contest ids of which the timeline notification is turned on.
     * @since 1.2
     */
    public void setContestsTimeline(List<Long> contestsTimeline) {
        this.contestsTimeline = contestsTimeline;
    }

    /**
     * Gets the contest ids of which the forum notification is turned on.
     *
     * @return the contest ids of which the forum notification is turned on.
     * @since 1.2
     */
    public List<Long> getContestsNotification() {
        return contestsNotification;
    }

    /**
     * Sets the contest ids of which the forum notification is turned on.
     *
     * @param contestsNotification the contest ids of which the forum notification is turned on.
     * @since 1.2
     */
    public void setContestsNotification(List<Long> contestsNotification) {
        this.contestsNotification = contestsNotification;
    }

    /**
     * Gets the user id.
     *
     * @return the user id.
     * @since 1.2
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId the user id.
     * @since 1.2
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Gets the billing account id to set.
     *
     * @return the billing account id to set.
     * @since 1.3
     */
    public long getProjectBillingAccountId() {
        return projectBillingAccountId;
    }

    /**
     * Sets the billing account id of the project.
     *
     * @param projectBillingAccountId the billing account id of the project.
     * @since 1.3
     */
    public void setProjectBillingAccountId(long projectBillingAccountId) {
        this.projectBillingAccountId = projectBillingAccountId;
    }
    
    /**
     * Gets the compeltion date of the milestone.
     * 
     * @return the completion date of the milestone.
     */
    public Date getCompletionDate() {
        return completionDate;
    }

    /**
     *Sets the completion date of the milestone.
     *
     *@param completionDate the completion date of the milestone.
     */
    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }
}
