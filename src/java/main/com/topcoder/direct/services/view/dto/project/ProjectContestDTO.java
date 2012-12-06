/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
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
 * <p>
 * Version 1.1 (Release Assembly - TC Direct Cockpit Release Eight)
 * <ul>
 *     <li>Add new property {@link #status2}</li>
 *     <li>Add new property {@link #status3}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.2 (BUGR-7592 Cockpit Release Eight Miscellaneous Fixes)
 * <ul>
 *     <li>Add properties {@link #isStatusLate}, {@link #isStatus2Late} and {@link #isStatus3Late}</li>
 * </ul>
 * </p>
 *
 * @author isv, GreatKevin
 * @version 1.2
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
     * <p>The second <code>ContestStatus</code> providing current contest status.</p>
     *
     * @since 1.1
     */
    private ContestStatus status2;

    /**
     * <p>The third <code>ContestStatus</code> providing current contest status.</p>
     *
     * @since 1.1
     */
    private ContestStatus status3;

    /**
     * <p>
     * Whether the status is late.
     * </p>
     *
     * @since 1.2
     */
    private boolean isStatusLate;

    /**
     * <p>
     * Whether the status2 is late.
     * </p>
     *
     * @since 1.2
     */
    private boolean isStatus2Late;

    /**
     * <p>
     * Whether the status3 is late.
     * </p>
     *
     * @since 1.2
     */
    private boolean isStatus3Late;

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
     * <p>A <code>int</code> providing the id of the contest forum.</p> 
     */
    private int forumId;
	
	private Boolean isStudio;
	
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
     * <p>Gets the id of forum for contest.</p>
     *
     * @return a <code>int</code> providing the id of forum for contest.
     */
    public int getForumId() {
        return this.forumId;
    }

    /**
     * <p>Sets the id of forum for contest.</p>
     *
     * @param forumId a <code>int</code> providing the id of forum for contest.
     */
    public void setForumId(int forumId) {
        this.forumId = forumId;
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

    /**
     * Gets whether the contest is studio.
     *
     * @return whether the contest is studio.
     */
	public Boolean getIsStudio() {
		return isStudio;
	}

    /**
     * Sets whether the contest is studio.
     *
     * @param isStudio whether the contest is studio.
     */
	public void setIsStudio(Boolean isStudio) {
		this.isStudio = isStudio;
	}

    /**
     * Gets the second contest phase status.
     *
     * @return the second contest phase status.
     *
     * @since 1.1
     */
    public ContestStatus getStatus2() {
        return status2;
    }

    /**
     * Sets the second contest phase status.
     *
     * @param status2 the second contest phase status.
     * @since 1.1
     */
    public void setStatus2(ContestStatus status2) {
        this.status2 = status2;
    }

    /**
     * Sets the third contest phase status.
     *
     * @return the third contest phase status.
     * @since 1.1
     */
    public ContestStatus getStatus3() {
        return status3;
    }

    /**
     * Gets the third contest phase status.
     *
     * @return the third contest phase status.
     * @since 1.1
     */
    public void setStatus3(ContestStatus status3) {
        this.status3 = status3;
    }

    /**
     * Gets whether the status is late.
     *
     * @return whether the status is late.
     * @since 1.2
     */
    public boolean isStatusLate() {
        return isStatusLate;
    }

    /**
     * Sets whether the status is late.
     *
     * @param statusLate whether the status is late.
     * @since 1.2
     */
    public void setStatusLate(boolean statusLate) {
        isStatusLate = statusLate;
    }


    /**
     * Gets whether the status2 is late.
     *
     * @return whether the status2 is late.
     * @since 1.2
     */
    public boolean isStatus2Late() {
        return isStatus2Late;
    }

    /**
     * Sets whether the status2 is late.
     *
     * @param status2Late whether the status2 is late.
     * @since 1.2
     */
    public void setStatus2Late(boolean status2Late) {
        isStatus2Late = status2Late;
    }

    /**
     * Gets whether the status3 is late.
     *
     * @return whether the status3 is late.
     * @since 1.2
     */
    public boolean isStatus3Late() {
        return isStatus3Late;
    }

    /**
     * Sets whether the status3 is late.
     *
     * @param status3Late whether the status3 is late.
     * @since 1.2
     */
    public void setStatus3Late(boolean status3Late) {
        isStatus3Late = status3Late;
    }
}
