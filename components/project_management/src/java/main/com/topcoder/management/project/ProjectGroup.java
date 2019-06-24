/*
 * Copyright (C) 2017 - 2019 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.Serializable;

/**
 * <p>
 * This is a ProjectGroup entity which represents the project_group_lu.
 * </p>
 *
 * Version 1.1 (Topcoder - Integrate Direct with Groups V5)
 *  - Add new property: {@link #newId}
 *
 * <p>
 * Thread safety: This class is not thread safe.
 * </p>
 *
 * @author TCSCODER
 * @version 1.1
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
     * Represent new Id format
     */
    private String newId;

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

    /**
     * Getter for {@link #newId}
     * @return
     */
    public String getNewId() {
        return newId;
    }

    /**
     * Setter for {@link #newId}
     * @param newId id
     */
    public void setNewId(String newId) {
        this.newId = newId;
    }
}
