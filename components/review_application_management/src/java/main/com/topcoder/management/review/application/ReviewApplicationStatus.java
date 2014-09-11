/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application;

/**
 * <p>
 * This class represents Review Application Status. e.g. "Pending", "Cancelled", "Approved", "Rejected".
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is immutable and thread safe.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public class ReviewApplicationStatus extends BaseLookupEntity {
    /**
     * The serial version ID.
     */
    private static final long serialVersionUID = -892879080098049665L;

    /**
     * Creates an instance of ReviewApplicationStatus.
     *
     * @param id
     *            the id
     * @param name
     *            the name.
     */
    public ReviewApplicationStatus(long id, String name) {
        super(id, name);
    }
}
