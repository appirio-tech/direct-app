/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.payments.entities;

import java.util.Date;

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
public class PaymentsByStatus {

    /**
     * Stands for the payment status update time.
     */
    private Date statusUpdateDate;

    /**
     * Stands for the payments in owed or accruing state.
     */
    private double owedOrAccruingPayments;

    /**
     * Stands for the payments in hold state.
     */
    private double onHoldPayments;

    /**
     * Stands for the payments in paid state.
     */
    private double paidPayments;

    /**
     * Stands for the payments in payment entered into payment system state.
     */
    private double paymentsEnteredIntoPaymentSystem;

    /**
     * The default empty ctor.
     */
    public PaymentsByStatus() {
        // do nothing
    }

    /**
     * <p>
     * Getter of statusUpdateDate field.
     * </p>
     * 
     * @return the statusUpdateDate
     */
    public Date getStatusUpdateDate() {
        return statusUpdateDate;
    }

    /**
     * <p>
     * Setter of statusUpdateDate field.
     * </p>
     * 
     * @param statusUpdateDate
     *            the statusUpdateDate to set
     */
    public void setStatusUpdateDate(Date statusUpdateDate) {
        this.statusUpdateDate = statusUpdateDate;
    }

    /**
     * <p>
     * Getter of owedOrAccruingPayments field.
     * </p>
     * 
     * @return the owedOrAccruingPayments
     */
    public double getOwedOrAccruingPayments() {
        return owedOrAccruingPayments;
    }

    /**
     * <p>
     * Setter of owedOrAccruingPayments field.
     * </p>
     * 
     * @param owedOrAccruingPayments
     *            the owedOrAccruingPayments to set
     */
    public void setOwedOrAccruingPayments(double owedOrAccruingPayments) {
        this.owedOrAccruingPayments = owedOrAccruingPayments;
    }

    /**
     * <p>
     * Getter of onHoldPayments field.
     * </p>
     * 
     * @return the onHoldPayments
     */
    public double getOnHoldPayments() {
        return onHoldPayments;
    }

    /**
     * <p>
     * Setter of onHoldPayments field.
     * </p>
     * 
     * @param onHoldPayments
     *            the onHoldPayments to set
     */
    public void setOnHoldPayments(double onHoldPayments) {
        this.onHoldPayments = onHoldPayments;
    }

    /**
     * <p>
     * Getter of paidPayments field.
     * </p>
     * 
     * @return the paidPayments
     */
    public double getPaidPayments() {
        return paidPayments;
    }

    /**
     * <p>
     * Setter of paidPayments field.
     * </p>
     * 
     * @param paidPayments
     *            the paidPayments to set
     */
    public void setPaidPayments(double paidPayments) {
        this.paidPayments = paidPayments;
    }

    /**
     * <p>
     * Getter of paymentsEnteredIntoPaymentSystem field.
     * </p>
     * 
     * @return the paymentsEnteredIntoPaymentSystem
     */
    public double getPaymentsEnteredIntoPaymentSystem() {
        return paymentsEnteredIntoPaymentSystem;
    }

    /**
     * <p>
     * Setter of paymentsEnteredIntoPaymentSystem field.
     * </p>
     * 
     * @param paymentsEnteredIntoPaymentSystem
     *            the paymentsEnteredIntoPaymentSystem to set
     */
    public void setPaymentsEnteredIntoPaymentSystem(double paymentsEnteredIntoPaymentSystem) {
        this.paymentsEnteredIntoPaymentSystem = paymentsEnteredIntoPaymentSystem;
    }

}
