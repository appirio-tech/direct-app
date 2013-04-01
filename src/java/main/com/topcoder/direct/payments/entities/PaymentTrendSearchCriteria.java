/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.payments.entities;

import java.util.Date;

/**
 * <p>
 * This entity is a container for PaymentTrendSearchCriteria information.
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
public class PaymentTrendSearchCriteria {

    /**
     * Stands for the payment status id.
     */
    private int paymentStatusId;

    /**
     * Stands for the start date.
     */
    private Date startDate;

    /**
     * Stands for the end date.
     */
    private Date endDate;

    /**
     * Stands for the sorr order.
     */
    private String sortOrder;

    /**
     * Stands for the sorting column.
     */
    private String sortingColumn;

    /**
     * The default empty ctor.
     */
    public PaymentTrendSearchCriteria() {
        // do nothing
    }

    /**
     * <p>
     * Getter of paymentStatusId field.
     * </p>
     * 
     * @return the paymentStatusId
     */
    public int getPaymentStatusId() {
        return paymentStatusId;
    }

    /**
     * <p>
     * Setter of paymentStatusId field.
     * </p>
     * 
     * @param paymentStatusId
     *            the paymentStatusId to set
     */
    public void setPaymentStatusId(int paymentStatusId) {
        this.paymentStatusId = paymentStatusId;
    }

    /**
     * <p>
     * Getter of startDate field.
     * </p>
     * 
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * <p>
     * Setter of startDate field.
     * </p>
     * 
     * @param startDate
     *            the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * <p>
     * Getter of endDate field.
     * </p>
     * 
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * <p>
     * Setter of endDate field.
     * </p>
     * 
     * @param endDate
     *            the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * <p>
     * Getter of sortOrder field.
     * </p>
     * 
     * @return the sortOrder
     */
    public String getSortOrder() {
        return sortOrder;
    }

    /**
     * <p>
     * Setter of sortOrder field.
     * </p>
     * 
     * @param sortOrder
     *            the sortOrder to set
     */
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * <p>
     * Getter of sortingColumn field.
     * </p>
     * 
     * @return the sortingColumn
     */
    public String getSortingColumn() {
        return sortingColumn;
    }

    /**
     * <p>
     * Setter of sortingColumn field.
     * </p>
     * 
     * @param sortingColumn
     *            the sortingColumn to set
     */
    public void setSortingColumn(String sortingColumn) {
        this.sortingColumn = sortingColumn;
    }

}
