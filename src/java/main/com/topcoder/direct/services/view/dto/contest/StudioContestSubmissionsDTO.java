/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import java.util.List;
import java.util.Map;

import com.topcoder.clients.model.Project;
import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.form.ContestIdForm;
import com.topcoder.service.project.CompetitionPrize;
import com.topcoder.service.studio.SubmissionData;

/**
 * <p>
 * A <code>DTO</code> class providing the data for displaying by <code>Studio Contest Submissions</code> view.
 * </p>
 * 
 * <p>
 * Version 1.1 (Direct Submission Viewer Release 2) change notes:
 * <ul>
 * <li>Added {@link #prizeNumber} private field and getter/setter for it.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Version 1.2 (Direct Submission Viewer Release 3) change notes:
 * <ul>
 * <li>Added {@link #billingAccounts} private field and getter/setter for it.</li>
 * <li>Added {@link #prizes} private field and getter/setter for it.</li>
 * <li>Added {@link #hasCheckout} private field and getter/setter for it.</li>
 * <li>Added {@link #milestonePrize} private field and getter/setter for it.</li>
 * <li>Added {@link #additionalPrize} private field and getter/setter for it.</li>
 * <li>Added {@link #milestoneAwardNumber} private field and getter/setter for it.</li>
 * <li>Added {@link #paidSubmissions} private field and getter/setter for it.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.4 (Direct Submission Viewer Release 4) change notes:
 * <ul>
 * <li>Added {@link #milestoneRoundFeedbackText} private field and getter/setter for it.</li>
 * </ul>
 * </p>
 *
 * @author isv, flexme, TCSDEVELOPER
 * @since Submission Viewer Release 1 assembly
 * @version 1.4
 */
public class StudioContestSubmissionsDTO extends CommonDTO implements ContestStatsDTO.Aware, ContestIdForm.Aware {

    /**
     * <p>
     * A <code>long</code> providing the ID of contest.
     * </p>
     */
    private long contestId;

    /**
     * <p>
     * A <code>ContestStatsDTO </code> providing the statistics on contest.
     * </p>
     */
    private ContestStatsDTO contestStats;

    /**
     * <p>
     * A <code>List</code> listing the submissions for requested contest.
     * </p>
     */
    private List<SubmissionData> contestSubmissions;

    /**
     * <p>
     * A <code>boolean</code> providing the flag indicating whether contest has milestone round set or not.
     * </p>
     */
    private boolean hasMilestoneRound;

    /**
     * <p>
     * An <code>int</code> providing the number of the prize slots.
     * </p>
     */
    private int prizeNumber;

    /**
     * <p>
     * An <code>List</code> providing the Billing Account of the client.
     * </p>
     */
    private List<Project> billingAccounts;

    /**
     * <p>
     * A <code>boolean</code> providing the flag indicating whether the submissions have already been checked out.
     * </p>
     */
    private boolean hasCheckout;

    /**
     * <p>
     * A <code>List</code> providing the prizes data.
     * </p>
     */
    private List<CompetitionPrize> prizes;

    /**
     * <p>
     * A <code>double</code> providing the milestone prize.
     * </p>
     */
    private double milestonePrize;

    /**
     * <p>
     * A <code>double</code> providing the additional prize.
     * </p>
     */
    private double additionalPrize;

    /**
     * <p>
     * An <code>int</code> providing the number of milestone submissions which should award.
     * </p>
     */
    private int milestoneAwardNumber;

    /**
     * <p>
     * A <code>String</code> providing the submissions id which have been already paid.
     */
    private String paidSubmissions;

    /**
     * <p>A <code>String</code> providing the text for milestone round overall feedback.</p>
     *
     * @since 1.4
     */
    private String milestoneRoundFeedbackText;

    /**
     * <p>A <code>Map</code> providing the handles for submitters.</p>
     *
     * @since 1.4
     */
    private Map<Long, String> submitterHandles;

    /**
     * <p>
     * Constructs new <code>StudioContestSubmissionsDTO</code> instance. This implementation does nothing.
     * </p>
     */
    public StudioContestSubmissionsDTO() {
    }

    /**
     * <p>
     * Gets the statistics on contest.
     * </p>
     * 
     * @return a <code>ContestStatsDTO </code> providing the statistics on contest.
     */
    public ContestStatsDTO getContestStats() {
        return this.contestStats;
    }

    /**
     * <p>
     * Sets the statistics on contest.
     * </p>
     * 
     * @param contestStats
     *            a <code>ContestStatsDTO </code> providing the statistics on contest.
     */
    public void setContestStats(ContestStatsDTO contestStats) {
        this.contestStats = contestStats;
    }

    /**
     * <p>
     * Gets the ID of contest.
     * </p>
     * 
     * @return a <code>long</code> providing the ID of contest.
     */
    public long getContestId() {
        return this.contestId;
    }

    /**
     * <p>
     * Sets the ID of contest.
     * </p>
     * 
     * @param contestId
     *            a <code>long</code> providing the ID of contest.
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * <p>
     * Gets the list of submissions for contest.
     * </p>
     * 
     * @return a <code>List</code> listing the submissions for requested contest.
     */
    public List<SubmissionData> getContestSubmissions() {
        return contestSubmissions;
    }

    /**
     * <p>
     * Sets the list of submissions for contest.
     * </p>
     * 
     * @param contestSubmissions
     *            a <code>List</code> listing the submissions for requested contest.
     */
    public void setContestSubmissions(List<SubmissionData> contestSubmissions) {
        this.contestSubmissions = contestSubmissions;
    }

    /**
     * <p>
     * Gets the flag indicating whether contest has milestone round set or not.
     * </p>
     * 
     * @return a <code>boolean</code> providing the flag indicating whether contest has milestone round set or not.
     */
    public boolean getHasMilestoneRound() {
        return this.hasMilestoneRound;
    }

    /**
     * <p>
     * Sets the flag indicating whether contest has milestone round set or not.
     * </p>
     * 
     * @param hasMilestoneRound
     *            a <code>boolean</code> providing the flag indicating whether contest has milestone round set or not.
     */
    public void setHasMilestoneRound(boolean hasMilestoneRound) {
        this.hasMilestoneRound = hasMilestoneRound;
    }

    /**
     * <p>
     * Gets the number of the prize slots.
     * </p>
     * 
     * @return An <code>int</code> providing the number of the prize slots
     * @since 1.1
     */
    public int getPrizeNumber() {
        return prizeNumber;
    }

    /**
     * <p>
     * Sets the number of the prize slots.
     * </p>
     * 
     * @param prizeNumber
     *            An <code>int</code> providing the number of the prize slots
     * @since 1.1
     */
    public void setPrizeNumber(int prizeNumber) {
        this.prizeNumber = prizeNumber;
    }

    /**
     * <p>
     * Gets the total number of submissions for requested contest.
     * </p>
     * 
     * @return an <code>int</code> providing the total number of submissions for requested contest.
     */
    public int getSubmissionsCount() {
        List<SubmissionData> submissions = getContestSubmissions();
        if (submissions != null) {
            return submissions.size();
        } else {
            return 0;
        }
    }

    /**
     * <p>
     * Gets the billing accounts of the client.
     * </p>
     * 
     * @return a <code>List</code> providing the billing accounts of the client.
     * @since 1.2
     */
    public List<Project> getBillingAccounts() {
        return billingAccounts;
    }

    /**
     * <p>
     * Sets the billing accounts of the client.
     * </p>
     * 
     * @param a
     *            <code>List</code> providing the billing accounts of the client.
     * @since 1.2
     */
    public void setBillingAccounts(List<Project> billingAccounts) {
        this.billingAccounts = billingAccounts;
    }

    /**
     * Gets the flag indicating whether the submissions have already been checked out.
     * 
     * @return A <code>boolean</code> providing the flag indicating whether the submissions have already been checked
     *         out.
     * @since 1.2
     */
    public boolean getHasCheckout() {
        return hasCheckout;
    }

    /**
     * Sets the flag indicating whether the submissions have already been checked out.
     * 
     * @param hasCheckout
     *            A <code>boolean</code> providing the flag indicating whether the submissions have already been checked
     *            out.
     * @since 1.2
     */
    public void setHasCheckout(boolean hasCheckout) {
        this.hasCheckout = hasCheckout;
    }

    /**
     * Gets the prizes data.
     * 
     * @return A <code>List</code> providing the prizes data.
     * @since 1.2
     */
    public List<CompetitionPrize> getPrizes() {
        return prizes;
    }

    /**
     * Sets the prizes data.
     * 
     * @param prizes
     *            A <code>List</code> providing the prizes data.
     * @since 1.2
     */
    public void setPrizes(List<CompetitionPrize> prizes) {
        this.prizes = prizes;
    }

    /**
     * Gets the milestone prize.
     * 
     * @return A <code>double</code> providing the milestone prize.
     * @since 1.2
     */
    public double getMilestonePrize() {
        return milestonePrize;
    }

    /**
     * Sets the milestone prize.
     * 
     * @param milestonePrize
     *            A <code>double</code> providing the milestone prize.
     * @since 1.2
     */
    public void setMilestonePrize(double milestonePrize) {
        this.milestonePrize = milestonePrize;
    }

    /**
     * Gets the additional prize.
     * 
     * @return A <code>double</code> providing the additional prize.
     * @since 1.2
     */
    public double getAdditionalPrize() {
        return additionalPrize;
    }

    /**
     * Sets the additional prize.
     * 
     * @param additionalPrize
     *            A <code>double</code> providing the additional prize.
     * @since 1.2
     */
    public void setAdditionalPrize(double additionalPrize) {
        this.additionalPrize = additionalPrize;
    }

    /**
     * Gets the number of milestone submissions which should award.
     * 
     * @return An <code>int</code> providing the number of milestone submissions which should award.
     * @since 1.2
     */
    public int getMilestoneAwardNumber() {
        return milestoneAwardNumber;
    }

    /**
     * Sets the number of milestone submissions which should award.
     * 
     * @param milestoneAwardNumber
     *            An <code>int</code> providing the number of milestone submissions which should award.
     * @since 1.2
     */
    public void setMilestoneAwardNumber(int milestoneAwardNumber) {
        this.milestoneAwardNumber = milestoneAwardNumber;
    }

    /**
     * Gets the submission id which have been already been paid.
     * 
     * @return the submission id which have been already been paid.
     * @since 1.2
     */
    public String getPaidSubmissions() {
        return paidSubmissions;
    }

    /**
     * Sets the submission id which have been already been paid.
     * 
     * @param paidSubmissions
     *            the submission id which have been already been paid.
     * @since 1.2
     */
    public void setPaidSubmissions(String paidSubmissions) {
        this.paidSubmissions = paidSubmissions;
    }

    /**
     * <p>Gets the text for milestone round overall feedback.</p>
     *
     * @return a <code>String</code> providing the text for milestone round overall feedback.
     * @since 1.4
     */
    public String getMilestoneRoundFeedbackText() {
        return this.milestoneRoundFeedbackText;
    }

    /**
     * <p>Sets the text for milestone round overall feedback.</p>
     *
     * @param milestoneRoundFeedbackText a <code>String</code> providing the text for milestone round overall feedback.
     * @since 1.4
     */
    public void setMilestoneRoundFeedbackText(String milestoneRoundFeedbackText) {
        this.milestoneRoundFeedbackText = milestoneRoundFeedbackText;
    }

    /**
     * <p>Gets the handles for submitters.</p>
     *
     * @return a <code>Map</code> providing the handles for submitters.
     * @since 1.4
     */
    public Map<Long, String> getSubmitterHandles() {
        return this.submitterHandles;
    }

    /**
     * <p>Sets the handles for submitters.</p>
     *
     * @param submitterHandles a <code>Map</code> providing the handles for submitters.
     * @since 1.4
     */
    public void setSubmitterHandles(Map<Long, String> submitterHandles) {
        this.submitterHandles = submitterHandles;
    }
}
