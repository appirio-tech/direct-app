/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application;

import java.util.List;

/**
 * <p>
 * This class represents Review Auction Type. e.g. "Regular Contest Review", "Component Development Review".
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is immutable and thread safe.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public class ReviewAuctionType extends BaseLookupEntity {
    /**
     * The serial version ID.
     */
    private static final long serialVersionUID = -4247381988356581992L;

    /**
     * Represents the Auction Category for this Review Auction Type. Can be any value. It will be initialized in
     * constructor and never change afterwards and can be retrieved via getter.
     */
    private final ReviewAuctionCategory auctionCategory;

    /**
     * Represents the list of application roles for this Review Auction Type. Can be any value. It will be initialized
     * in constructor and never change afterwards and can be retrieved via getter.
     */
    private final List<ReviewApplicationRole> applicationRoles;

    /**
     * Creates an instance of ReviewAuctionType.
     *
     * @param id
     *            the id
     * @param name
     *            the name
     * @param auctionCategory
     *            the auction category
     * @param applicationRoles
     *            the application roles
     */
    public ReviewAuctionType(long id, String name, ReviewAuctionCategory auctionCategory,
        List<ReviewApplicationRole> applicationRoles) {
        super(id, name);

        this.auctionCategory = auctionCategory;
        this.applicationRoles = applicationRoles;
    }

    /**
     * Gets the Auction Category for this Review Auction Type.
     *
     * @return the Auction Category for this Review Auction Type.
     */
    public ReviewAuctionCategory getAuctionCategory() {
        return auctionCategory;
    }

    /**
     * Gets the list of application roles for this Review Auction Type.
     *
     * @return the list of application roles for this Review Auction Type.
     */
    public List<ReviewApplicationRole> getApplicationRoles() {
        return applicationRoles;
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
            "id:", getId(),
            ", name:", getName(),
            ", auctionCategory:", auctionCategory,
            ", applicationRoles:", applicationRoles,
            "}");
    }
}
