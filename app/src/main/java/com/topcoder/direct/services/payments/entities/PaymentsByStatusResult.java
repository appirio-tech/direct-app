/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.payments.entities;

import java.util.List;

/**
 * <p>
 * This entity is a container for payment status information.
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
public class PaymentsByStatusResult {

    /**
     * Instance of {@link DateRange} holds the payment status start and end
     * time.
     */
    private DateRange dateRange;

    /**
     * Stands for the list of {@link PaymentsByStatus} instances.
     */
    private List<PaymentsByStatus> paymentsByStatus;

    /**
     * The default empty ctor.
     */
    public PaymentsByStatusResult() {
        // do nothing
    }

    /**
     * <p>
     * Getter of dateRange field.
     * </p>
     * 
     * @return the dateRange
     */
    public DateRange getDateRange() {
        return dateRange;
    }

    /**
     * <p>
     * Setter of dateRange field.
     * </p>
     * 
     * @param dateRange
     *            the dateRange to set
     */
    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    /**
     * <p>
     * Getter of paymentsByStatus field.
     * </p>
     * 
     * @return the paymentsByStatus
     */
    public List<PaymentsByStatus> getPaymentsByStatus() {
        return paymentsByStatus;
    }

    /**
     * <p>
     * Setter of paymentsByStatus field.
     * </p>
     * 
     * @param paymentsByStatus
     *            the paymentsByStatus to set
     */
    public void setPaymentsByStatus(List<PaymentsByStatus> paymentsByStatus) {
        this.paymentsByStatus = paymentsByStatus;
    }

}
