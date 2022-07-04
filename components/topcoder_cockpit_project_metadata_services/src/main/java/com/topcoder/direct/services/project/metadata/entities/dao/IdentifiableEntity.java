/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.dao;

import java.io.Serializable;

import com.topcoder.json.object.JSONObject;

/**
 * <p>
 *  This is the base class for all entities that have an identification number. It is a simple JavaBean (POJO) that
 *  provides getters and setters for all private attributes and performs no argument validation in the setters.
 *  It defines toJSONString() method that can be used for logging the entity data.
 *  Subclasses need to override toJSONObject() to populate JSON object properly.
 * </p>
 *
 * <p>
 *  Thread Safety: This class is mutable and not thread safe.
 * </p>
 *
 * @author Standlove, CaDenza
 * @version 1.0
 */
@SuppressWarnings("serial")
public abstract class IdentifiableEntity implements Serializable {
    /**
     * The primary identifier of the entity.
     * Can be any value. Has getter and setter.
     */
    private long id;

    /**
     * Default constructor.
     */
    public IdentifiableEntity() {
        // Do nothing.
    }

    /**
     * Retrieves the primary identifier of the entity.
     *
     * @return the primary identifier of the entity
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the primary identifier of the entity.
     *
     * @param id the primary identifier of the entity
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Converts the entity to a JSON object.
     * @param keyOnly
     *          Whether only construct JSONObject for Entity ID only.
     * @return the JSON object with data from this entity (not null)
     */
    public JSONObject toJSONObject(boolean keyOnly) {
        return new JSONObject();
    }

    /**
     * Converts the entity to a JSON string that can be used for logging.
     * @return the JSON string with entity data (not null)
     */
    public String toJSONString() {
        JSONObject jsonObject = toJSONObject(false);
        jsonObject.setLong("id", id);
        return jsonObject.toJSONString();
    }
}

