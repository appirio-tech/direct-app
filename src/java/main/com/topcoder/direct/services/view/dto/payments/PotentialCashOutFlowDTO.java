/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.view.dto.payments;

/**
 * <p>This class is used to hold potential cash out flow data.</p>
 *
 * <p>
 * <strong>Thread safety</strong>: This class is not thread-safe. However, it will be
 * used in a thread safe manner.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version  1.0 (System Assembly - TopCoder Direct Member Payments Dashboard v1.0)
 */
public class PotentialCashOutFlowDTO {
    /**
     * The payment method name.
     */
    private String paymentMethodName;

    /**
     * The cash amounts.
     */
    private double[] cashAmounts;

    /**
     * Constructor.
     *
     * @param paymentMethodName the payment method name.
     */
    public PotentialCashOutFlowDTO(String paymentMethodName) {
        setPaymentMethodName(paymentMethodName);
    }

    /**
     * Get the payment method name.
     * @return payment method name
     */
    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    /**
     * Set the payment method name.
     *
     * @param paymentMethodName the payment method name.
     */
    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    /**
     * Get the cash amounts.
     *
     * @return the amounts
     */
    public double[] getCashAmounts() {
        return cashAmounts;
    }

    /**
     * Set the cash amounts.
     *
     * @param cashAmounts the amounts.
     */
    public void setCashAmounts(double[] cashAmounts) {
        this.cashAmounts = cashAmounts;
    }
}
