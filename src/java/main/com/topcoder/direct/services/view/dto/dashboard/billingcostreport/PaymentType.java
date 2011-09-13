/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.billingcostreport;

/**
 * <p/>
 * An enumeration over the payments types used when billing a contest.
 *
 * @author Blues
 * @version 1.0 (TC Cockpit Billing Cost Report Assembly)
 */
public enum PaymentType {
    /**
     * Contest fee payment type.
     */
    CONTEST_FEE(1, "Contest Fee"),

    /**
     * Prizes payment type.
     */
    PRIZES(2, "Winner"),

    /**
     * Spec review payment type.
     */
    SPEC_REVIEW(3, "Spec Review"),

    /**
     * Review payment type.
     */
    REVIEW(4, "Review"),

    /**
     * Reliability payment type.
     */
    RELIABILITY(5, "Reliability"),

    /**
     * Digital run payment type.
     */
    DIGITAL_RUN(6, "Digital Run"),

    /**
     * Copilot payment type.
     */
    COPILOT(7, "Copilot"),

    /**
     * Build payment type.
     */
    BUILD(8, "Build"),

    /**
     * Bugs payment type.
     */
    BUGS(9, "Bugs"),

    /**
     * Misc payment type.
     */
    MISC(10, "Misc");

    /**
     * The id of the payment type.
     */
    private long id;

    /**
     * The description of the payment type.
     */
    private String description;

    /**
     * Private constructor for initializing the payment type enum.
     *
     * @param id the id of the payment type.
     * @param description the description of the payment type.
     */
    private PaymentType(long id, String description) {
        this.id = id;
        this.description = description;
    }

    /**
     * Gets the id of the payment type.
     *
     * @return the id of the payment type.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id of the payment type.
     *
     * @param id the if of the payment type.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the description of the payment type.
     *
     * @return the description of the payment type.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the payment type.
     *
     * @param description the description of payment type.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
