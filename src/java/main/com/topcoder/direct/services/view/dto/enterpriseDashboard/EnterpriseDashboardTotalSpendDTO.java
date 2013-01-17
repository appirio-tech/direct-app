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
 * @author GreatKevin
 * @version 1.1
 */
public class EnterpriseDashboardTotalSpendDTO implements Serializable {

    /**
     * The total spend of the month.
     */
    private long totalSpend;

    /**
     * The member cost of the month.
     *
     * @since 1.1
     */
    private long memberCost;

    /**
     * The contest fee of the month.
     *
     * @since 1.1
     */
    private long contestFee;

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
        return getMemberCost() + getContestFee();
    }

    /**
     * Sets the total spend.
     *
     * @param totalSpend the total spend.
     */
    public void setTotalSpend(long totalSpend) {
        this.totalSpend = totalSpend;
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
        return memberCost;
    }

    /**
     * Sets the member cost of the month.
     *
     * @param memberCost the member cost of the month.
     * @since 1.1
     */
    public void setMemberCost(long memberCost) {
        this.memberCost = memberCost;
    }

    /**
     * Gets the contest fee of the month.
     *
     * @return the contest fee of the month.
     * @since 1.1
     */
    public long getContestFee() {
        return contestFee;
    }

    /**
     * Sets the contest fee of the month.
     *
     * @param contestFee the contest fee of the month
     * @since 1.1
     */
    public void setContestFee(long contestFee) {
        this.contestFee = contestFee;
    }
}
