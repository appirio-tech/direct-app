/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.specreview;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

/**
 * The Class UpdatedSpecSectionData - represents the updates made to various sections.
 * 
 * This is used to notify the end users about updates made to the spec review.
 * 
 * @author TCSASSEMBLER
 * @version 1.0
 * @since Spec Review Finishing Touches v1.0
 */
@SqlResultSetMapping(name = "UpdatedSpecSectionDataResults", 
                     entities = { @EntityResult(entityClass = UpdatedSpecSectionData.class, 
                     fields = {
                                @FieldResult(name = "sectionId", column = "sectionId"),
                                @FieldResult(name = "sectionName", column = "sectionName"), 
                                @FieldResult(name = "status", column = "statusName"),
                                @FieldResult(name = "comment", column = "comment"),
                                @FieldResult(name = "user", column = "user")}) })
@Entity
public class UpdatedSpecSectionData implements Serializable {

    /** Default serial version id. */
    private static final long serialVersionUID = 1L;

    @Id
    private long sectionId;

    /** The section name. */
    private String sectionName;

    /** The status. */
    private String status;

    /** The comment. */
    private String comment;

    /** The user. */
    private String user;

    /**
     * Gets the section id.
     * 
     * @return the section id
     */
    public long getSectionId() {
        return this.sectionId;
    }

    /**
     * Sets the section id.
     * 
     * @param sectionId
     *            the new section id
     */
    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }

    /**
     * Gets the section name.
     * 
     * @return the section name
     */
    public String getSectionName() {
        return this.sectionName;
    }

    /**
     * Sets the section name.
     * 
     * @param sectionName
     *            the new section name
     */
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    /**
     * Gets the status.
     * 
     * @return the status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Sets the status.
     * 
     * @param status
     *            the new status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the comment.
     * 
     * @return the comment
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * Sets the comment.
     * 
     * @param comment
     *            the new comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Gets the user.
     * 
     * @return the user
     */
    public String getUser() {
        return this.user;
    }

    /**
     * Sets the user.
     * 
     * @param user
     *            the new user
     */
    public void setUser(String user) {
        this.user = user;
    }
}
