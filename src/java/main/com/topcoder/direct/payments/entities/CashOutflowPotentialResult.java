/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.payments.entities;

import java.util.List;

/**
 * <p>
 * This entity is a container for potential member payments. It contains the
 * list of {@link CashOutflowPotential} and payment method.
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
public class CashOutflowPotentialResult {

    /**
     * Represents the payment method. Instance of {@link PaymentMethod} enum.
     */
    private PaymentMethod paymentMethod;

    /**
     * List of {@link CashOutflowPotential} instances.
     */
    private List<CashOutflowPotential> items;

    /**
     * The default empty ctor.
     */
    public CashOutflowPotentialResult() {
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
    public List<CashOutflowPotential> getItems() {
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
    public void setItems(List<CashOutflowPotential> items) {
        this.items = items;
    }

}
