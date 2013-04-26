/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.payments.entities;

import java.util.Date;

/**
 * <p>
 * This entity is a container for Top Member Payment information.
 * </p>
 * 
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable and not thread safe.
 * </p>
 *
 * <p>
 * Version 1.1 (System Assembly - TopCoder Direct Member Payments Dashboard v1.0)
 * - add userId, startDate, endDate, memberLink field and corresponding get/set method.
 * </p>
 *
 * @author TCSASSEMBLER, tangzx
 * @version 1.1 (Module Assembly - TopCoder Direct Member Payments Dashboard
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
     * The user id.
     *
     * @since 1.1
     */
    private long userId;

    /**
     * The start date.
     *
     * @since 1.1
     */
    private Date startDate;

    /**
     * The end date.
     *
     * @since 1.1
     */
    private Date endDate;

    /**
     * The member link.
     *
     * @since 1.1
     */
    private String memberLink;

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

    /**
     * Get the user id.
     *
     * @return the user id.
     * @since 1.1
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Set the user id.
     *
     * @param userId the user id.
     * @since 1.1
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Get the start date.
     *
     * @return the start date.
     * @since 1.1
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Set the start date.
     *
     * @param startDate the start date.
     * @since 1.1
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * The the end date.
     *
     * @return the end date.
     * @since 1.1
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Set the end date.
     *
     * @param endDate the end date.
     * @since 1.1
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Get the member link.
     *
     * @return the member link.
     * @since 1.1
     */
    public String getMemberLink() {
        return memberLink;
    }

    /**
     * Set the member link.
     *
     * @param memberLink the member link.
     * @since 1.1
     */
    public void setMemberLink(String memberLink) {
        this.memberLink = memberLink;
    }
}
