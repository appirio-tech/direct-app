/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.payments.entities;

import java.util.List;

/**
 * <p>
 * This entity is a container for PaymentTrend information.
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
public class PaymentTrendResult {

    /**
     * Stands for the payment status id.
     */
    private int paymentStatusId;

    /**
     * Stands for the payment trend items.
     */
    private List<PaymentTrend> paymentTrends;

    /**
     * The default empty ctor.
     */
    public PaymentTrendResult() {
        // do nothing
    }

    /**
     * <p>
     * Getter of paymentStatusId field.
     * </p>
     * 
     * @return the paymentStatusId
     */
    public int getPaymentStatusId() {
        return paymentStatusId;
    }

    /**
     * <p>
     * Setter of paymentStatusId field.
     * </p>
     * 
     * @param paymentStatusId
     *            the paymentStatusId to set
     */
    public void setPaymentStatusId(int paymentStatusId) {
        this.paymentStatusId = paymentStatusId;
    }

    /**
     * <p>
     * Getter of paymentTrends field.
     * </p>
     * 
     * @return the paymentTrends
     */
    public List<PaymentTrend> getPaymentTrends() {
        return paymentTrends;
    }

    /**
     * <p>
     * Setter of paymentTrends field.
     * </p>
     * 
     * @param paymentTrends
     *            the paymentTrends to set
     */
    public void setPaymentTrends(List<PaymentTrend> paymentTrends) {
        this.paymentTrends = paymentTrends;
    }

}
