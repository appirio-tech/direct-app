/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.dao;

import java.util.Date;

import com.topcoder.direct.services.project.metadata.entities.HelperEntities;
import com.topcoder.json.object.JSONObject;

/**
 * <p>
 *  This is the base class for all class which need to be audited.It is a simple JavaBean (POJO) that provides
 *  getters and setters for all private attributes and performs no argument validation in the setters. It overrides
 *  toJSONObject() method to support conversion of this entity to JSON string that can be used for logging
 * </p>
 *
 * <p>
 *  Thread Safety: This class is mutable and not thread safe.
 * </p>
 *
 * @author Standlove, CaDenza
 * @version 1.0
 */
@SuppressWarnings("serial")
public abstract class AuditEntity extends IdentifiableEntity {

    /**
     * The audit action type id.
     * Can be any value. Has getter and setter.
     */
    private int auditActionTypeId;

    /**
     * The action date.
     * Can be any value. Has getter and setter.
     */
    private Date actionDate;

    /**
     * The action user id.
     * Can be any value. Has getter and setter.
     */
    private long actionUserId;

    /**
     * Default constructor.
     */
    public AuditEntity() {
        // Do nothing.
    }

    /**
     * Retrieves value of audit-action-type-id.
     *
     * @return value of audit-action-type-id
     */
    public int getAuditActionTypeId() {
        return auditActionTypeId;
    }

    /**
     * Setter for audit-action-type-id.
     *
     * @param auditActionTypeId the value of audit-action-type-id.
     */
    public void setAuditActionTypeId(int auditActionTypeId) {
        this.auditActionTypeId = auditActionTypeId;
    }

    /**
     * Retrieves value of action-date.
     *
     * @return value of action-date.
     */
    public Date getActionDate() {
        return actionDate;
    }

    /**
     * Setter for action-date.
     *
     * @param actionDate the value of action-date.
     */
    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    /**
     * Retrieves value of action-user-id.
     *
     * @return value of action-user-id.
     */
    public long getActionUserId() {
        return actionUserId;
    }

    /**
     * Setter for action-user-id.
     *
     * @param actionUserId the value of action-user-id.
     */
    public void setActionUserId(long actionUserId) {
        this.actionUserId = actionUserId;
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
            jsonObject.setInt("auditActionTypeId", auditActionTypeId);
            HelperEntities.setDate(jsonObject, "actionDate", actionDate);
            jsonObject.setLong("actionUserId", actionUserId);
        }

        return jsonObject;
    }
}
