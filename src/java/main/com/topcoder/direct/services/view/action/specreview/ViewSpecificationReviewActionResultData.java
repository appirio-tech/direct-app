/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.specreview;

import java.util.List;

import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.specreview.services.SpecReviewComment;
import com.topcoder.service.review.specification.SpecificationReview;
import com.topcoder.service.review.specification.SpecificationReviewStatus;
import com.topcoder.util.errorhandling.ExceptionUtils;

/**
 * <p>
 * This class is a simple <code>DTO</code> that holds the result data of
 * <code>ViewSpecificationReviewAction</code>. It holds the retrieved specification review, its status and its
 * comments.
 * </p>
 *
 * <p>
 * <b>Thread safety:</b> This class is mutable and not thread safe. This is not an issue because each action
 * instance will be used by one thread only, and will create/use only one instance of this class at a time.
 * Also, this is not an issue at the client side because it is single-threaded.
 * </p>
 *
 * @author caru, TCSDEVELOPER
 * @version 1.0
 */
public class ViewSpecificationReviewActionResultData {

    /**
     * <p>
     * Represents the retrieved specification review.
     * </p>
     *
     * <p>
     * Initially set to null, once set cannot be null. It set by setter and accessed by getter.
     * Used in <code>execute</code> method of <code>ViewSpecificationReviewAction</code>.
     * </p>
     */
    private SpecificationReview specificationReview;

    /**
     * <p>
     * Represents the retrieved specification review status.
     * </p>
     *
     * <p>
     * Initially set to null, once set cannot be null. It set by setter and accessed by getter.
     * Used in <code>execute</code> method of <code>ViewSpecificationReviewAction</code>.
     * </p>
     */
    private SpecificationReviewStatus specificationReviewStatus;

    /**
     * <p>
     * Represents the retrieved specification review comments.
     * </p>
     *
     * <p>
     * Initially set to null, once set cannot be null, can be empty, cannot contain null elements.
     * It set by setter and accessed by getter.
     * Used in <code>execute</code> method of <code>ViewSpecificationReviewAction</code>.
     * </p>
     */
    private List<SpecReviewComment> specReviewComments;

    /**
     * <p>A <code>ContestStatsDTO </code> providing the statistics on contest.</p>
     */
    private ContestStatsDTO contestStats;
    
    /**
     * Default constructor, creates new instance.
     */
    public ViewSpecificationReviewActionResultData() {
    }

    /**
     * Getter for specification review.
     *
     * @return the specification review
     */
    public SpecificationReview getSpecificationReview() {
        return specificationReview;
    }

    /**
     * Setter for specification review.
     *
     * @param specificationReview the specification review
     * @throws IllegalArgumentException if argument is null
     */
    public void setSpecificationReview(SpecificationReview specificationReview) {
        ExceptionUtils.checkNull(specificationReview, null, null, "specificationReview cannot be null.");
        this.specificationReview = specificationReview;
    }

    /**
     * Getter for specification review status.
     *
     * @return the specification review status
     */
    public SpecificationReviewStatus getSpecificationReviewStatus() {
        return specificationReviewStatus;
    }

    /**
     * Setter for specification review status.
     *
     * @param specificationReviewStatus the specification review status
     * @throws IllegalArgumentException if argument is null
     */
    public void setSpecificationReviewStatus(SpecificationReviewStatus specificationReviewStatus) {
        ExceptionUtils.checkNull(specificationReviewStatus, null, null, "specificationReviewStatus cannot be null.");
        this.specificationReviewStatus = specificationReviewStatus;
    }

    /**
     * Getter for list of specification review comments.
     *
     * @return the list of specification review comments
     */
    public List<SpecReviewComment> getSpecReviewComments() {
        return specReviewComments;
    }

    /**
     * Setter for list of specification review comments.
     *
     * @param specReviewComments the list of specification review comments
     * @throws IllegalArgumentException if argument is null or contains null elements
     */
    public void setSpecReviewComments(List<SpecReviewComment> specReviewComments) {
        ExceptionUtils.checkNull(specReviewComments, null, null, "specReviewComments cannot be null.");

        // make sure no null elements are present
        for (SpecReviewComment specReviewComment : specReviewComments) {
            if (specReviewComment == null) {
                throw new IllegalArgumentException("specReviewComments cannot contain null elements.");
            }
        }
        this.specReviewComments = specReviewComments;
    }

    /**
     * Retrieve the contestStats field.
     *
     * @return the contestStats
     */
    public ContestStatsDTO getContestStats() {
        return contestStats;
    }

    /**
     * Set the contestStats field.
     *
     * @param contestStats the contestStats to set
     */
    public void setContestStats(ContestStatsDTO contestStats) {
        this.contestStats = contestStats;
    }
}
