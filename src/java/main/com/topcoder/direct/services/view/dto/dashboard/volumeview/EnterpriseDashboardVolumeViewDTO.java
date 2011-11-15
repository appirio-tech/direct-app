/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.volumeview;

import java.io.Serializable;
import java.util.Date;

/**
 * The DTO for store the enterprise dashboard volume view data per month and category.
 *
 * @author TCSDEVELOPER
 * @version 1.0 (Release Assembly - TC Cockpit Enterprise Dashboard Volume View Assembly)
 */
public class EnterpriseDashboardVolumeViewDTO implements Serializable {

    /**
     * Stats date in month.
     */
    private Date statisticDate;

    /**
     * The number of the completed contests.
     */
    private int completedContestsNumber;

    /**
     * The number of failed contests.
     */
    private int failedContestsNumber;

    /**
     * The contest category id.
     */
    private long contestCategoryId;

    /**
     * Gets the stats date.
     *
     * @return the stats date.
     */
    public Date getStatisticDate() {
        return statisticDate;
    }

    /**
     * Sets the statistics date.
     *
     * @param statisticDate the statistic date.
     */
    public void setStatisticDate(Date statisticDate) {
        this.statisticDate = statisticDate;
    }

    /**
     * Gets the number of completed contests.
     *
     * @return the number of completed contests.
     */
    public int getCompletedContestsNumber() {
        return completedContestsNumber;
    }

    /**
     * Sets the number of completed contests.
     *
     * @param completedContestsNumber the number of completed contests.
     */
    public void setCompletedContestsNumber(int completedContestsNumber) {
        this.completedContestsNumber = completedContestsNumber;
    }

    /**
     * Gets the number of failed contests.
     *
     * @return the number of failed contests.
     */
    public int getFailedContestsNumber() {
        return failedContestsNumber;
    }

    /**
     * Sets the number of failed contests.
     *
     * @param failedContestsNumber the number of failed contests.
     */
    public void setFailedContestsNumber(int failedContestsNumber) {
        this.failedContestsNumber = failedContestsNumber;
    }

    /**
     * Gets the contest category id.
     *
     * @return the contest category id.
     */
    public long getContestCategoryId() {
        return contestCategoryId;
    }

    /**
     * Sets the contest category id.
     *
     * @param contestCategoryId the contest category id.
     */
    public void setContestCategoryId(long contestCategoryId) {
        this.contestCategoryId = contestCategoryId;
    }
}
