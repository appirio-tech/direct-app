/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.Serializable;

import java.util.Date;


/**
 * <p>
 * Represents the entity class for db table <i>contest_sale</i>.
 * </p>
 * 
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author PE
 * @version 1.0
 *
 * @since Module Contest Service Software Contest Sales Assembly
 */
@SuppressWarnings("serial")
public class ContestSale implements Serializable {
    /** Represents the entity id. */
    private long contestSaleId;

    /** Represents the contest id. */
    private long contestId;

    /** Represents the sale status. */
    private SaleStatus status;

    /** Represents the price. */
    private Double price;

    /** Represents the paypal order id. */
    @Deprecated
    private String payPalOrderId;

    /** Represents the create date. */
    private Date createDate;

    /** Represents the sale type. */
    private SaleType saleType;

    /** Represents the sale reference id. */
    private String saleReferenceId;

    /**
     * Default constructor.
     */
    public ContestSale() {
        // empty
    }

    /**
     * Gets the contestSaleId.
     *
     * @return the contestSaleId.
     */
    public long getContestSaleId() {
        return contestSaleId;
    }

    /**
     * Sets the contestSaleId.
     *
     * @param contestSaleId the contestSaleId to set.
     */
    public void setContestSaleId(long contestSaleId) {
        this.contestSaleId = contestSaleId;
    }

    /**
     * Returns the contest id.
     *
     * @return the contest id.
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Updates the contest id with the specified value.
     *
     * @param contestId the contest id to set.
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * Returns the status.
     *
     * @return the status.
     */
    public SaleStatus getStatus() {
        return status;
    }

    /**
     * Updates the status with the specified value.
     *
     * @param status the status to set.
     */
    public void setStatus(SaleStatus status) {
        this.status = status;
    }

    /**
     * Returns the price.
     *
     * @return the price.
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Updates the PayPal order id with the specified value.
     *
     * @param payPalOrderId the PayPal order id to set.
     */
    public void setPayPalOrderId(String payPalOrderId) {
        this.payPalOrderId = payPalOrderId;
    }

    /**
     * Returns the PayPal order id.
     *
     * @return the PayPalOrderId.
     */
    public String getPayPalOrderId() {
        return payPalOrderId;
    }

    /**
     * Updates the price with the specified value.
     *
     * @param price the price to set.
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Returns create date.
     *
     * @return the create date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Set create date.
     *
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Compares this object with the passed object for equality. Only the id will be compared.
     *
     * @param obj the {@code Object} to compare to this one
     *
     * @return true if this object is equal to the other, {@code false} if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ContestSale) {
            return (getContestSaleId() == ((ContestSale) obj).getContestSaleId());
        }

        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's {@link #equals(Object)}
     * method.
     *
     * @return a hash code for this {@code ContestSale}
     */
    @Override
    public int hashCode() {
        return new Long(contestSaleId).hashCode();
    }

    /**
     * Updates the sale type with the specified value.
     *
     * @param saleType the sale order id to set.
     */
    public void setSaleType(SaleType saleType) {
        this.saleType = saleType;
    }

    /**
     * Returns the saleType.
     *
     * @return sale type
     */
    public SaleType getSaleType() {
        return saleType;
    }

    /**
     * Updates the sale reference id with the specified value.
     *
     * @param saleReferenceId the sale order id to set.
     */
    public void setSaleReferenceId(String saleReferenceId) {
        this.saleReferenceId = saleReferenceId;
    }

    /**
     * Returns the saleOrderId.
     *
     * @return sale reference id
     */
    public String getSaleReferenceId() {
        return saleReferenceId;
    }
}
