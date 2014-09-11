/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.dto;

import java.io.Serializable;

import com.topcoder.direct.services.project.metadata.entities.HelperEntities;
import com.topcoder.json.object.JSONObject;

/**
 * The POJO class which store information for DTO object direct project metadata.
 * This entity support conversion into JSONString.
 *
 * <p>
 *  Thread Safety: This class is mutable and not thread safe.
 * </p>
 *
 * @author Standlove, CaDenza
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DirectProjectMetadataDTO implements Serializable {
    /**
     * The id of project metadata.
     */
    private long projectMetadataKeyId;

    /**
     * The value of metadata.
     */
    private String metadataValue;

    /**
     * Default constructor.
     */
    public DirectProjectMetadataDTO() {
        // Do nothing.
    }

    /**
     * Retrieves id of project metadata key.
     *
     * @return id of project metadata key.
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
     * Retrieves metadata value.
     *
     * @return metadata value.
     */
    public String getMetadataValue() {
        return metadataValue;
    }

    /**
     * Setter for metadata value.
     *
     * @param metadataValue the value of metadata.
     */
    public void setMetadataValue(String metadataValue) {
        this.metadataValue = metadataValue;
    }

    /**
     * Converts the entity to a JSON string that can be used for logging.
     * @return the JSON string with entity data (not null)
     */
    public String toJSONString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.setLong("projectMetadataKeyId", projectMetadataKeyId);
        HelperEntities.setString(jsonObject, "metadataValue", metadataValue);
        return jsonObject.toJSONString();
    }
}

