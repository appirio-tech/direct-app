/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto;

import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>A DTO class providing the data for latest activities on projects associated with current user.</p>
 *
 * @author isv
 * @version 1.0
 */
public class LatestActivitiesDTO implements Serializable {

    /**
     * <p>An interface to be implemented by the parties interested in getting the details on latest activities on
     * projects associated with current user.</p>
     */
    public static interface Aware {

        /**
         * <p>Sets the details on latest activities for projects associated with current user.</p>
         *
         * @param latestActivities a <code>LatestActivitiesDTO</code> providing the details on latest activities for
         *        projects associated with current user.
         */
        void setLatestActivities(LatestActivitiesDTO latestActivities);

    }

    /**
     * <p>A <code>Map</code> mapping the projects associated with current user to list of latest activities on those
     * projects.</p>
     */
    private Map<ProjectBriefDTO, List<ActivityDTO>> activities;

    /**
     * <p>Constructs new <code>LatestActivitiesDTO</code> instance. This implementation does nothing.</p>
     */
    public LatestActivitiesDTO() {
    }

    /**
     * <p>Gets the latest activities on projects associated with current user.</p>
     *
     * @return a <code>Map</code> mapping the projects associated with current user to list of latest activities on
     *         those projects.
     */
    public Map<ProjectBriefDTO, List<ActivityDTO>> getActivities() {
        

        return activities;
    }

    /**
     * <p>Sets the latest activities on projects associated with current user.</p>
     *
     * @param activities a <code>Map</code> mapping the projects associated with current user to list of latest
     *        activities on those projects.
     */
    public void setActivities(Map<ProjectBriefDTO, List<ActivityDTO>> activities) {
        this.activities = activities;
    }
}
