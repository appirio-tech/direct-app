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
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TopCoder Direct Member Payments Dashboard
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

}
