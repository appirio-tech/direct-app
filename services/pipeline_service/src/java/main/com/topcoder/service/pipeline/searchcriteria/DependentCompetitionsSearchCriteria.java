/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.searchcriteria;


/**
 * <p>
 * The search criteria instance for searching with 'has_dependant_competitions' field.
 * </p>
 */
public class DependentCompetitionsSearchCriteria extends ContestsSearchCriteria {
    /** serial version UID. */
    private static final long serialVersionUID = -8480492199853633776L;

    /** The value for 'has_dependant_competitions' field. */
    private boolean hasDependentCompetitions;

    /**
     * Creates a new DependentCompetitionsSearchCriteria object.
     */
    public DependentCompetitionsSearchCriteria() {
    }

    /**
     * Constructs the criteria value.
     *
     * @param approvedbyClient the value for 'has_dependant_competitions' field.
     */
    public DependentCompetitionsSearchCriteria(boolean hasDependentCompetitions) {
        this.hasDependentCompetitions = hasDependentCompetitions;
    }

    /**
     * Constructs the where clause for the criteria, it will query the 'has_dependant_competitions' field.
     *
     * @return where clause, not empty or null
     */
    public String getWhereClause() {
        return new StringBuffer("pinfo.hasDependentCompetitions = ").append(hasDependentCompetitions).toString();
    }

    /**
     * Overrides the toString to print the value.
     *
     * @return the string for this criteria
     */
    public String toString() {
        return "DependentCompetitionsSearchCriteria[hasDependentCompetitions=" + hasDependentCompetitions + "]";
    }
}
