/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.payments.DTO;

import java.util.Date;

/**
 * <p>This class is used to hold payment status data.</p>
 *
 * <p>
 * <strong>Thread safety</strong>: This class is not thread-safe. However, it will be
 * used in a thread safe manner.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version  1.0 (System Assembly - TopCoder Direct Member Payments Dashboard v1.0)
 */
public class PaymentStatusDTO {
    /**
     * Current date string.
     */
    private static final String CURRENT_DATE_STR = "Current";

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
     * The start date.
     */
    private Date startDate;

    /**
     * The end date.
     */
    private Date endDate;

    /**
     * The start date string.
     */
    private String startDateStr;

    /**
     * The end date string.
     */
    private String endDateStr;

    /**
     * Default constructor.
     */
    public PaymentStatusDTO() {
    }

    /**
     * Get owed or accruing payments.
     *
     * @return the owed or accruing payments.
     */
    public double getOwedOrAccruingPayments() {
        return owedOrAccruingPayments;
    }

    /**
     * Add owed or accruing payments.
     *
     * @param owedOrAccruingPaymentsDiff the owed or accruing payments diff
     */
    public void addOwedOrAccruingPayments(double owedOrAccruingPaymentsDiff) {
        this.owedOrAccruingPayments += owedOrAccruingPaymentsDiff;
    }

    /**
     * Get on hold payment.
     *
     * @return the payment
     */
    public double getOnHoldPayments() {
        return onHoldPayments;
    }

    /**
     * Add the on hold payment.
     *
     * @param onHoldPaymentsDiff the on hold payment diff
     */
    public void addOnHoldPayments(double onHoldPaymentsDiff) {
        this.onHoldPayments += onHoldPaymentsDiff;
    }

    /**
     * Get the paid payments.
     *
     * @return the paid payments
     */
    public double getPaidPayments() {
        return paidPayments;
    }

    /**
     * Add the paid payments.
     *
     * @param paidPaymentsDiff the the paid payments diff
     */
    public void addPaidPayments(double paidPaymentsDiff) {
        this.paidPayments += paidPaymentsDiff;
    }

    /**
     * Get the payments entered into payment system.
     *
     * @return the payments entered into payment system
     */
    public double getPaymentsEnteredIntoPaymentSystem() {
        return paymentsEnteredIntoPaymentSystem;
    }

    /**
     * Add the payments entered into payment system.
     *
     * @param paymentsEnteredIntoPaymentSystemDiff the payments entered into payment system
     */
    public void addPaymentsEnteredIntoPaymentSystem(double paymentsEnteredIntoPaymentSystemDiff) {
        this.paymentsEnteredIntoPaymentSystem += paymentsEnteredIntoPaymentSystemDiff;
    }

    /**
     * Get start date string.
     *
     * @return the string
     */
    public String getStartDateStr() {
        return startDateStr;
    }

    /**
     * Set the start date string with diff days.
     *
     * @param diff the diff days
     */
    public void setStartDateStr(int diff) {
        startDateStr = diff > 0 ? String.valueOf(diff) : CURRENT_DATE_STR;
    }

    /**
     * Get the end date string.
     *
     * @return the string
     */
    public String getEndDateStr() {
        return endDateStr;
    }

    /**
     * Set the end date string with diff days.
     *
     * @param diff the diff days
     */
    public void setEndDateStr(int diff) {
        endDateStr = diff > 0 ? String.valueOf(diff) : CURRENT_DATE_STR;
    }

    /**
     * Get the total payment.
     *
     * @return the total payment.
     */
    public double getTotalPayment() {
        return owedOrAccruingPayments + onHoldPayments + paidPayments + paymentsEnteredIntoPaymentSystem;
    }

    /**
     * Get the start date.
     *
     * @return the start date.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Set the start date.
     *
     * @param startDate the start date.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Get the end date.
     *
     * @return the end date.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Set the end date.
     *
     * @param endDate the end date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
