/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application;

/**
 * <p>
 * This class represents Review Auction Category. e.g. "Specification Review", "Contest Review".
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is immutable and thread safe.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public class ReviewAuctionCategory extends BaseLookupEntity {
    /**
     * The serial version ID.
     */
    private static final long serialVersionUID = -8168081783550945024L;

    /**
     * Creates an instance of ReviewAuctionCategory.
     *
     * @param id
     *            the id
     * @param name
     *            the name.
     */
    public ReviewAuctionCategory(long id, String name) {
        super(id, name);
    }
}
