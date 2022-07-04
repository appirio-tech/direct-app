/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone.model;

/**
 * <p>
 * This class represents a person that owns a milestone. The name property would here be the user's handle.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is mutable and not thread safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class ResponsiblePerson extends NamedEntity {
    /**
     * Represents the user ID of the person. It is managed with a getter and setter. It may have any value. It is
     * fully mutable.
     */
    private long userId;

    /**
     * Empty constructor.
     */
    public ResponsiblePerson() {
        // Empty
    }

    /**
     * <p>
     * Getter method for userId, simply return the user ID of the person.
     * </p>
     *
     * @return the value of userId
     */
    public long getUserId() {
        return userId;
    }

    /**
     * <p>
     * Setter method for userId, simply set the user ID of the person.
     * </p>
     *
     * @param userId
     *            the value of userId to set
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * The toString method.
     *
     * @return the string of entity
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{id:").append(getId());
        sb.append(", name:").append(getName());
        sb.append(", userId:").append(userId).append("}");
        return sb.toString();
    }
}
