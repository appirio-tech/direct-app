/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto;

import java.io.Serializable;


/**
 * A simple java bean class which can be used to represent id/name pair. It can be used
 * by many DTO classes to store id/name pair of any information.
 *
 * @author TCSDEVELOPER
 * @version  1.0 (TopCoder Cockpit - Cost Report Assembly)
 */
public class IdNamePair implements Serializable {

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
     * @param id the id to set.
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
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
}
