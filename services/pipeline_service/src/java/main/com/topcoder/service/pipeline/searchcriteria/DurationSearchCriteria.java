/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.searchcriteria;


/**
 * <p>
 * The search criteria instance for searching with studio#contest#'start_time'/'end_time' fields.
 * </p>
 */
public class DurationSearchCriteria extends ContestsSearchCriteria {
    /** serial version UID. */
    private static final long serialVersionUID = -2052187771976002939L;

    /** The duration lower bound. */
    private int contestDurationLowerBound;

    /** The duration upper bound. */
    private int contestDurationUpperBound;

    /**
     * Creates a new DurationSearchCriteria object.
     */
    public DurationSearchCriteria() {
    }

    /**
     * <p>
     * Constructs the criteria with lower bound and upper bound.
     * </p>
     *
     * @param contestDurationLowerBound the lower bound
     * @param contestDurationUpperBound the upper bound
     */
    public DurationSearchCriteria(int contestDurationLowerBound, int contestDurationUpperBound) {
        this.contestDurationLowerBound = contestDurationLowerBound;
        this.contestDurationUpperBound = contestDurationUpperBound;
    }

    /**
     * Constructs the where clause for the criteria.
     *
     * @return where clause, could be empty, not null
     */
    public String getWhereClause() {
        if (contestDurationLowerBound > contestDurationUpperBound) {
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
        return "DurationSearchCriteria[contestDurationLowerBound=" + contestDurationLowerBound +
        ", contestDurationUpperBound" + contestDurationUpperBound + "]";
    }
}
