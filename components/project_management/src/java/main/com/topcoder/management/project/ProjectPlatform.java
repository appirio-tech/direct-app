/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.Serializable;

/**
 * <p>
 * This class represents the project platform.
 * </p>
 *
 * @author GreatKevin
 * @version 1.0 (Module Assembly - TC Cockpit Launch F2F contest)
 */
public class ProjectPlatform implements Serializable {

    /**
     * The id.
     */
    private long id;

    /**
     * The name.
     */
    private String name;

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
     * @param id the id.
     */
    public void setId(long id) {
        this.id = id;
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
     * Sets the name.
     *
     * @param name the name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Creates the instance with id and name.
     *
     * @param id   the id.
     * @param name the name.
     */
    public ProjectPlatform(long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Creates the instance.
     */
    public ProjectPlatform() {
        // empty constructor
    }
}
