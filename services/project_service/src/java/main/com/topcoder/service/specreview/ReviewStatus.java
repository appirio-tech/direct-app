/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.specreview;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Represents the Review Status. Possible values are: PENDING, PASSED, FAILED
 * 
 * @author snow01
 * @version 1.0
 * @since Cockpit Launch Contest - Inline Spec Review Part 2
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reviewStatus", propOrder = { "reviewStatusId", "name" })
public class ReviewStatus implements Serializable {

    /**
     * Default Serial Version Id.
     */
    private static final long serialVersionUID = 1L;

    /** The review status id. */
    private long reviewStatusId;

    /** The name. */
    private String name;

    /**
     * Instantiates a new review status.
     */
    public ReviewStatus() {
    }

    /**
     * Gets the review status id.
     * 
     * @return the review status id
     */
    public long getReviewStatusId() {
        return reviewStatusId;
    }

    /**
     * Sets the review status id.
     * 
     * @param reviewStatusId
     *            the new review status id
     */
    public void setReviewStatusId(long reviewStatusId) {
        this.reviewStatusId = reviewStatusId;
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
