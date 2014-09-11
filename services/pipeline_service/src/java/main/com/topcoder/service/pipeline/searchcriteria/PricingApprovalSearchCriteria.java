/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.searchcriteria;


/**
 * <p>
 * The search criteria instance for searching with 'passed_spec_review' field.
 * </p>
 * @author TCSASSEMBLER
 * @since Pipeline Conversion Service Layer Assembly v1.0
 * @version 1.0
 */
public class PricingApprovalSearchCriteria extends ContestsSearchCriteria {
    /** serial version UID. */
    private static final long serialVersionUID = -1786329425561801572L;

    /** The value for 'pricing_approval' field. */
    private boolean priceApproved;

    /**
     * Creates a new PricingApprovalSearchCriteria object.
     */
    public PricingApprovalSearchCriteria() {
    }

    /**
     * Constructs the criteria with price approved or not.
     *
     * @param approvedbyClient the value for 'pricing_approval' field.
     */
    public PricingApprovalSearchCriteria(boolean priceApproved) {
        this.priceApproved = priceApproved;
    }

    /**
     * Constructs the where clause for the criteria, it will query the 'pricing_approval' field.
     *
     * @return where clause, not empty or null
     */
    public String getWhereClause() {
        return new StringBuffer("pinfo.pricingApproval = ").append(priceApproved).toString();
    }

    /**
     * Overrides the toString to print the value.
     *
     * @return the string for this criteria
     */
    public String toString() {
        return "PricingApprovalSearchCriteria[priceApproved=" + priceApproved + "]";
    }
}
