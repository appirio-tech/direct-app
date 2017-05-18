/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.Serializable;

/**
 * <p>
 * This is a ProjectGroup entity which represents the project_group_lu.
 * </p>
 *
 * <p>
 * Thread safety: This class is not thread safe.
 * </p>
 *
 * @author TCSCODER
 * @version 1.0
 */
public class ProjectGroup implements Serializable {
    /**
     * Represents group id
     */
    private long id;

    /**
     * Represents group name
     */
    private String name;

    /**
     * Constructor
     */
    public ProjectGroup() {
    }

    /**
     * Constructor
     *
     * @param id group id
     * @param name group name
     */
    public ProjectGroup(long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Getter for {@link #id}
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for {@link #id}
     * @param id the group id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter for {@link #name}
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for {@link #name}
     * @param name the name of group
     */
    public void setName(String name) {
        this.name = name;
    }
}
