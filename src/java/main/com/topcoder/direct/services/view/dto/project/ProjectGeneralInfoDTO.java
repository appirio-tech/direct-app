/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project;


import com.topcoder.service.project.ProjectData;

import java.util.List;
import java.util.ArrayList;

/**
 * <p>
 *  This class is the DTO to store the general project information such as budget, planned duration, actual cost etc.
 * </p>
 *
 * @version 1.0 (Module Assembly - TC Cockpit Project Overview Project General Info)
 * @author TCSASSEMBLER
 */
public class ProjectGeneralInfoDTO {

    /**
     * The project data object.
     */
    private ProjectData project;

    /**
     * The svn link of the project.
     */
    private String svn;

    /**
     * The jira link of the project.
     */
    private String jira;

    /**
     * The total budget of the project.
     */
    private Integer totalBudget;

    /**
     * The actual cost of the project.
     */
    private Integer actualCost;

    /**
     * The planned duration of the project.
     */
    private Integer plannedDuration;

    /**
     * The actual duration of the project.
     */
    private Integer actualDuration;

    /**
     * The client managers of the project.
     */
    private List<Long> clientManagers = new ArrayList<Long>();

    /**
     * The TopCoder managers of the project.
     */
    private List<Long> topcoderManagers = new ArrayList<Long>();

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
            return "Deleted";
        } else if (project.getProjectStatusId() == 4L) {
            return "Completed";
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
     * Ges the jira link of the project.
     *
     * @return the jira link of the project.
     */
    public String getJira() {
        return jira;
    }

    /**
     * Sets the jira link of the project.
     *
     * @param jira the jira link of the project.
     */
    public void setJira(String jira) {
        this.jira = jira;
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
}
