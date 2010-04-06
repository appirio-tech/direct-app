/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project;

import com.topcoder.direct.services.view.dto.contest.ContestBriefDTO;
import com.topcoder.direct.services.view.dto.contest.ContestStatus;
import com.topcoder.direct.services.view.dto.contest.ContestType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>A DTO class providing the details for single contest displayed by <code>Project Contests</code> view.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ProjectContestDTO implements Serializable {

    /**
     * <p>A <code>ContestBriefDTO</code> providing the details for contest.</p>
     */
    private ContestBriefDTO contest;

    /**
     * <p>An <code>int</code> providing the number of registrants for contest.</p>
     */
    private int registrantsNumber;

    /**
     * <p>An <code>int</code> providing the number of submissions for contest.</p>
     */
    private int submissionsNumber;

    /**
     * <p>An <code>int</code> providing the number of forum posts for contest.</p>
     */
    private int forumPostsNumber;

    /**
     * <p>A <code>ContestStatus</code> providing current contest status.</p>
     */
    private ContestStatus status;

    /**
     * <p>A <code>Date</code> providing the start time for contest.</p>
     */
    private Date startTime;

    /**
     * <p>A <code>Date</code> providing the end time for contest.</p>
     */
    private Date endTime;


    /**
     * <p>A <code>ContestType</code> providing the contest type.</p>
     */
    private ContestType contestType;

    /**
     * <p>Constructs new <code>ProjectContestDTO</code> instance. This implementation does nothing.</p>
     */
    public ProjectContestDTO() {
    }

    /**
     * <p>Gets the details for contest.</p>
     *
     * @return a <code>ContestBriefDTO</code> providing the details for contest.
     */
    public ContestBriefDTO getContest() {
        return contest;
    }

    /**
     * <p>Sets the details for contest.</p>
     *
     * @param contest a <code>ContestBriefDTO</code> providing the details for contest.
     */
    public void setContest(ContestBriefDTO contest) {
        this.contest = contest;
    }

    /**
     * <p>Gets the number of registrants for contest.</p>
     *
     * @return an <code>int</code> providing the number of registrants for contest.
     */
    public int getRegistrantsNumber() {
        return registrantsNumber;
    }

    /**
     * <p>Sets the number of registrants for contest.</p>
     *
     * @param registrantsNumber an <code>int</code> providing the number of registrants for contest.
     */
    public void setRegistrantsNumber(int registrantsNumber) {
        this.registrantsNumber = registrantsNumber;
    }

    /**
     * <p>Gets the number of registrants for contest.</p>
     *
     * @return an <code>int</code> providing the number of registrants for contest.
     */
    public int getSubmissionsNumber() {
        return submissionsNumber;
    }

    /**
     * <p>Sets the number of registrants for contest.</p>
     *
     * @param submissionsNumber an <code>int</code> providing the number of registrants for contest.
     */
    public void setSubmissionsNumber(int submissionsNumber) {
        this.submissionsNumber = submissionsNumber;
    }

    /**
     * <p>Gets the number of forum posts for contest.</p>
     *
     * @return an <code>int</code> providing the number of forum posts for contest.
     */
    public int getForumPostsNumber() {
        return forumPostsNumber;
    }

    /**
     * <p>Sets the number of forum posts for contest.</p>
     *
     * @param forumPostsNumber an <code>int</code> providing the number of forum posts for contest.
     */
    public void setForumPostsNumber(int forumPostsNumber) {
        this.forumPostsNumber = forumPostsNumber;
    }

    /**
     * <p>Gets current contest status.</p>
     *
     * @return a <code>ContestStatus</code> providing current contest status.
     */
    public ContestStatus getStatus() {
        return status;
    }

    /**
     * <p>Sets current contest status.</p>
     *
     * @param status a <code>ContestStatus</code> providing current contest status.
     */
    public void setStatus(ContestStatus status) {
        this.status = status;
    }

    /**
     * <p>Gets the start time for contest.</p>
     *
     * @return a <code>Date</code> providing the start time for contest.
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * <p>Sets the start time for contest.</p>
     *
     * @param startTime a <code>Date</code> providing the start time for contest.
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * <p>Gets the end time for contest.</p>
     *
     * @return a <code>Date</code> providing the end time for contest.
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * <p>Sets the end time for contest.</p>
     *
     * @param endTime a <code>Date</code> providing the end time for contest.
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * <p>Gets the contest type.</p>
     *
     * @return a <code>ContestType</code> providing the contest type.
     */
    public ContestType getContestType() {
        return contestType;
    }

    /**
     * <p>Sets the contest type.</p>
     *
     * @param contestType a <code>ContestType</code> providing the contest type.
     */
    public void setContestType(ContestType contestType) {
        this.contestType = contestType;
    }
}
