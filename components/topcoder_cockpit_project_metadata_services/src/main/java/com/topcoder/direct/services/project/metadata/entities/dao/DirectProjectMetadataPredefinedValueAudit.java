/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.dao;

import org.hibernate.annotations.Entity;

import com.topcoder.direct.services.project.metadata.entities.HelperEntities;
import com.topcoder.json.object.JSONObject;

/**
 * <p>
 *  This POJO class contains information for audit of DirectProjectMetadataPredefinedValue.
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
public class DirectProjectMetadataPredefinedValueAudit extends AuditEntity {
    /**
     * The id of project metadata predefined.
     */
    private long projectMetadataPredefinedValueId;

    /**
     * The value of predefined metadata.
     */
    private String predefinedMetadataValue;

    /**
     * The position of predefined metadata.
     */
    private int position;

    /**
     * The id of project metadata key.
     */
    private long projectMetadataKeyId;

    /**
     * Default constructor.
     */
    public DirectProjectMetadataPredefinedValueAudit() {
        // Do nothing.
    }

    /**
     * Retrieve id of project metadata predefined value.
     *
     * @return id of project metadata predefined value.
     */
    public long getProjectMetadataPredefinedValueId() {
        return projectMetadataPredefinedValueId;
    }

    /**
     * Setter for id of project metadata predefined value.
     *
     * @param projectMetadataPredefinedValueId the id of project metadata predefined value.
     */
    public void setProjectMetadataPredefinedValueId(
            long projectMetadataPredefinedValueId) {
        this.projectMetadataPredefinedValueId = projectMetadataPredefinedValueId;
    }

    /**
     * Retrieves value of predefined metadata.
     *
     * @return value of predefined metadata.
     */
    public String getPredefinedMetadataValue() {
        return predefinedMetadataValue;
    }

    /**
     * Setter for value of predefined metadata.
     *
     * @param predefinedMetadataValue the value of predefined metadata.
     */
    public void setPredefinedMetadataValue(String predefinedMetadataValue) {
        this.predefinedMetadataValue = predefinedMetadataValue;
    }

    /**
     * Retrieves position of predefined metadata.
     *
     * @return position of predefined metadata.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Setter for position of predefined metadata.
     *
     * @param position the position of predefined metadata.
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Retrieves id of project metadata key.
     *
     * @return the id of project metadata key.
     */
    public long getProjectMetadataKeyId() {
        return projectMetadataKeyId;
    }

    /**
     * Setter for id of project metadata key.
     *
     * @param projectMetadataKeyId the id of project metadata key.
     */
    public void setProjectMetadataKeyId(long projectMetadataKeyId) {
        this.projectMetadataKeyId = projectMetadataKeyId;
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
            jsonObject.setLong("projectMetadataPredefinedValueId", projectMetadataPredefinedValueId);
            HelperEntities.setString(jsonObject, "predefinedMetadataValue", predefinedMetadataValue);
            jsonObject.setInt("position", position);
            jsonObject.setLong("projectMetadataKeyId", projectMetadataKeyId);
        }

        return jsonObject;
    }
}

