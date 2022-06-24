/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.searchcriteria;


/**
 * <p>
 * The search criteria instance for searching with 'review_payment' field.
 * </p>
 * @author TCSASSEMBLER
 * @since Pipeline Conversion Service Layer Assembly v1.0
 * @version 1.0
 */
public class ReviewCostSearchCriteria extends ContestsSearchCriteria {
    /** serial version UID. */
    private static final long serialVersionUID = -2710311979537806025L;

    /** The lower bound for the 'review_payment'. */
    private double reviewCostLowerBound;

    /** The upper bound for the 'review_payment'. */
    private double reviewCostUpperBound;

    /**
     * Creates a new ReviewCostSearchCriteria object.
     */
    public ReviewCostSearchCriteria() {
    }

    /**
     * Constructs the search criteria with the cost range.
     *
     * @param reviewCostLowerBound the lower bound
     * @param reviewCostUpperBound the upper bound
     */
    public ReviewCostSearchCriteria(double reviewCostLowerBound, double reviewCostUpperBound) {
        this.reviewCostLowerBound = reviewCostLowerBound;
        this.reviewCostUpperBound = reviewCostUpperBound;
    }

    /**
     * Constructs the where clause.
     */
    public String getWhereClause() {
        if (reviewCostLowerBound > reviewCostUpperBound) {
            return "";
        }

        return new StringBuffer("pinfo.reviewPayment <=").append(reviewCostUpperBound).append(" AND pinfo.reviewPayment >=")
                                                    .append(reviewCostLowerBound).toString();
    }

    /**
     * Overrides the toString to print the value.
     *
     * @return the string for this criteria
     */
    public String toString() {
        return "ReviewCostSearchCriteria[reviewCostLowerBound=" + reviewCostLowerBound + ", reviewCostUpperBound" +
        reviewCostUpperBound + "]";
    }
}
