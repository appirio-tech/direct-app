/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.payments.entities;

/**
 * <p>
 * This class stands for the payment status.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TopCoder Direct Member Payments Dashboard
 *          Backend Assembly)
 * @since 1.0
 */
public enum PaymentStatus {

    /**
     * Stands for the paid status.
     */
    PAID(53),

    /**
     * Stands for the on hole status.
     */
    ON_HOLD(55),

    /**
     * Stands for the owed status.
     */
    OWED(56),

    /**
     * Stands for the entered into payment system status.
     */
    ENTERED_INTO_PAYMENT_SYSTEM(70),

    /**
     * Stands for the accruing status.
     */
    ACCRUING(71);

    /**
     * Stands for the payment status id.
     */
    private int paymentStatusId;

    /**
     * <p>
     * Default constructor.
     * </p>
     * 
     * @param statusId
     *            the id for status
     */
    private PaymentStatus(int statusId) {
        this.paymentStatusId = statusId;
    }

    /**
     * Get payment status id.
     * 
     * @return the payment status id
     */
    public int getPaymentStatusId() {
        return this.paymentStatusId;
    }

}
