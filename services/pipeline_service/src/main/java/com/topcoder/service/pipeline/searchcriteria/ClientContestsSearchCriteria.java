/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.searchcriteria;


/**
 * <p>
 * The search criteria instance for searching with 'client' field.
 * </p>
 */
public class ClientContestsSearchCriteria extends ContestsSearchCriteria {
    /** serial version UID. */
    private static final long serialVersionUID = -7568243607828133885L;

    /** The value for the client. */
    private String client;

    /**
     * Default ctor.
     */
    public ClientContestsSearchCriteria() {
    }

    /**
     * Constructs the criteria with client name.
     *
     * @param client the value for 'client' field.
     */
    public ClientContestsSearchCriteria(String client) {
        this.client = client;
    }

    /**
     * Constructs the where clause for the criteria, it will query the 'client' field.
     *
     * @return where clause, could be empty if the client is empty or null
     */
    public String getWhereClause() {
        if ((client == null) || (client.trim().length() == 0)) {
            return "";
        }

        return new StringBuffer("pinfo.clientName = '").append(client).append("'").toString();
    }

    /**
     * Overrides the toString to print the value.
     *
     * @return the string for this criteria
     */
    public String toString() {
        return "ClientContestsSearchCriteria[client=" + client + "]";
    }
}
