/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.payments.entities;

import java.util.Date;

/**
 * <p>
 * This entity is a container for Payment Trend information.
 * </p>
 * 
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable and not thread safe.
 * </p>
 *
 * <p>
 * Version 1.1 (System Assembly - TopCoder Direct Member Payments Dashboard v1.0)
 * - add paymentMethodId field and corresponding get/set method.
 * </p>
 *
 * @author TCSASSEMBLER, tangzx
 * @version 1.1 (Module Assembly - TopCoder Direct Member Payments Dashboard
 *          Backend Assembly)
 * @since 1.0
 */
public class PaymentTrend {

    /**
     * Stands for the total payments amount.
     */
    private double payments;

    /**
     * Stands for created or paid date.
     */
    private Date createdOrPaidDate;

    /**
     * The payment method id.
     *
     * @since 1.1
     */
    private int paymentMethodId;

    /**
     * The default empty ctor.
     */
    public PaymentTrend() {
        // do nothing
    }

    /**
     * <p>
     * Getter of payments field.
     * </p>
     * 
     * @return the payments
     */
    public double getPayments() {
        return payments;
    }

    /**
     * <p>
     * Setter of payments field.
     * </p>
     * 
     * @param payments
     *            the payments to set
     */
    public void setPayments(double payments) {
        this.payments = payments;
    }

    /**
     * <p>
     * Getter of createdOrPaidDate field.
     * </p>
     * 
     * @return the createdOrPaidDate
     */
    public Date getCreatedOrPaidDate() {
        return createdOrPaidDate;
    }

    /**
     * <p>
     * Setter of createdOrPaidDate field.
     * </p>
     * 
     * @param createdOrPaidDate
     *            the createdOrPaidDate to set
     */
    public void setCreatedOrPaidDate(Date createdOrPaidDate) {
        this.createdOrPaidDate = createdOrPaidDate;
    }

    /**
     * Set the payment method id.
     *
     * @return the payment method id.
     * @since 1.1
     */
    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    /**
     * Set the payment method id.
     *
     * @param paymentMethodId the payment method id.
     * @since 1.1
     */
    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
}
