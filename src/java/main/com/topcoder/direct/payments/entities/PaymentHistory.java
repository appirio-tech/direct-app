/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.payments.entities;

import java.util.Date;

/**
 * <p>
 * This entity is a container for member payment history. It contains the
 * following fields:
 * <ol>
 * <li>{@link #monthDate} month of the payment history</li>
 * <li>{@link #amount} total amount</li>
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
public class PaymentHistory {

    /**
     * Represents the month of the payment history.
     */
    private Date monthDate;

    /**
     * Represents the amount of this payment item.
     */
    private double amount;

    /**
     * The default empty ctor.
     */
    public PaymentHistory() {
        // do nothing
    }

    /**
     * <p>
     * Getter of monthDate field.
     * </p>
     * 
     * @return the monthDate
     */
    public Date getMonthDate() {
        return monthDate;
    }

    /**
     * <p>
     * Setter of monthDate field.
     * </p>
     * 
     * @param monthDate
     *            the monthDate to set
     */
    public void setMonthDate(Date monthDate) {
        this.monthDate = monthDate;
    }

    /**
     * <p>
     * Getter of amount field.
     * </p>
     * 
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * <p>
     * Setter of amount field.
     * </p>
     * 
     * @param amount
     *            the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

}
