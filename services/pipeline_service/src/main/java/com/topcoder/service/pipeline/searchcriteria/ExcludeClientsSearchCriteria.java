/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.searchcriteria;


import java.util.List;


/**
 * <p>
 * The search criteria instance for searching contests that does not belongs to the given client list.
 * </p>
 * @author TCSASSEMBLER
 * @since Pipeline Conversion Service Layer Assembly v1.0
 * @version 1.0
 */
public class ExcludeClientsSearchCriteria extends ContestsSearchCriteria {
    /**
	 * serial version UID.
	 */
	private static final long serialVersionUID = -9039771654457679180L;
	/** The client list to exclude. */
    private List<String> clientsToExclude;

    /**
     * Creates a new ExcludeClientsSearchCriteria object.
     */
    public ExcludeClientsSearchCriteria() {
    }

    /**
     * Constructs the ExcludeClientsSearchCriteria with list of clients to exclude.
     *
     * @param clientsToExclude the list of clients
     */
    public ExcludeClientsSearchCriteria(List<String> clientsToExclude) {
        this.clientsToExclude = clientsToExclude;
    }

    /**
     * Constructs the where clause for the criteria.
     *
     * @return where clause, could be empty, not null
     */
    public String getWhereClause() {
        if ((clientsToExclude == null) || clientsToExclude.isEmpty()) {
            return "";
        }

        StringBuffer sb = new StringBuffer("pinfo.clientName NOT IN(");

        for (int i = 0; i < clientsToExclude.size(); i++) {
            if ((clientsToExclude.get(i) == null) || (clientsToExclude.get(i).trim().length() == 0)) {
                continue;
            }

            sb.append("'").append(clientsToExclude.get(i)).append("'");

            if (i != (clientsToExclude.size() - 1)) {
                sb.append(",");
            }
        }

        return sb.append(")").toString();
    }

    /**
     * Overrides the toString to print the value.
     *
     * @return the string for this criteria
     */
    public String toString() {
        StringBuffer sb = new StringBuffer("ExcludeClientsSearchCriteria[");

        if (clientsToExclude != null) {
            for (String c : this.clientsToExclude) {
                sb.append(c).append(",");
            }
        }

        return sb.append("]").toString();
    }
}
