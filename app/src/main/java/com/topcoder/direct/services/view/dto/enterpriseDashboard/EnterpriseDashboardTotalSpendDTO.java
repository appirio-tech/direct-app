/*
 * Copyright (C) 2012 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.enterpriseDashboard;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * The DTO represents total spend data in a month of the enterprise dashboard total spend chart.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TC Cockpit New Enterprise Dashboard Release 1)
 * <ul>
 *     <li>Add properties {@link #memberCost} and {@link #contestFee}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - Cockpit Enterprise Dashboard Chart Drill-In)
 * <ul>
 *     <li>Add property {@link #memberCostSum}</li>
 *     <li>Add property {@link #contestFeeSum}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.3 (Release Assembly - TC Cockpit Enterprise Dashboard Projected Cost and Project Health Page)
 * <ul>
 *     <li>Adds property {@link #projectedContestFeeSum} and its getter and setter</li>
 *     <li>Adds property {@link #projectedMemberCostSum} and its getter and setter</li>
 * </ul>
 * </p>
 *
 * @author GreatKevin
 * @version 1.3
 */
public class EnterpriseDashboardTotalSpendDTO implements Serializable {

    /**
     * The sum of member cost.
     *
     * @since 1.2
     */
    private double memberCostSum;

    /**
     * The sum of contest fee.
     *
     * @since 1.2
     */
    private double contestFeeSum;

    /**
     * The projected member cost sum.
     *
     * @since 1.3
     */
    private double projectedMemberCostSum;


    /**
     * The projected contest fee sum.
     *
     * @since 1.3
     */
    private double projectedContestFeeSum;

    /**
     * The average spend of the month.
     */
    private long averageSpend;

    /**
     * The month representing by <code>Date</code>
     */
    private Date date;

    /**
     * Gets the total spend.
     *
     * @return the total spend.
     */
    public long getTotalSpend() {
        return Math.round(this.contestFeeSum + this.memberCostSum);
    }

    /**
     * Gets the projected total spend
     *
     * @return the projected total spend
     * @since 1.3
     */
    public long getProjectedTotalSpend() {
        return Math.round(
                this.contestFeeSum + this.memberCostSum + this.projectedContestFeeSum + this.projectedMemberCostSum);
    }

    /**
     * Gets the planned total cost (active + draft contests)
     *
     * @return the planned total cost
     * @since 1.3
     */
    public long getTotalProjected() {
        return Math.round(this.projectedContestFeeSum + this.projectedMemberCostSum);
    }

    /**
     * Gets the average spend.
     *
     * @return the average spend.
     */
    public long getAverageSpend() {
        return averageSpend;
    }

    /**
     * Sets the average spend.
     *
     * @param averageSpend the average spend.
     */
    public void setAverageSpend(long averageSpend) {
        this.averageSpend = averageSpend;
    }

    /**
     * Gets the month date.
     *
     * @return the month date.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the month date.
     *
     * @param date the month date.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the member cost of the month.
     *
     * @return the member cost of the month
     * @since 1.1
     */
    public long getMemberCost() {
        return Math.round(this.memberCostSum);
    }

    /**
     * Gets the contest fee of the month.
     *
     * @return the contest fee of the month.
     * @since 1.1
     */
    public long getContestFee() {
        return Math.round(contestFeeSum);
    }

    /**
     * Gets the projected member cost.
     *
     * @return the projected member cost.
     * @since 1.3
     */
    public long getProjectedMemberCost() {
        return Math.round(this.projectedMemberCostSum);
    }

    /**
     * Gets the projected contest fee.
     *
     * @return the projected contest fee.
     * @since 1.3
     */
    public long getProjectedContestFee() {
        return Math.round(this.projectedContestFeeSum);
    }

    /**
     * Gets the member cost sum.
     *
     * @return the member cost sum.
     * @since 1.2
     */
    public double getMemberCostSum() {
        return memberCostSum;
    }

    /**
     * Sets the member cost sum.
     *
     * @param memberCostSum the member cost sum.
     * @since 1.2
     */
    public void setMemberCostSum(double memberCostSum) {
        this.memberCostSum = memberCostSum;
    }

    /**
     * Gets the contest fee sum.
     *
     * @return the contest fee sum.
     * @since 1.2
     */
    public double getContestFeeSum() {
        return contestFeeSum;
    }

    /**
     * Sets the contest fee sum.
     *
     * @param contestFeeSum the contest fee sum.
     * @since 1.2
     */
    public void setContestFeeSum(double contestFeeSum) {
        this.contestFeeSum = contestFeeSum;
    }

    /**
     * Gets the projected member cost sum
     *
     * @return the projected member cost sum.
     * @since 1.3
     */
    public double getProjectedMemberCostSum() {
        return projectedMemberCostSum;
    }

    /**
     * Sets the projected member cost sum.
     *
     * @param projectedMemberCostSum the projected member cost sum.
     * @since 1.3
     */
    public void setProjectedMemberCostSum(double projectedMemberCostSum) {
        this.projectedMemberCostSum = projectedMemberCostSum;
    }

    /**
     * Gets the projected contest fee sum.
     *
     * @return the projected contest fee sum.
     * @since 1.3
     */
    public double getProjectedContestFeeSum() {
        return projectedContestFeeSum;
    }

    /**
     * Sets the projected contest fee sum.
     *
     * @param projectedContestFeeSum the projected contest fee sum.
     * @since 1.3
     */
    public void setProjectedContestFeeSum(double projectedContestFeeSum) {
        this.projectedContestFeeSum = projectedContestFeeSum;
    }
}
