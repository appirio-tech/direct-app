/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.fees.entities;

import java.util.Date;

/**
 * Represent the audit record for contest fee changes.
 * 
 * Thread safety: The class is mutable and not thread safe.
 * 
 * @author winstips, TCSDEVELOPER
 * @version 1.0
 */
public class FeeAuditRecord {
    /**
     * Represents the id field.It is managed with a getter and setter. It may have any value. It is fully mutable.
     */
    private long id;
    /**
     * Represents the string representation of billing account identifier. It is managed with a getter and setter. It may
     * have any value. It is fully mutable.
     */
    private String billingAccount;
    /**
     * Represents the previous contest fee value. It is managed with a getter and setter. It may have any value. It is
     * fully mutable.
     */
    private String oldFeeValue;
    /**
     * Represents the new contest fee value. It is managed with a getter and setter. It may have any value. It is fully
     * mutable.
     */
    private String newFeeValue;
    /**
     * Represents the timestamp of the contest fee change operation. It is managed with a getter and setter. It may have
     * any value. It is fully mutable.
     */
    private Date timestamp;
    /**
     * Represents the user identifier. It is managed with a getter and setter. It may have any value. It is fully
     * mutable.
     */
    private String userId;
    /**
     * Represents the contest type identifier. It is managed with a getter and setter. It may have any value. It is fully
     * mutable.
     */
    private String contestTypeId;
    /**
     * Represents the user action. Example create or update contest fee. It is managed with a getter and setter. It may
     * have any value. It is fully mutable.
     */
    private String action;

    /**
     * Default Constructor.
     */
    public FeeAuditRecord() {
        super();
    }

    /**
     * Returns the billingAccount field value.
     * 
     * @return billingAccount field value.
     */
    public String getBillingAccount() {
        return billingAccount;
    }

    /**
     * Sets the given value to billingAccount field.
     * 
     * @param billingAccount
     *            - the given value to set.
     */
    public void setBillingAccount(String billingAccount) {
        this.billingAccount = billingAccount;
    }

    /**
     * Returns the oldFeeValue field value.
     * 
     * @return oldFeeValue field value.
     */
    public String getOldFeeValue() {
        return oldFeeValue;
    }

    /**
     * Sets the given value to oldFeeValue field.
     * 
     * @param oldFeeValue
     *            - the given value to set.
     */
    public void setOldFeeValue(String oldFeeValue) {
        this.oldFeeValue = oldFeeValue;
    }

    /**
     * Returns the newFeeValue field value.
     * 
     * @return newFeeValue field value.
     */
    public String getNewFeeValue() {
        return newFeeValue;
    }

    /**
     * Sets the given value to newFeeValue field.
     * 
     * @param newFeeValue
     *            - the given value to set.
     */
    public void setNewFeeValue(String newFeeValue) {
        this.newFeeValue = newFeeValue;
    }

    /**
     * Returns the timestamp field value.
     * 
     * @return timestamp field value.
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the given value to timestamp field.
     * 
     * @param timestamp
     *            - the given value to set.
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Returns the userId field value.
     * 
     * @return userId field value.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the given value to userId field.
     * 
     * @param userId
     *            - the given value to set.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Returns the contestTypeId field value.
     * 
     * @return contestTypeId field value.
     */
    public String getContestTypeId() {
        return contestTypeId;
    }

    /**
     * Sets the given value to contestTypeId field.
     * 
     * @param contestTypeId
     *            - the given value to set.
     */
    public void setContestTypeId(String contestTypeId) {
        this.contestTypeId = contestTypeId;
    }

    /**
     * Returns the action field value.
     * 
     * @return action field value.
     */
    public String getAction() {
        return action;
    }

    /**
     * Sets the given value to action field.
     * 
     * @param action
     *            - the given value to set.
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Returns the id field value.
     * 
     * @return id field value.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the given value to id field.
     * 
     * @param id
     *            - the given value to set.
     */
    public void setId(long id) {
        this.id = id;
    }
}
