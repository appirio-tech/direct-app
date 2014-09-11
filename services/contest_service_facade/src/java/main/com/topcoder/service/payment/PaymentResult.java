/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.payment;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * This class captures the payment result after successful processing of the
 * payment data. The current implementation just captures the payment reference
 * number as the result.
 * </p>
 *
 * @author shailendra_80
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paymentResult", propOrder =  {
    "referenceNumber"}
)
public class PaymentResult implements Serializable {
    /**
     * Default serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * <p>
     * Represents the reference number of the successful payment.
     * </p>
     */
    private String referenceNumber;

    /**
     * Do nothing default constructor.
     */
    public PaymentResult() {
    }

    /**
     * <p>
     * This constructor initializes this instance with the given payment
     * reference number.
     * </p>
     *
     * @param referenceNumber
     *            a <code>String</code> payment reference number.
     */
    public PaymentResult(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     * <p>
     * Gets the payment reference number of this result.
     * </p>
     *
     * @return a <code>String</code> the payment reference number.
     */
    public String getReferenceNumber() {
        return referenceNumber;
    }

    /**
     * <p>
     * Sets the payment reference number of this result.
     * </p>
     *
     * @param referenceNumber
     *            a <code>String</code> payment reference number.
     */
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
}
