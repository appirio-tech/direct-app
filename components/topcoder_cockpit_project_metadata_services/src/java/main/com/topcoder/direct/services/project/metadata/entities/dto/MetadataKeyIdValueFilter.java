/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.dto;

import com.topcoder.direct.services.project.metadata.entities.HelperEntities;
import com.topcoder.json.object.JSONObject;

/**
 * <p>
 *  The Direct Project Filter implementation class to filter MetadataKeyId.
 *  This class support conversion into JSONString.
 * </p>
 *
 * <p>
 *  Thread Safety: This class is mutable and not thread safe.
 * </p>
 *
 * @author Standlove, CaDenza
 * @version 1.0
 */
public class MetadataKeyIdValueFilter implements DirectProjectFilter {
    /**
     * The id of projectMetadataKey.
     */
    private long projectMetadataKeyId;

    /**
     * The value of metadata.
     */
    private String metadataValue;

    /**
     * The metadata value operator.
     */
    private MetadataValueOperator metadataValueOperator;

    /**
     * Default operator.
     */
    public MetadataKeyIdValueFilter() {
        // Do nothing.
    }

    /**
     * Retrieves id of projectMetadataKey.
     *
     * @return id of projectMetadataKey.
     */
    public long getProjectMetadataKeyId() {
        return projectMetadataKeyId;
    }

    /**
     * Setter id of projectMetadataKey.
     *
     * @param projectMetadataKeyId the id of projectMetadataKey.
     */
    public void setProjectMetadataKeyId(long projectMetadataKeyId) {
        this.projectMetadataKeyId = projectMetadataKeyId;
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
     * Setter value of metadata.
     *
     * @param metadataValue the value of metadata.
     */
    public void setMetadataValue(String metadataValue) {
        this.metadataValue = metadataValue;
    }

    /**
     * Getter metadata value operator.
     *
     * @return metadata value operator.
     */
    public MetadataValueOperator getMetadataValueOperator() {
        return metadataValueOperator;
    }

    /**
     * Setter for metadata value operator.
     *
     * @param metadataValueOperator the metadata value operator.
     */
    public void setMetadataValueOperator(MetadataValueOperator metadataValueOperator) {
        this.metadataValueOperator = metadataValueOperator;
    }

    /**
     * Converts the entity to a JSON string that can be used for logging.
     * @return the JSON string with entity data (not null)
     */
    public String toJSONString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.setLong("projectMetadataKeyId", projectMetadataKeyId);
        HelperEntities.setString(jsonObject, "metadataValue", metadataValue);

        if (metadataValueOperator == null) {
            jsonObject.setNull("metadataValueOperator");
        } else {
            jsonObject.setString("metadataValueOperator", metadataValueOperator.toString());
        }
        return jsonObject.toJSONString();
    }
}

