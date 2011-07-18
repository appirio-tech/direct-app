package com.topcoder.direct.services.view.dto.dashboard;

import java.io.Serializable;

/**
 * The entity represents the summary data in enterprise dashboard.
 *
 * @author TCSASSEMBLER
 * @version 1.0
 */
public class EnterpriseDashboardSummary implements Serializable {

    /**
     * The average fulfillment.
     */
    private double averageFulfillment;

    /**
     * The average cost of the contest.
     */
    private double averageContestCost;

    /**
     * The average duration of the contest.
     */
    private double averageContestDuration;

    /**
     * The average contest volume in 30 days.
     */
    private double averageVolPerMonth;

    /**
     * The total contest volume.
     */
    private int totalContestVolume;

    /**
     * The total member cost of the contests.
     */
    private double totalMemberCost;

    /**
     * The min contest cost.
     */
    private Double minContestCost;

    /**
     * The max contest cost.
     */
    private Double maxContestCost;

    /**
     * The min contest duration.
     */
    private Double minContestDuration;

    /**
     * The max contest duration.
     */
    private Double maxContestDuration;

    /**
     * Gets the average fulfilment.
     *
     * @return the average fulfillment.
     */
    public double getAverageFulfillment() {
        return averageFulfillment;
    }

    /**
     * Sets the average fulfillment.
     *
     * @param averageFulfillment the average fulfillment to set.
     */
    public void setAverageFulfillment(double averageFulfillment) {
        this.averageFulfillment = averageFulfillment;
    }

    /**
     * Gets the average contest cost.
     *
     * @return the average contest cost.
     */
    public double getAverageContestCost() {
        return averageContestCost;
    }

    /**
     * Sets the average contest cost.
     *
     * @param averageContestCost the average contest cost to set.
     */
    public void setAverageContestCost(double averageContestCost) {
        this.averageContestCost = averageContestCost;
    }


    /**
     * Gets the average contest duration.
     *
     * @return the average contest duration.
     */
    public double getAverageContestDuration() {
        return averageContestDuration;
    }

    /**
     * Sets the average contest duration.
     *
     * @param averageContestDuration the average contest duration to set.
     */
    public void setAverageContestDuration(double averageContestDuration) {
        this.averageContestDuration = averageContestDuration;
    }

    /**
     * Gets the average contests volume in 30 days.
     *
     * @return the average contests volume in 30 days.
     */
    public double getAverageVolPerMonth() {
        return averageVolPerMonth;
    }

    /**
     * Sets the average contest volume in 30 days.
     *
     * @param averageVolPerMonth the average contest volume in 30 days.
     */
    public void setAverageVolPerMonth(double averageVolPerMonth) {
        this.averageVolPerMonth = averageVolPerMonth;
    }

    /**
     * Gets the total contests volume/
     *
     * @return the total contest volume.
     */
    public int getTotalContestVolume() {
        return totalContestVolume;
    }

    /**
     * Sets the total contests volume
     *
     * @param totalContestVolume the total contest volume.
     */
    public void setTotalContestVolume(int totalContestVolume) {
        this.totalContestVolume = totalContestVolume;
    }

    /**
     * Gets the total member cost.
     *
     * @return the total member cost.
     */
    public double getTotalMemberCost() {
        return totalMemberCost;
    }

    /**
     * Sets the total member cost.
     *
     * @param totalMemberCost the total member cost to set.
     */
    public void setTotalMemberCost(double totalMemberCost) {
        this.totalMemberCost = totalMemberCost;
    }

    /**
     * Gets the min contest cost.
     *
     * @return the min contest cost.
     */
    public Double getMinContestCost() {
        return minContestCost;
    }

    /**
     * Sets the min contest cost.
     *
     * @param minContestCost the min contest cost to set.
     */
    public void setMinContestCost(Double minContestCost) {
        this.minContestCost = minContestCost;
    }

    /**
     * Gets the max contest cost.
     *
     * @return the max contest cost.
     */
    public Double getMaxContestCost() {
        return maxContestCost;
    }

    /**
     * Sets the max contest cost.
     *
     * @param maxContestCost the max contest cost.
     */
    public void setMaxContestCost(Double maxContestCost) {
        this.maxContestCost = maxContestCost;
    }

    /**
     * Gets the min contest duration.
     *
     * @return the min contes duration.
     */
    public Double getMinContestDuration() {
        return minContestDuration;
    }

    /**
     * Sets the min contes duration.
     *
     * @param minContestDuration the min contest duration.
     */
    public void setMinContestDuration(Double minContestDuration) {
        this.minContestDuration = minContestDuration;
    }

    /**
     * Gets the max contest duration.
     *
     * @return the max contest duration.
     */
    public Double getMaxContestDuration() {
        return maxContestDuration;
    }

    /**
     * Sets the max contest duration.
     *
     * @param maxContestDuration the max contest duration.
     */
    public void setMaxContestDuration(Double maxContestDuration) {
        this.maxContestDuration = maxContestDuration;
    }
}
