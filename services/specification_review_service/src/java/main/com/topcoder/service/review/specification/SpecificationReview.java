/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification;

import java.io.Serializable;

import com.topcoder.management.review.data.Review;
import com.topcoder.management.scorecard.data.Scorecard;

/**
 * <p>
 * This class is a container for information about a single specification review. It holds
 * Review and Scorecard instances. It is a simple JavaBean (POJO) that provides getters
 * and setters for all private attributes and performs no argument validation in the
 * setters.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe.
 * </p>
 *
 * @author saarixx, myxgyy
 * @version 1.0
 */
public class SpecificationReview implements Serializable {
    /**
     * The generated serial version UID.
     */
    private static final long serialVersionUID = 7914204045976607777L;

    /**
     * <p>
     * The Review instance for this specification review.
     * </p>
     * <p>
     * Can be any value.
     * </p>
     * <p>
     * Has getter and setter.
     * </p>
     */
    private Review review;

    /**
     * <p>
     * The Scorecard instance for this specification review.
     * </p>
     * <p>
     * Can be any value.
     * </p>
     * <p>
     * Has getter and setter.
     * </p>
     */
    private Scorecard scorecard;
    
    /**
     * <p>
     * The creation user handle.
     * </p>
     * <p>
     * Can be any value.
     * </p>
     * <p>
     * Has getter and setter.
     * </p>
     */
    private String creationUserHandle;
    
    /**
     * Creates an instance of <code>SpecificationReview</code>.
     */
    public SpecificationReview() {
    }

    /**
     * Retrieves the <code>Review</code> instance for this specification review.
     *
     * @return the <code>Review</code> instance for this specification review.
     */
    public Review getReview() {
        return this.review;
    }

    /**
     * Sets the <code>Review</code> instance for this specification review.
     *
     * @param review
     *            the <code>Review</code> instance for this specification review.
     */
    public void setReview(Review review) {
        this.review = review;
    }

    /**
     * Retrieves the <code>Scorecard</code> instance for this specification review.
     *
     * @return the <code>Scorecard</code> instance for this specification review.
     */
    public Scorecard getScorecard() {
        return this.scorecard;
    }

    /**
     * Sets the <code>Scorecard</code> instance for this specification review.
     *
     * @param scorecard
     *            the <code>Scorecard</code> instance for this specification review.
     */
    public void setScorecard(Scorecard scorecard) {
        this.scorecard = scorecard;
    }
    
    /**
     * Get the creation user handle.
     *
     * @return the creation user handle.
     */    
    public String getCreationUserHandle() {
        return this.creationUserHandle;
    }
    
    /**
     * Set the creation user handle.
     *
     * @param cuh the creation user handle.
     */
    public void setCreationUserHandle(String cuh) {
        this.creationUserHandle = cuh;
    }
}
