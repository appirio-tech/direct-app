/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest.cost;

import com.topcoder.management.project.Prize;

import java.util.List;

/**
 * The Cost DTO which represents the cost information of a challenge.
 *
 * @version 1.0 (TopCoder Direct - Add Estimation Cost Details to Receipt page)
 * @author Veve
 */
public class CostDTO {

    /**
     * The prizes
     */
    private List<Prize> prizes;

    /**
     * The dr points
     */
    private Double drPoints;

    /**
     * The reliability bonus.
     */
    private Double reliabilityBonus;

    /**
     * The spec review cost.
     */
    private Double specReviewCost;

    /**
     * The review cost.
     */
    private Double reviewCost;

    /**
     * The copilot cost.
     */
    private Double copilotCost;

    /**
     * The admin fee.
     */
    private Double adminFee;

    /**
     * Gets the prizes
     *
     * @return the prizes.
     */
    public List<Prize> getPrizes() {
        return prizes;
    }

    /**
     * Sets the prizes
     *
     * @param prizes the prizes.
     */
    public void setPrizes(List<Prize> prizes) {
        this.prizes = prizes;
    }

    /**
     * Gets the DR points.
     *
     * @return the DR points.
     */
    public Double getDrPoints() {
        return drPoints;
    }

    /**
     * Sets the DR points.
     *
     * @param drPoints
     */
    public void setDrPoints(Double drPoints) {
        this.drPoints = drPoints;
    }

    /**
     * Gets the reliability bonus.
     *
     * @return the reliability bonus.
     */
    public Double getReliabilityBonus() {
        return reliabilityBonus;
    }

    /**
     * Sets the reliability bonus.
     *
     * @param reliabilityBonus the reliability bonus.
     */
    public void setReliabilityBonus(Double reliabilityBonus) {
        this.reliabilityBonus = reliabilityBonus;
    }

    /**
     * Gets the spec review cost.
     *
     * @return the spec review cost.
     */
    public Double getSpecReviewCost() {
        return specReviewCost;
    }

    /**
     * Sets the spec review cost.
     *
     * @param specReviewCost the spec review cost.
     */
    public void setSpecReviewCost(Double specReviewCost) {
        this.specReviewCost = specReviewCost;
    }

    /**
     * Gets the review cost.
     *
     * @return
     */
    public Double getReviewCost() {
        return reviewCost;
    }

    /**
     * Sets the review cost.
     *
     * @param reviewCost the review cost.
     */
    public void setReviewCost(Double reviewCost) {
        this.reviewCost = reviewCost;
    }

    /**
     * Gets the copilot cost.
     *
     * @return the copilot cost.
     */
    public Double getCopilotCost() {
        return copilotCost;
    }

    /**
     * Sets the copilot cost.
     *
     * @param copilotCost the copilot cost.
     */
    public void setCopilotCost(Double copilotCost) {
        this.copilotCost = copilotCost;
    }

    /**
     * Gets the admin fee.
     *
     * @return the admin fee.
     */
    public Double getAdminFee() {
        return adminFee;
    }

    /**
     * Sets the admin fee.
     *
     * @param adminFee the admin fee.
     */
    public void setAdminFee(Double adminFee) {
        this.adminFee = adminFee;
    }

    /**
     * Gets total cost.
     *
     * @return the total cost.
     */
    public double getTotal() {
        double total = 0;
        if (getPrizes() != null && getPrizes().size() > 0) {
            for (Prize pr : getPrizes()) {
                total += (pr.getPrizeAmount() * pr.getNumberOfSubmissions());
            }
        }

        return (total + val(this.drPoints) + val(this.reliabilityBonus)
                + val(this.specReviewCost) + val(this.reviewCost) + val(this.copilotCost) + val(this.adminFee));
    }

    /**
     * The helper method.
     *
     * @param d the double value
     * @return 0 if null otherwise the value
     */
    private static double val(Double d) {
        return d == null ? 0 : d.doubleValue();
    }
}
