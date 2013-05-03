/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.payments.entities;

/**
 * <p>
 * This entity is a container for PullablePayments information.
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
public class PullablePayments {

    /**
     * Stands for the pullable PayPal amounts/payments.
     */
    private double paypalPayments;

    /**
     * Stands for the pullable Payoneer amounts/payments.
     */
    private double payoneerPayments;

    /**
     * Stands for the pullable western union amounts/payments.
     */
    private double westernUnionPayments;

    /**
     * Stands for the pullable not set amounts/payments.
     */
    private double notSetPayments;

    /**
     * The default empty ctor.
     */
    public PullablePayments() {
        // do nothing
    }

    /**
     * <p>
     * Getter of paypalPayments field.
     * </p>
     * 
     * @return the paypalPayments
     */
    public double getPaypalPayments() {
        return paypalPayments;
    }

    /**
     * <p>
     * Setter of paypalPayments field.
     * </p>
     * 
     * @param paypalPayments
     *            the paypalPayments to set
     */
    public void setPaypalPayments(double paypalPayments) {
        this.paypalPayments = paypalPayments;
    }

    /**
     * <p>
     * Getter of payoneerPayments field.
     * </p>
     * 
     * @return the payoneerPayments
     */
    public double getPayoneerPayments() {
        return payoneerPayments;
    }

    /**
     * <p>
     * Setter of payoneerPayments field.
     * </p>
     * 
     * @param payoneerPayments
     *            the payoneerPayments to set
     */
    public void setPayoneerPayments(double payoneerPayments) {
        this.payoneerPayments = payoneerPayments;
    }

    /**
     * <p>
     * Getter of westernUnionPayments field.
     * </p>
     * 
     * @return the westernUnionPayments
     */
    public double getWesternUnionPayments() {
        return westernUnionPayments;
    }

    /**
     * <p>
     * Setter of westernUnionPayments field.
     * </p>
     * 
     * @param westernUnionPayments
     *            the westernUnionPayments to set
     */
    public void setWesternUnionPayments(double westernUnionPayments) {
        this.westernUnionPayments = westernUnionPayments;
    }

    /**
     * Get not set payments.
     *
     * @return the payments
     */
    public double getNotSetPayments() {
        return notSetPayments;
    }

    /**
     * Set not set payments.
     *
     * @param notSetPayments the payments
     */
    public void setNotSetPayments(double notSetPayments) {
        this.notSetPayments = notSetPayments;
    }
}
