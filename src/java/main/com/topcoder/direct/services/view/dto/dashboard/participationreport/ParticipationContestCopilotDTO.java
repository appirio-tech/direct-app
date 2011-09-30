/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.participationreport;

import java.io.Serializable;

/**
 * <p>
 * The DTO stores the the copilot for a contest.
 * </p>
 *
 * @author TCSASSEMBER
 * @version  1.0 (TC Cockpit Participation Metrics Report Part One Assembly 1)
 */
public class ParticipationContestCopilotDTO implements Serializable {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -1467045333052476441L;

    /**
     * Represents the id of the direct project.
     */
    private long projectId;
    
    /**
     * Represents the id of the contest.
     */
    private long contestId;
    
    /**
     * Represents the id of the copilot.
     */
    private long copilot;
    
    /**
     * Empty constructor.
     */
    public ParticipationContestCopilotDTO() {
        
    }

    /**
     * Gets the id of the direct project.
     * 
     * @return the id of the direct project.
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Sets the id of the direct project.
     * 
     * @param projectId the id of the direct project.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the id of the contest.
     * 
     * @return the id of the contest.
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Sets the id of the contest.
     * 
     * @param contestId the id of the contest.
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * Gets the id of the copilot.
     * 
     * @return the id of the copilot.
     */
    public long getCopilot() {
        return copilot;
    }

    /**
     * Sets the id of the copilot.
     * 
     * @param copilot the id of the copilot.
     */
    public void setCopilot(long copilot) {
        this.copilot = copilot;
    }
}
