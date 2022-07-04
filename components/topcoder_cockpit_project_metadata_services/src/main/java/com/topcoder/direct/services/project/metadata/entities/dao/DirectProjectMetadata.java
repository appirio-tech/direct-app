/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.dao;

import org.hibernate.annotations.Entity;

import com.topcoder.direct.services.project.metadata.entities.HelperEntities;
import com.topcoder.json.object.JSONObject;

/**
 * <p>
 *  This POJO class contains information for DirectProjectMetadata.
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
public class DirectProjectMetadata extends IdentifiableEntity {

    /**
     * The id of TC Direct Project.
     * Can be any value. Have setter and getter.
     */
    private long tcDirectProjectId;

    /**
     * The key of project metadata.
     * Can be any value.
     */
    private DirectProjectMetadataKey projectMetadataKey;

    /**
     * The value of metadata.
     * Can be any value.
     */
    private String metadataValue;

    /**
     * Default constructor.
     */
    public DirectProjectMetadata() {
        // Do nothing.
    }

    /**
     * Retrieves the id of TC Direct project.
     *
     * @return id of TC Direct Project.
     */
    public long getTcDirectProjectId() {
        return tcDirectProjectId;
    }

    /**
     * Setter for id of TC Direct Project.
     *
     * @param tcDirectProjectId the id of TC Direct Project.
     */
    public void setTcDirectProjectId(long tcDirectProjectId) {
        this.tcDirectProjectId = tcDirectProjectId;
    }

    /**
     * Retrieves key of direct project metadata.
     *
     * @return key of direct project metadata.
     */
    public DirectProjectMetadataKey getProjectMetadataKey() {
        return projectMetadataKey;
    }

    /**
     * Setter for key of project metadata.
     *
     * @param projectMetadataKey the key of project metadata.
     */
    public void setProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey) {
        this.projectMetadataKey = projectMetadataKey;
    }

    /**
     * Retrieves value of metadata.
     *
     * @return value of metadata.
     */
    public String getMetadataValue() {
        return metadataValue;
    }

    /**
     * Setter for value of metadata.
     *
     * @param metadataValue the value of metadata.
     */
    public void setMetadataValue(String metadataValue) {
        this.metadataValue = metadataValue;
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

        jsonObject.setLong("tcDirectProjectId", tcDirectProjectId);
        HelperEntities.setIdentifiableEntity(jsonObject, "projectMetadataKey", projectMetadataKey);
        HelperEntities.setString(jsonObject, "metadataValue", metadataValue);
        return jsonObject;
    }
}

