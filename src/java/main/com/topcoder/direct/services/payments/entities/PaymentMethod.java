/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.payments.entities;

/**
 * <p>
 * This entity is a container for payment method. It’s an enum entity.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TopCoder Direct Member Payments Dashboard
 *          Backend Assembly)
 * @since 1.0
 */
public enum PaymentMethod {

    /**
     * Stands for the PAYPAL payment method.
     */
    PAYPAL(2),

    /**
     * Stands for the PAYONEER payment method.
     */
    PAYONEER(5),

    /**
     * Stands for the WESTERN UNION payment method.
     */
    WESTERN_UNION(6),

    /**
     * Stands for the NOT SET payment method.
     */
    NOT_SET(1);

    /**
     * Stands for the payment method id.
     */
    private int paymentMethodId;

    /**
     * <p>
     * Default constructor.
     * </p>
     * 
     * @param value
     *            the payment id
     */
    private PaymentMethod(int value) {
        this.paymentMethodId = value;
    }

    /**
     * Get payment method id.
     * 
     * @return payment method id
     */
    public int getPaymentMethodId() {
        return this.paymentMethodId;
    }
}