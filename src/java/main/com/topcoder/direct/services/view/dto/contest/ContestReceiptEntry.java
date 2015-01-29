/*
 * Copyright (C) 2012 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

/**
 * Represents a record entry in the contest receipt
 *
 * <p>
 * Version 1.1 ([Direct] - challenge receipt page update)
 * <ul>
 *     <li>Added {@link #paymentTypeId} and its getter and setter</li>
 *     <li>Added {@link #estimatedAmount} and its getter and setter</li>
 *     <li>Added default constructor {@link #ContestReceiptEntry()}</li>
 *     <li>Added copy constructor {@link #ContestReceiptEntry(ContestReceiptEntry)}</li>
 * </ul>
 * </p>
 *
 * @author GreatKevin, Veve
 * @version 1.1
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
     * The payment type id.
     * @since 1.1
     */
    private long paymentTypeId;

    /**
     * The estimation cost amount of this payment type.
     * @since 1.1
     */
    private double estimatedAmount;

    /**
     * The default constructor.
     *
     * @since 1.1
     */
    public ContestReceiptEntry() {

    }

    /**
     * The copy constructor.
     *
     * @param entry the entry to copy.
     * @since 1.1
     */
    public ContestReceiptEntry(ContestReceiptEntry entry) {
        this.paymentType = entry.getPaymentType();
        this.paymentAmount = entry.getPaymentAmount();
        this.paymentTypeId = entry.getPaymentTypeId();
        this.estimatedAmount = entry.getEstimatedAmount();
    }

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

    /**
     * Gets the payment type id.
     *
     * @return the payment type id.
     * @since 1.1
     */
    public long getPaymentTypeId() {
        return paymentTypeId;
    }

    /**
     * Sets the payment type id.
     *
     * @param paymentTypeId the payment type id.
     * @since 1.1
     */
    public void setPaymentTypeId(long paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    /**
     * Gets the estimated amount.
     *
     * @return the estimated amount.
     * @since 1.1
     */
    public double getEstimatedAmount() {
        return estimatedAmount;
    }

    /**
     * Sets the estimated amount.
     *
     * @param estimatedAmount the estimated amount.
     * @since 1.1
     */
    public void setEstimatedAmount(double estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }
}
