/*
 * Copyright (C) 2010-2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard;

import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;

import java.io.Serializable;

/**
 * <p>A DTO class providing the statistical data for a single project to be displayed in <code>Enterprise Health</code>
 * area on <code>Enterprise Dashboard</code> page view.</p>
 * <p>
 * Version 1.0.1 - Direct - Project Dashboard Assembly Change Note
 * <ul>
 * <li>Added {@link #averageContestDurationText} and {@link #projectStatusColor} parameters. </li>
 * </ul>
 * </p>
 * <p>
 * Version 1.0.2 - TC Cockpit Bug Tracking R1 Cockpit Project Tracking version 1.0 Change Note
 * <ul>
 * <li>Added property unresolvedIssuesNumber. </li>
 * <li>Added property ongoingBugRacesNumber. </li>
 * </ul>
 * </p>
 *
 * @author isv, Veve
 * @version 1.0.2
 */
public class EnterpriseDashboardProjectStatDTO implements Serializable {

    /**
     * <p>A <code>ProjectBriefDTO</code> providing the details for project.</p>
     */
    private ProjectBriefDTO project;

    /**
     * <p>A <code>double</code> providing the average contest duration for specified project.</p>
     */
    private double averageContestDuration;

    /**
     * <p>A <code>double</code> providing the average cost per contest for specified project.</p>
     */
    private double averageCostPerContest;

    /*
    * <p>A <code>double</code> providing the average member cost per contest for specified project.</p>
    */
    private double averageMemberCostPerContest;

    /*
    * <p>A <code>double</code> providing the total member cost for specified project.</p>
    */
    private double totalMemberCost;

    /*
    * <p>A <code>double</code> providing the average contest fee per contest for specified project.</p>
    */
    private double averageContestFeePerContest;

    /*
    * <p>A <code>double</code> providing the total contest fee for specified project.</p>
    */
    private double totalContestFee;

    /**
     * <p>A <code>double</code> providing the total cost for specified project.</p>
     */
    private double totalProjectCost;

    /**
     * <p>A <code>double</code> providing the average fulfillment for specified project.</p>
     */
    private double averageFulfillment;

    /**
     * <p>A <code>double</code> providing the completed contests number for specified project.</p>
     */
    private int completedNumber;

    /**
     * <p>A <code>double</code> providing the cancelled contests number for specified project.</p>
     */
    private int cancelledNumber;

    /**
     * <p>A <code>String</code> represents the average contest duration text.</p>
     * 
     * @since 1.0.1
     */
    private String averageContestDurationText;
    
    /**
     * <p>A <code>DashboardStatusColor</code> represents the color of whole project.</p>
     * 
     * @since 1.0.1
     */
    private DashboardStatusColor projectStatusColor;

    /**
     * <p>A <code>int</code> represents the number of unresolved issues of the project.
     *
     * @since 1.0.2
     */
    private int unresolvedIssuesNumber;

    /**
     * <p>A <code>int</code> represents the number of ongoing bug races of the project.
     *
     * @since 1.0.2
     */
    private int ongoingBugRacesNumber;

    /**
     * <p>Constructs new <code>EnterpriseDashboardProjectStatDTO</code> instance. This implementation does nothing.</p>
     */
    public EnterpriseDashboardProjectStatDTO() {
    }

    /**
     * <p>Gets the details for project.</p>
     *
     * @return a <code>ProjectBriefDTO</code> providing the details for project.
     */
    public ProjectBriefDTO getProject() {
        return this.project;
    }

    /**
     * <p>Sets the details for project.</p>
     *
     * @param project a <code>ProjectBriefDTO</code> providing the details for project.
     */
    public void setProject(ProjectBriefDTO project) {
        this.project = project;
    }

    /**
     * <p>Gets the average contest duration for specified project.</p>
     *
     * @return a <code>double</code> providing the average contest duration for specified project.
     */
    public double getAverageContestDuration() {
        return this.averageContestDuration;
    }

    /**
     * <p>Sets the average contest duration for specified project.</p>
     *
     * @param averageContestDuration a <code>double</code> providing the average contest duration for specified
     *                                project.
     */
    public void setAverageContestDuration(double averageContestDuration) {
        this.averageContestDuration = averageContestDuration;
    }

    /**
     * <p>Gets the average fulfillment for specified project.</p>
     *
     * @return a <code>double</code> providing the average fulfillment for specified project.
     */
    public double getAverageFulfillment() {
        return this.averageFulfillment;
    }

    /**
     * <p>Sets the average fulfillment for specified project.</p>
     *
     * @param averageFulfillment a <code>double</code> providing the average fulfillment for specified project.
     */
    public void setAverageFulfillment(double averageFulfillment) {
        this.averageFulfillment = averageFulfillment;
    }

    /**
     * <p>Gets the total cost for specified project.</p>
     *
     * @return a <code>double</code> providing the total cost for specified project.
     */
    public double getTotalProjectCost() {
        return this.totalProjectCost;
    }

    /**
     * <p>Sets the total cost for specified project.</p>
     *
     * @param totalProjectCost a <code>double</code> providing the total cost for specified project.
     */
    public void setTotalProjectCost(double totalProjectCost) {
        this.totalProjectCost = totalProjectCost;
    }

    /**
     * <p>Gets the average cost per contest for specified project.</p>
     *
     * @return a <code>double</code> providing the average cost per contest for specified project.
     */
    public double getAverageCostPerContest() {
        return this.averageCostPerContest;
    }

    /**
     * <p>Sets the average cost per contest for specified project.</p>
     *
     * @param averageCostPerContest a <code>double</code> providing the average cost per contest for specified project.
     */
    public void setAverageCostPerContest(double averageCostPerContest) {
        this.averageCostPerContest = averageCostPerContest;
    }

     /**
     * <p>Gets the average member cost per contest for specified project.</p>
     *
     * @return a <code>double</code> providing the average member cost per contest for specified project.
     */
    public double getAverageMemberCostPerContest() {
        return this.averageMemberCostPerContest;
    }

    /**
     * <p>Sets the average member cost per contest for specified project.</p>
     *
     * @param averageMemberCostPerContest a <code>double</code> providing the average member cost per contest for specified project.
     */
    public void setAverageMemberCostPerContest(double averageMemberCostPerContest) {
        this.averageMemberCostPerContest = averageMemberCostPerContest;
    }

    /**
     * <p>Gets the total member cost for specified project.</p>
     *
     * @return a <code>double</code> providing the total member cost for specified project.
     */
    public double getTotalMemberCost() {
        return this.totalMemberCost;
    }

    /**
     * <p>Sets the total member cost for specified project.</p>
     *
     * @param totalMemberCost a <code>double</code> providing the total member cost for specified project.
     */
    public void setTotalMemberCost(double totalMemberCost) {
        this.totalMemberCost = totalMemberCost;
    }

    /**
     * <p>Gets the average contest Fee per contest for specified project.</p>
     *
     * @return a <code>double</code> providing the average contest Fee per contest for specified project.
     */
    public double getAverageContestFeePerContest() {
        return this.averageContestFeePerContest;
    }

    /**
     * <p>Sets the average contest Fee per contest for specified project.</p>
     *
     * @param averageContestFeePerContest a <code>double</code> providing the average contest Fee per contest for specified project.
     */
    public void setAverageContestFeePerContest(double averageContestFeePerContest) {
        this.averageContestFeePerContest = averageContestFeePerContest;
    }

    /**
     * <p>Gets the total contest fee for specified project.</p>
     *
     * @return a <code>double</code> providing the total contest fee for specified project.
     */
    public double getTotalContestFee() {
        return this.totalContestFee;
    }

    /**
     * <p>Sets the total contest fee for specified project.</p>
     *
     * @param totalContestFee a <code>double</code> providing the total contest fee for specified project.
     */
    public void setTotalContestFee(double totalContestFee) {
        this.totalContestFee = totalContestFee;
    }

     /**
     * <p>Gets the completed contest number for specified project.</p>
     *
     * @return a <code>int</code> providing the completed contest number for specified project.
     */
    public int getCompletedNumber() {
        return this.completedNumber;
    }

    /**
     * <p>Sets the completed contest number for specified project.</p>
     *
     * @param completedNumber a <code>int</code> providing the completed contest number for specified project.
     */
    public void setCompletedNumber(int completedNumber) {
        this.completedNumber = completedNumber;
    }

     /**
     * <p>Gets the Cancelled contest number for specified project.</p>
     *
     * @return a <code>int</code> providing the Cancelled contest number for specified project.
     */
    public int getCancelledNumber() {
        return this.cancelledNumber;
    }

    /**
     * <p>Sets the Cancelled contest number for specified project.</p>
     *
     * @param cancelledNumber a <code>int</code> providing the Cancelled contest number for specified project.
     */
    public void setCancelledNumber(int cancelledNumber) {
        this.cancelledNumber = cancelledNumber;
    }


    /**
     * Retrieves the averageContestDurationText field.
     *
     * @return the averageContestDurationText
     * @since 1.0.1
     */
    public String getAverageContestDurationText() {
        return averageContestDurationText;
    }

    /**
     * Sets the averageContestDurationText field.
     *
     * @param averageContestDurationText the averageContestDurationText to set
     * @since 1.0.1
     */
    public void setAverageContestDurationText(String averageContestDurationText) {
        this.averageContestDurationText = averageContestDurationText;
    }

    /**
     * Retrieves the projectStatusColor field.
     *
     * @return the projectStatusColor
     * @since 1.0.1
     */
    public DashboardStatusColor getProjectStatusColor() {
        return projectStatusColor;
    }

    /**
     * Sets the projectStatusColor field.
     *
     * @param projectStatusColor the projectStatusColor to set
     * @since 1.0.1
     */
    public void setProjectStatusColor(DashboardStatusColor projectStatusColor) {
        this.projectStatusColor = projectStatusColor;
    }

    /**
     * Gets the number of unresolved issues.
     *
     * @return the number of unresolved issues.
     * @since 1.0.2
     */
    public int getUnresolvedIssuesNumber() {
        return unresolvedIssuesNumber;
    }

    /**
     * Sets the number of unresolved issues.
     *
     * @param unresolvedIssuesNumber the number of unresolved issues.
     * @since 1.0.2
     */
    public void setUnresolvedIssuesNumber(int unresolvedIssuesNumber) {
        this.unresolvedIssuesNumber = unresolvedIssuesNumber;
    }

    /**
     * Gets the number of ongoing bug races.
     *
     * @return the number of ongoing bug races.
     * @since 1.0.2
     */
    public int getOngoingBugRacesNumber() {
        return ongoingBugRacesNumber;
    }

    /**
     * Sets the number of ongoing bug races.
     *
     * @param ongoingBugRacesNumber the number of ongoing bug races.
     * @since 1.0.2
     */
    public void setOngoingBugRacesNumber(int ongoingBugRacesNumber) {
        this.ongoingBugRacesNumber = ongoingBugRacesNumber;
    }
}


