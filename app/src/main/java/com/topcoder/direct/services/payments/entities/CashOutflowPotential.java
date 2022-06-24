/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.payments.entities;

import java.util.Date;

/**
 * <p>
 * This entity is a container for potential member payments. It contains the
 * payments and due date of payments.
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
public class CashOutflowPotential {

    /**
     * Represents the amount.
     */
    private double payments;

    /**
     * Denotes the due date of payments.
     */
    private Date dueDate;

    /**
     * The default empty ctor.
     */
    public CashOutflowPotential() {
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
     * Getter of dueDate field.
     * </p>
     * 
     * @return the dueDate
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * <p>
     * Setter of dueDate field.
     * </p>
     * 
     * @param dueDate
     *            the dueDate to set
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

}
