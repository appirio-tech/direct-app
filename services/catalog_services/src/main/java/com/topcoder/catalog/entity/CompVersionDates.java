/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>This class contains dates for each phase of a component compVersion, as well as comments for those dates
 * and some additional information.</p>
 * <p>Validation of parameters is not performed in this class. It's supposed to be a caller's responsibility.</p>
 * <p><strong>Thread safety: </strong></p> <p>This class is mutable and not thread safe.</p>
 *
 * @author caru, Retunsky
 * @version 1.0
 */
public class CompVersionDates implements Serializable {
    /**
     * <p>This field represents the id of the entity.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     */
    private Long id;
    /**
     * <p>This field represents the component compVersion which this class is associated with.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>CompVersion</code> value or <code>null</code>.</p>
     */
    private CompVersion compVersion;
    /**
     * <p>This field represents the phase of the compVersion.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Phase</code> instance or <tt>null</tt>.</p>
     */
    private Phase phase;
    /**
     * <p>This field represents the posting date of the compVersion.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Date</code> value or <tt>null</tt>.</p>
     */
    private Date postingDate;
    /**
     * <p>This field represents the initial submission date of the compVersion.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Date</code> value or <tt>null</tt>.</p>
     */
    private Date initialSubmissionDate;
    /**
     * <p>This field represents the winner announced date of the compVersion.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Date</code> value or <tt>null</tt>.</p>
     */
    private Date winnerAnnouncedDate;
    /**
     * <p>This field represents the final submission date of the compVersion.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Date</code> value or <tt>null</tt>.</p>
     */
    private Date finalSubmissionDate;
    /**
     * <p>This field represents the estimated development date of the compVersion.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Date</code> value or <tt>null</tt>.</p>
     */
    private Date estimatedDevDate;
    /**
     * <p>This field represents the price of the compVersion.</p>
     * <p>The initial value is <tt>0.0d</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>double</code> value.</p>
     */
    private double price;
    /**
     * <p>This field represents the total submissions of the compVersion.</p>
     * <p>The initial value is <tt>0</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>int</code> value.</p>
     */
    private int totalSubmissions;
    /**
     * <p>This field represents the status of the compVersion.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Status</code> instance or <tt>null</tt>.</p>
     */
    private Status status;
    /**
     * <p>This field represents the id of the level.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     */
    private Long levelId;
    /**
     * <p>This field represents the screening complete date of the compVersion.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Date</code> value or <tt>null</tt>.</p>
     */
    private Date screeningCompleteDate;
    /**
     * <p>This field represents the review complete date of the compVersion.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Date</code> value or <tt>null</tt>.</p>
     */
    private Date reviewCompleteDate;
    /**
     * <p>This field represents the aggregation complete date of the compVersion.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Date</code> value or <tt>null</tt>.</p>
     */
    private Date aggregationCompleteDate;
    /**
     * <p>This field represents the phase complete date of the compVersion.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Date</code> value or <tt>null</tt>.</p>
     */
    private Date phaseCompleteDate;
    /**
     * <p>This field represents the production date of the compVersion.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Date</code> value or <tt>null</tt>.</p>
     */
    private Date productionDate;
    /**
     * <p>This field represents the aggregation complete date comment of the compVersion.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     */
    private String aggregationCompleteDateComment;
    /**
     * <p>This field represents the phase complete date comment of the compVersion.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     */
    private String phaseCompleteDateComment;
    /**
     * <p>This field represents the review complete date comment of the compVersion.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     */
    private String reviewCompleteDateComment;
    /**
     * <p>This field represents the winner announced date comment of the compVersion.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     */
    private String winnerAnnouncedDateComment;
    /**
     * <p>This field represents the initial submission date comment of the compVersion.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     */
    private String initialSubmissionDateComment;
    /**
     * <p>This field represents the screening complete date comment of the compVersion.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     */
    private String screeningCompleteDateComment;
    /**
     * <p>This field represents the final submission date comment of the compVersion.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     */
    private String finalSubmissionDateComment;
    /**
     * <p>This field represents the production date comment of the compVersion.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     */
    private String productionDateComment;


    /**
     * <p>Default constructor.</p> <p><em>Does nothing.</em></p>
     */
    public CompVersionDates() {
    }

    /**
     * <p>Sets a value to the {@link #id} field.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     * @param id the id of the entity.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * <p>Retrieves the id of the entity.</p>
     *
     * @return {@link #id} property's value.
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>Sets a value to the {@link #phase} field.</p>
     * <p>The acceptance region: any <code>Phase</code> instance or <tt>null</tt>.</p>
     * @param phase the phase of the compVersion.
     */
    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    /**
     * <p>Retrieves the phase of the compVersion.</p>
     *
     * @return {@link #phase} property's value.
     */
    public Phase getPhase() {
        return phase;
    }

    /**
     * <p>Sets a value to the {@link #postingDate} field.</p>
     * <p>The acceptance region: any <code>Date</code> value or <tt>null</tt>.</p>
     * @param postingDate the posting date of the compVersion.
     */
    public void setPostingDate(Date postingDate) {
        this.postingDate = postingDate;
    }

    /**
     * <p>Retrieves the posting date of the compVersion.</p>
     *
     * @return {@link #postingDate} property's value.
     */
    public Date getPostingDate() {
        return postingDate;
    }

    /**
     * <p>Sets a value to the {@link #initialSubmissionDate} field.</p>
     * <p>The acceptance region: any <code>Date</code> value or <tt>null</tt>.</p>
     * @param initialSubmissionDate the initial submission date of the compVersion.
     */
    public void setInitialSubmissionDate(Date initialSubmissionDate) {
        this.initialSubmissionDate = initialSubmissionDate;
    }

    /**
     * <p>Retrieves the initial submission date of the compVersion.</p>
     *
     * @return {@link #initialSubmissionDate} property's value.
     */
    public Date getInitialSubmissionDate() {
        return initialSubmissionDate;
    }

    /**
     * <p>Sets a value to the {@link #winnerAnnouncedDate} field.</p>
     * <p>The acceptance region: any <code>Date</code> value or <tt>null</tt>.</p>
     * @param winnerAnnouncedDate the winner announced date of the compVersion.
     */
    public void setWinnerAnnouncedDate(Date winnerAnnouncedDate) {
        this.winnerAnnouncedDate = winnerAnnouncedDate;
    }

    /**
     * <p>Retrieves the winner announced date of the compVersion.</p>
     *
     * @return {@link #winnerAnnouncedDate} property's value.
     */
    public Date getWinnerAnnouncedDate() {
        return winnerAnnouncedDate;
    }

    /**
     * <p>Sets a value to the {@link #finalSubmissionDate} field.</p>
     * <p>The acceptance region: any <code>Date</code> value or <tt>null</tt>.</p>
     * @param finalSubmissionDate the final submission date of the compVersion.
     */
    public void setFinalSubmissionDate(Date finalSubmissionDate) {
        this.finalSubmissionDate = finalSubmissionDate;
    }

    /**
     * <p>Retrieves the final submission date of the compVersion.</p>
     *
     * @return {@link #finalSubmissionDate} property's value.
     */
    public Date getFinalSubmissionDate() {
        return finalSubmissionDate;
    }

    /**
     * <p>Sets a value to the {@link #estimatedDevDate} field.</p>
     * <p>The acceptance region: any <code>Date</code> value or <tt>null</tt>.</p>
     * @param estimatedDevDate the estimated development date of the compVersion.
     */
    public void setEstimatedDevDate(Date estimatedDevDate) {
        this.estimatedDevDate = estimatedDevDate;
    }

    /**
     * <p>Retrieves the estimated development date of the compVersion.</p>
     *
     * @return {@link #estimatedDevDate} property's value.
     */
    public Date getEstimatedDevDate() {
        return estimatedDevDate;
    }

    /**
     * <p>Sets a value to the {@link #price} field.</p>
     * <p>The acceptance region: any <code>double</code> value.</p>
     * @param price the price of the compVersion.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * <p>Retrieves the price of the compVersion.</p>
     *
     * @return {@link #price} property's value.
     */
    public double getPrice() {
        return price;
    }

    /**
     * <p>Sets a value to the {@link #totalSubmissions} field.</p>
     * <p>The acceptance region: any <code>int</code> value.</p>
     * @param totalSubmissions the total submissions of the compVersion.
     */
    public void setTotalSubmissions(int totalSubmissions) {
        this.totalSubmissions = totalSubmissions;
    }

    /**
     * <p>Retrieves the total submissions of the compVersion.</p>
     *
     * @return {@link #totalSubmissions} property's value.
     */
    public int getTotalSubmissions() {
        return totalSubmissions;
    }

    /**
     * <p>Sets a value to the {@link #status} field.</p>
     * <p>The acceptance region: any <code>Status</code> instance or <tt>null</tt>.</p>
     * @param status the status of the compVersion.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * <p>Retrieves the status of the compVersion.</p>
     *
     * @return {@link #status} property's value.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * <p>Sets a value to the {@link #levelId} field.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     * @param levelId the id of the level.
     */
    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    /**
     * <p>Retrieves the id of the level.</p>
     *
     * @return {@link #levelId} property's value.
     */
    public Long getLevelId() {
        return levelId;
    }

    /**
     * <p>Sets a value to the {@link #screeningCompleteDate} field.</p>
     * <p>The acceptance region: any <code>Date</code> value or <tt>null</tt>.</p>
     * @param screeningCompleteDate the screening complete date of the compVersion.
     */
    public void setScreeningCompleteDate(Date screeningCompleteDate) {
        this.screeningCompleteDate = screeningCompleteDate;
    }

    /**
     * <p>Retrieves the screening complete date of the compVersion.</p>
     *
     * @return {@link #screeningCompleteDate} property's value.
     */
    public Date getScreeningCompleteDate() {
        return screeningCompleteDate;
    }

    /**
     * <p>Sets a value to the {@link #reviewCompleteDate} field.</p>
     * <p>The acceptance region: any <code>Date</code> value or <tt>null</tt>.</p>
     * @param reviewCompleteDate the review complete date of the compVersion.
     */
    public void setReviewCompleteDate(Date reviewCompleteDate) {
        this.reviewCompleteDate = reviewCompleteDate;
    }

    /**
     * <p>Retrieves the review complete date of the compVersion.</p>
     *
     * @return {@link #reviewCompleteDate} property's value.
     */
    public Date getReviewCompleteDate() {
        return reviewCompleteDate;
    }

    /**
     * <p>Sets a value to the {@link #aggregationCompleteDate} field.</p>
     * <p>The acceptance region: any <code>Date</code> value or <tt>null</tt>.</p>
     * @param aggregationCompleteDate the aggregation complete date of the compVersion.
     */
    public void setAggregationCompleteDate(Date aggregationCompleteDate) {
        this.aggregationCompleteDate = aggregationCompleteDate;
    }

    /**
     * <p>Retrieves the aggregation complete date of the compVersion.</p>
     *
     * @return {@link #aggregationCompleteDate} property's value.
     */
    public Date getAggregationCompleteDate() {
        return aggregationCompleteDate;
    }

    /**
     * <p>Sets a value to the {@link #phaseCompleteDate} field.</p>
     * <p>The acceptance region: any <code>Date</code> value or <tt>null</tt>.</p>
     * @param phaseCompleteDate the phase complete date of the compVersion.
     */
    public void setPhaseCompleteDate(Date phaseCompleteDate) {
        this.phaseCompleteDate = phaseCompleteDate;
    }

    /**
     * <p>Retrieves the phase complete date of the compVersion.</p>
     *
     * @return {@link #phaseCompleteDate} property's value.
     */
    public Date getPhaseCompleteDate() {
        return phaseCompleteDate;
    }

    /**
     * <p>Sets a value to the {@link #productionDate} field.</p>
     * <p>The acceptance region: any <code>Date</code> value or <tt>null</tt>.</p>
     * @param productionDate the production date of the compVersion.
     */
    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    /**
     * <p>Retrieves the production date of the compVersion.</p>
     *
     * @return {@link #productionDate} property's value.
     */
    public Date getProductionDate() {
        return productionDate;
    }

    /**
     * <p>Sets a value to the {@link #aggregationCompleteDateComment} field.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     * @param aggregationCompleteDateComment the aggregation complete date comment of the compVersion.
     */
    public void setAggregationCompleteDateComment(String aggregationCompleteDateComment) {
        this.aggregationCompleteDateComment = aggregationCompleteDateComment;
    }

    /**
     * <p>Retrieves the aggregation complete date comment of the compVersion.</p>
     *
     * @return {@link #aggregationCompleteDateComment} property's value.
     */
    public String getAggregationCompleteDateComment() {
        return aggregationCompleteDateComment;
    }

    /**
     * <p>Sets a value to the {@link #phaseCompleteDateComment} field.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     * @param phaseCompleteDateComment the phase complete date comment of the compVersion.
     */
    public void setPhaseCompleteDateComment(String phaseCompleteDateComment) {
        this.phaseCompleteDateComment = phaseCompleteDateComment;
    }

    /**
     * <p>Retrieves the phase complete date comment of the compVersion.</p>
     *
     * @return {@link #phaseCompleteDateComment} property's value.
     */
    public String getPhaseCompleteDateComment() {
        return phaseCompleteDateComment;
    }

    /**
     * <p>Sets a value to the {@link #reviewCompleteDateComment} field.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     * @param reviewCompleteDateComment the review complete date comment of the compVersion.
     */
    public void setReviewCompleteDateComment(String reviewCompleteDateComment) {
        this.reviewCompleteDateComment = reviewCompleteDateComment;
    }

    /**
     * <p>Retrieves the review complete date comment of the compVersion.</p>
     *
     * @return {@link #reviewCompleteDateComment} property's value.
     */
    public String getReviewCompleteDateComment() {
        return reviewCompleteDateComment;
    }

    /**
     * <p>Sets a value to the {@link #winnerAnnouncedDateComment} field.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     * @param winnerAnnouncedDateComment the winner announced date comment of the compVersion.
     */
    public void setWinnerAnnouncedDateComment(String winnerAnnouncedDateComment) {
        this.winnerAnnouncedDateComment = winnerAnnouncedDateComment;
    }

    /**
     * <p>Retrieves the winner announced date comment of the compVersion.</p>
     *
     * @return {@link #winnerAnnouncedDateComment} property's value.
     */
    public String getWinnerAnnouncedDateComment() {
        return winnerAnnouncedDateComment;
    }

    /**
     * <p>Sets a value to the {@link #initialSubmissionDateComment} field.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     * @param initialSubmissionDateComment the initial submission date comment of the compVersion.
     */
    public void setInitialSubmissionDateComment(String initialSubmissionDateComment) {
        this.initialSubmissionDateComment = initialSubmissionDateComment;
    }

    /**
     * <p>Retrieves the initial submission date comment of the compVersion.</p>
     *
     * @return {@link #initialSubmissionDateComment} property's value.
     */
    public String getInitialSubmissionDateComment() {
        return initialSubmissionDateComment;
    }

    /**
     * <p>Sets a value to the {@link #screeningCompleteDateComment} field.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     * @param screeningCompleteDateComment the screening complete date comment of the compVersion.
     */
    public void setScreeningCompleteDateComment(String screeningCompleteDateComment) {
        this.screeningCompleteDateComment = screeningCompleteDateComment;
    }

    /**
     * <p>Retrieves the screening complete date comment of the compVersion.</p>
     *
     * @return {@link #screeningCompleteDateComment} property's value.
     */
    public String getScreeningCompleteDateComment() {
        return screeningCompleteDateComment;
    }

    /**
     * <p>Sets a value to the {@link #finalSubmissionDateComment} field.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     * @param finalSubmissionDateComment the final submission date comment of the compVersion.
     */
    public void setFinalSubmissionDateComment(String finalSubmissionDateComment) {
        this.finalSubmissionDateComment = finalSubmissionDateComment;
    }

    /**
     * <p>Retrieves the final submission date comment of the compVersion.</p>
     *
     * @return {@link #finalSubmissionDateComment} property's value.
     */
    public String getFinalSubmissionDateComment() {
        return finalSubmissionDateComment;
    }

    /**
     * <p>Sets a value to the {@link #productionDateComment} field.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     * @param productionDateComment the production date comment of the compVersion.
     */
    public void setProductionDateComment(String productionDateComment) {
        this.productionDateComment = productionDateComment;
    }

    /**
     * <p>Retrieves the production date comment of the compVersion.</p>
     *
     * @return {@link #productionDateComment} property's value.
     */
    public String getProductionDateComment() {
        return productionDateComment;
    }

    /**
     * <p>Retrieves the component compVersion which this class is associated with.</p>
     *
     * @return {@link #compVersion} property's value.
     */
    public CompVersion getCompVersion() {
        return compVersion;
    }

    /**
     * <p>Sets a value to the {@link #compVersion} field.</p>
     * <p>The acceptance region: any <code>CompVersion</code> value or <code>null</code>.</p>
     * @param compVersion the component compVersion which link is associated with
     */
    public void setCompVersion(CompVersion compVersion) {
        this.compVersion = compVersion;
    }
}

