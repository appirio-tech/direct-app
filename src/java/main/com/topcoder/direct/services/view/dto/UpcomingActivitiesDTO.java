/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto;

import java.io.Serializable;
import java.util.List;

/**
 * <p>A DTO class providing the data for upcoming activities on projects associated with current user.</p>
 *
 * @author isv
 * @version 1.0
 */
public class UpcomingActivitiesDTO implements Serializable {

    /**
     * <p>An interface to be implemented by the parties interested in getting the details on upcoming activities on
     * projects associated with current user.</p>
     */
    public static interface Aware {

        /**
         * <p>Sets the details on upcoming activities for projects associated with current user.</p>
         *
         * @param upcomingActivities a <code>LatestActivitiesDTO</code> providing the details on latest activities for
         *        projects associated with current user.
         */
        void setUpcomingActivities(UpcomingActivitiesDTO upcomingActivities);

    }

    /**
     * <p>A <code>List</code> listing the upcoming activities on projects associated with current user.</p>
     */
    private List<ActivityDTO> activities;

    /**
     * <p>Constructs new <code>UpcomingActivitiesDTO</code> instance. This implementation does nothing.</p>
     */
    public UpcomingActivitiesDTO() {
    }

    /**
     * <p>Gets the list of upcoming activities</p>
     *
     * @return a <code>List</code> listing the upcoming activities on projects associated with current user.
     */
    public List<ActivityDTO> getActivities() {
        return activities;
    }

    /**
     * <p>Sets the list of upcoming activities</p>
     *
     * @param activities a <code>List</code> listing the upcoming activities on projects associated with current user.
     */
    public void setActivities(List<ActivityDTO> activities) {
        this.activities = activities;
    }
}
