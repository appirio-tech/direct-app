/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.payments.DTO;

/**
 * <p>This class is used to hold payment trend data.</p>
 *
 * <p>
 * <strong>Thread safety</strong>: This class is not thread-safe. However, it will be
 * used in a thread safe manner.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version  1.0 (System Assembly - TopCoder Direct Member Payments Dashboard v1.0)
 */
public class PaymentTrendItem {
    /**
     * The time stamp.
     */
    private String timeStamp;

    /**
     * The paid payment.
     */
    private double paid;

    /**
     * The created payment.
     */
    private double created;

    /**
     * Get the time stamp.
     *
     * @return the time stamp
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * Set the time stamp.
     *
     * @param timeStamp the time stamp
     */
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Get the paid payment.
     *
     * @return the payment.
     */
    public double getPaid() {
        return paid;
    }

    /**
     * Add the paid payment.
     *
     * @param paidDiff the payment.
     */
    public void addPaid(double paidDiff) {
        this.paid += paidDiff;
    }

    /**
     * Get the created payment.
     *
     * @return the payment.
     */
    public double getCreated() {
        return created;
    }

    /**
     * Add the created payment.
     *
     * @param createdDiff the created payment.
     */
    public void addCreated(double createdDiff) {
        this.created += createdDiff;
    }
}
