/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.specreview;

import java.util.List;
import java.util.Map;

import com.topcoder.direct.services.view.dto.contest.ContestDashboardDTO;
import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.service.review.comment.specification.SpecReviewComment;
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
 * <p>
 * Version 1.1 (TC Direct Contest Dashboard Update Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #dashboard} field and corresponding get/set methods.</li>
 *   </ol>
 * </p>
 *
 * @author caru, TCSDEVELOPER, TCSASSEMBLER
 * @version 1.1
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
     * <p>A <code>boolean </code> to indicate whether to show spec review comments.</p>
     */
    private boolean showSpecReview;
    
    /**
     * <p>A <code>boolean </code> to indicate whether to show progress.</p>
     */
    private boolean showProgress;
    
    /**
     * The spec comments.
     */
    private Map<Long, List<SpecComment>> specComments;
    
    /**
     * The contest dashboard data.s
     */
    private ContestDashboardDTO dashboard;
    
    /**
     * Default constructor, creates new instance.
     */
    public ViewSpecificationReviewActionResultData() {
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

    /**
     * Get the showSpecReview field.
     *
     * @return the showSpecReview
     */
    public boolean isShowSpecReview() {
        return showSpecReview;
    }

    /**
     * Set the showSpecReview field.
     *
     * @param showSpecReview the showSpecReview to set
     */
    public void setShowSpecReview(boolean showSpecReview) {
        this.showSpecReview = showSpecReview;
    }

    /**
     * Get the showProgress field.
     *
     * @return the showProgress
     */
    public boolean isShowProgress() {
        return showProgress;
    }

    /**
     * Set the showProgress field.
     *
     * @param showProgress the showProgress to set
     */
    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    /**
     * Gets the specificationReview field.
     *
     * @return the specificationReview
     */
    public SpecificationReview getSpecificationReview() {
        return specificationReview;
    }

    /**
     * Sets the specificationReview field.
     *
     * @param specificationReview the specificationReview to set
     */
    public void setSpecificationReview(SpecificationReview specificationReview) {
        this.specificationReview = specificationReview;
    }

    /**
     * Gets the specComments field.
     *
     * @return the specComments
     */
    public Map<Long, List<SpecComment>> getSpecComments() {
        return specComments;
    }

    /**
     * Sets the specComments field.
     *
     * @param specComments the specComments to set
     */
    public void setSpecComments(Map<Long, List<SpecComment>> specComments) {
        this.specComments = specComments;
    }

    /**
     * Gets the dashboard field.
     * 
     * @return the dashboard
     * @since 1.1
     */
    public ContestDashboardDTO getDashboard() {
        return dashboard;
    }

    /**
     * Sets the dashboard field.
     *
     * @param dashboard the dashboard to set
     * @since 1.1
     */
    public void setDashboard(ContestDashboardDTO dashboard) {
        this.dashboard = dashboard;
    }

    
}
