/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project;

import com.topcoder.direct.services.view.dto.ActivityDTO;
import com.topcoder.direct.services.view.dto.contest.ContestBriefDTO;

import java.util.List;
import java.util.Map;

/**
 * <p>A DTO class providing the data for latest activities on requested project associated with current user.</p>
 *
 * @author isv
 * @version 1.0
 */
public class LatestProjectActivitiesDTO {

    /**
     * <p>An interface to be implemented by the parties interested in getting the details on latest activities on
     * requested project associated with current user.</p>
     */
    public static interface Aware {

        /**
         * <p>Sets the details on latest activities for requested project associated with current user.</p>
         *
         * @param latestActivities a <code>LatestProjectActivitiesDTO</code> providing the details on latest activities
         *        for requested project associated with current user.
         */
        void setLatestProjectActivities(LatestProjectActivitiesDTO latestActivities);

    }

    /**
     * <p>A <code>Map</code> mapping the contests associated with requested project for current user to list of latest
     * activities on those contests.</p>
     */
    private Map<ContestBriefDTO, List<ActivityDTO>> activities;

    /**
     * <p>Constructs new <code>LatestProjectActivitiesDTO</code> instance. This implementation does nothing.</p>
     */
    public LatestProjectActivitiesDTO() {
    }

    /**
     * <p>Gets the activities on contests for the requested project associated with current user.</p>
     *
     * @return a <code>List</code> listing the activities on contests associated with requested project for current
     *         user.
     */
    public Map<ContestBriefDTO, List<ActivityDTO>> getActivities() {
        return this.activities;
    }

    /**
     * <p>Sets the activities on contests for the requested project associated with current user.</p>
     *
     * @param activities a <code>List</code> listing the activities on contests associated with requested project for
     *        current user.
     */
    public void setActivities(Map<ContestBriefDTO, List<ActivityDTO>> activities) {
        this.activities = activities;
    }
}
