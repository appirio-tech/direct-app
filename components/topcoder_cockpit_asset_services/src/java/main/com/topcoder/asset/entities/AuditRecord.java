/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.entities;

import java.util.Date;

/**
 * <p>
 * This class is a container for audit record. It is a simple JavaBean (POJO) that provides getters and setters for
 * all private attributes and performs no argument validation in the setters.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author LOY, sparemax
 * @version 1.0
 */
public class AuditRecord {
    /**
     * The id. Can be any value. Has getter and setter.
     */
    private long id;

    /**
     * The timestamp. Can be any value. Has getter and setter.
     */
    private Date timestamp;

    /**
     * The id of user who performs the action. Can be any value. Has getter and setter.
     */
    private long userId;

    /**
     * The action name. Can be any value. Has getter and setter.
     */
    private String action;

    /**
     * The entity type. Can be any value. Has getter and setter.
     */
    private String entityType;

    /**
     * The entity id. Can be any value. Has getter and setter.
     */
    private long entityId;

    /**
     * The old value of the entity. Can be any value. Has getter and setter.
     */
    private String oldValue;

    /**
     * The new value of the entity. Can be any value. Has getter and setter.
     */
    private String newValue;

    /**
     * Creates an instance of this class.
     */
    public AuditRecord() {
        // Empty
    }

    /**
     * Retrieves the id.
     *
     * @return the id.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the id.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Retrieves the timestamp.
     *
     * @return the timestamp.
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp.
     *
     * @param timestamp
     *            the timestamp.
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Retrieves the id of user who performs the action.
     *
     * @return the id of user who performs the action.
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets the id of user who performs the action.
     *
     * @param userId
     *            the id of user who performs the action.
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Retrieves the action name.
     *
     * @return the action name.
     */
    public String getAction() {
        return action;
    }

    /**
     * Sets the action name.
     *
     * @param action
     *            the action name.
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Retrieves the entity type.
     *
     * @return the entity type.
     */
    public String getEntityType() {
        return entityType;
    }

    /**
     * Sets the entity type.
     *
     * @param entityType
     *            the entity type.
     */
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    /**
     * Retrieves the entity id.
     *
     * @return the entity id.
     */
    public long getEntityId() {
        return entityId;
    }

    /**
     * Sets the entity id.
     *
     * @param entityId
     *            the entity id.
     */
    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    /**
     * Retrieves the old value of the entity.
     *
     * @return the old value of the entity.
     */
    public String getOldValue() {
        return oldValue;
    }

    /**
     * Sets the old value of the entity.
     *
     * @param oldValue
     *            the old value of the entity.
     */
    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    /**
     * Retrieves the new value of the entity.
     *
     * @return the new value of the entity.
     */
    public String getNewValue() {
        return newValue;
    }

    /**
     * Sets the new value of the entity.
     *
     * @param newValue
     *            the new value of the entity.
     */
    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
