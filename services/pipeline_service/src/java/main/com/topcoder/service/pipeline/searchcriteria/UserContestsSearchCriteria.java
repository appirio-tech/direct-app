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
public class UserContestsSearchCriteria extends ContestsSearchCriteria {
    /** serial version UID. */
    private static final long serialVersionUID = 5376690852733456455L;

    /** The value of username. */
    private String username;

    /**
     * Creates a new UserContestsSearchCriteria object.
     */
    public UserContestsSearchCriteria() {
    }

    /**
     * Constructs with user name.
     *
     * @param username the value of user name.
     */
    public UserContestsSearchCriteria(String username) {
        this.username = username;
    }

    /**
     * Constructs the where clause for the criteria.
     *
     * @return where clause, could be empty, not null
     */
    public String getWhereClause() {
        if ((username == null) || (username.trim().length() == 0)) {
            return "";
        }

        return new StringBuffer("pinfo.clientName = '").append(username)
                               .append("' OR resource.name = '").append(username).append("'").toString();
    }

    /**
     * Overrides the toString to print the value.
     *
     * @return the string for this criteria
     */
    public String toString() {
        return "UserContestsSearchCriteria[username=" + username + "]";
    }
}
