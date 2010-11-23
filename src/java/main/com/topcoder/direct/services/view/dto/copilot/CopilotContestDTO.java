/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.copilot;

import java.io.Serializable;
import java.util.List;

import com.topcoder.direct.services.view.dto.contest.ContestBriefDTO;

/**
 * <p>
 * A DTO used to store information of copilot contest.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0
 * @since TC Direct Manage Copilots Assembly
 */
public class CopilotContestDTO implements Serializable {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 3232556427768610904L;

    /**
     * Some basic information of this contest.
     */
    private ContestBriefDTO contest;
    
    /**
     * The list of copilots.
     */
    private List<String> copilots;

    /**
     * Retrieves the contest field.
     *
     * @return the contest
     */
    public ContestBriefDTO getContest() {
        return contest;
    }

    /**
     * Sets the contest field.
     *
     * @param contest the contest to set
     */
    public void setContest(ContestBriefDTO contest) {
        this.contest = contest;
    }

    /**
     * Retrieves the copilots field.
     *
     * @return the copilots
     */
    public List<String> getCopilots() {
        return copilots;
    }

    /**
     * Sets the copilots field.
     *
     * @param copilots the copilots to set
     */
    public void setCopilots(List<String> copilots) {
        this.copilots = copilots;
    }
    
}
