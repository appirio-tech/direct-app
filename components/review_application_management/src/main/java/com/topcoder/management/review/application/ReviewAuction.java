/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * This class represents Review Auction, which is a review application campaign for a project. Each project will
 * normally have two review auctions: one for the specification review and one for the contest review, but some projects
 * may have less than two auctions (e.g. Component Developments usually don't have spec review).
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public class ReviewAuction implements Serializable {
    /**
     * The serial version ID.
     */
    private static final long serialVersionUID = -1324092108414566729L;

    /**
     * <p>
     * Represents the id.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private long id;

    /**
     * Represents the ID of the project which the Review Auction is for. Can be any value. It will be initialized to 0
     * and can be set/retrieved via setter/getter.
     */
    private long projectId;

    /**
     * Represents the auction type of this Review Auction. Can be any value. It will be initialized to null and can be
     * set/retrieved via setter/getter.
     */
    private ReviewAuctionType auctionType;

    /**
     * Represents the flag indicating whether this auction is open. This means that the project is in Active status,
     * certain phases are currently open and there are still open review positions available. The specific conditions
     * depend on the auction category. Can be true/false. It will be initialized to false and can be set/retrieved via
     * setter/getter.
     */
    private boolean open;

    /**
     * Represents a list that defines how many positions for each application role are currently open. The list will
     * have exactly as many elements as ReviewAuction#auctionType.applicationRoles list. i-th element of this list will
     * correspond to i-th element of ReviewAuction#auctionType.applicationRoles list and will be less or equal than
     * ReviewAuction#auctionType.applicationRoles[i].positions. Can be any value. It will be initialized to null and can
     * be set/retrieved via setter/getter.
     */
    private List<Long> openPositions;

    /**
     * Represents the date when the system will assign applicants to open review positions. If the date is in the past
     * it means that the assignment will happen immediately when someone applies for review. Can be any value. It will
     * be initialized to null and can be set/retrieved via setter/getter.
     */
    private Date assignmentDate;

    /**
     * Creates an instance of ReviewAuction.
     */
    public ReviewAuction() {
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
     * Gets the ID of the project which the Review Auction is for.
     *
     * @return the ID of the project which the Review Auction is for.
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Sets the ID of the project which the Review Auction is for.
     *
     * @param projectId
     *            the ID of the project which the Review Auction is for.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the auction type of this Review Auction.
     *
     * @return the auction type of this Review Auction.
     */
    public ReviewAuctionType getAuctionType() {
        return auctionType;
    }

    /**
     * Sets the auction type of this Review Auction.
     *
     * @param auctionType
     *            the auction type of this Review Auction.
     */
    public void setAuctionType(ReviewAuctionType auctionType) {
        this.auctionType = auctionType;
    }

    /**
     * Gets the flag indicating whether this auction is open.
     *
     * @return the flag indicating whether this auction is open.
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * Sets the flag indicating whether this auction is open.
     *
     * @param open
     *            the flag indicating whether this auction is open.
     */
    public void setOpen(boolean open) {
        this.open = open;
    }

    /**
     * Gets a list that defines how many positions for each application role are currently open.
     *
     * @return a list that defines how many positions for each application role are currently open.
     */
    public List<Long> getOpenPositions() {
        return openPositions;
    }

    /**
     * Sets a list that defines how many positions for each application role are currently open.
     *
     * @param openPositions
     *            a list that defines how many positions for each application role are currently open.
     */
    public void setOpenPositions(List<Long> openPositions) {
        this.openPositions = openPositions;
    }

    /**
     * Gets the date when the system will assign applicants to open review positions.
     *
     * @return the date when the system will assign applicants to open review positions.
     */
    public Date getAssignmentDate() {
        return assignmentDate;
    }

    /**
     * Sets the date when the system will assign applicants to open review positions.
     *
     * @param assignmentDate
     *            the date when the system will assign applicants to open review positions.
     */
    public void setAssignmentDate(Date assignmentDate) {
        this.assignmentDate = assignmentDate;
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
            ", projectId:", projectId,
            ", auctionType:", auctionType,
            ", open:", open,
            ", openPositions:", openPositions,
            ", assignmentDate:", assignmentDate,
            "}");
    }
}
