/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.payment;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This is base class for capturing the payment data.
 *
 * @author shailendra_80
 */
@XmlSeeAlso({CreditCardPaymentData.class, TCPurhcaseOrderPaymentData.class})
public abstract class PaymentData implements Serializable {
    /**
     * Default serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Type of payment (like: Credit Card or Purchase Order).
     */
    protected PaymentType type;

    /**
     * Do nothing default constructor.
     */
    public PaymentData() {
        // do nothing.
    }

    /**
     * <p>
     * Constructor.</p?
     *
     * @param type
     *            a <code>PaymentType</code> the type of payment.
     */
    public PaymentData(PaymentType type) {
        this.type = type;
    }

    /**
     * <p>
     * Gets the type of payment.
     * </p>
     *
     * @return a <code>PaymentType</code> the type of payment
     */
    public PaymentType getType() {
        return type;
    }

    /**
     * <p>
     * Sets the type of payment.
     * </p>
     *
     * @param type
     *            a <code>PaymentType</code> the type of payment
     */
    public void setType(PaymentType type) {
        this.type = type;
    }
}
