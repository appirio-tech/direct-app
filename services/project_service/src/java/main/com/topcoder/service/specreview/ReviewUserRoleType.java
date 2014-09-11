/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.specreview;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * The role type for a review user creator.
 * 
 * @author snow01
 * @version 1.0
 * @since Cockpit Launch Contest - Inline Spec Review Part 2
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reviewUserRoleType", propOrder = { "reviewUserRoleTypeId", "name" })
public class ReviewUserRoleType implements Serializable {

    /**
     * Default Serial Version Id.
     */
    private static final long serialVersionUID = 1L;

    /** The review user role type id. */
    private long reviewUserRoleTypeId;

    /** The name. */
    private String name;

    /**
     * Instantiates a new review user role type.
     */
    public ReviewUserRoleType() {

    }

    /**
     * Gets the review user role type id.
     * 
     * @return the review user role type id
     */
    public long getReviewUserRoleTypeId() {
        return reviewUserRoleTypeId;
    }

    /**
     * Sets the review user role type id.
     * 
     * @param reviewUserRoleTypeId
     *            the new review user role type id
     */
    public void setReviewUserRoleTypeId(long reviewUserRoleTypeId) {
        this.reviewUserRoleTypeId = reviewUserRoleTypeId;
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param name
     *            the new name
     */
    public void setName(String name) {
        this.name = name;
    }
}
