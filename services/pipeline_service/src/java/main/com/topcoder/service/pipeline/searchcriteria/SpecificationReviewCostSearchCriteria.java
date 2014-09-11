/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.searchcriteria;


/**
 * <p>
 * The search criteria instance for searching with 'specification_review_payment' field.
 * </p>
 *
 * @since Pipeline Conversion Service Layer Assembly v1.0
 */
public class SpecificationReviewCostSearchCriteria extends ContestsSearchCriteria {
    /** serial version UID. */
    private static final long serialVersionUID = 2618485343537678374L;

    /** The lower bound for the 'specification_review_payment'. */
    private double specificationReviewCostUpperBound;

    /** The lower bound for the 'specification_review_payment'. */
    private double specificationReviewCostLowerBound;

    /**
     * Creates a new SpecificationReviewCostSearchCriteria object.
     */
    public SpecificationReviewCostSearchCriteria() {
    }

    /**
     * Constructs the search criteria with the cost range.
     *
     * @param specificationReviewCostUpperBound the lower bound
     * @param specificationReviewCostLowerBound the upper bound
     */
    public SpecificationReviewCostSearchCriteria(double specificationReviewCostLowerBound,
        double specificationReviewCostUpperBound) {
        this.specificationReviewCostUpperBound = specificationReviewCostUpperBound;
        this.specificationReviewCostLowerBound = specificationReviewCostLowerBound;
    }

    /**
     * Constructs the where clause.
     */
    public String getWhereClause() {
        if (specificationReviewCostLowerBound > specificationReviewCostUpperBound) {
            return "";
        }

        return new StringBuffer("pinfo.specificationReviewPayment <=").append(specificationReviewCostUpperBound)
                                                                  .append(" AND pinfo.specificationReviewPayment >= ")
                                                                  .append(specificationReviewCostLowerBound).toString();
    }

    /**
     * Overrides the toString to print the value.
     *
     * @return the string for this criteria
     */
    public String toString() {
        return "SpecificationReviewCostSearchCriteria[specificationReviewCostLowerBound=" +
        specificationReviewCostLowerBound + ", specificationReviewCostUpperBound" + specificationReviewCostUpperBound +
        "]";
    }
}
