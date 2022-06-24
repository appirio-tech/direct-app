/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.dao;

import org.hibernate.annotations.Entity;

import com.topcoder.direct.services.project.metadata.entities.HelperEntities;
import com.topcoder.json.object.JSONObject;

/**
 * <p>
 *  This POJO class contains information for audit of DirectProjectMetadata.
 *  This class extends AuditEntity.
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
public class DirectProjectMetadataAudit extends AuditEntity {

    /**
     * The id of project metadata.
     */
    private long projectMetadataId;

    /**
     * The id of TC Direct project.
     */
    private long tcDirectProjectId;

    /**
     * The id of project metadata key.
     */
    private long projectMetadataKeyId;

    /**
     * The value of metadata.
     */
    private String metadataValue;

    /**
     * The default constructor.
     */
    public DirectProjectMetadataAudit() {
        // Do nothing.
    }

    /**
     * Receives id of project metadata.
     *
     * @return id of project metadata.
     */
    public long getProjectMetadataId() {
        return projectMetadataId;
    }

    /**
     * Setter for id of project metadata.
     *
     * @param projectMetadataId the id of project metadata.
     */
    public void setProjectMetadataId(long projectMetadataId) {
        this.projectMetadataId = projectMetadataId;
    }

    /**
     * Receives id of TC Direct Project.
     *
     * @return id of TC Direct Project.
     */
    public long getTcDirectProjectId() {
        return tcDirectProjectId;
    }

    /**
     * Setter for id of TC Direct Project.
     *
     * @param tcDirectProjectId the id of direct project.
     */
    public void setTcDirectProjectId(long tcDirectProjectId) {
        this.tcDirectProjectId = tcDirectProjectId;
    }

    /**
     * Receives id of project metadata key.
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
     * Receive value of metadata.
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

        if (!keyOnly) {
            jsonObject.setLong("projectMetadataId", projectMetadataId);
            jsonObject.setLong("tcDirectProjectId", tcDirectProjectId);
            jsonObject.setLong("projectMetadataKeyId", projectMetadataKeyId);
            HelperEntities.setString(jsonObject, "metadataValue", metadataValue);
        }

        return jsonObject;
    }
}

