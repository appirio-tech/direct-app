/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

/**
 * Represents a record entry in the contest receipt
 *
 * @author GreatKevin
 * @version 1.0
 */
public class ContestReceiptEntry {

    /**
     * The type of the receipt payment entry.
     */
    private String paymentType;

    /**
     * The amount of the receipt payment entry.
     */
    private double paymentAmount;

    /**
     * Gets the payment type.
     *
     * @return the payment type.
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * Sets the payment type.
     *
     * @param paymentType the payment type.
     */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * Gets the payment amount.
     *
     * @return the payment amount.
     */
    public double getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * Sets the payment amount.
     *
     * @param paymentAmount the payment amount;
     */
    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
