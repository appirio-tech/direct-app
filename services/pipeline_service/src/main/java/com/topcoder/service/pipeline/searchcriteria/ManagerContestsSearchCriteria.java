/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.searchcriteria;


/**
 * <p>
 * The criteria instance with the name for the role 'manager'.
 * </p>
 * @author TCSASSEMBLER
 * @since Pipeline Conversion Service Layer Assembly v1.0
 * @version 1.0
 */
public class ManagerContestsSearchCriteria extends ContestsSearchCriteria {
    /** serial version UID. */
    private static final long serialVersionUID = -836314455608929289L;

    /**
     * <p>
     * The value of the criteria: the name of manager.
     * </p>
     */
    private String manager;

    /**
     * Creates a new ManagerContestsSearchCriteria object.
     */
    public ManagerContestsSearchCriteria() {
    }

    /**
     * Constructs with the name of manager.
     *
     * @param architect the value of manager
     */
    public ManagerContestsSearchCriteria(String manager) {
        this.manager = manager;
    }

    /**
     * <p>
     * Constructs the where clause, it will query the 'resource_role_lu' and 'resource' table.
     * </p>
     *
     * @return the where clause will be empty if the query value is empty or null
     */
    public String getWhereClause() {
        if ((manager == null) || (manager.trim().length() == 0)) {
            return "";
        }

        return new StringBuffer("resource.resourceRole.name = 'manager' AND resource.name = '")
                                                                    .append(manager).append("'").toString();
    }

    /**
     * Overrides the toString to print the value.
     *
     * @return the string for this criteria
     */
    public String toString() {
        return "ManagerContestsSearchCriteria[manager=" + manager + "]";
    }
}
