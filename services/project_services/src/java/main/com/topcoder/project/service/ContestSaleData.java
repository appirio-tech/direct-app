/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service;

import java.io.Serializable;

import java.util.Date;


/**
 * <p>
 * It is the DTO class which is used to transfer contest sale data. The information can be null or can be empty,
 * therefore this check is not present in the setters. It's the related to the equivalent ContestSale entity.
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
public class ContestSaleData implements Serializable {
    /** Represents id. */
    private long contestSaleId;

    /** Represents contestId id. */
    private Long contestId;

    /** Represents contestId id. */
    private Long saleStatusId;

    /** Represents paypalOrderId id. */
    private String paypalOrderId;

    /** Represents price id. */
    private Double price;

    /** Represents the create date. */
    private Date createDate;

    /** Represents the sale type. */
    private Long saleTypeId;

    /** Represents the sale reference id. */
    private String saleReferenceId;

    /**
     * Creates a new ContestSaleData object.
     */
    public ContestSaleData() {
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
     * Returns price.
     *
     * @return the price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price to set
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Returns paypalOrderId.
     *
     * @return the paypalOrderId
     */
    public String getPaypalOrderId() {
        return paypalOrderId;
    }

    /**
     * Sets paypalOrderId.
     *
     * @param paypalOrderId the paypalOrderId to set
     */
    public void setPaypalOrderId(String paypalOrderId) {
        this.paypalOrderId = paypalOrderId;
    }

    /**
     * Returns contestId.
     *
     * @return the contestId
     */
    public Long getContestId() {
        return contestId;
    }

    /**
     * Sets contestId.
     *
     * @param contestId the contestId to set
     */
    public void setContestId(Long contestId) {
        this.contestId = contestId;
    }

    /**
     * Returns saleStatusId.
     *
     * @return the saleStatusId
     */
    public Long getSaleStatusId() {
        return saleStatusId;
    }

    /**
     * Sets saleStatusId.
     *
     * @param saleStatusId the saleStatusId to set
     */
    public void setSaleStatusId(Long saleStatusId) {
        this.saleStatusId = saleStatusId;
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
     * Updates the sale type with the specified value.
     *
     * @param saleTypeId the sale order id to set.
     */
    public void setSaleTypeId(Long saleTypeId) {
        this.saleTypeId = saleTypeId;
    }

    /**
     * Returns the saleType.
     *
     * @return sale type
     */
    public Long getSaleTypeId() {
        return saleTypeId;
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
