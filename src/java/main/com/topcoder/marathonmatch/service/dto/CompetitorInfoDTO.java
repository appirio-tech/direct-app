/*
 * Copyright (C) 2001 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.marathonmatch.service.dto;

import java.util.Date;

/**
 * This DTO holds a single competitor's information.
 * This class extend from <code>RegistrantInfo</code> class because any competitor is a registrant first.
 *
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable and not thread-safe.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0
 * @since 1.0 (Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Competitors Tab)
 */
public class CompetitorInfoDTO extends RegistrantInfo {
    /**
     * The rank of competitor.
     */
    private Integer rank;

    /**
     * The provisional score.
     */
    private Double provisionalScore;

    /**
     * The last submission time.
     */
    private Date lastSubmissionTime;

    /**
     * The submission number for the last submission of this competitor.
     */
    private Integer lastSubmissionNumber;

    /**
     * The language that competitor used.
     */
    private String language;

    /**
     * The number of full submission for this competitor.
     */
    private Integer noOfFullSubmissions;

    /**
     * The rank of competitor's submission in marathon match contest.
     */
    private Integer submissionRank;

    /**
     * Create a instance of <code>CompetitorInfoDTO</code>
     */
    public CompetitorInfoDTO() {
        //Empty
    }

    /**
     * Gets rank.
     *
     * @return the rank
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * Sets rank.
     *
     * @param rank the rank
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * Gets provisional score.
     *
     * @return the provisional score
     */
    public Double getProvisionalScore() {
        return provisionalScore;
    }

    /**
     * Sets provisional score.
     *
     * @param provisionalScore the provisional score
     */
    public void setProvisionalScore(Double provisionalScore) {
        this.provisionalScore = provisionalScore;
    }

    /**
     * Gets last submission time.
     *
     * @return the last submission time
     */
    public Date getLastSubmissionTime() {
        return lastSubmissionTime;
    }

    /**
     * Sets last submission time.
     *
     * @param lastSubmissionTime the last submission time
     */
    public void setLastSubmissionTime(Date lastSubmissionTime) {
        this.lastSubmissionTime = lastSubmissionTime;
    }

    /**
     * Gets language.
     *
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets language.
     *
     * @param language the language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Gets submission rank.
     *
     * @return the submission rank
     */
    public Integer getSubmissionRank() {
        return submissionRank;
    }

    /**
     * Sets submission rank.
     *
     * @param submissionRank the submission rank
     */
    public void setSubmissionRank(Integer submissionRank) {
        this.submissionRank = submissionRank;
    }

    /**
     * Gets no of full submissions.
     *
     * @return the no of full submissions
     */
    public Integer getNoOfFullSubmissions() {
        return noOfFullSubmissions;
    }

    /**
     * Sets no of full submissions.
     *
     * @param noOfFullSubmissions the no of full submissions
     */
    public void setNoOfFullSubmissions(Integer noOfFullSubmissions) {
        this.noOfFullSubmissions = noOfFullSubmissions;
    }

    /**
     * Gets last submission number.
     *
     * @return the last submission number
     */
    public Integer getLastSubmissionNumber() {
        return lastSubmissionNumber;
    }

    /**
     * Sets last submission number.
     *
     * @param lastSubmissionNumber the last submission number
     */
    public void setLastSubmissionNumber(Integer lastSubmissionNumber) {
        this.lastSubmissionNumber = lastSubmissionNumber;
    }
}
