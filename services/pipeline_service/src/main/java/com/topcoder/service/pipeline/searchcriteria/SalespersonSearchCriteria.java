/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.searchcriteria;


/**
 * <p>The criteria instance with the name for the role 'salesperson'.</p>
 * @author TCSASSEMBLER
 * @since Pipeline Conversion Service Layer Assembly v1.0
 * @version 1.0
 */
public class SalespersonSearchCriteria extends ContestsSearchCriteria {
    /** serial version UID. */
    private static final long serialVersionUID = -8444514511528223224L;

    /**
     * <p>
     * The value of the criteria: the name of salesperson.
     * </p>
     */
    private String salesperson;

    /**
     * Creates a new SalespersonSearchCriteria object.
     */
    public SalespersonSearchCriteria() {
    }

    /**
     * Creates a new SalespersonSearchCriteria object.
     *
     * @param reviewer the value of salesperson
     */
    public SalespersonSearchCriteria(String salesperson) {
        this.salesperson = salesperson;
    }

    /**
     * <p>
     * Constructs the where clause, it will query the 'resource_role_lu' and 'resource' table.
     * </p>
     *
     * @return the where clause will be empty if the query value is empty or null
     */
    public String getWhereClause() {
        if ((salesperson == null) || (salesperson.trim().length() == 0)) {
            return "";
        }

        return new StringBuffer("resource.resourceRole.name = 'salesperson' AND resource.name = '")
                                                                        .append(salesperson).append("'").toString();
    }

    /**
     * Overrides the toString to print the value.
     *
     * @return the string for this criteria
     */
    public String toString() {
        return "SalespersonSearchCriteria[salesperson=" + salesperson + "]";
    }
}
