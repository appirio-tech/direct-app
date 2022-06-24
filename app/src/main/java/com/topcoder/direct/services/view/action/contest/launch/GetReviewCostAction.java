/*
 * Copyright (C) 2013 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.contest.ReviewType;
import com.topcoder.direct.services.view.util.challenge.CostCalculationService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>An action for handling the requests for getting the review cost for desired contest.</p>
 * <p/>
 * <p>
 * Version 1.1 (Module Assembly - TC Cockpit Launch F2F contest)
 * <ul>
 * <li>Updated {@link #executeAction()} to calculate the iterative review payment</li>
 * </ul>
 * </p>
 * <p/>
 * <p>
 * Version 2.0 (TopCoder Direct - Review Cost Calculation Quick Updates) @author GreatKevin @challenge 30044580
 * <ul>
 * <li>Updated the review calculation logic, the screen, review, aggregation etc review cost are determined
 * by phase. The default number of submissions, reviewer number and review type are all taken into account</li>
 * </ul>
 * </p>
 *
 * @author notpad
 * @version 2.0
 */
public class GetReviewCostAction extends BaseDirectStrutsAction {

    /**
     * The contest id.
     *
     * @since 2.0
     */
    private long projectId;

    /**
     * Represents the project category id.
     */
    private long projectCategoryId;

    /**
     * The review type, can be 'Internal' or 'Community'.
     *
     * @since 2.0
     */
    private ReviewType reviewType;

    /**
     * Represents the first place prize value to be used to compute the payment.
     */
    private BigDecimal prize;

    /**
     * <p>Constructs new <code>GetReviewCostAction</code> instance. This implementation does nothing.</p>
     */
    public GetReviewCostAction() {
    }

    /**
     * <p>Handles the incoming request. Calculates the reivew cost for requested contest and binds
     * them to request.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    protected void executeAction() throws Exception {

        Map<String, Object> result = new HashMap<String, Object>();


        Map<String, BigDecimal> phasesPayment = CostCalculationService.getInstance().getPhasesPayment(getProjectId(),
                getProjectCategoryId(), reviewType, getPrize());


        for (Map.Entry<String, BigDecimal> entry : phasesPayment.entrySet()) {
            result.put(entry.getKey(), entry.getValue());
        }

        result.put("total", phasesPayment.get(CostCalculationService.TOTAL_RESULT_KEY));

        setResult(result);
    }

    /**
     * Gets the contest id.
     *
     * @return the contest id.
     * @since 2.0
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Sets the contest id.
     *
     * @param projectId the contest id.
     * @since 2.0
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the project category id.
     *
     * @return the project category id.
     */
    public long getProjectCategoryId() {
        return projectCategoryId;
    }

    /**
     * Sets the project category id.
     *
     * @param projectCategoryId the project category id.
     */
    public void setProjectCategoryId(long projectCategoryId) {
        this.projectCategoryId = projectCategoryId;
    }

    /**
     * @return the prize
     */
    public BigDecimal getPrize() {
        return prize;
    }

    /**
     * @param prize the prize to set
     */
    public void setPrize(BigDecimal prize) {
        this.prize = prize;
    }

    /**
     * Sets the review type.
     *
     * @param reviewType the review type.
     * @since 2.0
     */
    public void setReviewType(ReviewType reviewType) {
        this.reviewType = reviewType;
    }

}
