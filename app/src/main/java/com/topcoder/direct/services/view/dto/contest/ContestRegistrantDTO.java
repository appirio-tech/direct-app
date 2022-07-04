/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>A DTO class providing details for a single registrant to a contest.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ContestRegistrantDTO implements Serializable {

    /**
     * <p>An interface to be implemented by the parties interested in getting the list of registrants for desired
     * contest.</p>
     */
    public static interface Aware {

        /**
         * <p>Gets the ID of contest to get registrants for.</p>
         *
         * @return a <code>long</code> providing the ID of contest to get registrants for.
         */
        long getContestId();

        /**
         * <p>Sets the list of registrants for requested contest.</p>
         *
         * @param registrants a <code>ContestRegistrantDTO</code> providing the details on registrants for requested
         *        contest.
         */
        void setContestRegistrants(List<ContestRegistrantDTO> registrants);

    }

    /**
     * <p>A <code>ContestBriefDTO</code> providing the details for the contest.</p>
     */
    private ContestBriefDTO contest;

    /**
     * <p>A <code>long</code> providing the ID for registrant's user account.</p>
     */
    private long userId;

    /**
     * <p>A <code>String</code> providing the username for registrant.</p>
     */
    private String username;

    /**
     * <p>A <code>Date</code> providing the date of user registration to contest.</p>
     */
    private Date registrationDate;

    /**
     * <p>A <code>Date</code> providing the date of user submission to contest.</p>
     */
    private Date submissionDate;

    /**
     * <p>A <code>boolean</code> flag indicating whether the registrant is rated or not.</p>
     */
    private boolean rated;

    /**
     * <p>Constructs new <code>ContestRegistrantDTO</code> instance. This implementation does nothing.</p>
     */
    public ContestRegistrantDTO() {
    }

    /**
     * <p>Gets the user ID.</p>
     *
     * @return a <code>long</code> providing the user ID.
     */
    public long getUserId() {
        return userId;
    }

    /**
     * <p>Sets the user ID.</p>
     *
     * @param userId a <code>long</code> providing the user ID.
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * <p>Gets the username for registrant.</p>
     *
     * @return a <code>String</code> providing the username for registrant.
     */
    public String getUsername() {
        return username;
    }

    /**
     * <p>Sets the username for registrant.</p>
     *
     * @param username a <code>String</code> providing the username for registrant.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * <p>Gets the date of registration to contest by registrant.</p>
     *
     * @return a <code>Date</code> providing the date of registration to contest.
     */
    public Date getRegistrationDate() {
        return registrationDate;
    }

    /**
     * <p>Sets the date of registration to contest by registrant.</p>
     *
     * @param registrationDate a <code>Date</code> providing the date of registration to contest.
     */
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * <p>Gets the date of submission to contest by registrant.</p>
     *
     * @return a <code>Date</code> providing the date of submission to contest.
     */
    public Date getSubmissionDate() {
        return submissionDate;
    }

    /**
     * <p>Sets the date of submission to contest by registrant.</p>
     *
     * @param submissionDate a <code>Date</code> providing the date of submission to contest.
     */
    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    /**
     * <p>Checks whether the registrant is rated or not.</p>
     *
     * @return <code>true</code> if registrant is rated; <code>false</code> otherwise.
     */
    public boolean isRated() {
        return rated;
    }

    /**
     * <p>Sets the flag indicating whether the registrant is rated or not.</p>
     *
     * @param rated <code>true</code> if registrant is rated; <code>false</code> otherwise.
     */
    public void setRated(boolean rated) {
        this.rated = rated;
    }

    /**
     * <p>Gets the details for the contest.</p>
     *
     * @return a <code>ContestBriefDTO</code> providing the details for the contest.
     */
    public ContestBriefDTO getContest() {
        return contest;
    }

    /**
     * <p>Sets the details for the contest.</p>
     *
     * @param contest a <code>ContestBriefDTO</code> providing the details for the contest.
     */
    public void setContest(ContestBriefDTO contest) {
        this.contest = contest;
    }
}
