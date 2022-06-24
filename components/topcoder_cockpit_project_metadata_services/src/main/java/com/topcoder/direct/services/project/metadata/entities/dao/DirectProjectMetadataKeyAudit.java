/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.dao;

import org.hibernate.annotations.Entity;

import com.topcoder.direct.services.project.metadata.entities.HelperEntities;
import com.topcoder.json.object.JSONObject;

/**
 * <p>
 *  This POJO class contains information for audit of DirectProjectMetadataKey.
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
public class DirectProjectMetadataKeyAudit extends AuditEntity {

    /**
     * The id of project metadata key.
     */
    private long projectMetadataKeyId;

    /**
     * the name of direct project metadata key audit.
     */
    private String name;

    /**
     * The audit description.
     */
    private String description;

    /**
     * The grouping information.
     */
    private Boolean grouping;

    /**
     * The id of client.
     */
    private Long clientId;

    /**
     * The flag for single identifier.
     */
    private boolean single;

    /**
     * Default constructor.
     */
    public DirectProjectMetadataKeyAudit() {
        // Do nothing.
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
     * Setter id of projectMetadataKey.
     *
     * @param projectMetadataKeyId the id of project metadata key.
     */
    public void setProjectMetadataKeyId(long projectMetadataKeyId) {
        this.projectMetadataKeyId = projectMetadataKeyId;
    }

    /**
     * Retrieves audit name.
     *
     * @return the audit name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter audit name.
     *
     * @param name the audit name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the audit description.
     *
     * @return audit description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for audit description.
     *
     * @param description the audit description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves grouping information.
     *
     * @return the grouping information.
     */
    public Boolean getGrouping() {
        return grouping;
    }

    /**
     * Setter grouping information.
     *
     * @param grouping the grouping information.
     */
    public void setGrouping(Boolean grouping) {
        this.grouping = grouping;
    }

    /**
     * Retrieves the id of client.
     *
     * @return id of client.
     */
    public Long getClientId() {
        return clientId;
    }

    /**
     * Setter id of client.
     *
     * @param clientId the id of client.
     */
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    /**
     * Retrieves flag for single identifier.
     *
     * @return single identifier or not.
     */
    public boolean isSingle() {
        return single;
    }

    /**
     * Setter for single identifier.
     *
     * @param single whether single identifier or not.
     */
    public void setSingle(boolean single) {
        this.single = single;
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
            jsonObject.setLong("projectMetadataKeyId", projectMetadataKeyId);
            HelperEntities.setString(jsonObject, "name", name);
            HelperEntities.setString(jsonObject, "description", description);

            if (grouping == null) {
                jsonObject.setNull("grouping");
            } else {
                jsonObject.setBoolean("grouping", grouping);
            }
            if (clientId == null) {
                jsonObject.setNull("clientId");
            } else {
                jsonObject.setLong("clientId", clientId);
            }
            jsonObject.setBoolean("single", single);
        }

        return jsonObject;
    }
}

