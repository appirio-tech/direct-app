/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.searchcriteria;


/**
 * <p>
 * The search criteria instance for searching with 'client_approval' field.
 * </p>
 */
public class ClientApprovalSearchCriteria extends ContestsSearchCriteria {
    /** serial version UID. */
    private static final long serialVersionUID = -4517492925550698922L;

    /** The value for 'client_approval' field. */
    private boolean approvedByClient;

    /**
     * Default ctor.
     */
    public ClientApprovalSearchCriteria() {
    }

    /**
     * Constructs the criteria with approved by client or not.
     *
     * @param approvedbyClient the value for 'client_approval' field.
     */
    public ClientApprovalSearchCriteria(boolean approvedbyClient) {
        this.approvedByClient = approvedbyClient;
    }

    /**
     * Constructs the where clause for the criteria, it will query the 'client_approval' field.
     *
     * @return where clause, not empty or null
     */
    public String getWhereClause() {
        return new StringBuffer("pinfo.clientApproval = ").append(approvedByClient).toString();
    }

    /**
     * Overrides the toString to print the value.
     *
     * @return the string for this criteria
     */
    public String toString() {
        return "ClientApprovalSearchCriteria[approvedByClient=" + approvedByClient + "]";
    }
}
