/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.payments.entities;

/**
 * <p>
 * This entity is a container for Top Member Payment information.
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
public class TopMemberPayment {

    /**
     * Stands for the topcoder handle.
     */
    private String handle;

    /**
     * Stands for the amount.
     */
    private double amount;

    /**
     * The default empty ctor.
     */
    public TopMemberPayment() {
        // do nothing
    }

    /**
     * <p>
     * Getter of handle field.
     * </p>
     * 
     * @return the handle
     */
    public String getHandle() {
        return handle;
    }

    /**
     * <p>
     * Setter of handle field.
     * </p>
     * 
     * @param handle
     *            the handle to set
     */
    public void setHandle(String handle) {
        this.handle = handle;
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
