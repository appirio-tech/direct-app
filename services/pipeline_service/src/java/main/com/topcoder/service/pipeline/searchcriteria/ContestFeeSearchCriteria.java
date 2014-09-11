/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.searchcriteria;


/**
 * <p>
 * The search criteria instance for searching with 'contest_fee' field.
 * </p>
 */
public class ContestFeeSearchCriteria extends ContestsSearchCriteria {
    /** serial version UID. */
    private static final long serialVersionUID = -5754376845386850654L;

    /** The lower bound for the 'contest_fee'. */
    private double contestFeeLowerBound;

    /** The upper bound for the 'contest_fee'. */
    private double contestFeeUpperBound;

    /**
     * Creates a new ContestFeeSearchCriteria object.
     */
    public ContestFeeSearchCriteria() {
    }

    /**
     * <p>
     * Constructs with contest fee lower bound and upper bound.
     * </p>
     *
     * @param contestFeeLowerBound the lower bound
     * @param contestFeeUpperBound the upper bound, should not be less than lower bound
     */
    public ContestFeeSearchCriteria(double contestFeeLowerBound, double contestFeeUpperBound) {
        this.contestFeeLowerBound = contestFeeLowerBound;
        this.contestFeeUpperBound = contestFeeUpperBound;
    }

    /**
     * Constructs the where clause for the criteria.
     *
     * @return where clause, could be empty, not null
     */
    public String getWhereClause() {
        if (contestFeeUpperBound < contestFeeLowerBound) {
            return "";
        }

        return new StringBuffer("pinfo.contestFee <=").append(contestFeeUpperBound).append(" AND pinfo.contestFee >=")
                                                 .append(contestFeeLowerBound).toString();
    }

    /**
     * Overrides the toString to print the value.
     *
     * @return the string for this criteria
     */
    public String toString() {
        return "ContestFeeSearchCriteria[contestFeeLowerBound=" + contestFeeLowerBound + ", contestFeeUpperBound =" +
        contestFeeUpperBound + "]";
    }
}
