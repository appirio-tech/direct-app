/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.dto;

import com.topcoder.direct.services.project.metadata.entities.HelperEntities;
import com.topcoder.json.object.JSONObject;

/**
 * <p>
 *  The implementation class of DirectProjectFilter interface which provide filter for MetadataKeyNameValue.
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
public class MetadataKeyNameValueFilter implements DirectProjectFilter {
    /**
     * The name of projectMetadataKey.
     */
    private String projectMetadataKeyName;

    /**
     * The value of metadata.
     */
    private String metadataValue;

    /**
     * The metadata value operator.
     */
    private MetadataValueOperator metadataValueOperator;

    /**
     * Default constructor.
     */
    public MetadataKeyNameValueFilter() {
        // Do nothing.
    }

    /**
     * Retrieves name of projectMetadataKey.
     *
     * @return name of projectMetadataKey.
     */
    public String getProjectMetadataKeyName() {
        return projectMetadataKeyName;
    }

    /**
     * Setter for name of projectMetadatakey.
     *
     * @param projectMetadataKeyName the name of projectMetadataKey.
     */
    public void setProjectMetadataKeyName(String projectMetadataKeyName) {
        this.projectMetadataKeyName = projectMetadataKeyName;
    }

    /**
     * Retrieves value of metadata.
     *
     * @return the value of metadata.
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
     * Getter for metadata value operator.
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
        HelperEntities.setString(jsonObject, "projectMetadataKeyName", projectMetadataKeyName);
        HelperEntities.setString(jsonObject, "metadataValue", metadataValue);

        if (metadataValueOperator == null) {
            jsonObject.setNull("metadataValueOperator");
        } else {
            jsonObject.setString("metadataValueOperator", metadataValueOperator.toString());
        }
        return jsonObject.toJSONString();
    }
}

