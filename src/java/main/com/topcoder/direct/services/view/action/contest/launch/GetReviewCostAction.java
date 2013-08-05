/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import com.topcoder.management.payment.calculator.impl.DefaultProjectPaymentCalculator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>An action for handling the requests for getting the review cost for desired contest.</p>
 * 
 * @author notpad
 * @version 1.0 (BUGR-8206 - TopCoder Direct Default Payment Update)
 */
public class GetReviewCostAction extends BaseDirectStrutsAction {
    /**
     * Represents the project payment calculator.
     */
    private DefaultProjectPaymentCalculator projectPaymentCalculator;

    /**
     * Represents the project category id.
     */
    private long projectCategoryId;

    /**
     * Represents the first place prize value to be used to compute the payment.
     */
    private BigDecimal prize;

    /**
     * Represents the default number of submissions to be considered when computing the payment.
     */
    private final int DEFAULT_SUBMISSION_COUNT = 2;

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
        ActionHelper.checkInjectedFieldNull(getProjectPaymentCalculator(), "projectPaymentCalculator");
        
        Map<String, Object> result = new HashMap<String, Object>();
        BigDecimal screeningCost = projectPaymentCalculator.getDefaultPayment(projectCategoryId, 
                DefaultProjectPaymentCalculator.PRIMARY_SCREENER_RESOURCE_ROLE_ID, prize, DEFAULT_SUBMISSION_COUNT);
        BigDecimal reviewCost = projectPaymentCalculator.getDefaultPayment(projectCategoryId,
                DefaultProjectPaymentCalculator.REVIEWER_RESOURCE_ROLE_ID, prize, DEFAULT_SUBMISSION_COUNT);
        BigDecimal aggregationCost = projectPaymentCalculator.getDefaultPayment(projectCategoryId,
                DefaultProjectPaymentCalculator.AGGREGATOR_RESOURCE_ROLE_ID, prize, DEFAULT_SUBMISSION_COUNT);
        BigDecimal finalReviewCost = projectPaymentCalculator.getDefaultPayment(projectCategoryId,
                DefaultProjectPaymentCalculator.FINAL_REVIEWER_RESOURCE_ROLE_ID, prize, DEFAULT_SUBMISSION_COUNT);
        BigDecimal specReviewCost = projectPaymentCalculator.getDefaultPayment(projectCategoryId,
                DefaultProjectPaymentCalculator.SPECIFICATION_REVIEWER_RESOURCE_ROLE_ID, prize, DEFAULT_SUBMISSION_COUNT);
        
        result.put("screeningCost", screeningCost);
        result.put("reviewCost", reviewCost);
        result.put("aggregationCost", aggregationCost);
        result.put("finalReviewCost", finalReviewCost);
        result.put("specReviewCost", specReviewCost);
        
        setResult(result);
    }
    
    /**
     * Gets the project payment calculator.
     * 
     * @return the project payment calculator.
     */
    public DefaultProjectPaymentCalculator getProjectPaymentCalculator() {
        return projectPaymentCalculator;
    }

    /**
     * Sets the project payment calculator.
     * 
     * @param projectId the project payment calculator.
     */
    public void setProjectPaymentCalculator(DefaultProjectPaymentCalculator projectPaymentCalculator) {
        this.projectPaymentCalculator = projectPaymentCalculator;
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
}
