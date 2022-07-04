/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application;

import java.io.Serializable;

/**
 * <p>
 * This is a base class for all model classes which have id and name.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is immutable and thread safe.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public abstract class BaseLookupEntity implements Serializable {
    /**
     * The serial version ID.
     */
    private static final long serialVersionUID = -8416360779163137052L;

    /**
     * Represents the ID. Can be any value. It will be initialized in constructor and never change afterwards and can be
     * retrieved via getter.
     */
    private final long id;

    /**
     * Represents the name. Can be any value. It will be initialized in constructor and never change afterwards and can
     * be retrieved via getter.
     */
    private final String name;

    /**
     * Creates an instance of BaseLookupEntity.
     *
     * @param id
     *            the id
     * @param name
     *            the name.
     */
    protected BaseLookupEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the ID.
     *
     * @return the ID.
     */
    public long getId() {
        return id;
    }

    /**
     * Gets the name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
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
            ", name:", name,
            "}");
    }
}
