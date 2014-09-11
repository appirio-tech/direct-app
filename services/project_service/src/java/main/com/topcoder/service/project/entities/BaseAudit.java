/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * The base class for the audit classes.
 * 
 * @author TCSASSEMBER
 * @version 1.0
 *
 * @since Release Assembly - TopCoder Direct Project Audit v1.0
 */
public abstract class BaseAudit implements Serializable {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -120475923856L;

    /**
     * The id of the "CREATE" audit type.
     */
    public static int CREATE_AUDIT_ACTION_ID = 1;

    /**
     * The id of the "DELETE" audit type.
     */
    public static int DELETE_AUDIT_ACTION_ID = 2;

    /**
     * The id of the "UPDATE" audit type.
     */
    public static int UPDATE_AUDIT_ACTION_ID = 3;

    /**
     * Represents the id.
     */
    private long id;

    /**
     * Represents the record id.
     */
    private Long recordId;

    /**
     * Represents the id of the audit action type.
     */
    private Integer actionTypeId;

    /**
     * Represents the user id of the operator.
     */
    private Long actionUserId;

    /**
     * Represents the field name.
     */
    private String fieldName;

    /**
     * Represents the old field value.
     */
    private String oldValue;

    /**
     * Represents the new field value.
     */
    private String newValue;

    /**
     * Represents the time stamp of the operation.
     */
    private Date timestamp;

    /**
     * Empty constructor.
     */
    protected BaseAudit() {

    }

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     * 
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the record id.
     * @return the record id.
     */
    public Long getRecordId() {
        return recordId;
    }

    /**
     * Sets the record id.
     * 
     * @param recordId the record id.
     */
    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    /**
     * Gets the id of the audit action type.
     * 
     * @return the id of the audit action type.
     */
    public Integer getActionTypeId() {
        return actionTypeId;
    }

    /**
     * Sets the id of the audit action type.
     * 
     * @param actionTypeId the id of the audit action type.
     */
    public void setActionTypeId(Integer actionTypeId) {
        this.actionTypeId = actionTypeId;
    }

    /**
     * Gets the user id of the operator.
     * 
     * @return the user id of the operator.
     */
    public Long getActionUserId() {
        return actionUserId;
    }

    /**
     * Sets the user id of the operator.
     * 
     * @param actionUserId the user id of the operator.
     */
    public void setActionUserId(Long actionUserId) {
        this.actionUserId = actionUserId;
    }

    /**
     * Gets the field name.
     * 
     * @return the field name.
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Sets the field name.
     * 
     * @param fieldName the field name.
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Gets the old field value.
     * 
     * @return the old field value.
     */
    public String getOldValue() {
        return oldValue;
    }

    /**
     * Sets the old field value.
     * 
     * @param oldValue the old field value.
     */
    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    /**
     * Gets the new field value.
     * 
     * @return the new field value.
     */
    public String getNewValue() {
        return newValue;
    }

    /**
     * Sets the new field value.
     * 
     * @param newValue the new field value.
     */
    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    /**
     * Gets the time stamp of the operation.
     * 
     * @return the time stamp of the operation.
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the time stamp of the operation.
     * 
     * @param timestamp the time stamp of the operation.
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
