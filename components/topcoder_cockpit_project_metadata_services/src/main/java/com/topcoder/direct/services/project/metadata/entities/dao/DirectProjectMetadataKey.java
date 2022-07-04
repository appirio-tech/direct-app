/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.dao;

import java.util.List;

import org.hibernate.annotations.Entity;

import com.topcoder.direct.services.project.metadata.entities.HelperEntities;
import com.topcoder.json.object.JSONObject;

/**
 * <p>
 *  This POJO class contains information for DirectProjectMetadataKey.
 *  This class extends IdentifiableEntity.
 *  There is no any argument validation in setter method.
 * </p>
 *
 * <p>
 *  Thread Safety: This class is mutable and not thread safe.
 * </p>
 *
 * <p>
 *     Version 1.1 (Release Assembly - TC Cockpit All Projects Management Page Update) changes
 *     <ol>
 *         <li>
 *             Overrides <code>hashcode</code> and <code>equals</code> so the DirectProjectMetadataKey can be used
 *             in collections like map and set.
 *         </li>
 *     </ol>
 * </p>
 *
 * @author Standlove, CaDenza, TCSASSEMBLER
 * @version 1.1
 */
@Entity
@SuppressWarnings("serial")
public class DirectProjectMetadataKey extends IdentifiableEntity {

    /**
     * The name of direct project metadata key.
     * Can be any value.
     */
    private String name;

    /**
     * The description of direct project metadata key.
     */
    private String description;

    /**
     * The grouping flag.
     */
    private Boolean grouping;

    /**
     * The Id of client.
     */
    private Long clientId;

    /**
     * The collection of predefined values.
     */
    private List<DirectProjectMetadataPredefinedValue> predefinedValues;

    /**
     * the flag for single identifier.
     */
    private boolean single;

    /**
     * Default constructor.
     */
    public DirectProjectMetadataKey() {
        // Do nothing.
    }

    /**
     * Retrieves value of name property.
     *
     * @return name value.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter name value.
     *
     * @param name the name to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves description property.
     *
     * @return description value.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for description.
     *
     * @param description the description to be set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves grouping flag information.
     *
     * @return grouping value.
     */
    public Boolean getGrouping() {
        return grouping;
    }

    /**
     * Setter for grouping information.
     *
     * @param grouping the grouping flag.
     */
    public void setGrouping(Boolean grouping) {
        this.grouping = grouping;
    }

    /**
     * Retrieves the id of client.
     *
     * @return the id of client.
     */
    public Long getClientId() {
        return clientId;
    }

    /**
     * Setter for id of client.
     *
     * @param clientId the id of client.
     */
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    /**
     * Retrieves collection of predefinedValues.
     *
     * @return collection of predefinedValues.
     */
    public List<DirectProjectMetadataPredefinedValue> getPredefinedValues() {
        return predefinedValues;
    }

    /**
     * Setter collection of predefined values.
     *
     * @param predefinedValues the collection of predefined values.
     */
    public void setPredefinedValues(
            List<DirectProjectMetadataPredefinedValue> predefinedValues) {
        this.predefinedValues = predefinedValues;
    }

    /**
     * Retrieves information whether this entity is single metadatakey or net.
     *
     * @return whether single or not.
     */
    public boolean isSingle() {
        return single;
    }

    /**
     * Setter for single flagging.
     *
     * @param single the single flagging.
     */
    public void setSingle(boolean single) {
        this.single = single;
    }

    /**
     * Gets the hash code of the metadata key.
     *
     * @return the hash code of the metadata key.
     * @since 1.1
     */
    @Override
    public int hashCode() {
        // return the key id as hash code
        return (int) getId();
    }

    /**
     * Compares equals
     *
     * @param obj the object to compare
     * @return true if equals, false otherwise
     * @since 1.1
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final DirectProjectMetadataKey other = (DirectProjectMetadataKey) obj;
        if (getId() != other.getId())
            return false;
        return true;
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
            HelperEntities.setLoggableEntityList(jsonObject, "predefinedValues", predefinedValues);
            jsonObject.setBoolean("single", single);
        }

        return jsonObject;
    }
}

