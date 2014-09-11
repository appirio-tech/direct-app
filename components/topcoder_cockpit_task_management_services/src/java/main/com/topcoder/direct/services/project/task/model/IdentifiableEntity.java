/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.model;

import java.io.Serializable;

/**
 * <p>
 * IdentifiableEntity is the base class for all entities with an ID.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is not thread safe because it is mutable.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public abstract class IdentifiableEntity implements Serializable {
    /**
     * The serial version ID.
     */
    private static final long serialVersionUID = -3500034164767072252L;

    /**
     * The number 31.
     */
    private static final int HASH_31 = 31;

    /**
     * The number 32.
     */
    private static final int HASH_32 = 32;
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
     * Creates an instance of IdentifiableEntity.
     */
    protected IdentifiableEntity() {
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
     * Determines whether the specified Object is equal to the current Object.
     *
     * @param obj
     *            the Object to compare with the current Object
     *
     * @return <code>true</code> if the specified Object is equal to the current Object; <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        return (this == obj)
                || ((obj != null) && (getClass() == obj.getClass()) && (id == ((IdentifiableEntity) obj).id));
    }

    /**
     * Serves as a hash function for a particular type.
     *
     * @return the hash code for the current Object.
     */
    @Override
    public int hashCode() {
        int hashCode = hashCode(1, getClass().hashCode());

        hashCode = hashCode(hashCode, (int) (id ^ (id >>> HASH_32)));

        return hashCode;
    }

    /**
     * Gets the hash code.
     *
     * @param result
     *            the previous result
     * @param hashCode
     *            the hash code
     *
     * @return the hash code.
     */
    private static int hashCode(int result, int hashCode) {
        return HASH_31 * result + hashCode;
    }
}