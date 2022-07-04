/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application;

import java.util.List;

/**
 * <p>
 * This class represents Review Application Role. e.g. "Primary Reviewer", "Secondary Reviewer".
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is immutable and thread safe.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public class ReviewApplicationRole extends BaseLookupEntity {
    /**
     * The serial version ID.
     */
    private static final long serialVersionUID = 6723418751038474752L;

    /**
     * Represents the number of available positions for this Review Application Role. Can be any value. It will be
     * initialized in constructor and never change afterwards and can be retrieved via getter.
     */
    private final long positions;

    /**
     * Represents the list of Online Review Application resource roles for this Review Application Role. For instance,
     * Review Application Role "Primary Reviewer" would have several resource roles in Online Review Application, namely
     * "Primrary Screener", "Reviewer", "Aggregator" and "Final Reviewer". Can be any value. It will be initialized in
     * constructor and never change afterwards and can be retrieved via getter.
     */
    private final List<ReviewApplicationResourceRole> resourceRoles;

    /**
     * Creates an instance of ReviewApplicationRole.
     *
     * @param id
     *            the id
     * @param name
     *            the name
     * @param positions
     *            the positions
     * @param resourceRoles
     *            the resource roles
     */
    public ReviewApplicationRole(long id, String name, long positions,
        List<ReviewApplicationResourceRole> resourceRoles) {
        super(id, name);

        this.positions = positions;
        this.resourceRoles = resourceRoles;
    }

    /**
     * Gets the number of available positions for this Review Application Role.
     *
     * @return the number of available positions for this Review Application Role.
     */
    public long getPositions() {
        return positions;
    }

    /**
     * Gets the list of Online Review Application resource roles for this Review Application Role.
     *
     * @return the list of Online Review Application resource roles for this Review Application Role.
     */
    public List<ReviewApplicationResourceRole> getResourceRoles() {
        return resourceRoles;
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
            ", positions:", positions,
            ", resourceRoles:", resourceRoles,
            "}");
    }
}
