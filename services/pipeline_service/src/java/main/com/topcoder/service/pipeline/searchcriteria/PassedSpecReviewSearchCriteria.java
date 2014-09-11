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
public class PassedSpecReviewSearchCriteria extends ContestsSearchCriteria {
    /**
	 * serial version UID.
	 */
	private static final long serialVersionUID = -9056573599122630941L;
	/** The value for 'client_approval' field. */
    private boolean passedSpecReview;

    /**
     * Creates a new PassedSpecReviewSearchCriteria object.
     */
    public PassedSpecReviewSearchCriteria() {
    }

    /**
     * Constructs the criteria with pass sepc review or not.
     *
     * @param approvedbyClient the value for 'client_approval' field.
     */
    public PassedSpecReviewSearchCriteria(boolean passedSpecReview) {
        this.passedSpecReview = passedSpecReview;
    }

    /**
     * Constructs the where clause for the criteria, it will query the 'passed_spec_review' field.
     *
     * @return where clause, not empty or null
     */
    public String getWhereClause() {
        return new StringBuffer("pinfo.passedSpecReview=").append(passedSpecReview).toString();
    }

    /**
     * Overrides the toString to print the value.
     *
     * @return the string for this criteria
     */
    public String toString() {
        return "PassedSpecReviewSearchCriteria[passedSpecReview=" + passedSpecReview + "]";
    }
}
