/*
 * Copyright (C) 2012 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project;


import com.topcoder.service.project.ProjectData;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * <p>
 *  This class is the DTO to store the general project information such as budget, planned duration, actual cost etc.
 * </p>
 *
 * <p>
 *     Version 1.1 (Release Assembly - TC Cockpit Edit Project and Project General Info Update) change notes:
 *     - Add properties for projected cost, projected duration, 5 project ratings and additional project info.
 * </p>
 *
 * <p>
 *     Version 1.1.1 (Module Assembly - TC Cockpit Direct Project Related Services Update and Integration)
 *     - Add the status label for draft status.
 * </p>
 *
 * <p>
 *     Version 1.2 (Release Assembly - TopCoder Cockpit Project Dashboard Project Type and Permission Notifications Integration)
 *     - Add the property <code>permissionInfo</code>
 *     - Add the property <code>canAccessPermissionEdit</code>
 * </p>
 *
 * <p>
 *     Version 1.3 (Release Assembly - TopCoder Direct Cockpit Release Assembly Ten)
 *     - Add the property <code>accountManagers</code>
 *     - Add the property <code>projectResourceEmails</code>
 *     - Add the property <code>accountManagersHandles</code>
 * </p>
 *
 * <p>
 *     Version 1.4 (Release Assembly - TopCoder Cockpit Copilot Selection Update and Other Fixes Assembly)
 *     <ol>
 *         <li>Update topcoderManagerEmails to proejctResourcesEmails</li>
 *     </ol>
 * </p>
 * 
 * <p>
 * Version 1.5 - Topcoder - Remove JIRA Issues Related Functionality In Direct App v1.0
 * - remove JIRA related functionality
 * </p>
 *
 * @version 1.5 
 * @author GreatKevin, TCCoder 
 */
public class ProjectGeneralInfoDTO {

    /**
     * The project data object.
     */
    private ProjectData project;

    /**
     * The project permission info.
     * @since 1.2
     */
    private ProjectPermissionInfoDTO permissionInfo;

    /**
     * The svn link of the project.
     */
    private String svn;

    /**
     * The total budget of the project.
     */
    private Integer totalBudget;

    /**
     * The actual cost of the project.
     */
    private Integer actualCost;

    /**
     * The projected cost of the project.
     * @since 1.1
     */
    private Integer projectedCost;

    /**
     * The planned duration of the project.
     */
    private Integer plannedDuration;

    /**
     * The actual duration of the project.
     */
    private Integer actualDuration;

    /**
     * The projected duration of the project.
     * @since 1.1
     */
    private Integer projectedDuration;

    /**
     * The client managers of the project.
     */
    private List<Long> clientManagers = new ArrayList<Long>();

    /**
     * The TopCoder managers of the project.
     */
    private List<Long> topcoderManagers = new ArrayList<Long>();

    /**
     * The TopCoder account managers of the project.
     *
     * @since 1.3
     */
    private List<Long> accountManagers = new ArrayList<Long>();

    /**
     * The TopCoder account managers of the project.
     *
     * @since 1.3
     */
    private Long appirioManager;

    /**
     * The TopCoder platform specialists emails.
     *
     * @since 1.3
     */
    private Map<Long, String> projectResourceEmails = new HashMap<Long, String>();

    /**
     * The client managers handles map.
     */
    private Map<Long, String> clientManagersHandles = new HashMap<Long, String>();

    /**
     * The topcoder managers handles map.
     */
    private Map<Long, String> topcoderManagersHandles = new HashMap<Long, String>();

    /**
     * The TopCoder account managers handles map.
     *
     * @since 1.3
     */
    private Map<Long, String> accountManagersHandles = new HashMap<Long, String>();

    /**
     * The project copilots handle.
     */
    private Map<Long, String> copilotHandles = new HashMap<Long, String>();

    /**
     * The business impact rating of project.
     * @since 1.1
     */
    private Integer businessImpactRating;

    /**
     * The risk level rating of the project.
     * @since 1.1
     */
    private Integer riskLevelRating;

    /**
     * The cost rating of the project.
     * @since 1.1
     */
    private Integer costRating;

    /**
     * The difficulty rating of the project.
     * @since 1.1
     */
    private Integer difficultyRating;

    /**
     * The ROI rating of the project.
     * @since 1.1
     */
    private Integer roiRating;

    /**
     * The additional project info.
     * @since 1.1
     */
    private Map<String, List<String>> additionalProjectInfo;

    /**
     * The flag of can access the permission edit link.
     * @since 1.2
     */
    private boolean canAccessPermissionEdit;

    /**
     * Gets the label for the project status.
     *
     * @return the label for the project status.
     */
    public String getStatusLabel() {
        if (project.getProjectStatusId() == 1L) {
            return "Active";
        } else if (project.getProjectStatusId() == 2L) {
            return "Archived";
        } else if (project.getProjectStatusId() == 3L) {
            return "Cancelled";
        } else if (project.getProjectStatusId() == 4L) {
            return "Completed";
        } else if (project.getProjectStatusId() == 5L) {
            return "Draft";
        }

        return "unknown";
    }

    /**
     * Gets the <code>ProjectData</code> object.
     *
     * @return the <code>ProjectData</code> object.
     */
    public ProjectData getProject() {
        return project;
    }

    /**
     * Sets the <code>ProjectData</code> object.
     *
     * @param project the <code>ProjectData</code> to set.
     */
    public void setProject(ProjectData project) {
        this.project = project;
    }

    /**
     * Gets the svn link of the project.
     *
     * @return the svn link of the project.
     */
    public String getSvn() {
        return svn;
    }

    /**
     * Sets the svn link of the project.
     *
     * @param svn the svn link of the project.
     */
    public void setSvn(String svn) {
        this.svn = svn;
    }

    /**
     * Gets the total budget of the project.
     *
     * @return the total budget of the project.
     */
    public Integer getTotalBudget() {
        return totalBudget;
    }

    /**
     * Sets the total budget of the project.
     *
     * @param totalBudget the total budget of the project.
     */
    public void setTotalBudget(Integer totalBudget) {
        this.totalBudget = totalBudget;
    }

    /**
     * Gets the actual cost of the project.
     *
     * @return the actual cost of the project.
     */
    public Integer getActualCost() {
        return actualCost;
    }

    /**
     * Sets the actual cost of the project.
     *
     * @param actualCost the actual cost of the project.
     */
    public void setActualCost(Integer actualCost) {
        this.actualCost = actualCost;
    }

    /**
     * Gets the planned duration of the project.
     *
     * @return the planned duration of the project.
     */
    public Integer getPlannedDuration() {
        return plannedDuration;
    }

    /**
     * Sets the planned duration of the project.
     *
     * @param plannedDuration the planned duration of the project.
     */
    public void setPlannedDuration(Integer plannedDuration) {
        this.plannedDuration = plannedDuration;
    }

    /**
     * Gets the actual duration of the project.
     *
     * @return the actual duration of the project.
     */
    public Integer getActualDuration() {
        return actualDuration;
    }

    /**
     * Sets the actual duration of the project.
     *
     * @param actualDuration the actual duration of the project.
     */
    public void setActualDuration(Integer actualDuration) {
        this.actualDuration = actualDuration;
    }

    /**
     * Gets the client managers of the project.
     *
     * @return the client managers of the project.
     */
    public List<Long> getClientManagers() {
        return clientManagers;
    }

    /**
     * Sets the client managers of the project.
     *
     * @param clientManagers the client managers of the project.
     */
    public void setClientManagers(List<Long> clientManagers) {
        this.clientManagers = clientManagers;
    }

    /**
     * Gets the TopCoder managers of the project.
     *
     * @return the TopCoder managers of the project.
     */
    public List<Long> getTopcoderManagers() {
        return topcoderManagers;
    }

    /**
     * Sets the TopCoder managers of the project.
     *
     * @param topcoderManagers the TopCoder managers of the project.
     */
    public void setTopcoderManagers(List<Long> topcoderManagers) {
        this.topcoderManagers = topcoderManagers;
    }

    /**
     * Gets the appirio manager.
     *
     * @return the appirio manager
     * @since 1.3
     */
    public Long getAppirioManager() {
        return appirioManager;
    }

    /**
     * Sets the appirio manager
     *
     * @param appirioManager the appirio manager.
     * @since 1.3
     */
    public void setAppirioManager(Long appirioManager) {
        this.appirioManager = appirioManager;
    }

    /**
     * Gets the account managers.
     *
     * @return the account managers
     * @since 1.3
     */
    public List<Long> getAccountManagers() {
        return accountManagers;
    }

    /**
     * Sets the account managers.
     *
     * @param accountManagers the account managers.
     * @since 1.3
     */
    public void setAccountManagers(List<Long> accountManagers) {
        this.accountManagers = accountManagers;
    }

    /**
     * Gets the TopCoder platform specialists emails.
     *
     * @return the TopCoder platform specialists emails.
     * @since 1.3
     */
    public Map<Long, String> getProjectResourceEmails() {
        return projectResourceEmails;
    }

    /**
     * Sets the TopCoder platform specialists emails.
     *
     * @param projectResourceEmails the TopCoder platform specialists emails.
     * @since 1.3
     */
    public void setProjectResourceEmails(Map<Long, String> projectResourceEmails) {
        this.projectResourceEmails = projectResourceEmails;
    }

    /**
     * Gets the business impact rating.
     *
     * @return the business impact rating.
     * @since 1.1
     */
    public Integer getBusinessImpactRating() {
        return businessImpactRating;
    }

    /**
     * Sets the business impact rating.
     *
     * @param businessImpactRating the business impact rating.
     * @since 1.1
     */
    public void setBusinessImpactRating(Integer businessImpactRating) {
        this.businessImpactRating = businessImpactRating;
    }

    /**
     * Gets the risk level rating.
     *
     * @return the risk level rating.
     * @since 1.1
     */
    public Integer getRiskLevelRating() {
        return riskLevelRating;
    }

    /**
     * Sets the risk level rating.
     *
     * @param riskLevelRating the risk level rating.
     * @since 1.1
     */
    public void setRiskLevelRating(Integer riskLevelRating) {
        this.riskLevelRating = riskLevelRating;
    }

    /**
     * Gets the cost rating.
     *
     * @return the cost rating.
     * @since 1.1
     */
    public Integer getCostRating() {
        return costRating;
    }

    /**
     * Sets the cost rating.
     *
     * @param costRating the cost rating.
     * @since 1.1
     */
    public void setCostRating(Integer costRating) {
        this.costRating = costRating;
    }

    /**
     * Gets the difficulty rating.
     *
     * @return the difficulty rating.
     * @since 1.1
     */
    public Integer getDifficultyRating() {
        return difficultyRating;
    }

    /**
     * Sets difficulty rating.
     *
     * @param difficultyRating the difficulty rating.
     * @since 1.1
     */
    public void setDifficultyRating(Integer difficultyRating) {
        this.difficultyRating = difficultyRating;
    }

    /**
     * Gets the ROI rating.
     *
     * @return the ROI rating of project.
     * @since 1.1
     */
    public Integer getRoiRating() {
        return roiRating;
    }

    /**
     * Sets the ROI rating.
     *
     * @param roiRating the ROI rating of project.
     * @since 1.1
     */
    public void setRoiRating(Integer roiRating) {
        this.roiRating = roiRating;
    }

    /**
     * Gets the additional project info.
     *
     * @return the additional project info.
     * @since 1.1
     */
    public Map<String, List<String>> getAdditionalProjectInfo() {
        return additionalProjectInfo;
    }

    /**
     * Sets the additional project info.
     *
     * @param additionalProjectInfo the additional project info.
     * @since 1.1
     */
    public void setAdditionalProjectInfo(Map<String, List<String>> additionalProjectInfo) {
        this.additionalProjectInfo = additionalProjectInfo;
    }

    /**
     * Gets the project cost.
     *
     * @return the project cost.
     * @since 1.1
     */
    public Integer getProjectedCost() {
        return projectedCost;
    }

    /**
     * Sets the projected cost.
     *
     * @param projectedCost the projected cost.
     * @since 1.1
     */
    public void setProjectedCost(Integer projectedCost) {
        this.projectedCost = projectedCost;
    }

    /**
     * Gets the projected duration.
     *
     * @return the projected duration.
     * @since 1.1
     */
    public Integer getProjectedDuration() {
        return projectedDuration;
    }

    /**
     * Sets the projected duration.
     *
     * @param projectedDuration the projected duration.
     * @since 1.1
     */
    public void setProjectedDuration(Integer projectedDuration) {
        this.projectedDuration = projectedDuration;
    }

    /**
     * Gets the handles of the client managers.
     *
     * @return the handles of the client managers.
     */
    public Map<Long, String> getClientManagersHandles() {
        return clientManagersHandles;
    }

    /**
     * Sets the handles of the client managers.
     *
     * @param clientManagersHandles the handles of the client managers.
     */
    public void setClientManagersHandles(Map<Long, String> clientManagersHandles) {
        this.clientManagersHandles = clientManagersHandles;
    }

    /**
     * Gets the handles of the TopCoder platform specialists.
     *
     * @return the handles of the TopCoder platform specialists.
     */
    public Map<Long, String> getTopcoderManagersHandles() {
        return topcoderManagersHandles;
    }

    /**
     * Sets the handles of the TopCoder platform specialists.
     *
     * @param topcoderManagersHandles the handles of the TopCoder platform specialists.
     */
    public void setTopcoderManagersHandles(Map<Long, String> topcoderManagersHandles) {
        this.topcoderManagersHandles = topcoderManagersHandles;
    }

    /**
     * Gets the handles of the TopCoder Account managers.
     *
     * @return the handles of the TopCoder Account managers.
     *
     * @since 1.3
     *
     */
    public Map<Long, String> getAccountManagersHandles() {
        return accountManagersHandles;
    }

    /**
     * Sets the handles of the TopCoder Account managers.
     *
     * @param accountManagersHandles the handles of the TopCoder Account managers.
     * @since 1.3
     */
    public void setAccountManagersHandles(Map<Long, String> accountManagersHandles) {
        this.accountManagersHandles = accountManagersHandles;
    }

    /**
     * Gets the handles of the copilots
     *
     * @return the handles of the copilots
     * @since 1.3
     */
    public Map<Long, String> getCopilotHandles() {
        return copilotHandles;
    }

    /**
     * Sets the handles of the copilots.
     *
     * @param copilotHandles the handles of the copilots.
     * @since 1.3
     */
    public void setCopilotHandles(Map<Long, String> copilotHandles) {
        this.copilotHandles = copilotHandles;
    }

    /**
     * Gets the project permission info.
     *
     * @return the project permission info.
     * @since 1.2
     */
    public ProjectPermissionInfoDTO getPermissionInfo() {
        return permissionInfo;
    }

    /**
     * Sets the project permission info.
     *
     * @param permissionInfo the project permission info.
     * @since 1.2
     */
    public void setPermissionInfo(ProjectPermissionInfoDTO permissionInfo) {
        this.permissionInfo = permissionInfo;
    }

    /**
     * Gets the flag of can access permission edit link.
     *
     * @return the flag of can access permission edit link.
     * @since 1.2
     */
    public boolean isCanAccessPermissionEdit() {
        return canAccessPermissionEdit;
    }

    /**
     * Sets the flag of can access permission edit link.
     *
     * @param canAccessPermissionEdit the flag of can access permission edit link.
     * @since 1.2
     */
    public void setCanAccessPermissionEdit(boolean canAccessPermissionEdit) {
        this.canAccessPermissionEdit = canAccessPermissionEdit;
    }
}
