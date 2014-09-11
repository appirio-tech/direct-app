/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.contest;

import java.io.Serializable;

/**
 * Project status data DTO to hold total number of contest and aggregate amount of payment data.
 *
 * @author BeBetter
 * @version 1.0
 */
public class ProjectStatusData implements Serializable {
    /**
     * Serial id.
     */
    private static final long serialVersionUID = 5601619496370424382L;

    /**
     * total number of contests.
     */
    private int totalNumber = 0;

    /**
     * total amount of payment.
     */
    private double totalPayment = 0;

    /**
     * Gets the total number.
     *
     * @return the total number
     */
    public int getTotalNumber() {
        return totalNumber;
    }

    /**
     * Sets total number.
     *
     * @param totalNumber the total number to set
     */
    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    /**
     * Gets the total payment
     *
     * @return the total payment
     */
    public double getTotalPayment() {
        return totalPayment;
    }

    /**
     * Sets the total payment.
     *
     * @param totalPayment the total payment to set
     */
    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    /**
     * The <code>toString</code> function to print the detailed statistics value.
     *
     * @return the statistics value for this object
     */
    @Override
    public String toString() {
        return "totalNumber : " + totalNumber + " totalPayment : " + totalPayment;
    }
}
