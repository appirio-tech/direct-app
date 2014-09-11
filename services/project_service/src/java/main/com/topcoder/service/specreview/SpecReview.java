/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.specreview;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * The Class SpecReviews - represents the set of spec sections reviews, reviewer (if any), status etc.
 * 
 * @author TCSASSEMBLER
 * @version 1.0
 * @since Spec Review Finishing Touches v1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "specReview", propOrder = { "specReviewId", "contestId", "reviewStatus", "studio",
        "sectionReviews", "readyForReviewTime", "reviewDoneTime", "reviewerId", "createUser", "createTime", "modifyUser", "modifyTime" })
public class SpecReview implements Serializable {

    /**
     * Default Serial Version Id.
     */
    private static final long serialVersionUID = 1L;

    /** The spec review id. */
    private long specReviewId;

    /** The contest id. */
    private long contestId;

    /** The review status. */
    private ReviewStatus reviewStatus;

    /** The studio. */
    private boolean studio;

    /** The section reviews. */
    private Set<SpecSectionReview> sectionReviews;

    /** The create user. */
    private String createUser;

    /** The create time. */
    private Date createTime;

    /** The modify user. */
    private String modifyUser;

    /** The modify time. */
    private Date modifyTime;
    
    /** The last time when this spec review was made ready for review */
    private Date readyForReviewTime;
    
    /** The last time when this spec review was marked 'review done' */
    private Date reviewDoneTime;
    
    /** The user id of the reviewer (if any) */
    private Long reviewerId;

    /**
     * Instantiates a new spec review.
     */
    public SpecReview() {

    }

    /**
     * Gets the spec review id.
     * 
     * @return the spec review id
     */
    public long getSpecReviewId() {
        return specReviewId;
    }

    /**
     * Sets the spec review id.
     * 
     * @param specReviewId
     *            the new spec review id
     */
    public void setSpecReviewId(long specReviewId) {
        this.specReviewId = specReviewId;
    }

    /**
     * Gets the contest id.
     * 
     * @return the contest id
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Sets the contest id.
     * 
     * @param contestId
     *            the new contest id
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * Gets the review status.
     * 
     * @return the review status
     */
    public ReviewStatus getReviewStatus() {
        return reviewStatus;
    }

    /**
     * Sets the review status.
     * 
     * @param reviewStatus
     *            the new review status
     */
    public void setReviewStatus(ReviewStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    /**
     * Checks if is studio.
     * 
     * @return true, if is studio
     */
    public boolean isStudio() {
        return studio;
    }

    /**
     * Sets the studio.
     * 
     * @param studio
     *            the new studio
     */
    public void setStudio(boolean studio) {
        this.studio = studio;
    }

    /**
     * Gets the spec sections reviews.
     * 
     * @return the spec sections reviews
     */
    public Set<SpecSectionReview> getSectionReviews() {
        return this.sectionReviews;
    }

    /**
     * Sets the spec sections reviews.
     * 
     * @param sectionReviews
     *            the spec sections reviews
     */
    public void setSectionReviews(Set<SpecSectionReview> sectionReviews) {
        this.sectionReviews = sectionReviews;
    }

    /**
     * Gets the creates the user.
     * 
     * @return the creates the user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * Sets the creates the user.
     * 
     * @param createUser
     *            the new creates the user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * Gets the creates the time.
     * 
     * @return the creates the time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * Sets the creates the time.
     * 
     * @param createTime
     *            the new creates the time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * Gets the modify user.
     * 
     * @return the modify user
     */
    public String getModifyUser() {
        return modifyUser;
    }

    /**
     * Sets the modify user.
     * 
     * @param modifyUser
     *            the new modify user
     */
    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    /**
     * Gets the modify time.
     * 
     * @return the modify time
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * Sets the modify time.
     * 
     * @param modifyTime
     *            the new modify time
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * Gets the last time when this spec review was marked ready for review.
     * 
     * @return the last time when this spec review was marked ready for review.
     */
    public Date getReadyForReviewTime() {
        return this.readyForReviewTime;
    }

    /**
     * Sets the last time when this spec review was marked ready for review.
     * 
     * @param readyForReviewTime the last time when this spec review was marked ready for review.
     */
    public void setReadyForReviewTime(Date readyForReviewTime) {
        this.readyForReviewTime = readyForReviewTime;
    }

    /**
     * Gets the last time when this spec review was marked review done.
     * 
     * @return the last time when this spec review was marked review done.
     */
    public Date getReviewDoneTime() {
        return this.reviewDoneTime;
    }

    /**
     * Sets the last time when this spec review was marked review done.
     * 
     * @param reviewDoneTime the last time when this spec review was marked review done.
     */
    public void setReviewDoneTime(Date reviewDoneTime) {
        this.reviewDoneTime = reviewDoneTime;
    }

    /**
     * Gets the reviewer id, if some reviewer exist else 0.
     * 
     * @return the reviewer id.
     */
    public Long getReviewerId() {
        return this.reviewerId;
    }

    /**
     * Sets the reviewer id.
     * 
     * @param reviewerId the reviewer id.
     */
    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }
}
