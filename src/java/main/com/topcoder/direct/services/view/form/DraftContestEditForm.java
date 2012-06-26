/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

import java.io.Serializable;

/**
 * <p>The form to store the changes to a draft contest</p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TC Cockpit Project Contests Batch Edit)
 */
public class DraftContestEditForm implements Serializable {

    /**
     * The id of the contest.
     */
    private long contestId;

    /**
     * The name of the contest.
     */
    private String contestName;

    /**
     * The id of the contest type.
     */
    private long contestTypeId;

    /**
     * The id of the contest billing account.
     */
    private long contestBillingAccountId;

    /**
     * The start date of the contest.
     */
    private String contestStartDate;

    /**
     * The id of the contest.
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
     * Gets the id of the contest type.
     *
     * @return the id of the contest type.
     */
    public long getContestTypeId() {
        return contestTypeId;
    }

    /**
     * Sets the id of the contest type.
     *
     * @param contestTypeId the id of the contest type.
     */
    public void setContestTypeId(long contestTypeId) {
        this.contestTypeId = contestTypeId;
    }

    /**
     * Gets the id of the contest billing account.
     *
     * @return the id of the contest billing account.
     */
    public long getContestBillingAccountId() {
        return contestBillingAccountId;
    }

    /**
     * Sets the id of the contest billing account.
     *
     * @param contestBillingAccountId the id of the contest billing account.
     */
    public void setContestBillingAccountId(long contestBillingAccountId) {
        this.contestBillingAccountId = contestBillingAccountId;
    }

    /**
     * Gets the start date of the contest.
     *
     * @return the start date of the contest.
     */
    public String getContestStartDate() {
        return contestStartDate;
    }

    /**
     * Sets the start date of the contest.
     *
     * @param contestStartDate the start date of the contest.
     */
    public void setContestStartDate(String contestStartDate) {
        this.contestStartDate = contestStartDate;
    }

    /**
     * Gets the contest name.
     *
     * @return the contest name.
     */
    public String getContestName() {
        return contestName;
    }

    /**
     * Sets the contest name.
     *
     * @param contestName the contest name.
     */
    public void setContestName(String contestName) {
        this.contestName = contestName;
    }
}
