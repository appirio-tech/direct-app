/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * <p>
 * The base entity class for the competition pipeline info.
 * </p>
 *
 * @author TCSASSEBMLER
 * @since Pipeline Conversion Service Layer Assembly v1.0
 * @version 1.0
 */
@MappedSuperclass
public abstract class CompetitionPipelineInfo implements Serializable {
    /** Serial version UID. */
    private static final long serialVersionUID = 3350854372397246527L;

    /** The id. */
    @Id
    @Column(name = "id")
    private long pipelineInfoId;

    /** The review payment. */
    @Column(name = "review_payment")
    private Double reviewPayment;

    /** The specification review payment. */
    @Column(name = "specification_review_payment")
    private Double specificationReviewPayment;

    /** The contest fee. */
    @Column(name = "contest_fee")
    private Double contestFee;

    /** The client name. */
    @Column(name = "client_name")
    private String clientName;

    /** The confidence. */
    @Column(name = "confidence")
    private Integer confidence;

    /** Client approval or not. */
    @Column(name = "client_approval")
    private Boolean clientApproval;

    /** The pricing approval or not. */
    @Column(name = "pricing_approval")
    private Boolean pricingApproval;

    /** The has wiki specification or not. */
    @Column(name = "has_wiki_specification")
    private Boolean hasWikiSpecification;

    /** The pass specification review or not. */
    @Column(name = "passed_spec_review")
    private Boolean passedSpecReview;

    /** The has dependent competition or not. */
    @Column(name = "has_dependent_competitions")
    private Boolean hasDependentCompetitions;

    /** The was posted or not. */
    @Column(name = "was_reposted")
    private Boolean wasReposted;

    /** The notes. */
    @Column(name = "notes")
    private String notes;

    /**
     * <p>
     * Gets the pipelineInfoId
     * </p>
     *
     * @return the pipelineInfoId
     */
    public long getPipelineInfoId() {
        return pipelineInfoId;
    }

    /**
     * <p>
     * Sets the pipelineInfoId
     * </p>
     *
     * @param pipelineInfoId the pipelineInfoId to set
     */
    public void setPipelineInfoId(long pipelineInfoId) {
        this.pipelineInfoId = pipelineInfoId;
    }

    /**
     * Returns the value of reviewPayment.
     *
     * @return the reviewPayment
     */
    public Double getReviewPayment() {
        return reviewPayment;
    }

    /**
     * Set the value to  reviewPayment field.
     *
     * @param reviewPayment the reviewPayment to set
     */
    public void setReviewPayment(Double reviewPayment) {
        this.reviewPayment = reviewPayment;
    }

    /**
     * Returns the value of specificationReviewPayment.
     *
     * @return the specificationReviewPayment
     */
    public Double getSpecificationReviewPayment() {
        return specificationReviewPayment;
    }

    /**
     * Set the value to  specificationReviewPayment field.
     *
     * @param specificationReviewPayment the specificationReviewPayment to set
     */
    public void setSpecificationReviewPayment(Double specificationReviewPayment) {
        this.specificationReviewPayment = specificationReviewPayment;
    }

    /**
     * Returns the value of contestFee.
     *
     * @return the contestFee
     */
    public Double getContestFee() {
        return contestFee;
    }

    /**
     * Set the value to  contestFee field.
     *
     * @param contestFee the contestFee to set
     */
    public void setContestFee(Double contestFee) {
        this.contestFee = contestFee;
    }

    /**
     * Returns the value of clientName.
     *
     * @return the clientName
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Set the value to  clientName field.
     *
     * @param clientName the clientName to set
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * Returns the value of confidence.
     *
     * @return the confidence
     */
    public Integer getConfidence() {
        return confidence;
    }

    /**
     * Set the value to  confidence field.
     *
     * @param confidence the confidence to set
     */
    public void setConfidence(Integer confidence) {
        this.confidence = confidence;
    }

    /**
     * Returns the value of clientApproval.
     *
     * @return the clientApproval
     */
    public Boolean getClientApproval() {
        return clientApproval;
    }

    /**
     * Set the value to  clientApproval field.
     *
     * @param clientApproval the clientApproval to set
     */
    public void setClientApproval(Boolean clientApproval) {
        this.clientApproval = clientApproval;
    }

    /**
     * Returns the value of pricingApproval.
     *
     * @return the pricingApproval
     */
    public Boolean getPricingApproval() {
        return pricingApproval;
    }

    /**
     * Set the value to  pricingApproval field.
     *
     * @param pricingApproval the pricingApproval to set
     */
    public void setPricingApproval(Boolean pricingApproval) {
        this.pricingApproval = pricingApproval;
    }

    /**
     * Returns the value of hasWikiSpecification.
     *
     * @return the hasWikiSpecification
     */
    public Boolean getHasWikiSpecification() {
        return hasWikiSpecification;
    }

    /**
     * Set the value to  hasWikiSpecification field.
     *
     * @param hasWikiSpecification the hasWikiSpecification to set
     */
    public void setHasWikiSpecification(Boolean hasWikiSpecification) {
        this.hasWikiSpecification = hasWikiSpecification;
    }

    /**
     * Returns the value of passedSpecReview.
     *
     * @return the passedSpecReview
     */
    public Boolean getPassedSpecReview() {
        return passedSpecReview;
    }

    /**
     * Set the value to  passedSpecReview field.
     *
     * @param passedSpecReview the passedSpecReview to set
     */
    public void setPassedSpecReview(Boolean passedSpecReview) {
        this.passedSpecReview = passedSpecReview;
    }

    /**
     * Returns the value of hasDependentCompetitions.
     *
     * @return the hasDependentCompetitions
     */
    public Boolean getHasDependentCompetitions() {
        return hasDependentCompetitions;
    }

    /**
     * Set the value to  hasDependentCompetitions field.
     *
     * @param hasDependentCompetitions the hasDependentCompetitions to set
     */
    public void setHasDependentCompetitions(Boolean hasDependentCompetitions) {
        this.hasDependentCompetitions = hasDependentCompetitions;
    }

    /**
     * Returns the value of wasReposted.
     *
     * @return the wasReposted
     */
    public Boolean getWasReposted() {
        return wasReposted;
    }

    /**
     * Set the value to  wasReposted field.
     *
     * @param wasReposted the wasReposted to set
     */
    public void setWasReposted(Boolean wasReposted) {
        this.wasReposted = wasReposted;
    }

    /**
     * Returns the value of notes.
     *
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Set the value to  notes field.
     *
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
