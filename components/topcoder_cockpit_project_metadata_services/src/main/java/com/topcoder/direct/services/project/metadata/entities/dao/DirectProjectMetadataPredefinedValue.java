/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.dao;

import org.hibernate.annotations.Entity;

import com.topcoder.direct.services.project.metadata.entities.HelperEntities;
import com.topcoder.json.object.JSONObject;

/**
 * <p>
 *  This POJO class contains information for DirectProjectMetadataPredefinedValue.
 *  This class extends IdentifiableEntity.
 *  There is no any argument validation in setter method.
 * </p>
 *
 * <p>
 *  Thread Safety: This class is mutable and not thread safe.
 * </p>
 *
 * @author Standlove, CaDenza
 * @version 1.0
 */
@Entity
@SuppressWarnings("serial")
public class DirectProjectMetadataPredefinedValue extends IdentifiableEntity {
    /**
     * The predefined metadata value.
     */
    private String predefinedMetadataValue;

    /**
     * The metadata position.
     */
    private int position;

    /**
     * The key of direct project metadata.
     */
    private DirectProjectMetadataKey projectMetadataKey;

    /**
     * Default constructor.
     */
    public DirectProjectMetadataPredefinedValue() {
        // Do nothing.
    }

    /**
     * Retrieves value of predefine metadata.
     *
     * @return value of predefine metadata.
     */
    public String getPredefinedMetadataValue() {
        return predefinedMetadataValue;
    }

    /**
     * Setter for value of predefine metadata.
     *
     * @param predefinedMetadataValue the value of predefine metadata.
     */
    public void setPredefinedMetadataValue(String predefinedMetadataValue) {
        this.predefinedMetadataValue = predefinedMetadataValue;
    }

    /**
     * Retrieves predefineMetadata position.
     *
     * @return the position of predefine metadata.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Setter for predefine metadata position.
     *
     * @param position the position of predefine metadata.
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Retrieves key of project metadata.
     *
     * @return key of project metadata.
     */
    public DirectProjectMetadataKey getProjectMetadataKey() {
        return projectMetadataKey;
    }

    /**
     * Setter for key of project metadata.
     *
     * @param projectMetadataKey the key pr project metadata.
     */
    public void setProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey) {
        this.projectMetadataKey = projectMetadataKey;
    }

    /**
     * Converts the entity to a JSON object.
     *
     * @param keyOnly
     *          Whether only construct JSONObject for Entity ID only.
     *
     * @return the JSON object with data from this entity (not null)
     */
    public JSONObject toJSONObject(boolean keyOnly) {
        JSONObject jsonObject = super.toJSONObject(keyOnly);

        if (!keyOnly) {
            HelperEntities.setString(jsonObject, "predefinedMetadataValue", predefinedMetadataValue);
            jsonObject.setInt("position", position);
            HelperEntities.setIdentifiableEntity(jsonObject, "projectMetadataKey", projectMetadataKey);
        }

        return jsonObject;
    }
}
