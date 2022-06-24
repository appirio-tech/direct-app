/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.enterpriseDashboard;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *     The DTO to represent the enterprise dashboard contests pipeline data of a single month.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0
 * @since Module Assembly - TC Cockpit Enterprise Dashboard Pipeline Part
 */
public class EnterpriseDashboardMonthPipelineDTO implements Serializable {

    /**
     * The total number of active contests.
     */
    private int totalActiveContests;

    /**
     * The total number of draft contests.
     */
    private int totalDraftContests;

    /**
     * The total number of scheduled contests.
     */
    private int totalScheduledContests;

    /**
     * The total number of completed contests.
     */
    private int totalCompletedContests;

    /**
     * The total number of failed contests.
     */
    private int totalFailedContests;

    /**
     * The date representing the month
     */
    private Date date;

    /**
     * Gets the total number of active contests.
     *
     * @return the total number of active contests.
     */
    public int getTotalActiveContests() {
        return totalActiveContests;
    }

    /**
     * Sets the total number of active contests.
     *
     * @param totalActiveContests the total number of active contests.
     */
    public void setTotalActiveContests(int totalActiveContests) {
        this.totalActiveContests = totalActiveContests;
    }

    /**
     * Gets the total number of draft contests.
     *
     * @return the total number of draft contests.
     */
    public int getTotalDraftContests() {
        return totalDraftContests;
    }

    /**
     * Sets the total number of draft contests.
     *
     * @param totalDraftContests the total number of draft contests.
     */
    public void setTotalDraftContests(int totalDraftContests) {
        this.totalDraftContests = totalDraftContests;
    }

    /**
     * Gets the total number of scheduled contests.
     *
     * @return the total number of scheduled contests.
     */
    public int getTotalScheduledContests() {
        return totalScheduledContests;
    }

    /**
     * Sets the total number of scheduled contests.
     *
     * @param totalScheduledContests the total number of scheduled contests.
     */
    public void setTotalScheduledContests(int totalScheduledContests) {
        this.totalScheduledContests = totalScheduledContests;
    }

    /**
     * Gets total number of completed contests.
     *
     * @return the total number of completed contests.
     */
    public int getTotalCompletedContests() {
        return totalCompletedContests;
    }

    /**
     * Sets the total number of completed contests.
     *
     * @param totalCompletedContests the total number of completed contests.
     */
    public void setTotalCompletedContests(int totalCompletedContests) {
        this.totalCompletedContests = totalCompletedContests;
    }

    /**
     * Gets the total number of failed contests.
     *
     * @return the total number of failed contests.
     */
    public int getTotalFailedContests() {
        return totalFailedContests;
    }

    /**
     * Sets the total number of failed contests.
     *
     * @param totalFailedContests the total number of failed contests.
     */
    public void setTotalFailedContests(int totalFailedContests) {
        this.totalFailedContests = totalFailedContests;
    }

    /**
     * Gets the date.
     *
     * @return the date.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date.
     *
     * @param date the date.
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
