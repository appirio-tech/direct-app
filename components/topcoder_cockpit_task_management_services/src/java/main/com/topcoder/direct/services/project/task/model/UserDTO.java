/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.model;

import java.io.Serializable;

/**
 * <p>
 * This is the DTO for a user, which holds the user ID and user handle.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is not thread safe because it is mutable.
 * </p>
 *
 * <p>
 * Version 1.1 (TC - Cockpit Tasks Management Assembly 3)
 * <ul>
 *     <li>Overrides hashcode and equals so it  can be used in hashmap and hashset</li>
 * </ul>
 * </p>
 *
 * @author albertwang, sparemax, GreatKevin
 * @version 1.1
 */
public class UserDTO implements Serializable {
    /**
     * The serial version ID.
     */
    private static final long serialVersionUID = 8939342833412108795L;
    /**
     * <p>
     * Represents the user id.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private long userId;
    /**
     * <p>
     * Represents the handle.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private String handle;

    /**
     * Creates an instance of UserDTO.
     */
    public UserDTO() {
        // Empty
    }

    /**
     * Gets the user id.
     *
     * @return the user id.
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId
     *            the user id.
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Gets the handle.
     *
     * @return the handle.
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Sets the handle.
     *
     * @param handle
     *            the handle.
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * Gets the hash code.
     *
     * @return the hash code.
     * @since 1.1
     */
    @Override
    public int hashCode() {
        return (int) this.userId;
    }

    /**
     * Compares if it equals.
     *
     * @param obj the object used to compare
     * @return true if it equals, false otherwise.
     * @since 1.1
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UserDTO)) {
            return false;
        }

        return this.userId == ((UserDTO) obj).getUserId();
    }
}
