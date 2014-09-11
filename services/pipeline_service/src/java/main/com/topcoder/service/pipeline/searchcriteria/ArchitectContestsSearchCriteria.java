/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.searchcriteria;


/**
 * The criteria instance with the name for the role 'architect'.
 */
public class ArchitectContestsSearchCriteria extends ContestsSearchCriteria {
    /** serial version UID. */
    private static final long serialVersionUID = 6791862515611725367L;

    /**
     * <p>
     * The value of the criteria: the name of architect.
     * </p>
     */
    private String architect;

    /**
     * Default ctor.
     */
    public ArchitectContestsSearchCriteria() {
    }

    /**
     * Constructs with the name of architect.
     *
     * @param architect the value of architect
     */
    public ArchitectContestsSearchCriteria(String architect) {
        this.architect = architect;
    }

    /**
     * <p>
     * Constructs the where clause, it will query the 'resource_role_lu' and 'resource' table.
     * </p>
     *
     * @return the where clause will be empty if the query value is empty or null
     */
    public String getWhereClause() {
        if ((architect == null) || (architect.trim().length() == 0)) {
            return "";
        }

        return new StringBuffer("resource.resourceRole.name = 'architect' AND resource.name = '")
                                                                      .append(architect).append("'").toString();
    }

    /**
     * Overrides the toString to print the value.
     *
     * @return the string for this criteria
     */
    public String toString() {
        return "ArchitectContestsSearchCriteria[architect=" + architect + "]";
    }
}
