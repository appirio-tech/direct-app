/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.contest;

import com.topcoder.service.project.ProjectCategory;
import com.topcoder.service.project.ProjectType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project summary data object to hold project data for each status.
 *
 * <p>
 *     Version 1.1 changes: add the property directProjectStatusId into the ProjectSummaryData.
 * </p>
 *
 * <p>
 *     Version 1.2 Release Assembly - TopCoder Cockpit DataTables Filter Panel and Search Bar changes:
 *     - Add property {@link #projectCreationDate}, {@link #customerId} and {@link #customerName}
 * </p>
 *
 * <p>
 *     Version 1.3 Release Assembly - TC Cockpit All Projects Management Page Update:
 *     - Add property {@link #projectForumCategoryId}
 * </p>
 *
 * <p>
 *       Version 1.4 (Module Assembly - TC Cockpit Direct Project Related Services Update and Integration)
 *       - Add properties {@link #projectCompletionDate}, {@link #directProjectType} and {@link #directProjectCategory}
 * </p>
 *
 * <p>
 *       Version 1.5 (Module Assembly - TC Cockpit Operations Dashboard For PMs)
 *       - Add properties {@link #projectFulfillment}, {@link #totalBudget}, {@link #actualCost},
 *       {@link #projectedCost}, {@link #plannedDuration}, {@link #actualDuration}, {@link #projectedDuration},
 *       {@link #messageNumber}, {@link #daysSinceLastPost}, {@link #lastPostHandle}, {@link #lastPostHandleId},
 *       {@link #hasStalledContests}
 * </p>
 *
 * <p>
 *       Version 1.5 (Release Assembly - TopCoder Security Groups Release 5)
 *       - Add property {@link #hasWritePermission}.
 * </p>
 *
 * <p>
 *     Version 1.6 (Release Assembly - TC Direct Cockpit Release Eight)
 *     <ul>
 *         <li>Add the property {@link #hasLateContests}</li>
 *     </ul>
 *       Version 1.7 (Module Assembly - TC Cockpit Operations Dashboard Bug Fix and Improvements 1)
 *       - Delete properties {@link #daysSinceLastPost}, {@link #lastPostHandle}, {@link #lastPostHandleId}
 *       - Add property {@link #latestThreePosters}
 * </p>
 *
 * <p>
 *       Version 1.8 (Release Assembly - TC Cockpit Operations Dashboard Improvements 2)
 *       - Add property {@link #phaseLateContestsNum}.
 *       - Add property {@link #launchLateContestsNum}.
 *       - Add property {@link #checkpointLateContestsNum}.
 *       - Add property {@link #stalledContestsNum}.
 *       - Add property {@link #apOffContestsNum}.
 *       - Add property {@link #historicalProjectedCost}.
 * </p>
 *
 * <p>
 * Version 1.9 (Release Assembly - TopCoder Cockpit Operations Dashboard Improvements 4 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #copilotNames} property.</li>
 *     <li>Added {@link #launchLateDelay} property.</li>
 *     <li>Added {@link #latePhaseDelay} property.</li>
 *     <li>Added {@link #activeContestsCount} property.</li>
 *   </ol>
 * </p>
 *
 * @author BeBetter, GreatKevin, tangzx, TCSDEVELOPER
 * @version 1.9
 */
public class ProjectSummaryData implements Serializable {
    /**
     * Serial id.
     */
    private static final long serialVersionUID = 5601619496370424382L;

    /**
     * <p>
     * The projectId field.
     * </p>
     */
    private Long projectId;

    /**
     * <p>
     * The projectName field.
     * </p>
     */
    private String projectName;

    /**
     * The direct project status id
     * @since 1.1
     */
    private Long directProjectStatusId;


    /**
     * The project creation date.
     * @since 1.2
     */
    private Date projectCreationDate;

    /**
     * The project compeltion date.
     * @since 1.4
     */
    private Date projectCompletionDate;

    /**
     * The id of the customer.
     * @since 1.2
     */
    private long customerId;

    /**
     * The name of the customer
     * @since 1.2
     */
    private String customerName;

    /**
     * The category id of the project forum.
     * @since 1.3
     */
    private long projectForumCategoryId;

    /**
     * The project type.
     * @since 1.4
     */
    private ProjectType directProjectType;

    /**
     * The project category
     * @since .14
     */
    private ProjectCategory directProjectCategory;

    /**
     * Represents whether the user has write permission on this project.
     * @since 1.5
     */
    private boolean hasWritePermission;

    /**
     * <p>
     * The draft field.
     * </p>
     */
    private ProjectStatusData draft = new ProjectStatusData();

    /**
     * <p>
     * The scheduled field.
     * </p>
     */
    private ProjectStatusData scheduled = new ProjectStatusData();

    /**
     * <p>
     * The active field.
     * </p>
     */
    private ProjectStatusData active = new ProjectStatusData();

    /**
     * <p>
     * The finished field.
     * </p>
     */
    private ProjectStatusData finished = new ProjectStatusData();

    /**
     * <p>
     * The cancelled field.
     * </p>
     */
    private ProjectStatusData cancelled = new ProjectStatusData();

    /**
     * The project fulfillment.
     * @since 1.5
     */
    private double projectFulfillment;

    /**
     * The total budget, unit (US$)
     * @since 1.5
     */
    private String totalBudget;

    /**
     * The actual cost, unit (US$)
     * @since 1.5
     */
    private double actualCost;

    /**
     * The projected cost, unit (US$)
     * @since 1.5
     */
    private double projectedCost;

    /**
     * The planned duration of the project, unit (days)
     * @since 1.5
     */
    private String plannedDuration;

    /**
     * The actual duration of the project, unit (days)
     * @since 1.5
     */
    private String actualDuration;

    /**
     * The projected duration of the project, unit (days)
     * @since 1.5
     */
    private String projectedDuration;

    /**
     * <p>A <code>long</code> providing the number of messages in forum.</p>
     * @since 1.5
     */
    private long messageNumber;

    /**
     * The latest three forum poster.
     * @since 1.6
     */
    private List<ForumPoster> latestThreePosters = new ArrayList<ForumPoster>();
    /**
     * The has stalled contests.
     * @since 1.5
     */
    private Boolean hasStalledContests;

    /**
     * The has late contests flag.
     * @since 1.6
     */
    private Boolean hasLateContests;

    /**
     * The project manager name.
     * @since 1.6
     */
    private String projectManagerName;

    /**
     * The number of phase late contests.
     *
     * @since 1.8
     */
    private int phaseLateContestsNum;

    /**
     * The number of launch late contests.
     *
     * @since 1.8
     */
    private int launchLateContestsNum;

    /**
     * The number of checkpoint late contests.
     *
     * @since 1.8
     */
    private int checkpointLateContestsNum;

    /**
     * The number of stalled contests.
     *
     * @since 1.8
     */
    private int stalledContestsNum;

    /**
     * The number of ap off contests.
     *
     * @since 1.8
     */
    private int apOffContestsNum;

    /**
     * The historical projected cost.
     *
     * @since 1.8
     */
    private double historicalProjectedCost;

    /**
     * <p>A <code>String</code> providing the comma-separated list of handles for copilots associated with TC Direct
     * project.</p>
     * 
     * @since 1.9
     */
    private String copilotNames;

    /**
     * <p>A <code>Long</code> providing the maximum delay in launching the contests for TC Direct project (in hours).</p>
     * 
     * @since 1.9
     */
    private Long launchLateDelay;

    /**
     * <p>A <code>Double</code> providing the maximum delay for running phase for the contests associated with TC Direct
     * projects (in hours).</p>
     * 
     * @since 1.9
     */
    private Double latePhaseDelay;

    /**
     * <p>A <code>Map</code> providing the mapping from the contest category names to number of active contests for TC
     * Direct project.</p>
     * 
     * @since 1.9
     */
    private Map<String, Integer> activeContestsCount;

    /**
     * Gets the projectManagerName.
     * @return the projectManagerName
     * @since 1.6
     */
    public String getProjectManagerName() {
        return projectManagerName;
    }

    /**
     * Sets the projectManagerName.
     * @param projectManagerName the projectManagerName to set
     * @since 1.6
     */
    public void setProjectManagerName(String projectManagerName) {
        this.projectManagerName = projectManagerName;
    }

    /**
     * <p>
     * Sets the <code>projectId</code> field value.
     * </p>
     *
     * @param projectId the value to set
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * <p>
     * Gets the <code>projectId</code> field value.
     * </p>
     *
     * @return the <code>projectId</code> field value
     */
    public Long getProjectId() {
        return this.projectId;
    }

    /**
     * <p>
     * Sets the <code>projectName</code> field value.
     * </p>
     *
     * @param projectName the value to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * <p>
     * Gets the <code>projectName</code> field value.
     * </p>
     *
     * @return the <code>projectName</code> field value
     */
    public String getProjectName() {
        return this.projectName;
    }

    /**
     * <p>
     * Sets the <code>draft</code> field value.
     * </p>
     *
     * @param draft the value to set
     */
    public void setDraft(ProjectStatusData draft) {
        this.draft = draft;
    }

    /**
     * <p>
     * Gets the <code>draft</code> field value.
     * </p>
     *
     * @return the <code>draft</code> field value
     */
    public ProjectStatusData getDraft() {
        return this.draft;
    }

    /**
     * <p>
     * Sets the <code>scheduled</code> field value.
     * </p>
     *
     * @param scheduled the value to set
     */
    public void setScheduled(ProjectStatusData scheduled) {
        this.scheduled = scheduled;
    }

    /**
     * <p>
     * Gets the <code>scheduled</code> field value.
     * </p>
     *
     * @return the <code>scheduled</code> field value
     */
    public ProjectStatusData getScheduled() {
        return this.scheduled;
    }

    /**
     * <p>
     * Sets the <code>active</code> field value.
     * </p>
     *
     * @param active the value to set
     */
    public void setActive(ProjectStatusData active) {
        this.active = active;
    }

    /**
     * <p>
     * Gets the <code>active</code> field value.
     * </p>
     *
     * @return the <code>active</code> field value
     */
    public ProjectStatusData getActive() {
        return this.active;
    }

    /**
     * <p>
     * Sets the <code>finished</code> field value.
     * </p>
     *
     * @param finished the value to set
     */
    public void setFinished(ProjectStatusData finished) {
        this.finished = finished;
    }

    /**
     * <p>
     * Gets the <code>finished</code> field value.
     * </p>
     *
     * @return the <code>finished</code> field value
     */
    public ProjectStatusData getFinished() {
        return this.finished;
    }

    /**
     * <p>
     * Sets the <code>cancelled</code> field value.
     * </p>
     *
     * @param cancelled the value to set
     */
    public void setCancelled(ProjectStatusData cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * <p>
     * Gets the <code>cancelled</code> field value.
     * </p>
     *
     * @return the <code>cancelled</code> field value
     */
    public ProjectStatusData getCancelled() {
        return this.cancelled;
    }

    /**
     * Gets the direct project status id.
     *
     * @return the direct project status id.
     * @since 1.1
     */
    public Long getDirectProjectStatusId() {
        return directProjectStatusId;
    }

    /**
     * Sets the direct project status id.
     *
     * @param directProjectStatusId the direct project status id.
     * @since 1.1
     */
    public void setDirectProjectStatusId(Long directProjectStatusId) {
        this.directProjectStatusId = directProjectStatusId;
    }

    /**
     * Gets the project creation date.
     *
     * @return the project creation date.
     * @since 1.2
     */
    public Date getProjectCreationDate() {
        return projectCreationDate;
    }

    /**
     * Sets the project creation date.
     *
     * @param projectCreationDate the project creation date.
     * @since 1.2
     */
    public void setProjectCreationDate(Date projectCreationDate) {
        this.projectCreationDate = projectCreationDate;
    }

    /**
     * Gets the id of the project customer.
     *
     * @return the id of the project customer.
     * @since 1.2
     */
    public long getCustomerId() {
        return customerId;
    }

    /**
     * Sets the id of the project customer.
     *
     * @param customerId the id of the project customer.
     */
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the name of the project customer.
     *
     * @return the name of the project customer.
     * @since 1.2
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the name of the project customer.
     *
     * @param customerName the name of the project customer.
     * @since 1.2
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets the forum category id of the project.
     *
     * @return the forum category id of the project.
     * @since 1.3
     */
    public long getProjectForumCategoryId() {
        return projectForumCategoryId;
    }

    /**
     * Sets the forum category id of the project.
     *
     * @param projectForumCategoryId the forum category id of the project.
     * @since 1.3
     */
    public void setProjectForumCategoryId(long projectForumCategoryId) {
        this.projectForumCategoryId = projectForumCategoryId;
    }

    /**
     * Gets the project completion date.
     *
     * @return the project completion date.
     * @since 1.4
     */
    public Date getProjectCompletionDate() {
        return projectCompletionDate;
    }

    /**
     * Sets the project completion date.
     *
     * @param projectCompletionDate the project completion date.
     * @since 1.4
     */
    public void setProjectCompletionDate(Date projectCompletionDate) {
        this.projectCompletionDate = projectCompletionDate;
    }

    /**
     * Gets the project type.
     *
     * @return the project type.
     * @since 1.4
     */
    public ProjectType getDirectProjectType() {
        return directProjectType;
    }

    /**
     * Sets the project type.
     *
     * @param directProjectType the direct project type.
     * @since 1.4
     */
    public void setDirectProjectType(ProjectType directProjectType) {
        this.directProjectType = directProjectType;
    }

    /**
     * Gets the project category.
     *
     * @return the project category.
     * @since 1.4
     */
    public ProjectCategory getDirectProjectCategory() {
        return directProjectCategory;
    }

    /**
     * Sets the project category.
     *
     * @param directProjectCategory the project category.
     * @since 1.4
     */
    public void setDirectProjectCategory(ProjectCategory directProjectCategory) {
        this.directProjectCategory = directProjectCategory;
    }

    /**
     * Gets the project fulfillment.
     *
     * @return the project fulfillment
     * @since 1.5
     */
    public double getProjectFulfillment() {
        return projectFulfillment;
    }

    /**
     * Sets the project fulfillment.
     *
     * @param projectFulfillment the new project fulfillment
     * @since 1.5
     */
    public void setProjectFulfillment(double projectFulfillment) {
        this.projectFulfillment = projectFulfillment;
    }

    /**
     * Gets the total budget.
     *
     * @return the total budget
     * @since 1.5
     */
    public String getTotalBudget() {
        return totalBudget;
    }

    /**
     * Sets the total budget.
     *
     * @param totalBudget the new total budget
     * @since 1.5
     */
    public void setTotalBudget(String totalBudget) {
        this.totalBudget = totalBudget;
    }

    /**
     * Gets the actual cost.
     *
     * @return the actual cost
     * @since 1.5
     */
    public double getActualCost() {
        return actualCost;
    }

    /**
     * Sets the actual cost.
     *
     * @param actualCost the new actual cost
     * @since 1.5
     */
    public void setActualCost(double actualCost) {
        this.actualCost = actualCost;
    }

    /**
     * Gets the projected cost.
     *
     * @return the projected cost
     * @since 1.5
     */
    public double getProjectedCost() {
        return projectedCost;
    }

    /**
     * Sets the projected cost.
     *
     * @param projectedCost the new projected cost
     * @since 1.5
     */
    public void setProjectedCost(double projectedCost) {
        this.projectedCost = projectedCost;
    }

    /**
     * Gets the planned duration.
     *
     * @return the planned duration
     * @since 1.5
     */
    public String getPlannedDuration() {
        return plannedDuration;
    }

    /**
     * Sets the planned duration.
     *
     * @param plannedDuration the new planned duration
     * @since 1.5
     */
    public void setPlannedDuration(String plannedDuration) {
        this.plannedDuration = plannedDuration;
    }

    /**
     * Gets the actual duration.
     *
     * @return the actual duration
     * @since 1.5
     */
    public String getActualDuration() {
        return actualDuration;
    }

    /**
     * Sets the actual duration.
     *
     * @param actualDuration the new actual duration
     * @since 1.5
     */
    public void setActualDuration(String actualDuration) {
        this.actualDuration = actualDuration;
    }

    /**
     * Gets the projected duration.
     *
     * @return the projected duration
     * @since 1.5
     */
    public String getProjectedDuration() {
        return projectedDuration;
    }

    /**
     * Sets the projected duration.
     *
     * @param projectedDuration the new projected duration
     * @since 1.5
     */
    public void setProjectedDuration(String projectedDuration) {
        this.projectedDuration = projectedDuration;
    }

    /**
     * Gets the message number.
     *
     * @return the message number
     * @since 1.5
     */
    public long getMessageNumber() {
        return messageNumber;
    }

    /**
     * Sets the message number.
     *
     * @param messageNumber the new message number
     * @since 1.5
     */
    public void setMessageNumber(long messageNumber) {
        this.messageNumber = messageNumber;
    }


    /**
     * Gets the latestThreePosters.
     * @return the latestThreePosters
     * @since 1.6
     */
    public List<ForumPoster> getLatestThreePosters() {
        return latestThreePosters;
    }

    /**
     * Sets the latestThreePosters.
     * @param latestThreePosters the latestThreePosters to set
     * @since 1.6
     */
    public void setLatestThreePosters(List<ForumPoster> latestThreePosters) {
        this.latestThreePosters = latestThreePosters;
    }

    /**
     * Gets the checks for stalled contests.
     *
     * @return the checks for stalled contests
     * @since 1.5
     */
    public Boolean getHasStalledContests() {
        return hasStalledContests;
    }

    /**
     * Sets the checks for stalled contests.
     *
     * @param hasStalledContests the new checks for stalled contests
     * @since 1.5
     */
    public void setHasStalledContests(Boolean hasStalledContests) {
        this.hasStalledContests = hasStalledContests;
    }

    /**
     * Gets the has late contests flag.
     *
     * @return has late contests flag.
     * @since 1.6
     */
    public Boolean getHasLateContests() {
        return hasLateContests;
    }

    /**
     * Sets the has late contests flag.
     *
     * @param hasLateContests has late contests flag.
     * @since 1.6
     */
    public void setHasLateContests(Boolean hasLateContests) {
        this.hasLateContests = hasLateContests;
    }

    /**
     * Whether the user has write permission on this project.
     *
     * @return the hasWritePermission true if the user has write permission on this project, false otherwise.
     * @since 1.5
     */
    public boolean isHasWritePermission() {
        return hasWritePermission;
    }

    /**
     * Sets whether the user has write permission on this project.
     *
     * @param hasWritePermission true if the user has write permission on this project, false otherwise.
     * @since 1.5
     */
    public void setHasWritePermission(boolean hasWritePermission) {
        this.hasWritePermission = hasWritePermission;
    }

    /**
     * Get the number of phase late contests.
     *
     * @return the number
     * @since 1.8
     */
    public int getPhaseLateContestsNum() {
        return phaseLateContestsNum;
    }

    /**
     * Set number of phase late contests.
     *
     * @param phaseLateContestsNum
     * @since 1.8
     */
    public void setPhaseLateContestsNum(int phaseLateContestsNum) {
        this.phaseLateContestsNum = phaseLateContestsNum;
    }

    /**
     * Get the number of launch late contests.
     *
     * @return the number
     * @since 1.8
     */
    public int getLaunchLateContestsNum() {
        return launchLateContestsNum;
    }

    /**
     * Set number of launch late contests.
     *
     * @param launchLateContestsNum
     * @since 1.8
     */
    public void setLaunchLateContestsNum(int launchLateContestsNum) {
        this.launchLateContestsNum = launchLateContestsNum;
    }

    /**
     * Get the number of checkpoint late contests.
     *
     * @return the number
     * @since 1.8
     */
    public int getCheckpointLateContestsNum() {
        return checkpointLateContestsNum;
    }

    /**
     * Set number of checkpoint late contests.
     *
     * @param checkpointLateContestsNum
     * @since 1.8
     */
    public void setCheckpointLateContestsNum(int checkpointLateContestsNum) {
        this.checkpointLateContestsNum = checkpointLateContestsNum;
    }

    /**
     * Get the number of stalled contests.
     *
     * @return the number
     * @since 1.8
     */
    public int getStalledContestsNum() {
        return stalledContestsNum;
    }

    /**
     * Set number of stalled contests.
     *
     * @param stalledContestsNum
     * @since 1.8
     */
    public void setStalledContestsNum(int stalledContestsNum) {
        this.stalledContestsNum = stalledContestsNum;
    }

    /**
     * Get the number of ap off contests.
     *
     * @return the number
     * @since 1.8
     */
    public int getApOffContestsNum() {
        return apOffContestsNum;
    }

    /**
     * Set number of ap off contests.
     *
     * @param apOffContestsNum
     * @since 1.8
     */
    public void setApOffContestsNum(int apOffContestsNum) {
        this.apOffContestsNum = apOffContestsNum;
    }

    /**
     * Get the historical projected cost.
     *
     * @return the cost
     * @since 1.8
     */
    public double getHistoricalProjectedCost() {
        return historicalProjectedCost;
    }

    /**
     * Set the historical projected cost.
     *
     * @param historicalProjectedCost
     * @since 1.8
     */
    public void setHistoricalProjectedCost(double historicalProjectedCost) {
        this.historicalProjectedCost = historicalProjectedCost;
    }

    /**
     * <p>Gets the comma-separated list of handles for copilots associated with TC Direct project.</p>
     *
     * @return a <code>String</code> providing the comma-separated list of handles for copilots associated with TC
     *         Direct project.
     * @since 1.9
     */
    public String getCopilotNames() {
        return this.copilotNames;
    }

    /**
     * <p>Sets the comma-separated list of handles for copilots associated with TC Direct project.</p>
     *
     * @param copilotNames a <code>String</code> providing the comma-separated list of handles for copilots associated
     * with TC Direct project.
     * @since 1.9
     */
    public void setCopilotNames(String copilotNames) {
        this.copilotNames = copilotNames;
    }

    /**
     * <p>Gets the maximum delay in launching the contests for TC Direct project (in hours).</p>
     *
     * @return a <code>Long</code> providing the maximum delay in launching the contests for TC Direct project (in
     *         days).
     * @since 1.9
     */
    public Long getLaunchLateDelay() {
        return this.launchLateDelay;
    }

    /**
     * <p>Sets the maximum delay in launching the contests for TC Direct project (in hours).</p>
     *
     * @param launchLateDelay a <code>Long</code> providing the maximum delay in launching the contests for TC Direct
     * project (in hours).
     * @since 1.9
     */
    public void setLaunchLateDelay(Long launchLateDelay) {
        this.launchLateDelay = launchLateDelay;
    }

    /**
     * <p>Gets the maximum delay for running phase for the contests associated with TC Direct projects (in hours).</p>
     *
     * @return a <code>Double</code> providing the maximum delay for running phase for the contests associated with TC
     *         Direct projects (in hours).
     * @since 1.9
     */
    public Double getLatePhaseDelay() {
        return this.latePhaseDelay;
    }

    /**
     * <p>Sets the maximum delay for running phase for the contests associated with TC Direct projects (in hours).</p>
     *
     * @param latePhaseDelay a <code>Double</code> providing the maximum delay for running phase for the contests
     * associated with TC Direct projects (in hours).
     * @since 1.9
     */
    public void setLatePhaseDelay(Double latePhaseDelay) {
        this.latePhaseDelay = latePhaseDelay;
    }

    /**
     * <p>Gets the mapping from the contest category names to number of active contests for TC Direct project.</p>
     *
     * @return a <code>Map</code> providing the mapping from the contest category names to number of active contests for
     *         TC Direct project.
     * @since 1.9
     */
    public Map<String, Integer> getActiveContestsCount() {
        return this.activeContestsCount;
    }

    /**
     * <p>Sets the mapping from the contest category names to number of active contests for TC Direct project.</p>
     *
     * @param activeContestsCount a <code>Map</code> providing the mapping from the contest category names to number of
     * active contests for TC Direct project.
     * @since 1.9
     */
    public void setActiveContestsCount(Map<String, Integer> activeContestsCount) {
        this.activeContestsCount = activeContestsCount;
    }
}
