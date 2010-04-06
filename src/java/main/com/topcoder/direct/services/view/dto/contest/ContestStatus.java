/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

/**
 * <p>An enumeration over the possible types of statuses of the contests.</p>
 *
 * @author isv
 * @version 1.0
 */
public enum ContestStatus {

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Draft</code> contest status.</p>
     */
    DRAFT("Draft", "draft", "Edit"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Running</code> contest status.</p>
     */
    RUNNING("Running", "running", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Milestone</code> contest status.</p>
     */
    MILESTONE("Milestone", "milestone", "Milestone"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Awarding</code> contest status.</p>
     */
    AWARDING("Awarding", "awarding", "Awarding"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Review</code> contest status.</p>
     */
    REVIEW("Review", "review", "Review"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Failed Review</code> contest status.</p>
     */
    FAILED_REVIEW("Failed Review", "failedReview", "Failed Review");

    /**
     * <p>A <code>String</code> providing the state name. Such a name serves as a textual presentation of the status.
     * </p>
     */
    private String name;

    /**
     * <p>A <code>String</code> providing the status short name. Such a short name serves as an ID of the status.</p>
     */
    private String shortName;

    /**
     * <p>A <code>String</code> providing the textual description of the action to be performed by user in context of
     * this contest status.</p>
     */
    private String actionText;

    /**
     * <p>Constructs new <code>ContestStatus</code> instance with specified parameters.</p>
     *
     * @param name a <code>String</code> providing the status name. Such a name serves as a textual presentation of
     *        the status.
     * @param shortName a <code>String</code> providing the status short name. Such a short name serves as an ID of the
     *        status.
     * @param actionText a <code>String</code> providing the textual description of the action performed by user in
     *        context of this activity.
     */
    private ContestStatus(String name, String shortName, String actionText) {
        this.name = name;
        this.shortName = shortName;
        this.actionText = actionText;
    }

    /**
     * <p>Gets the name of this status.</p>
     *
     * @return a <code>String</code> providing the status name. Such a name serves as a textual presentation of the
     *         status.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Gets the short name of this status.</p>
     *
     * @return a <code>String</code> providing the status short name. Such a short name serves as an ID of the status.
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * <p>Gets the action text of this activity.</p>
     *
     * @return a <code>String</code> providing the textual description of the action performed by user in context of
     *         this activity.
     */
    public String getActionText() {
        return actionText;
    }
}
