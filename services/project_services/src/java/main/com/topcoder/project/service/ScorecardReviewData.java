/*
 * Copyright (C) 2006-2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service;

import java.io.Serializable;

import com.topcoder.management.review.data.Review;
import com.topcoder.management.scorecard.data.Scorecard;

/**
 * <p>
 * This class is a DTO assembled to transfer the data regarding scorecard and review.
 * </p>
 * <p>
 * This entity follows java bean conventions for defining setters and getters for these properties.
 * Two constructors are also provided for convenient usage: A simple empty constructor and another to
 * set all fields in this class.
 * </p>
 *
 * <p>
 * Thread Safety: This class is mutable and not thread safe.
 * </p>
 *
 * @author pulky
 * @version 1.0 (Cockpit Spec Review Backend Service Update v1.0)
 */
@SuppressWarnings("serial")
public class ScorecardReviewData implements Serializable {

    /**
     * <p>
     * Represents the scorecard entity
     * </p>
     */
    private Scorecard scorecard;

    /**
     * <p>
     * Represents the review entity
     * </p>
     */
    private Review review;

    /**
     * <p>
     * Simple empty constructor
     * </p>
     */
    public ScorecardReviewData() {
        super();
    }

    /**
     * <p>
     * Constructs an instance of this class with given scorecard and review
     * </p>
     *
     * @param scorecard the scorecard to set
     * @param review the review to set
     */
    public ScorecardReviewData(Scorecard scorecard, Review review) {
        super();
        this.scorecard = scorecard;
        this.review = review;
    }

    /**
     * <p>
     * Gets the scorecard field value.
     * </p>
     *
     * @return The scorecard field value
     */
    public Scorecard getScorecard() {
        return this.scorecard;
    }

    /**
     * <p>
     * Sets the scorecard field value.
     * </p>
     *
     * @param scorecard The scorecard field value
     */
    public void setScorecard(Scorecard scorecard) {
        this.scorecard = scorecard;
    }

    /**
     * <p>
     * Gets the review field value.
     * </p>
     * @return The review field value
     */
    public Review getReview() {
        return this.review;
    }

    /**
     * <p>
     * Sets the review field value.
     * </p>
     *
     * @param review The reviewfield value
     */
    public void setReview(Review review) {
        this.review = review;
    }
}
