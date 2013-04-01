/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.payments.entities;

/**
 * <p>
 * This entity is a container for payment balance information.
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
public class PaymentBalance {

    /**
     * Represents the PayPal balance amount.
     */
    private double paypalBalance;

    /**
     * Represents Payoneer balance amount.
     */
    private double payoneerBalance;

    /**
     * Represents the western union balance amount.
     */
    private double westernUnionBalance;

    /**
     * The default empty ctor.
     */
    public PaymentBalance() {
        // do nothing
    }

    /**
     * <p>
     * Getter of paypalBalance field.
     * </p>
     * 
     * @return the paypalBalance
     */
    public double getPaypalBalance() {
        return paypalBalance;
    }

    /**
     * <p>
     * Setter of paypalBalance field.
     * </p>
     * 
     * @param paypalBalance
     *            the paypalBalance to set
     */
    public void setPaypalBalance(double paypalBalance) {
        this.paypalBalance = paypalBalance;
    }

    /**
     * <p>
     * Getter of payoneerBalance field.
     * </p>
     * 
     * @return the payoneerBalance
     */
    public double getPayoneerBalance() {
        return payoneerBalance;
    }

    /**
     * <p>
     * Setter of payoneerBalance field.
     * </p>
     * 
     * @param payoneerBalance
     *            the payoneerBalance to set
     */
    public void setPayoneerBalance(double payoneerBalance) {
        this.payoneerBalance = payoneerBalance;
    }

    /**
     * <p>
     * Getter of westernUnionBalance field.
     * </p>
     * 
     * @return the westernUnionBalance
     */
    public double getWesternUnionBalance() {
        return westernUnionBalance;
    }

    /**
     * <p>
     * Setter of westernUnionBalance field.
     * </p>
     * 
     * @param westernUnionBalance
     *            the westernUnionBalance to set
     */
    public void setWesternUnionBalance(double westernUnionBalance) {
        this.westernUnionBalance = westernUnionBalance;
    }

}
