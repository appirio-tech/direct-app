/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.payments.entities;

/**
 * <p>
 * This class represents the payment diff of different payment method.
 * </p>
 * 
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable and not thread safe.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TopCoder Direct Member Payments Dashboard
 *          Backend Assembly)
 * @since 1.0
 */
public class PaymentDiff {

    /**
     * Represents the paypal diff amount.
     */
    private double paypalDiffAmount;

    /**
     * Represents the payoneer diff amount.
     */
    private double payoneerDiffAmount;

    /**
     * Represents the western union diff amount.
     */
    private double westernUnionDiffAmount;

    /**
     * The default empty ctor.
     */
    public PaymentDiff() {
        // do nothing
    }

    /**
     * <p>
     * Getter of paypalDiffAmount field.
     * </p>
     * 
     * @return the paypalDiffAmount
     */
    public double getPaypalDiffAmount() {
        return paypalDiffAmount;
    }

    /**
     * <p>
     * Setter of paypalDiffAmount field.
     * </p>
     * 
     * @param paypalDiffAmount
     *            the paypalDiffAmount to set
     */
    public void setPaypalDiffAmount(double paypalDiffAmount) {
        this.paypalDiffAmount = paypalDiffAmount;
    }

    /**
     * <p>
     * Getter of payoneerDiffAmount field.
     * </p>
     * 
     * @return the payoneerDiffAmount
     */
    public double getPayoneerDiffAmount() {
        return payoneerDiffAmount;
    }

    /**
     * <p>
     * Setter of payoneerDiffAmount field.
     * </p>
     * 
     * @param payoneerDiffAmount
     *            the payoneerDiffAmount to set
     */
    public void setPayoneerDiffAmount(double payoneerDiffAmount) {
        this.payoneerDiffAmount = payoneerDiffAmount;
    }

    /**
     * <p>
     * Getter of westernUnionDiffAmount field.
     * </p>
     * 
     * @return the westernUnionDiffAmount
     */
    public double getWesternUnionDiffAmount() {
        return westernUnionDiffAmount;
    }

    /**
     * <p>
     * Setter of westernUnionDiffAmount field.
     * </p>
     * 
     * @param westernUnionDiffAmount
     *            the westernUnionDiffAmount to set
     */
    public void setWesternUnionDiffAmount(double westernUnionDiffAmount) {
        this.westernUnionDiffAmount = westernUnionDiffAmount;
    }

}
