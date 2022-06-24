/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application;

import java.io.Serializable;

/**
 * <p>
 * This class represents Online Review Application Resource Role. e.g. "Primary Screener", "Reviewer", etc.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is immutable and thread safe.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public class ReviewApplicationResourceRole implements Serializable {
    /**
     * The serial version ID.
     */
    private static final long serialVersionUID = 7643140645098559883L;

    /**
     * Represents the resource role ID. Can be any value. It will be initialized in constructor and never change
     * afterwards and can be retrieved via getter.
     */
    private final long resourceRoleId;

    /**
     * Represents the flag indicating whether this resource role is a unique role (e.g. a Primary Screener resource role
     * is unique, but a Reviewer resource role is not unique). Can be true/false. It will be initialized in constructor
     * and never change afterwards and can be retrieved via getter.
     */
    private final boolean uniqueRole;

    /**
     * Creates an instance of ReviewApplicationResourceRole.
     *
     * @param resourceRoleId
     *            the resource role ID.
     * @param uniqueRole
     *            a boolean value indicating whether the role is unique.
     */
    public ReviewApplicationResourceRole(long resourceRoleId, boolean uniqueRole) {
        this.resourceRoleId = resourceRoleId;
        this.uniqueRole = uniqueRole;
    }

    /**
     * Gets the resource role ID.
     *
     * @return the resource role ID.
     */
    public long getResourceRoleId() {
        return resourceRoleId;
    }

    /**
     * Gets the flag indicating whether this resource role is a unique role (e.g. a Primary Screener resource role is
     * unique, but a Reviewer resource role is not unique).
     *
     * @return the flag indicating whether this resource role is a unique role (e.g. a Primary Screener resource role is
     *         unique, but a Reviewer resource role is not unique).
     */
    public boolean isUniqueRole() {
        return uniqueRole;
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
            "resourceRoleId:", resourceRoleId,
            ", uniqueRole:", uniqueRole,
            "}");
    }
}
