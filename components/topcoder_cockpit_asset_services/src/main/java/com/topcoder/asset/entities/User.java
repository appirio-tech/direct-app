/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.entities;

/**
 * <p>
 * This class is a container for user. It is a simple JavaBean (POJO) that provides getters and setters for all
 * private attributes and performs no argument validation in the setters.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author LOY, sparemax
 * @version 1.0
 */
public class User {
    /**
     * The user id. Can be any value. Has getter and setter.
     */
    private long id;

    /**
     * The user name. Can be any value. Has getter and setter.
     */
    private String name;

    /**
     * Creates an instance of User.
     */
    public User() {
        // Empty
    }

    /**
     * Retrieves the user id.
     *
     * @return the user id.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the user id.
     *
     * @param id
     *            the user id.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Retrieves the user name.
     *
     * @return the user name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user name.
     *
     * @param name
     *            the user name.
     */
    public void setName(String name) {
        this.name = name;
    }
}
