/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.searchcriteria;


/**
 * <p>
 * The search criteria instance for searching with 'was_reposted' field.
 * </p>
 *
 * @author TCSASSEMBLER
 * @since Pipeline Conversion Service Layer Assembly v1.0
 * @version 1.0
 */
public class RepostSearchCriteria extends ContestsSearchCriteria {
    /** serial version UID. */
    private static final long serialVersionUID = -5117158964637307891L;

    /** The value for 'was_reposted' field. */
    private boolean repost;

    /**
     * Creates a new RepostSearchCriteria object.
     */
    public RepostSearchCriteria() {
    }

    /**
     * Constructs the criteria with re post or not.
     *
     * @param approvedbyClient the value for 'was_reposted' field.
     */
    public RepostSearchCriteria(boolean repost) {
        this.repost = repost;
    }

    /**
     * Constructs the where clause for the criteria, it will query the 'was_reposted' field.
     *
     * @return where clause, not empty or null
     */
    public String getWhereClause() {
        return new StringBuffer("pinfo.rePost = ").append(repost).toString();
    }

    /**
     * Overrides the toString to print the value.
     *
     * @return the string for this criteria
     */
    public String toString() {
        return "RepostSearchCriteria[repost=" + repost + "]";
    }
}
