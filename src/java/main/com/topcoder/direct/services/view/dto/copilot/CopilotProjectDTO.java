/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.copilot;

import java.io.Serializable;
import java.util.List;

import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;

/**
 * <p>
 * A DTO used to store copilot information of one project.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0
 * @since TC Direct Manage Copilots Assembly
 */
public class CopilotProjectDTO implements Serializable {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = -6256439238932049235L;

    /**
     * The list of contests associate with this project.
     */
    private List<CopilotContestDTO> contests;

    /**
     * The list of copilots associate with this project.
     */
    private List<CopilotBriefDTO> copilots;

    /**
     * Some basic project information.
     */
    private ProjectBriefDTO project;

    /**
     * Retrieves the contests field.
     * 
     * @return the contests
     */
    public List<CopilotContestDTO> getContests() {
        return contests;
    }

    /**
     * Sets the contests field.
     * 
     * @param contests
     *            the contests to set
     */
    public void setContests(List<CopilotContestDTO> contests) {
        this.contests = contests;
    }

    /**
     * Retrieves the copilots field.
     * 
     * @return the copilots
     */
    public List<CopilotBriefDTO> getCopilots() {
        return copilots;
    }

    /**
     * Sets the copilots field.
     * 
     * @param copilots
     *            the copilots to set
     */
    public void setCopilots(List<CopilotBriefDTO> copilots) {
        this.copilots = copilots;
    }

    /**
     * Retrieves the project field.
     * 
     * @return the project
     */
    public ProjectBriefDTO getProject() {
        return project;
    }

    /**
     * Sets the project field.
     * 
     * @param project
     *            the project to set
     */
    public void setProject(ProjectBriefDTO project) {
        this.project = project;
    }

}
