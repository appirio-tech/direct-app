/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import com.topcoder.direct.services.view.dto.UserDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * A <code>DTO</code> providing the data to be displayed by
 * <code>Contest Dashboard</code> section of <code>Contest
 * Details</code> page.
 * </p>
 * <p>
 * Version 1.0.1 - Direct - Project Dashboard Assembly Change Note
 * <ul>
 * <li>Added colors for each status and the contest.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.1 (Cockpit Performance Improvement Project Overview and Manage Copilot Posting Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Refactored the class and made it a descendant of {@link ContestHealthDTO} class.</li>
 *   </ol>
 * </p>
 * Version 1.2 (Direct Replatforming Release 4) Change notes:
 *   <ol>
 *     <li>Refactored the currentPhase being type of List.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.3 (TC Direct Contest Dashboard Update Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Add allPhases : List<ProjectPhaseDTO> field and corresponding get/set methods.</li>
 *     <li>Add startTime : Date field and corresponding get/set methods.</li>
 *     <li>Add endTime : Date field and corresponding get/set methods.</li>
 *     <li>Add regProgressPercent : int field and corresponding get/set methods.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.4 (Cockpit Customer Copilot Posting Process Revamp Copilot Posting Dashboard)
 * <ol>
 *     <li>Add data {@link #copilotProjectTypes} {@link #budget} {@link #otherManagingExperienceString}
 *     for store copilot dashboard data</li>
 * </ol>
 * </p>
 * @author isv, morehappiness, GreatKevin
 * @version 1.4
 */
public class ContestDashboardDTO extends ContestHealthDTO implements Serializable {

    /**
     * <p>A <code>List</code> providing the details for dependencies.</p>
     */
    private List<DependencyDTO> dependencies;

    /**
     * <p>A <code>List</code> providing the details for reviewers signed up for project review.</p>
     */
    private List<UserDTO> reviewers;

    /**
     * <p>An <code>int</code> providing the number of reviewers required for project.</p>
     */
    private int requiredReviewersNumber;

    /**
     * <p>A <code>ForumPostDTO</code> providing the details on most recent forum post for project.</p>
     */
    private ForumPostDTO latestForumPost;

    /**
     * <p>An <code>int</code> providing the total number of forum posts for project.</p>
     */
    private int totalForumPostsCount;

    /**
     * <p>A <code>String</code> providing the URL for forum for project.</p>
     */
    private String forumURL;

    /**
     * <p>An <code>int</code> providing the predicted number of submissions for project.</p>
     */
    private int predictedNumberOfSubmissions;

    /**
     * <p>An <code>int</code> providing the number of submissions for project.</p>
     */
    private int numberOfSubmissions;

    /**
     * <p>An <code>int</code> providing the number of registrants for project.</p>
     */
    private int numberOfRegistrants;

    /**
     * <p>A <code>ProjectPhaseDTO</code> providing the details for current phase.</p>
     */
    private ProjectPhaseDTO currentPhase;

    /**
     * <p>A <code>ProjectPhaseDTO</code> providing the details for next project phase.</p>
     */
    private ProjectPhaseDTO nextPhase;

    /**
     * <p>A list of <code>ProjectPhaseDTO</code> providing the details for all phases.</p>
     * 
     * @since 1.3
     */
    private List<ProjectPhaseDTO> allPhases;
    
    /**
     * The start time.
     * 
     * @since 1.3
     */
    private Date startTime;
    
    /**
     * The end time.
     * 
     * @since 1.3
     */
    private Date endTime;
    
    /**
     * The registration progress percent data.
     * 
     * @since 1.3
     */
    private int regProgressPercent;
    
    /**
     * copilot project types required for copilot posting dashboard.
     * @since 1.4
     */
    private Set<Long> copilotProjectTypes;

    /**
     * copilot project budget required for copilot posting dashboard.
     * @since 1.4
     */
    private String budget;

    /**
     * other project manage experience string for copilot posting dashboard.
     * @since 1.4
     */
    private String otherManagingExperienceString;

    private String directProjectType;

    private String directProjectDuration;
    
    /**
     * <p>
     * Constructs new <code>ContestDashboardDTO</code> instance. This
     * implementation does nothing.
     * </p>
     */
    public ContestDashboardDTO() {

    }

    /**
     * <p>Gets the details for dependencies.</p>
     *
     * @return a <code>List</code> providing the details for dependencies.
     */
    public List<DependencyDTO> getDependencies() {
        return this.dependencies;
    }

    /**
     * <p>Sets the details for dependencies.</p>
     *
     * @param dependencies a <code>List</code> providing the details for dependencies.
     */
    public void setDependencies(List<DependencyDTO> dependencies) {
        this.dependencies = dependencies;
    }

    /**
     * <p>Gets the details for reviewers signed up for project review.</p>
     *
     * @return a <code>List</code> providing the details for reviewers signed up for project review.
     */
    public List<UserDTO> getReviewers() {
        return this.reviewers;
    }

    /**
     * <p>Sets the details for reviewers signed up for project review.</p>
     *
     * @param reviewers a <code>List</code> providing the details for reviewers signed up for project review.
     */
    public void setReviewers(List<UserDTO> reviewers) {
        this.reviewers = reviewers;
    }

    /**
     * <p>Gets the number of reviewers required for project.</p>
     *
     * @return an <code>int</code> providing the number of reviewers required for project.
     */
    public int getRequiredReviewersNumber() {
        return this.requiredReviewersNumber;
    }

    /**
     * <p>Sets the number of reviewers required for project.</p>
     *
     * @param requiredReviewersNumber an <code>int</code> providing the number of reviewers required for project.
     */
    public void setRequiredReviewersNumber(int requiredReviewersNumber) {
        this.requiredReviewersNumber = requiredReviewersNumber;
    }

    /**
     * <p>Gets the details on most recent forum post for project.</p>
     *
     * @return a <code>ForumPostDTO</code> providing the details on most recent forum post for project.
     */
    public ForumPostDTO getLatestForumPost() {
        return this.latestForumPost;
    }

    /**
     * <p>Sets the details on most recent forum post for project.</p>
     *
     * @param latestForumPost a <code>ForumPostDTO</code> providing the details on most recent forum post for project.
     */
    public void setLatestForumPost(ForumPostDTO latestForumPost) {
        this.latestForumPost = latestForumPost;
    }

    /**
     * <p>Gets the total number of forum posts for project.</p>
     *
     * @return an <code>int</code> providing the total number of forum posts for project.
     */
    public int getTotalForumPostsCount() {
        return this.totalForumPostsCount;
    }

    /**
     * <p>Sets the total number of forum posts for project.</p>
     *
     * @param totalForumPostsCount an <code>int</code> providing the total number of forum posts for project.
     */
    public void setTotalForumPostsCount(int totalForumPostsCount) {
        this.totalForumPostsCount = totalForumPostsCount;
    }

    /**
     * <p>Gets the URL for forum for project.</p>
     *
     * @return a <code>String</code> providing the URL for forum for project.
     */
    public String getForumURL() {
        return this.forumURL;
    }

    /**
     * <p>Sets the URL for forum for project.</p>
     *
     * @param forumURL a <code>String</code> providing the URL for forum for project.
     */
    public void setForumURL(String forumURL) {
        this.forumURL = forumURL;
    }

    /**
     * <p>Gets the predicted number of submissions for project.</p>
     *
     * @return an <code>int</code> providing the predicted number of submissions for project.
     */
    public int getPredictedNumberOfSubmissions() {
        return this.predictedNumberOfSubmissions;
    }

    /**
     * <p>Sets the predicted number of submissions for project.</p>
     *
     * @param predictedNumberOfSubmissions an <code>int</code> providing the predicted number of submissions for
     *                                     project.
     */
    public void setPredictedNumberOfSubmissions(int predictedNumberOfSubmissions) {
        this.predictedNumberOfSubmissions = predictedNumberOfSubmissions;
    }

    /**
     * <p>Gets the number of registrants for project.</p>
     *
     * @return an <code>int</code> providing the number of registrants for project.
     */
    public int getNumberOfRegistrants() {
        return this.numberOfRegistrants;
    }

    /**
     * <p>Sets the number of registrants for project.</p>
     *
     * @param numberOfRegistrants an <code>int</code> providing the number of registrants for project.
     */
    public void setNumberOfRegistrants(int numberOfRegistrants) {
        this.numberOfRegistrants = numberOfRegistrants;
    }

    /**
     * <p>Gets the number of submissions for project.</p>
     *
     * @return an <code>int</code> providing the number of submissions for project.
     */
    public int getNumberOfSubmissions() {
        return this.numberOfSubmissions;
    }

    /**
     * <p>Sets the number of submissions for project.</p>
     *
     * @param numberOfSubmissions an <code>int</code> providing the number of submissions for project.
     */
    public void setNumberOfSubmissions(int numberOfSubmissions) {
        this.numberOfSubmissions = numberOfSubmissions;
    }

    /**
     * <p>Gets the details for next project phase.</p>
     *
     * @return a <code>ProjectPhaseDTO</code> providing the details for next project phase.
     */
    public ProjectPhaseDTO getNextPhase() {
        return this.nextPhase;
    }

    /**
     * <p>Sets the details for next project phase.</p>
     *
     * @param nextPhase a <code>ProjectPhaseDTO</code> providing the details for next project phase.
     */
    public void setNextPhase(ProjectPhaseDTO nextPhase) {
        this.nextPhase = nextPhase;
    }

    /**
     * <p>Gets the details for current phase.</p>
     *
     * @return a <code>ProjectPhaseDTO</code> providing the details for current phase.
     */
    public ProjectPhaseDTO getCurrentPhase() {
        return this.currentPhase;
    }

    /**
     * <p>Sets the details for current phase.</p>
     *
     * @param currentPhase a <code>ProjectPhaseDTO</code> providing the details for current phase.
     */
    public void setCurrentPhase(ProjectPhaseDTO currentPhase) {
        this.currentPhase = currentPhase;
    }

    /**
     * Gets the all phases field.
     * 
     * @return the allPhases
     * @since 1.3
     */
    public List<ProjectPhaseDTO> getAllPhases() {
        return allPhases;
    }

    /**
     * Sets the all phases field.
     *
     * @param allPhases the allPhases to set
     * @since 1.
     */
    public void setAllPhases(List<ProjectPhaseDTO> allPhases) {
        this.allPhases = allPhases;
    }

    /**
     * Gets the startTime field.
     * 
     * @return the startTime
     * @since 1.3
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets the startTime field.
     *
     * @param startTime the startTime to set
     * @since 1.3
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the endTime field.
     * 
     * @return the endTime
     * @since 1.3
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Sets the endTime field.
     *
     * @param endTime the endTime to set
     * @since 1.3
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the regProgressPercent field.
     * 
     * @return the regProgressPercent
     * @since 1.3
     */
    public int getRegProgressPercent() {
        return regProgressPercent;
    }

    /**
     * Sets the regProgressPercent field.
     *
     * @param regProgressPercent the regProgressPercent to set
     * @since 1.3
     */
    public void setRegProgressPercent(int regProgressPercent) {
        this.regProgressPercent = regProgressPercent;
    }
    
    /**
     * Checks whether the contest is stalled.
     * 
     * @return true if the contest is stalled, false otherwise.
     */
    public boolean isStalled() {
        ProjectPhaseDTO specReviewPhase = null;
        ProjectPhaseDTO regPhase = null;
        for (ProjectPhaseDTO phase : allPhases) {
            if (phase.getPhaseType().getPhaseTypeId() == ProjectPhaseType.SPECIFICATION_REVIEW.getPhaseTypeId()) {
                specReviewPhase = phase;
            } else if (phase.getPhaseType().getPhaseTypeId() == ProjectPhaseType.REGISTRATION.getPhaseTypeId()) {
	            regPhase = phase;
	        }
            if (phase.getPhaseStatus().getPhaseStatusId() == ProjectPhaseStatus.OPEN.getPhaseStatusId()) {
                return false;
            }
        }
        if (regPhase.getPhaseStatus().getPhaseStatusId() != ProjectPhaseStatus.CLOSED.getPhaseStatusId()
            && (specReviewPhase == null || specReviewPhase.getPhaseStatus().getPhaseStatusId() == ProjectPhaseStatus.CLOSED.getPhaseStatusId())) {
            return false;
        }
        return true;
    }

    /**
     * Gets the copilot project types.
     *
     * @return the copilot project types.
     * @since 1.4
     */
    public Set<Long> getCopilotProjectTypes() {
        return copilotProjectTypes;
    }

    /**
     * Sets the copilot project types.
     *
     * @param copilotProjectTypes the copilot project types.
     * @since 1.4
     */
    public void setCopilotProjectTypes(Set<Long> copilotProjectTypes) {
        this.copilotProjectTypes = copilotProjectTypes;
    }

    /**
     * Gets the budget.
     *
     * @return the budget.
     * @since 1.4
     */
    public String getBudget() {
        return budget;
    }

    /**
     * Sets the budget.
     *
     * @param budget the project budget.
     * @since 1.4
     */
    public void setBudget(String budget) {
        this.budget = budget;
    }

    /**
     * Gets the other required management experience.
     *
     * @return the other required management experience.
     * @since 1.4
     */
    public String getOtherManagingExperienceString() {
        return otherManagingExperienceString;
    }

    /**
     * Sets the other required management experience.
     *
     * @param otherManagingExperienceString the other required management experience.
     * @since 1.4
     */
    public void setOtherManagingExperienceString(String otherManagingExperienceString) {
        this.otherManagingExperienceString = otherManagingExperienceString;
    }

    public String getDirectProjectType() {
        return directProjectType;
    }

    public void setDirectProjectType(String directProjectType) {
        this.directProjectType = directProjectType;
    }

    public String getDirectProjectDuration() {
        return directProjectDuration;
    }

    public void setDirectProjectDuration(String directProjectDuration) {
        this.directProjectDuration = directProjectDuration;
    }
}
