/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import com.topcoder.direct.services.view.dto.UserDTO;
import com.topcoder.direct.services.view.dto.dashboard.DashboardStatusColor;

import java.io.Serializable;
import java.util.List;

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
 * @author isv, TCSASSEMBLER
 * @version 1.0.1
 */
public class ContestDashboardDTO implements Serializable {

    /**
     * <p>A <code>DependenciesStatus</code> providing the overall status for dependencies for target contest.</p>
     */
    private DependenciesStatus dependenciesStatus;

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
     * <p>A <code>ReviewersSignupStatus</code> providing the status for reviewers signup for the project.</p>
     */
    private ReviewersSignupStatus reviewersSignupStatus;

    /**
     * <p>An <code>int</code> providing the number of unanswered forum posts for project.</p>
     */
    private int unansweredForumPostsNumber;

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
     * <p>A <code>RegistrationStatus</code> providing the status for registration for project.</p>
     */
    private RegistrationStatus registrationStatus;

    /**
     * <p>A <code>ProjectPhaseDTO</code> providing the details for current phase.</p>
     */
    private ProjectPhaseDTO currentPhase;

    /**
     * <p>A <code>ProjectPhaseDTO</code> providing the details for next project phase.</p>
     */
    private ProjectPhaseDTO nextPhase;

    /**
     * <p>A <code>RunningPhaseStatus</code> providing the status of the current phase for project.</p>
     */
    private RunningPhaseStatus currentPhaseStatus;

    /**
     * <p>
     * A <code>DashboardStatusColor</code> presents which color should be used
     * when render current phase status to page.
     * </p>
     * 
     * @since 1.0.1
     */
    private DashboardStatusColor phaseStatusColor;

    /**
     * <p>
     * A <code>DashboardStatusColor</code> presents which color should be used
     * when render registration status to page.
     * </p>
     * 
     * @since 1.0.1
     */
    private DashboardStatusColor regStatusColor;

    /**
     * <p>
     * A <code>DashboardStatusColor</code> presents which color should be used
     * when render forum activity status to page.
     * </p>
     * 
     * @since 1.0.1
     */
    private DashboardStatusColor forumActivityStatusColor;

    /**
     * <p>
     * A <code>DashboardStatusColor</code> presents which color should be used
     * when render reviewers sign up status to page.
     * </p>
     * 
     * @since 1.0.1
     */
    private DashboardStatusColor reviewersSignupStatusColor;

    /**
     * <p>
     * A <code>DashboardStatusColor</code> presents which color should be used
     * when render dependencies status to page.
     * </p>
     * 
     * @since 1.0.1
     */
    private DashboardStatusColor dependenciesStatusColor;

    /**
     * <p>
     * A <code>DashboardStatusColor</code> presents which color should be used
     * when render whole contest status to page.
     * </p>
     * 
     * @since 1.0.1
     */
    private DashboardStatusColor contestStatusColor;

    /**
     * <p>
     * Constructs new <code>ContestDashboardDTO</code> instance. This
     * implementation does nothing.
     * </p>
     */
    public ContestDashboardDTO() {
    }

    /**
     * <p>Gets the overall status for dependencies for target contest.</p>
     *
     * @return a <code>DependenciesStatus</code> providing the overall status for dependencies for target contest.
     */
    public DependenciesStatus getDependenciesStatus() {
        return this.dependenciesStatus;
    }

    /**
     * <p>Sets the overall status for dependencies for target contest.</p>
     *
     * @param dependenciesStatus a <code>DependenciesStatus</code> providing the overall status for dependencies for
     *                           target contest.
     */
    public void setDependenciesStatus(DependenciesStatus dependenciesStatus) {
        this.dependenciesStatus = dependenciesStatus;
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
     * <p>Gets the status for reviewers signup for the project.</p>
     *
     * @return a <code>ReviewersSignupStatus</code> providing the status for reviewers signup for the project.
     */
    public ReviewersSignupStatus getReviewersSignupStatus() {
        return this.reviewersSignupStatus;
    }

    /**
     * <p>Sets the status for reviewers signup for the project.</p>
     *
     * @param reviewersSignupStatus a <code>ReviewersSignupStatus</code> providing the status for reviewers signup for
     *        the project.
     */
    public void setReviewersSignupStatus(ReviewersSignupStatus reviewersSignupStatus) {
        this.reviewersSignupStatus = reviewersSignupStatus;
    }

    /**
     * <p>Gets the number of unanswered forum posts for project.</p>
     *
     * @return an <code>int</code> providing the number of unanswered forum posts for project.
     */
    public int getUnansweredForumPostsNumber() {
        return this.unansweredForumPostsNumber;
    }

    /**
     * <p>Sets the number of unanswered forum posts for project.</p>
     *
     * @param unansweredForumPostsNumber an <code>int</code> providing the number of unanswered forum posts for project.
     */
    public void setUnansweredForumPostsNumber(int unansweredForumPostsNumber) {
        this.unansweredForumPostsNumber = unansweredForumPostsNumber;
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
     * <p>Gets the status for registration for project.</p>
     *
     * @return a <code>RegistrationStatus</code> providing the status for registration for project.
     */
    public RegistrationStatus getRegistrationStatus() {
        return this.registrationStatus;
    }

    /**
     * <p>Sets the status for registration for project.</p>
     *
     * @param registrationStatus a <code>RegistrationStatus</code> providing the status for registration for project.
     */
    public void setRegistrationStatus(RegistrationStatus registrationStatus) {
        this.registrationStatus = registrationStatus;
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
     * <p>Gets the status of the current phase for project.</p>
     *
     * @return a <code>RunningPhaseStatus</code> providing the status of the current phase for project.
     */
    public RunningPhaseStatus getCurrentPhaseStatus() {
        return this.currentPhaseStatus;
    }

    /**
     * <p>Sets the status of the current phase for project.</p>
     *
     * @param currentPhaseStatus a <code>RunningPhaseStatus</code> providing the status of the current phase for
     *                           project.
     */
    public void setCurrentPhaseStatus(RunningPhaseStatus currentPhaseStatus) {
        this.currentPhaseStatus = currentPhaseStatus;
    }

    /**
     * Retrieves the phaseStatusColor field.
     * 
     * @return the phaseStatusColor
     * @since 1.0.1
     */
    public DashboardStatusColor getPhaseStatusColor() {
        return phaseStatusColor;
    }

    /**
     * Sets the phaseStatusColor field.
     * 
     * @param phaseStatusColor
     *            the phaseStatusColor to set
     * @since 1.0.1
     */
    public void setPhaseStatusColor(DashboardStatusColor phaseStatusColor) {
        this.phaseStatusColor = phaseStatusColor;
    }

    /**
     * Retrieves the regStatusColor field.
     * 
     * @return the regStatusColor
     * @since 1.0.1
     */
    public DashboardStatusColor getRegStatusColor() {
        return regStatusColor;
    }

    /**
     * Sets the regStatusColor field.
     * 
     * @param regStatusColor
     *            the regStatusColor to set
     * @since 1.0.1
     */
    public void setRegStatusColor(DashboardStatusColor regStatusColor) {
        this.regStatusColor = regStatusColor;
    }

    /**
     * Retrieves the forumActivityStatusColor field.
     * 
     * @return the forumActivityStatusColor
     * @since 1.0.1
     */
    public DashboardStatusColor getForumActivityStatusColor() {
        return forumActivityStatusColor;
    }

    /**
     * Sets the forumActivityStatusColor field.
     * 
     * @param forumActivityStatusColor
     *            the forumActivityStatusColor to set
     * @since 1.0.1
     */
    public void setForumActivityStatusColor(
            DashboardStatusColor forumActivityStatusColor) {
        this.forumActivityStatusColor = forumActivityStatusColor;
    }

    /**
     * Retrieves the reviewersSignupStatusColor field.
     * 
     * @return the reviewersSignupStatusColor
     * @since 1.0.1
     */
    public DashboardStatusColor getReviewersSignupStatusColor() {
        return reviewersSignupStatusColor;
    }

    /**
     * Sets the reviewersSignupStatusColor field.
     * 
     * @param reviewersSignupStatusColor
     *            the reviewersSignupStatusColor to set
     * @since 1.0.1
     */
    public void setReviewersSignupStatusColor(
            DashboardStatusColor reviewersSignupStatusColor) {
        this.reviewersSignupStatusColor = reviewersSignupStatusColor;
    }

    /**
     * Retrieves the dependenciesStatusColor field.
     * 
     * @return the dependenciesStatusColor
     * @since 1.0.1
     */
    public DashboardStatusColor getDependenciesStatusColor() {
        return dependenciesStatusColor;
    }

    /**
     * Sets the dependenciesStatusColor field.
     * 
     * @param dependenciesStatusColor
     *            the dependenciesStatusColor to set
     * @since 1.0.1
     */
    public void setDependenciesStatusColor(
            DashboardStatusColor dependenciesStatusColor) {
        this.dependenciesStatusColor = dependenciesStatusColor;
    }

    /**
     * Retrieves the contestStatusColor field.
     * 
     * @return the contestStatusColor
     * @since 1.0.1
     */
    public DashboardStatusColor getContestStatusColor() {
        return contestStatusColor;
    }

    /**
     * Sets the contestStatusColor field.
     * 
     * @param contestStatusColor
     *            the contestStatusColor to set
     * @since 1.0.1
     */
    public void setContestStatusColor(DashboardStatusColor contestStatusColor) {
        this.contestStatusColor = contestStatusColor;
    }

}
