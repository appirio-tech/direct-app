/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.cost;

import com.topcoder.management.project.Prize;
import com.topcoder.management.project.PrizeType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The Cost DTO which represents the cost information of a challenge.
 *
 * <p>
 * Version 1.1 ([Direct] - challenge receipt page update)
 * <ul>
 *     <li>Added {@link #getMainPrizes()} to filter out the main prizes</li>
 *     <li>Added {@link #getCheckpointPrize()} ()} to filter out the checkpoint prizes</li>
 * </ul>
 * </p>
 *
 * @version 1.1
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
    private Double drPoints = 0d;

    /**
     * The reliability bonus.
     */
    private Double reliabilityBonus  = 0d;

    /**
     * The spec review cost.
     */
    private Double specReviewCost  = 0d;

    /**
     * The review cost.
     */
    private Double reviewCost  = 0d;

    /**
     * The copilot cost.
     */
    private Double copilotCost  = 0d;

    /**
     * The admin fee.
     */
    private Double adminFee  = 0d;

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
     * Filter out the prizes of certain prize type
     *
     * @param prize the list of prize to filter
     * @param prizeTypeId the prize type id
     * @return the list of prizes filtered.
     * @since 1.1
     */
    private List<Prize> filterAndSortPrizes(List<Prize> prize, long prizeTypeId) {
        List<Prize> filteredPrizes = new ArrayList<Prize>();

        for (Prize p : getPrizes()) {
            if (p.getPrizeType().getId() == prizeTypeId) {
                filteredPrizes.add(p);
            }
        }

        Collections.sort(filteredPrizes, new Comparator<Prize>() {
            public int compare(Prize o1, Prize o2) {
                return o1.getPlace() - o2.getPlace();
            }
        });

        return filteredPrizes;
    }

    /**
     * Filters out the main prizes and return
     *
     * @return the list of main prizes.
     * @since 1.1
     */
    public List<Prize> getMainPrizes() {
        return filterAndSortPrizes(this.getPrizes(), PrizeType.CONTEST_PRIZE.getId());
    }

    /**
     * Filter out the checkpoint prize and return. There should be only one checkpint prize.
     *
     * @return the filtered out checkpoint prize.
     * @since 1.1
     */
    public Prize getCheckpointPrize() {
        for (Prize p : getPrizes()) {
            if (p.getPrizeType().getId() == PrizeType.CHECKPOINT_PRIZE.getId()) {
                return p;
            }
        }

        return null;
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
