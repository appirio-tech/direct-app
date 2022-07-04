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
public class NotContestsSearchCriteria extends ContestsSearchCriteria {
    /** serial version UID. */
    private static final long serialVersionUID = 7955033688455705243L;

    /** The given criteria for this criteria. */
    private ContestsSearchCriteria searchCriteria;

    /**
     * Creates a new NotContestsSearchCriteria object.
     */
    public NotContestsSearchCriteria() {
    }

    /**
     * Constructs the NOT criteria with given criteria.
     *
     * @param searchCriteria the criteria.
     */
    public NotContestsSearchCriteria(ContestsSearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    /**
     * Returns the value of searchCriteria.
     *
     * @return the searchCriteria
     */
    public ContestsSearchCriteria getSearchCriteria() {
        return searchCriteria;
    }

    /**
     * Constructs the where clause for the criteria.
     *
     * @return where clause, could be empty, not null
     */
    public String getWhereClause() {
        if ((searchCriteria == null) || (searchCriteria.getWhereClause().trim().length() == 0)) {
            return "";
        }

        return new StringBuffer(" NOT (").append(searchCriteria.getWhereClause()).append(")").toString();
    }

    /**
     * Overrides the toString to print the value.
     *
     * @return the string for this criteria
     */
    public String toString() {
        return "NotContestsSearchCriteria[criteria=" + searchCriteria.toString() + "]";
    }
}
