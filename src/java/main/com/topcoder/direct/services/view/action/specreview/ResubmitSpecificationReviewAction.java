/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.specreview;

import com.topcoder.service.review.specification.SpecificationReviewService;
import com.topcoder.util.errorhandling.ExceptionUtils;

/**
 * <p>
 * This action is responsible for handling the request to resubmit a specification review.
 * It extends <code>SpecificationReviewAction</code>.
 * </p>
 *
 * <p>
 * <code>SpecificationReviewService</code> is used to perform the request. Also, <code>content</code> should
 * be configured using injection, as it is not a request parameter.
 * </p>
 *
 * <p>
 * <strong>An example of how to configure action in struts.xml:</strong>
 *
 * <pre>
 * &lt;action name="resubmitSpecificationReviewAction" class="resubmitSpecificationReviewAction"&gt;
 *     &lt;result name="input"&gt;/input.jsp&lt;/result&gt;
 *     &lt;result name="success"&gt;/success.jsp&lt;/result&gt;
 * &lt;/action&gt;
 * </pre>
 *
 * </p>
 * <p>Version 1.1 change notes:
 * <ul>
 *  <li>Update {@link #execute()} to use the projectId instead of contestId</li>
 * </ul>
 * </p>
 * <p>
 * <b>Thread safety:</b> This class is mutable and not thread safe. This should be fine since different
 * instances of this class should be created to serve different user requests and will not be shared across
 * user threads (this will be taken care of by Struts 2 framework). The business services used by this
 * component are reasonably thread safe and are responsible for handling transactions. Hence this class can be
 * considered as being effectively thread safe.
 * </p>
 *
 * @author caru, TCSDEVELOPER, morehappiness
 * @version 1.1
 */
public class ResubmitSpecificationReviewAction extends SpecificationReviewAction {

    /**
     * <p>
     * Represents the content.
     * </p>
     *
     * <p>
     * Initially set to null, once set cannot be null or empty. It set by setter and accessed by getter. Used
     * in <code>execute</code> method.
     * </p>
     */
    private String content;

    /**
     * <p>
     * Represents the specification review service used to resubmit the specification review.
     * <p>
     *
     * <p>
     * Initially set to null, once set cannot be null. It set by setter and accessed by getter. Used in
     * <code>execute</code> method.
     * </p>
     */
    private SpecificationReviewService specificationReviewService;

    /**
     * Default constructor, creates new instance.
     */
    public ResubmitSpecificationReviewAction() {
    }

    /**
     * <p>
     * Executes the request to resubmit a specification review.
     * </p>
     *
     * <p>
     * No exception is thrown by this method. If any exception occurs, the exception is wrapped in
     * <code>ResubmitSpecificationReviewActionException</code> with a proper message, stored in the model
     * using the <code>setException</code> method, and <code>Action.ERROR</code> is returned.
     * </p>
     *
     * <p>
     * If any required field hasn't been injected, <code>ResubmitSpecificationReviewActionException</code>
     * will be stored in the model and <code>Action.ERROR</code> is returned.
     * </p>
     *
     * @return the action result String, will either be <code>Action.SUCCESS</code> or
     *         <code>Action.ERROR</code> depending on whether method was successful
     */
    public String execute() {
        try {
            // make sure required fields are present and valid
            if (specificationReviewService == null) {
                return Helper.handleActionError(this, new ResubmitSpecificationReviewActionException(
                    "specificationReviewService has not been injected."));
            }

            // resubmit the specification
            specificationReviewService.resubmitSpecification(getTCSubject(), getProjectId());
            
            return SUCCESS;

        } catch (Exception e) {
            return Helper.handleActionError(this, new ResubmitSpecificationReviewActionException(
                "An error occurred in execute method for ResubmitSpecificationReviewAction.", e));
        }
    }

    /**
     * Getter for content.
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Setter for content.
     *
     * @param content the content
     * @throws IllegalArgumentException if argument is null or empty
     */
    public void setContent(String content) {
        ExceptionUtils.checkNullOrEmpty(content, null, null, "content cannot be null or empty.");
        this.content = content;
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
}
