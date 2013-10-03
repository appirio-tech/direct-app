/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project.milestone;

import com.topcoder.direct.services.project.milestone.model.Milestone;

import java.io.Serializable;
import java.util.List;

/**
 * The DTO to represent a milestone which contains contest associations.
 *
 * @author TCSASSEMBLER
 * @version 1.0
 * @since 1.0 (Module Assembly - TC Cockpit Contest Milestone Association Milestone Page Update)
 */
public class ProjectMilestoneDTO implements Serializable {
    /**
     * The milestone.
     */
    private Milestone milestone;

    /**
     * The contests associated with the milestone.
     */
    private List<MilestoneContestDTO> contests;

    /**
     * Gets the milestone.
     *
     * @return the milestone.
     */
    public Milestone getMilestone() {
        return milestone;
    }

    /**
     * Sets the milestone.
     *
     * @param milestone the milestone.
     */
    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    /**
     * Gets the contests.
     *
     * @return the contests.
     */
    public List<MilestoneContestDTO> getContests() {
        return contests;
    }

    /**
     * Sets the contests.
     *
     * @param contests the contests.
     */
    public void setContests(List<MilestoneContestDTO> contests) {
        this.contests = contests;
    }

    /**
     * Gets the completed contest number of the milestone.
     *
     * @return the completed contest number of the milestone.
     */
    public int getCompletedContestNumber() {
        int count = 0;
        if (contests != null) {
            for (MilestoneContestDTO mcd : contests) {
                if (!mcd.getContestStatus().toLowerCase().equals("active") &&
                        !mcd.getContestStatus().toLowerCase().equals("draft")) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Gets the total contest number of the milestone.
     *
     * @return the total contest number of the milestone.
     */
    public int getTotalContestNumber() {
        return contests == null ? 0 : contests.size();
    }
}
