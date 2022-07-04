/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.searchcriteria;


/**
 * The criteria instance with the name for the role 'reviewer'.
 *
 * @author TCSASSEMBLER
 * @since Pipeline Conversion Service Layer Assembly v1.0
 * @version 1.0
 */
public class ReviewerContestsSearchCriteria extends ContestsSearchCriteria {
    /** serial version UID. */
    private static final long serialVersionUID = -1720064558839164600L;

    /**
     * <p>
     * The value of the criteria: the name of reviewer.
     * </p>
     */
    private String reviewer;

    /**
     * Creates a new ReviewerContestsSearchCriteria object.
     */
    public ReviewerContestsSearchCriteria() {
    }

    /**
     * Creates a new ReviewerContestsSearchCriteria object.
     *
     * @param reviewer the value of reviewer
     */
    public ReviewerContestsSearchCriteria(String reviewer) {
        this.reviewer = reviewer;
    }

    /**
     * <p>
     * Constructs the where clause, it will query the 'resource_role_lu' and 'resource' table.
     * </p>
     *
     * @return the where clause will be empty if the query value is empty or null
     */
    public String getWhereClause() {
        if ((reviewer == null) || (reviewer.trim().length() == 0)) {
            return "";
        }

        return new StringBuffer("resource.resourceRole.name = 'reviewer' AND resource.name = '")
                                                                         .append(reviewer).append("'").toString();
    }

    /**
     * Overrides the toString to print the value.
     *
     * @return the string for this criteria
     */
    public String toString() {
        return "ReviewerContestsSearchCriteria[reviewer=" + reviewer + "]";
    }
}
