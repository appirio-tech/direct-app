/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone.model;

/**
 * <p>
 * This is the base class for all entities that have an identification number.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is mutable and not thread safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public abstract class IdentifiableEntity {
    /**
     * Represents the primary identifier of the entity. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private long id;

    /**
     * Empty constructor.
     */
    protected IdentifiableEntity() {
        // Empty
    }

    /**
     * <p>
     * Getter method for id, simply return the primary identifier of the entity.
     * </p>
     *
     * @return the value of id
     */
    public long getId() {
        return id;
    }

    /**
     * <p>
     * Setter method for id, simply set the primary identifier of the entity.
     * </p>
     *
     * @param id
     *            the value of id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Determines whether the specified Object is equal to the current Object.
     *
     * @param obj
     *            The Object to compare with the current Object
     * @return true if the specified Object is equal to the current Object; otherwise, false.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj != null && getClass().equals(obj.getClass()) && this.id == ((IdentifiableEntity) obj).getId()) {
            return true;
        }
        return false;
    }

    /**
     * Serves as a hash function for a particular type.
     *
     * @return hash code for the current Object
     */
    @Override
    public int hashCode() {
        return (int) this.id;
    }
}
