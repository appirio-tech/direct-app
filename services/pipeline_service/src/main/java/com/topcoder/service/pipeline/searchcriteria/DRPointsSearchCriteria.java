/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.searchcriteria;


/**
 * <p>
 * The search criteria instance for searching contests in the given dr points range. field.
 * </p>
 */
public class DRPointsSearchCriteria extends ContestsSearchCriteria {
    /** serial version UID. */
    private static final long serialVersionUID = -138810641638842622L;

    /** The dr points lower bound for the criteria. */
    private int drPointsLowerBound;

    /** The dr points upper bound for the criteria. */
    private int drPointsUpperBound;

    /**
     * Creates a new DRPointsSearchCriteria object.
     */
    public DRPointsSearchCriteria() {
    }

    /**
     * Constructs the criteria with dr points range
     *
     * @param drPointsLowerBound the lower bound of dr points
     * @param drPointsUpperBound the upper bound of dr points
     */
    public DRPointsSearchCriteria(int drPointsLowerBound, int drPointsUpperBound) {
        this.drPointsLowerBound = drPointsLowerBound;
        this.drPointsUpperBound = drPointsUpperBound;
    }

    /**
     * Constructs the where clause for the criteria.
     *
     * @return where clause, could be empty, not null
     */
    public String getWhereClause() {
        if (drPointsLowerBound > drPointsUpperBound) {
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
        return "DRPointsSearchCriteria[drPointsLowerBound=" + drPointsLowerBound + ", drPointsUpperBound" +
        drPointsUpperBound + "]";
    }
}
