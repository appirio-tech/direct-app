/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto;

import com.topcoder.direct.services.view.dto.contest.ContestBriefDTO;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>A DTO class providing the details on a single activity on a contest associated with current user.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ActivityDTO implements Serializable {

    /**
     * <p>A <code>long</code> providing the ID of a user who have performed the action reflected by this activity
     * record.</p>
     */
    private long originatorId;

    /**
     * <p>A <code>String</code> providing the handle of a user who have performed the action reflected by this activity
     * record.</p>
     */
    private String originatorHandle;

    /**
     * <p>A <code>ContestBriefDTO</code> providing the details on contest associated with this activity.</p>
     */
    private ContestBriefDTO contest;

    /**
     * <p>A <code>Date</code> providing the timestamp for the activity.</p>
     */
    private Date date;

    /**
     * <p>An <code>ActivityType</code> referencing the type of the performed activity.</p>
     */
    private ActivityType type;

    /**
     * <p>Constructs new <code>ActivityDTO</code> instance. This implementation does nothing.</p>
     */
    public ActivityDTO() {
    }

    /**
     * <p>Gets the ID of a user associated wit this activity.</p>
     *
     * @return a <code>long</code> providing the ID of a user who have performed the action reflected by this activity
     * record
     */
    public long getOriginatorId() {
        return originatorId;
    }

    /**
     * <p>Sets the ID of a user associated wit this activity.</p>
     *
     * @param originatorId a <code>long</code> providing the ID of a user who have performed the action reflected by
     *        this activity record
     */
    public void setOriginatorId(long originatorId) {
        this.originatorId = originatorId;
    }

    /**
     * <p>Gets the handle of a user associated wit this activity.</p>
     *
     * @return a <code>String</code> providing the handle of a user who have performed the action reflected by this
     *         activity record.
     */
    public String getOriginatorHandle() {
        return originatorHandle;
    }

    /**
     * <p>Sets the handle of a user associated wit this activity.</p>
     *
     * @param originatorHandle a <code>String</code> providing the handle of a user who have performed the action
     *        reflected by this activity record.
     */
    public void setOriginatorHandle(String originatorHandle) {
        this.originatorHandle = originatorHandle;
    }

    /**
     * <p>Gets the details for contest associated with this activity.</p>
     *
     * @return a <code>ContestBriefDTO</code> providing the details on contest associated with this activity.
     */
    public ContestBriefDTO getContest() {
        return contest;
    }

    /**
     * <p>Sets the details for contest associated with this activity.</p>
     *
     * @param contest a <code>ContestBriefDTO</code> providing the details on contest associated with this activity.
     */
    public void setContest(ContestBriefDTO contest) {
        this.contest = contest;
    }

    /**
     * <p>Gets the timestamp for this activity.</p>
     *
     * @return a <code>Date</code> providing the timestamp for the activity.
     */
    public Date getDate() {
        return date;
    }

    /**
     * <p>Sets the timestamp for this activity.</p>
     *
     * @param date a <code>Date</code> providing the timestamp for the activity.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * <p>Gets the type of this activity.</p>
     *
     * @return an <code>ActivityType</code> referencing the type of the performed activity.
     */
    public ActivityType getType() {
        return type;
    }

    /**
     * <p>Sets the type of this activity.</p>
     *
     * @param type an <code>ActivityType</code> referencing the type of the performed activity.
     */
    public void setType(ActivityType type) {
        this.type = type;
    }
}
