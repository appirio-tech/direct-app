/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.specreview;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.review.specification.SpecificationReviewService;
import com.topcoder.util.errorhandling.ExceptionUtils;

/**
 * <p>
 * This action is responsible for handling the request to start a specification review.
 * It extends <code>SpecificationReviewAction</code>.
 * </p>
 *
 * <p>
 * <code>SpecificationReviewService</code> and <code>ContestServiceFacade</code> are used to
 * perform the request.
 * </p>
 *
 * <p>
 * <strong>An example of how to configure action in struts.xml:</strong>
 * <pre>
 * &lt;action name="startSpecificationReviewAction" class="startSpecificationReviewAction"&gt;
 *     &lt;result name="input"&gt;/input.jsp&lt;/result&gt;
 *     &lt;result name="success"&gt;/success.jsp&lt;/result&gt;
 * &lt;/action&gt;
 * </pre>
 * </p>
 * <p>Version 1.1 change notes:
 * <ul>
 *  <li>Update {@link #execute()} to use the projectId instead of contestId</li>
 * </ul>
 * </p>
 * <p>
 * <b>Thread safety:</b>
 * This class is mutable and not thread safe. This should be fine since different instances of this
 * class should be created to serve different user requests and will not be shared across user
 * threads (this will be taken care of by Struts 2 framework).
 * The business services used by this component are reasonably thread safe and are responsible
 * for handling transactions. Hence this class can be considered as being effectively thread safe.
 * </p>
 *
 * @author caru, TCSDEVELOPER, morehappiness
 * @version 1.1
 * @since 1.0
 */
public class StartSpecificationReviewAction extends SpecificationReviewAction {

    /**
     * Represents the default specification review start period before contest launch in minutes.
     * Set to 48 hours (48*60 minutes).
     */
    public static final int DEFAULT_SPECIFICATION_REVIEW_START_PERIOD_BEFORE_CONTEST = 48 * 60;

    /**
     * Represents the start mode value for <code>now</code>.
     */
    private static final String START_MODE_NOW = "now";

    /**
     * Represents the start mode value for <code>later</code>.
     */
    private static final String START_MODE_LATER = "later";

    /**
     * <p>
     * Represents the start mode.
     * </p>
     *
     * <p>
     * Initially set to null, can be any value. It set by setter and accessed by getter.
     * Used in <code>execute</code> method.
     * </p>
     */
    private String startMode;

    /**
     * <p>
     * Represents the specification review start period before contest launch in minutes.
     * </p>
     *
     * <p>
     * Initially set to <code>DEFAULT_SPECIFICATION_REVIEW_START_PERIOD_BEFORE_CONTEST</code>, can be any value.
     * It set by setter and accessed by getter. Used in <code>execute</code> method.
     * </p>
     */
    private int specificationReviewStartPeriodBeforeContest =
        DEFAULT_SPECIFICATION_REVIEW_START_PERIOD_BEFORE_CONTEST;

    /**
     * <p>
     * Represents the specification review service used to schedule the specification review.
     * <p>
     *
     * <p>
     * Initially set to null, once set cannot be null. It set by setter and accessed by getter. Used in
     * <code>execute</code> method.
     * </p>
     */
    private SpecificationReviewService specificationReviewService;

    /**
     * <p>
     * Represents the contest service facade used to retrieve contest start date.
     * <p>
     *
     * <p>
     * Initially set to null, once set cannot be null. It set by setter and accessed by getter. Used in
     * <code>execute</code> method.
     * </p>
     */
    private ContestServiceFacade contestServiceFacade;

    /**
     * Default constructor, creates new instance.
     */
    public StartSpecificationReviewAction() {
    }

    /**
     * <p>
     * Executes the request to start a specification review.
     * </p>
     *
     * <p>
     * No exception is thrown by this method. If any exception occurs, the exception is wrapped in
     * <code>StartSpecificationReviewActionException</code> with a proper message, stored in the model
     * using the <code>setException</code> method, and <code>Action.ERROR</code> is returned.
     * </p>
     *
     * <p>
     * If any required field hasn't been injected, start mode is not <code>START_MODE_NOW</code> or
     * <code>START_MODE_LATER</code> or <code>specificationReviewStartPeriodBeforeContest</code> is
     * not positive then <code>StartSpecificationReviewActionException</code> will be stored in the model and
     * <code>Action.ERROR</code> is returned.
     * </p>
     *
     * @return the action result String, will either be <code>Action.SUCCESS</code> or
     *         <code>Action.ERROR</code> depending on whether method was successful
     */
    public String execute() {
        try {
            // make sure required fields are present and valid
            if (startMode == null || !(startMode.equals(START_MODE_NOW) || startMode.equals(START_MODE_LATER))) {
                return Helper.handleActionError(this,
                    new StartSpecificationReviewActionException("startMode cannot be null and must be either "
                        + START_MODE_NOW + " or " + START_MODE_LATER + "."));
            }

            if (specificationReviewStartPeriodBeforeContest <= 0) {
                return Helper.handleActionError(this,
                    new StartSpecificationReviewActionException(
                        "specificationReviewStartPeriodBeforeContest must be positive."));
            }

            if (contestServiceFacade == null) {
                return Helper.handleActionError(this, new StartSpecificationReviewActionException(
                    "contestServiceFacade has not been injected."));
            }

            if (specificationReviewService == null) {
                return Helper.handleActionError(this, new StartSpecificationReviewActionException(
                    "specificationReviewService has not been injected."));
            }

            boolean startnow = false;
            if (startMode.equals(START_MODE_NOW)) {
                startnow = true;
            } 

            // schedule the specification review using the start date
            specificationReviewService.scheduleSpecificationReview(getTCSubject(), getProjectId(), startnow);

            return SUCCESS;

        } catch (Exception e) {
            return Helper.handleActionError(this, new StartSpecificationReviewActionException(
                "An error occurred in execute method for StartSpecificationReviewAction.", e));
        }
    }

    /**
     * Getter for start mode.
     *
     * @return the start mode
     */
    public String getStartMode() {
        return startMode;
    }

    /**
     * Setter for start mode.
     *
     * @param startMode the start mode
     */
    public void setStartMode(String startMode) {
        this.startMode = startMode;
    }

    /**
     * Getter for specification review start period before contest.
     *
     * @return the specification review start period before contest
     */
    public int getSpecificationReviewStartPeriodBeforeContest() {
        return specificationReviewStartPeriodBeforeContest;
    }

    /**
     * Setter for specification review start period before contest.
     *
     * @param specificationReviewStartPeriodBeforeContest the specification review start period before contest
     */
    public void setSpecificationReviewStartPeriodBeforeContest(int specificationReviewStartPeriodBeforeContest) {
        this.specificationReviewStartPeriodBeforeContest = specificationReviewStartPeriodBeforeContest;
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
     * @param specificationReviewService the specification review service
     * @throws IllegalArgumentException if argument is null
     */
    public void setSpecificationReviewService(SpecificationReviewService specificationReviewService) {
        ExceptionUtils
            .checkNull(specificationReviewService, null, null, "specificationReviewService cannot be null.");
        this.specificationReviewService = specificationReviewService;
    }

    /**
     * Getter for contest service facade.
     *
     * @return the contest service facade
     */
    public ContestServiceFacade getContestServiceFacade() {
        return contestServiceFacade;
    }

    /**
     * Setter for contest service facade.
     *
     * @param contestServiceFacade the contest service facade
     * @throws IllegalArgumentException if argument is null
     */
    public void setContestServiceFacade(ContestServiceFacade contestServiceFacade) {
        ExceptionUtils.checkNull(contestServiceFacade, null, null, "contestServiceFacade cannot be null.");
        this.contestServiceFacade = contestServiceFacade;
    }
}
