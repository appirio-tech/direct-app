/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.searchcriteria;



/**
 * <p>
 * The search criteria instance used for searching the contests.
 * </p>
 * @author TCSASSEMBLER
 * @since Pipeline Conversion Service Layer Assembly v1.0
 * @version 1.0
 */
public class SecondPrizeSearchCriteria extends ContestsSearchCriteria {
    /** serial version UID. */
    private static final long serialVersionUID = 8376203851542500788L;

    /** The lower bound. */
    private double lowerBoundPrize;

    /** The upper bound. */
    private double upperBoundPrize;

    /**
     * Creates a new SecondPrizeSearchCriteria object.
     */
    public SecondPrizeSearchCriteria() {
    }

    /**
     * Constructs the criteria with lower bound and upper bound.
     *
     * @param loweBoundPrize the lower bound prize
     * @param upperBoundPrize the upper bound prize
     */
    public SecondPrizeSearchCriteria(double lowerBoundPrize, double upperBoundPrize) {
        this.lowerBoundPrize = lowerBoundPrize;
        this.upperBoundPrize = upperBoundPrize;
    }

    /**
     * Constructs the where clause for the criteria.
     *
     * @return where clause, could be empty, not null
     */
    public String getWhereClause() {
        if (lowerBoundPrize > upperBoundPrize) {
            return "";
        }

		return "";
    }

    /**
     * Overrides the toString to print the value.
     *
     * @return the string for this criteria
     */
    public String toString() {
        return "SecondPrizeSearchCriteria[upperBoundPrize=" + upperBoundPrize + ", lowerBoundPrize" + lowerBoundPrize +
        "]";
    }
}
