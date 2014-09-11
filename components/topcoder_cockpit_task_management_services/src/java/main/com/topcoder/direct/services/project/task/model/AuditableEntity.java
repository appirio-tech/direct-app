/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.model;

import java.util.Date;

/**
 * <p>
 * AuditableEntity is the base class for all entities with auditing fields (i.e. createdDate, createdBy,
 * lastModifiedDate, lastModifiedBy).
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is not thread safe because it is mutable.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public abstract class AuditableEntity extends IdentifiableEntity {
    /**
     * The serial version ID.
     */
    private static final long serialVersionUID = 1492605651891811100L;
    /**
     * <p>
     * Represents the created date.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private Date createdDate;
    /**
     * <p>
     * Represents the name that created the record.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private String createdBy;
    /**
     * <p>
     * Represents the last modified date.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private Date lastModifiedDate;
    /**
     * <p>
     * Represents the last name that modified the record.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private String lastModifiedBy;

    /**
     * Creates an instance of AuditableEntity.
     */
    protected AuditableEntity() {
        // Empty
    }

    /**
     * Gets the created date.
     *
     * @return the created date.
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the created date.
     *
     * @param createdDate
     *            the created date.
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Gets the name that created the record.
     *
     * @return the name that created the record.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the name that created the record.
     *
     * @param createdBy
     *            the name that created the record.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the last modified date.
     *
     * @return the last modified date.
     */
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * Sets the last modified date.
     *
     * @param lastModifiedDate
     *            the last modified date.
     */
    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    /**
     * Gets the last name that modified the record.
     *
     * @return the last name that modified the record.
     */
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    /**
     * Sets the last name that modified the record.
     *
     * @param lastModifiedBy
     *            the last name that modified the record.
     */
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}