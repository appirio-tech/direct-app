/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.jirareport;

/**
 * <p>
 * Enum for the jira issue payment status.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - JIRA issues loading update and report creation)
 */
public enum JiraIssueStatus {


    ACCEPTED(0L, "Accepted"),
	
	APPROVED(1L, "Approved"),
	
	CLOSED(2L, "Closed"),
	
	FORMAL_REVIEW(3L, "Formal Review"),
	
	HOLD_FOR_3RD_PARTY(4L, "Hold for 3rd Party"),
	
	HOLD_FOR_CUSTOMER(5L, "Hold for Customer"),
	
	HOLD_FOR_IT(6L, "Hold for I.T."),
	
	IN_PROGRESS(7L, "In Progress"),
	
	INFORMAL_REVIEW(8L, "Informal Review"),
	
	INFORMAL_REVIEW_PENDING(9L, "Informal Review Pending"),
	
	LIVE_DESIGN(10L, "Live Design"),
	
	LIVE_DEVELOPMENT(11L, "Live Development"),
	
	NEW_REQUEST(11L, "New Request"),
	
	ON_HOLD(12L, "On Hold"),
	
	OPEN(13L, "Open"),
	
	PREPPING(14L, "Prepping"),
	
	READY_TO_DEPLOY_TO_DEV(15L, "Ready to Deploy to DEV"),
	
	READY_TO_DEPLOY_TO_PROD(16L, "Ready to Deploy to PROD"),
	
	READY_TO_DEPLOY_TO_TEST(17L, "Ready to Deploy to TEST"),
	
	REOPENED(18L, "Reopened"),
	
	RESOLVED(19L, "Resolved"),
	
	STUCK(20L, "Stuck"),

    TESTING(21L, "Testing");
	


    /**
     * The id of the payment status.
     */
    private final long id;

    /**
     * The name of the payment status.
     */
    private final String name;

    /**
     * The JiraIssueStatus constructor.
     *
     * @param id
     * @param name
     */
    JiraIssueStatus(long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the payment status id.
     *
     * @return the payment status id.
     */
    public long getStatusId() {
        return this.id;
    }

    /**
     * Gets the payment status name.
     *
     * @return the payment status name.
     */
    public String getStatusName() {
        return this.name;
    }
}
