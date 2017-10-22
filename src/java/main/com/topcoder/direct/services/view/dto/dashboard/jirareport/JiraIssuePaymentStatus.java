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
public enum JiraIssuePaymentStatus {

    /**
     * The not paid payment status.
     */
    NOT_PAID(0L, "Not Paid"),

    /**
     * The paid payment status.
     */
    PAID(1L, "Paid");


    /**
     * The id of the payment status.
     */
    private final long id;

    /**
     * The name of the payment status.
     */
    private final String name;

    /**
     * The JiraIssuePaymentStatus constructor.
     *
     * @param id
     * @param name
     */
    JiraIssuePaymentStatus(long id, String name) {
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
