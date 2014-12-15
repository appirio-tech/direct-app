/*
 * Copyright (C) 2011 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import com.topcoder.direct.services.view.dto.cost.CostDTO;

import java.io.Serializable;
import java.util.List;

/**
 * <p>A DTO providing the complete details on a contest receipt.</p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TC Direct Cockpit Release Two) updates:
 * - Add the field <code>checkpointPrizeNumber</code> to show how many checkpoint submission can have checkpoint prize.
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TC Direct Cockpit Release Five) updates:
 * - Add the field <code>contestLauncherId</code> to store the launcher of the contest.
 * </p>
 *
 * <p>
 * Version 1.3 (Release Assembly - TC Direct Cockpit Release Seven version 1.0)
 * <ul>
 *     <li>Add the property {@link #showReceipt}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.4 (TopCoder Direct - Add Estimation Cost Details to Receipt page)
 * <ul>
 *     <li>Added the property {@link #estimation} </li>
 * </ul>
 * </p>
 *
 * @author flexme, GreatKevin, Veve
 * @version 1.4
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
     * Represents the checkpoint prize.
     */
    private double checkpointPrize;
    
    /**
     * Represents how many submissions have been paid with checkpoint prize.
     * @since 1.1
     */
    private int checkpointPrizeNumber;
    
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
     * The user id of the contest launcher.
     *
     * @since 1.2
     */
    private long contestLauncherId;

    /**
     * Whether to show the receipt details to the user.
     *
     * @since 1.3
     */
    private boolean showReceipt;

    /**
     * The receipt payment entries.
     *
     * @since BUGR-7169
     */
    private List<ContestReceiptEntry> entries;

    /**
     * The estimation cost in the receipt.
     *
     * @since 1.4
     */
    private CostDTO estimation;

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
     * Gets the checkpoint prize.
     * 
     * @return the checkpoint prize.
     */
    public double getCheckpointPrize() {
        return checkpointPrize;
    }

    /**
     * Sets the checkpoint prize.
     * 
     * @param checkpointPrize the checkpoint prize.
     */
    public void setCheckpointPrize(double checkpointPrize) {
        this.checkpointPrize = checkpointPrize;
    }

    /**
     * Gets the number of checkpoint prize.
     *
     * @return the number of checkpoint prize.
     * @since 1.1
     */
    public int getCheckpointPrizeNumber() {
        return checkpointPrizeNumber;
    }

    /**
     * Sets the number of checkpoint prize.
     *
     * @param checkpointPrizeNumber the number of checkpoint prize.
     * @since 1.1
     */
    public void setCheckpointPrizeNumber(int checkpointPrizeNumber) {
        this.checkpointPrizeNumber = checkpointPrizeNumber;
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
        double total = 0;

        for(ContestReceiptEntry entry : getEntries()) {
            total += entry.getPaymentAmount();
        }

        return total;
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

    /**
     * Gets the user id of the contest launcher.
     *
     * @return the user id of the contest launcher.
     * @since 1.2
     */
    public long getContestLauncherId() {
        return contestLauncherId;
    }

    /**
     * Sets the user id of the contest launcher.
     *
     * @param contestLauncherId the user id of the contest launcher.
     * @since 1.2
     */
    public void setContestLauncherId(long contestLauncherId) {
        this.contestLauncherId = contestLauncherId;
    }

    /**
     * Gets whether to show the receipt.
     *
     * @return whether to show the receipt.
     */
    public boolean isShowReceipt() {
        return showReceipt;
    }

    /**
     * Sets whether to show the receipt.
     *
     * @param showReceipt whether to show the receipt.
     */
    public void setShowReceipt(boolean showReceipt) {
        this.showReceipt = showReceipt;
    }

    /**
     * Gets the contest receipt entries.
     *
     * @return the contest receipt entries.
     */
    public List<ContestReceiptEntry> getEntries() {
        return entries;
    }

    /**
     * Sets the contest receipt entries.
     *
     * @param entries
     */
    public void setEntries(List<ContestReceiptEntry> entries) {
        this.entries = entries;
    }

    /**
     * Gets the estimation cost.
     *
     * @return the estimation cost.
     * @since 1.4
     */
    public CostDTO getEstimation() {
        return estimation;
    }

    /**
     * Sets the estimation cost.
     *
     * @param estimation the estimation cost.
     * @since 1.4
     */
    public void setEstimation(CostDTO estimation) {
        this.estimation = estimation;
    }
}
