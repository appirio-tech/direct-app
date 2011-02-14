/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import java.io.Serializable;

/**
 * <p>A DTO providing the complete details on a contest receipt.</p>
 *
 * @author flexme
 * @version 1.0
 */
public class ContestReceiptDTO implements Serializable {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 5419111891236132022L;
    
    /**
     * Represents the first place prize.
     */
    private double firstPlacePrize;
    
    /**
     * Represents the second place prize.
     */
    private double secondPlacePrize;
    
    /**
     * Represents the third place prize.
     */
    private double thirdPlacePrize;
    
    /**
     * Represents the fourth place prize.
     */
    private double fourthPlacePrize;
    
    /**
     * Represents the fifth place prize.
     */
    private double fifthPlacePrize;
    
    /**
     * Represents the milestone prize.
     */
    private double milestonePrize;
    
    /**
     * Represents the DR points.
     */
    private double drPoints;
    
    /**
     * Represents the contest fee.
     */
    private double contestFee;
    
    /**
     * Represents the reliability bonus.
     */
    private double reliabilityBonus;
    
    /**
     * Represents the specification review cost.
     */
    private double specReviewCost;
    
    /**
     * Represents the review cost.
     */
    private double reviewCost;
    
    /**
     * Represents the copilot cost.
     */
    private double copilotCost;
    
    /**
     * Represents the bug fix cost.
     */
    private double bugFixCost;
    
    /**
     * Represents the total cost.
     */
    private double totalCost;

    /**
     * Represents whether the contest has been finished.
     */
    private boolean finished;

    /**
     * <p>Constructs new <code>ContestReceiptDTO</code> instance. This implementation does nothing.</p>
     */
    public ContestReceiptDTO() {
        
    }

    /**
     * Gets the first place prize.
     * 
     * @return the first place prize.
     */
    public double getFirstPlacePrize() {
        return firstPlacePrize;
    }

    /**
     * Sets the first place prize.
     * 
     * @param firstPlacePrize the first place prize.
     */
    public void setFirstPlacePrize(double firstPlacePrize) {
        this.firstPlacePrize = firstPlacePrize;
    }

    /**
     * Gets the second place prize.
     * 
     * @return the second place prize.
     */
    public double getSecondPlacePrize() {
        return secondPlacePrize;
    }

    /**
     * Sets the second place prize.
     * 
     * @param secondPlacePrize the second place prize.
     */
    public void setSecondPlacePrize(double secondPlacePrize) {
        this.secondPlacePrize = secondPlacePrize;
    }

    /**
     * Gets the third place prize.
     * 
     * @return the third place prize.
     */
    public double getThirdPlacePrize() {
        return thirdPlacePrize;
    }

    /**
     * Sets the third place prize.
     * @param thirdPlacePrize the third place prize.
     */
    public void setThirdPlacePrize(double thirdPlacePrize) {
        this.thirdPlacePrize = thirdPlacePrize;
    }

    /**
     * Gets the fourth place prize.
     * 
     * @return the fourth place prize.
     */
    public double getFourthPlacePrize() {
        return fourthPlacePrize;
    }

    /**
     * Sets the fourth place prize.
     * 
     * @param fourthPlacePrize the fourth place prize.
     */
    public void setFourthPlacePrize(double fourthPlacePrize) {
        this.fourthPlacePrize = fourthPlacePrize;
    }

    /**
     * Gets the fifth place prize.
     * 
     * @return the fifth place prize.
     */
    public double getFifthPlacePrize() {
        return fifthPlacePrize;
    }

    /**
     * Sets the fifth place prize.
     * @param fifthPlacePrize the fifth place prize.
     */
    public void setFifthPlacePrize(double fifthPlacePrize) {
        this.fifthPlacePrize = fifthPlacePrize;
    }

    /**
     * Gets the milestone prize.
     * 
     * @return the milestone prize.
     */
    public double getMilestonePrize() {
        return milestonePrize;
    }

    /**
     * Sets the milestone prize.
     * 
     * @param milestonePrize the milestone prize.
     */
    public void setMilestonePrize(double milestonePrize) {
        this.milestonePrize = milestonePrize;
    }

    /**
     * Gets the DR points.
     * 
     * @return the DR points.
     */
    public double getDrPoints() {
        return drPoints;
    }

    /**
     * Sets the DR points.
     * @param drPoints the DR points.
     */
    public void setDrPoints(double drPoints) {
        this.drPoints = drPoints;
    }

    /**
     * Gets the contest fee.
     * 
     * @return the contest fee.
     */
    public double getContestFee() {
        return contestFee;
    }

    /**
     * Sets the contest fee.
     * 
     * @param contestFee the contest fee.
     */
    public void setContestFee(double contestFee) {
        this.contestFee = contestFee;
    }

    /**
     * Gets the reliability bonus.
     * 
     * @return the reliability bonus.
     */
    public double getReliabilityBonus() {
        return reliabilityBonus;
    }

    /**
     * Sets the reliability bonus.
     * 
     * @param reliabilityBonus the reliability bonus.
     */
    public void setReliabilityBonus(double reliabilityBonus) {
        this.reliabilityBonus = reliabilityBonus;
    }

    /**
     * Gets the specification review cost.
     * 
     * @return the specification review cost.
     */
    public double getSpecReviewCost() {
        return specReviewCost;
    }

    /**
     * Sets the specification review cost.
     * 
     * @param specReviewCost the specification review cost.
     */
    public void setSpecReviewCost(double specReviewCost) {
        this.specReviewCost = specReviewCost;
    }

    /**
     * Gets the review cost.
     * 
     * @return the review cost.
     */
    public double getReviewCost() {
        return reviewCost;
    }

    /**
     * Sets the review cost.
     * 
     * @param reviewCost the review cost.
     */
    public void setReviewCost(double reviewCost) {
        this.reviewCost = reviewCost;
    }

    /**
     * Gets the copilot cost.
     * 
     * @return the copilot cost.
     */
    public double getCopilotCost() {
        return copilotCost;
    }

    /**
     * Sets the copilot cost.
     * 
     * @param copilotCost the copilot cost.
     */
    public void setCopilotCost(double copilotCost) {
        this.copilotCost = copilotCost;
    }

    /**
     * Gets the bug fix cost.
     * 
     * @return the bug fix cost.
     */
    public double getBugFixCost() {
        return bugFixCost;
    }

    /**
     * Sets the bug fix cost.
     * 
     * @param bugFixCost the bug fix cost.
     */
    public void setBugFixCost(double bugFixCost) {
        this.bugFixCost = bugFixCost;
    }

    /**
     * Gets the total cost.
     * 
     * @return the total cost.
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * Sets the total cost.
     * 
     * @param totalCost the total cost.
     */
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    /**
     * Gets the flag indicates whether the contest has been finished.
     * 
     * @return the flag indicates whether the contest has been finished.
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Sets the flag indicates whether the contest has been finished.
     * 
     * @param finished the flag indicates whether the contest has been finished.
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
