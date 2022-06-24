/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.payments.entities;

import java.util.List;

/**
 * <p>
 * This entity is a container for member payment history result. It contains the
 * following fields:
 * <ol>
 * <li>{@link #paymentMethod} payment method. Instance of {@link PaymentMethod}
 * enum.</li>
 * <li>{@link #items} list of PaymentHistory entities.</li>
 * </ol>
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
public class PaymentHistoryResult {

    /**
     * Represents payment method. Instance of {@link PaymentMethod} enum.
     */
    private PaymentMethod paymentMethod;

    /**
     * Represents the payment history items.
     */
    private List<PaymentHistory> items;

    /**
     * The default empty ctor.
     */
    public PaymentHistoryResult() {
        // do nothing
    }

    /**
     * <p>
     * Getter of paymentMethod field.
     * </p>
     * 
     * @return the paymentMethod
     */
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * <p>
     * Setter of paymentMethod field.
     * </p>
     * 
     * @param paymentMethod
     *            the paymentMethod to set
     */
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * <p>
     * Getter of items field.
     * </p>
     * 
     * @return the items
     */
    public List<PaymentHistory> getItems() {
        return items;
    }

    /**
     * <p>
     * Setter of items field.
     * </p>
     * 
     * @param items
     *            the items to set
     */
    public void setItems(List<PaymentHistory> items) {
        this.items = items;
    }

}
