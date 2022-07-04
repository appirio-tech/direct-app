/*
 * Copyright (C) 2012-2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * This class represents Review Application, which is a member's application for a review role within an auction.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0.1
 */
public class ReviewApplication implements Serializable {
    /**
     * The serial version ID.
     */
    private static final long serialVersionUID = -8392054630662208195L;

    /**
     * Represents the ID. Can be any value. It will be initialized in constructor and never change afterwards and can be
     * set/retrieved via setter/getter.
     */
    private long id;

    /**
     * Represents the user ID of the member who has submitted the review application. Can be any value. It will be
     * initialized to 0 and can be set/retrieved via setter/getter.
     */
    private long userId;

    /**
     * Represents the ID of Review Auction. Can be any value. It will be initialized to 0 and can be set/retrieved via
     * setter/getter.
     */
    private long auctionId;

    /**
     * Represents the ID of the Review Application Role, which the review application has applied for. Can be any value.
     * It will be initialized to 0 and can be set/retrieved via setter/getter.
     */
    private long applicationRoleId;

    /**
     * Represents the status of the Review Application. Can be any value. It will be initialized to null and can be
     * set/retrieved via setter/getter.
     */
    private ReviewApplicationStatus status;

    /**
     * Represents the creation date of the Review Application. Can be any value. It will be initialized to null and can
     * be set/retrieved via setter/getter.
     */
    private Date createDate;
    
    /**
     * Represents the modification date of the Review Application. Can be any value. It will be initialized to null and can
     * be set/retrieved via setter/getter.
     */
    private Date modifyDate;

    /**
     * Creates an instance of ReviewApplication.
     */
    public ReviewApplication() {
        // Empty
    }

    /**
     * Gets the id.
     *
     * @return the id.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the id.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the user ID of the member who has submitted the review application.
     *
     * @return the user ID of the member who has submitted the review application.
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets the user ID of the member who has submitted the review application.
     *
     * @param userId
     *            the user ID of the member who has submitted the review application.
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Gets the ID of Review Auction.
     *
     * @return the ID of Review Auction.
     */
    public long getAuctionId() {
        return auctionId;
    }

    /**
     * Sets the ID of Review Auction.
     *
     * @param auctionId
     *            the ID of Review Auction.
     */
    public void setAuctionId(long auctionId) {
        this.auctionId = auctionId;
    }

    /**
     * Gets the ID of the Review Application Role, which the review application has applied for.
     *
     * @return the ID of the Review Application Role, which the review application has applied for.
     */
    public long getApplicationRoleId() {
        return applicationRoleId;
    }

    /**
     * Sets the ID of the Review Application Role, which the review application has applied for.
     *
     * @param applicationRoleId
     *            the ID of the Review Application Role, which the review application has applied for.
     */
    public void setApplicationRoleId(long applicationRoleId) {
        this.applicationRoleId = applicationRoleId;
    }

    /**
     * Gets the status of the Review Application.
     *
     * @return the status of the Review Application.
     */
    public ReviewApplicationStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the Review Application.
     *
     * @param status
     *            the status of the Review Application.
     */
    public void setStatus(ReviewApplicationStatus status) {
        this.status = status;
    }

    /**
     * Gets the creation date of the Review Application.
     *
     * @return the creation date of the Review Application.
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Sets the creation date of the Review Application.
     *
     * @param createDate
     *            the creation date of the Review Application.
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    /**
     * Gets the modification date of the Review Application.
     *
     * @return the modification date of the Review Application.
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * Sets the modification date of the Review Application.
     *
     * @param modifyDate
     *            the modification date of the Review Application.
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * <p>
     * Returns a <code>String</code> representing this object.
     * </p>
     *
     * @return a string representation of this object.
     */
    @Override
    public String toString() {
        return Helper.concat(this.getClass().getName(), "{",
            "id:", id,
            ", userId:", userId,
            ", auctionId:", auctionId,
            ", applicationRoleId:", applicationRoleId,
            ", status:", status,
            ", createDate:", createDate,
            "}");
    }
}
