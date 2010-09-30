/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.specreview;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.direct.specreview.services.SpecReviewComment;
import com.topcoder.direct.specreview.services.SpecReviewCommentService;
import com.topcoder.service.review.specification.SpecificationReview;
import com.topcoder.service.review.specification.SpecificationReviewService;
import com.topcoder.service.review.specification.SpecificationReviewStatus;
import com.topcoder.util.errorhandling.ExceptionUtils;

/**
 * <p>
 * This action is responsible for handling the request to view a specification
 * review. It extends <code>SpecificationReviewAction</code>.
 * </p>
 * 
 * <p>
 * <code>SpecificationReviewService</code> and
 * <code>SpecReviewCommentService</code> are used to perform the request. The
 * result data is aggregated into
 * <code>ViewSpecificationReviewActionResultData</code>.
 * </p>
 * 
 * <p>
 * <strong>An example of how to configure action in struts.xml:</strong>
 * 
 * <pre>
 * &lt;action name="viewSpecificationReviewAction" class="viewSpecificationReviewAction"&gt;
 *     &lt;result name="input"&gt;/input.jsp&lt;/result&gt;
 *     &lt;result name="success"&gt;/displaySpecReviewComments.jsp&lt;/result&gt;
 * &lt;/action&gt;
 * </pre>
 * 
 * </p>
 * 
 * <p>
 * <b>Thread safety:</b> This class is mutable and not thread safe. This should
 * be fine since different instances of this class should be created to serve
 * different user requests and will not be shared across user threads (this will
 * be taken care of by Struts 2 framework). The business services used by this
 * component are reasonably thread safe and are responsible for handling
 * transactions. Hence this class can be considered as being effectively thread
 * safe.
 * </p>
 * 
 * @author caru, TCSDEVELOPER
 * @version 1.0
 */
public class ViewSpecificationReviewAction extends SpecificationReviewAction {

    /**
     * <p>
     * Represents the specification review service used to retrieve the
     * specification review and its status.
     * </p>
     * 
     * <p>
     * Initially set to null, once set cannot be null. It set by setter and
     * accessed by getter. Used in <code>execute</code> method.
     * </p>
     */
    private SpecificationReviewService specificationReviewService;

    /**
     * <p>
     * Represents the specification review comment service used to retrieve the
     * specification review comments.
     * </p>
     * 
     * <p>
     * Initially set to null, once set cannot be null. It set by setter and
     * accessed by getter. Used in <code>execute</code> method.
     * </p>
     */
    private SpecReviewCommentService specReviewCommentService;

    /**
     * <p>
     * A <code>ViewSpecificationReviewActionResultData</code> providing the view
     * data for displaying by <code>Specification Review</code> view.
     * </p>
     */
    private ViewSpecificationReviewActionResultData viewData;
    
    /**
     * <p>A <code>SessionData</code> providing interface to current session.</p>
     */
    private SessionData sessionData;

    /**
     * Default constructor, creates new instance.
     */
    public ViewSpecificationReviewAction() {
    }

    /**
     * <p>
     * Executes the request to view a specification review.
     * </p>
     * 
     * <p>
     * No exception is thrown by this method. If any exception occurs, the
     * exception is wrapped in
     * <code>ViewSpecificationReviewActionException</code> with a proper
     * message, stored in the model using the <code>setException</code> method,
     * and <code>Action.ERROR</code> is returned.
     * </p>
     * 
     * <p>
     * If any required field hasn't been injected then
     * <code>ViewSpecificationReviewActionException</code> will be stored in the
     * model and <code>Action.ERROR</code> is returned.
     * </p>
     * 
     * @return the action result String, will either be
     *         <code>Action.SUCCESS</code> or <code>Action.ERROR</code>
     *         depending on whether method was successful
     */
    public String execute() {
        try {
            // make sure required fields are present and valid
            if (specReviewCommentService == null) {
                return Helper
                        .handleActionError(
                                this,
                                new ViewSpecificationReviewActionException(
                                        "specReviewCommentService has not been injected."));
            }

            if (specificationReviewService == null) {
                return Helper
                        .handleActionError(
                                this,
                                new ViewSpecificationReviewActionException(
                                        "specificationReviewService has not been injected."));
            }

            // fetch the specification review, status and comments
            SpecificationReview specificationReview = specificationReviewService
                    .getSpecificationReview(getTCSubject(), getContestId());
            SpecificationReviewStatus specificationReviewStatus = specificationReviewService
                    .getSpecificationReviewStatus(getTCSubject(),
                            getContestId());
            List<SpecReviewComment> specReviewComments = specReviewCommentService
                    .getSpecReviewComments(getTCSubject(), getContestId(),
                            isStudio());

            // load the specification review, status and comments to the model
            ViewSpecificationReviewActionResultData result = new ViewSpecificationReviewActionResultData();
            result.setSpecificationReview(specificationReview);
            result.setSpecificationReviewStatus(specificationReviewStatus);
            result.setSpecReviewComments(specReviewComments);

            // for normal request flow prepare various data to be displayed to
            // user
            // Set contest stats
            ContestStatsDTO contestStats = DirectUtils.getContestStats(
                    getTCSubject(), getContestId(), isStudio());
            result.setContestStats(contestStats);
            
            // Get current session
            HttpServletRequest request = DirectUtils.getServletRequest();
            sessionData = new SessionData(request.getSession());
            // Set current project contests
            List<TypedContestBriefDTO> contests = DataProvider.getProjectTypedContests(
                getTCSubject().getUserId(), contestStats.getContest().getProject().getId());
            sessionData.setCurrentProjectContests(contests);

            setViewData(result);

            return SUCCESS;
        } catch (Exception e) {
            return Helper
                    .handleActionError(
                            this,
                            new ViewSpecificationReviewActionException(
                                    "An error occurred in execute method for ViewSpecificationReviewAction.",
                                    e));
        }
    }

    /**
     * Getter for specification review service.
     * 
     * @return the specification review service
     */
    public SpecificationReviewService getSpecificationReviewService() {
        return specificationReviewService;
    }

    /**
     * Setter for specification review service.
     * 
     * @param specificationReviewService
     *            the specification review service
     * @throws IllegalArgumentException
     *             if argument is null
     */
    public void setSpecificationReviewService(
            SpecificationReviewService specificationReviewService) {
        ExceptionUtils.checkNull(specificationReviewService, null, null,
                "specificationReviewService cannot be null.");
        this.specificationReviewService = specificationReviewService;
    }

    /**
     * Getter for specification review comment service.
     * 
     * @return the specification review comment service
     */
    public SpecReviewCommentService getSpecReviewCommentService() {
        return specReviewCommentService;
    }

    /**
     * Setter for specification review comment service.
     * 
     * @param specReviewCommentService
     *            the specification review comment service
     * @throws IllegalArgumentException
     *             if argument is null
     */
    public void setSpecReviewCommentService(
            SpecReviewCommentService specReviewCommentService) {
        ExceptionUtils.checkNull(specReviewCommentService, null, null,
                "specReviewCommentService cannot be null.");
        this.specReviewCommentService = specReviewCommentService;
    }

    /**
     * Retrieve the viewData field.
     *
     * @return the viewData
     */
    public ViewSpecificationReviewActionResultData getViewData() {
        return viewData;
    }

    /**
     * Set the viewData field.
     *
     * @param viewData the viewData to set
     */
    public void setViewData(ViewSpecificationReviewActionResultData viewData) {
        this.viewData = viewData;
    }

    /**
     * Retrieve the sessionData field.
     *
     * @return the sessionData
     */
    public SessionData getSessionData() {
        return sessionData;
    }

    /**
     * Set the sessionData field.
     *
     * @param sessionData the sessionData to set
     */
    public void setSessionData(SessionData sessionData) {
        this.sessionData = sessionData;
    }
}
