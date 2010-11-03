/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
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
 *
 * @author isv, TCSASSEMBLER
 * @version 1.0.1
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

    /**
     * <p>A <code>double</code> providing the total cost for specified project.</p>
     */
    private double totalProjectCost;

    /**
     * <p>A <code>double</code> providing the average fulfillment for specified project.</p>
     */
    private double averageFulfillment;

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
}
