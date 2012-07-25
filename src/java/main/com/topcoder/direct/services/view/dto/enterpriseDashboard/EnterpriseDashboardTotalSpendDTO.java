/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.enterpriseDashboard;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * The DTO represents total spend data in a month of the enterprise dashboard total spend chart.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0
 */
public class EnterpriseDashboardTotalSpendDTO implements Serializable {

    /**
     * The total spend of the month.
     */
    private long totalSpend;

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
        return totalSpend;
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
}
