/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import com.topcoder.direct.services.view.dto.copilot.CopilotBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;

/**
 * <p>A DTO providing information of A copilot who copilots a contest of a project
 * This DTO extends CopilotBriefDTO.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 * @since TC Direct - Software Contest Creation Update Assembly
 */
public class ContestCopilotDTO extends CopilotBriefDTO {

    /**
     * The TopCoder user id of this copilot.
     */
    private long userId;

    /**
     * The contest the copilot is assigned.
     */
    private ContestBriefDTO copilotContest;

    /**
     * The direct project the copilot is assigned.
     */
    private ProjectBriefDTO copilotProject;

    /**
     * Gets the TopCoder user id of the copilot
     *
     * @return the TopCoder user id of the copilot
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets the TopCoder user id of the copilot
     *
     * @param userId the user id to set.
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Gets the contest the copilot is assigned.
     *
     * @return the contest the copilot is assigned.
     */
    public ContestBriefDTO getCopilotContest() {
        return copilotContest;
    }

    /**
     * Sets the contest the copilot is assigned.
     *
     * @param copilotContest the contest the copilot is assigned.
     */
    public void setCopilotContest(ContestBriefDTO copilotContest) {
        this.copilotContest = copilotContest;
    }

    /**
     * Gets the direct project the copilot is assigned.
     *
     * @return the direct project info.
     */
    public ProjectBriefDTO getCopilotProject() {
        return copilotProject;
    }

    /**
     * Sets the direct project the copilot is assigned.
     *
     * @param copilotProject
     */
    public void setCopilotProject(ProjectBriefDTO copilotProject) {
        this.copilotProject = copilotProject;
    }
}