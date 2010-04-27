/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

/**
 * <p>
 * An enumeration over the possible types of statuses of the contests.
 * </p>
 * <p>
 * Version 1.1 - Direct Search Assembly - adjust action value to Edit or View
 * </p>
 *
 * @author isv, BeBetter
 * @version 1.1
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
     * <p>A <code>ContestStatus</code> corresponding to <code>Active</code> contest status.</p>
     */
    INACTIVE("Inactive", "inactive", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Deleted</code> contest status.</p>
     */
    DELETED("Deleted", "deleted", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Milestone</code> contest status.</p>
     */
    MILESTONE("Milestone", "running", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Awarding</code> contest status.</p>
     */
    AWARDING("Awarding", "running", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Review</code> contest status.</p>
     */
    REVIEW("Review", "running", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Active</code> contest status.</p>
     */
    ACTIVE("Active", "running", "View"),

     /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Action Required</code> contest status.</p>
     */
    ACTION_REQUIRED("Action Required", "running", "View"),

    /**
     * <p>
     * A <code>ContestStatus</code> corresponding to <code>Completed</code> contest status.
     * </p>
     */
    COMPLETED("Completed", "completed", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Scheduled</code> contest status.</p>
     */
    SCHEDULED("Scheduled", "scheduled", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Scheduled</code> contest status.</p>
     */
    IN_DANGER("In Danger", "running", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>No Submissions - Repost</code> contest status.</p>
     */
    NO_SUBMISSIONS_REPOST("No Submissions - Repost", "running", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Unactive - Not Yet Published</code> contest status.</p>
     */
    UNACTIVE("Unactive - Not Yet Published", "draft", "Edit"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Active - Public</code> contest status.</p>
     */
    ACTIVE_PUBLIC("Active - Public", "running", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>No Winner Chosen</code> contest status.</p>
     */
    NO_WINNER_CHOSEN("No Winner Chosen", "completed", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Insufficient Submissions - ReRun Possible</code> contest status.</p>
     */
    INSUFFICIENT_SUBMISSIONS("Insufficient Submissions - ReRun Possible", "finished", "View"),

     /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Extended</code> contest status.</p>
     */
    EXTENDED("Extended", "running", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Abandoned</code> contest status.</p>
     */
    ABANDONED("Abandoned", "abandoned", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Cancelled</code> contest status.</p>
     */
    CANCELLED("Cancelled", "cancelled", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Registration</code> contest status.</p>
     */
    REGISTRATION("Registration", "running", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Submission</code> contest status.</p>
     */
    SUBMISSION("Submission", "running", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Screening</code> contest status.</p>
     */
    SCREENING("Screening", "running", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Appeals</code> contest status.</p>
     */
    APPEALS("Appeals", "running", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Appeals Response</code> contest status.</p>
     */
    APPEALS_RESPONSE("Appeals Response", "running", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Aggregation</code> contest status.</p>
     */
    AGGREGATION("Aggregation", "running", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Aggregation Review</code> contest status.</p>
     */
    AGGREGATION_REVIEW("Aggregation Review", "running", "View"),

     /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Final Fix</code> contest status.</p>
     */
    FINAL_FIX("Final Fix", "running", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Final Review</code> contest status.</p>
     */
    FINAL_REVIEW("Final Review", "running", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Approval</code> contest status.</p>
     */
    APPROVAL("Approval", "running", "View"),

     /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Cancelled - Failed Review</code> contest status.</p>
     */
    CANCELLED_FAILED_REVIEW("Cancelled - Failed Review", "cancelled", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Cancelled - Failed Screening</code> contest status.</p>
     */
    CANCELLED_FAILED_SCREENING("Cancelled - Failed Screening", "cancelled", "View"),

     /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Cancelled - Zero Submissions</code> contest status.</p>
     */
    CANCELLED_ZERO_SUBMISSION("Cancelled - Zero Submissions", "cancelled", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Cancelled - Winner Unresponsive</code> contest status.</p>
     */
    CANCELLED_WINNER_UNRESPONSIVE("Cancelled - Winner Unresponsive", "cancelled", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Cancelled - Client Request</code> contest status.</p>
     */
    CANCELLED_CLIENT_REQUEST("Cancelled - Client Request", "cancelled", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Cancelled - Requirements Infeasible</code> contest status.</p>
     */
    CANCELLED_REQUIREMENTS_INFEASIBLE("Cancelled - Requirements Infeasible", "cancelled", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Cancelled - Zero Registrations</code> contest status.</p>
     */
    CANCELLED_ZERO_REGISTRATION("Cancelled - Zero Registrations", "cancelled", "View"),

     /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Terminated</code> contest status.</p>
     */
    TERMINATED("Terminated", "terminated", "View"),

    /**
     * <p>A <code>ContestStatus</code> corresponding to <code>Failed Review</code> contest status.</p>
     */
    FAILED_REVIEW("Failed Review", "finished", "View");

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

    /**
     * <p>Gets the <code>ContestStatus</code> instance matching the specified name.</p>
     *
     * @param name a <code>String</code> providing the name for requested contest status.
     * @return an <code>ContestStatus</code> matching the specified name or <code>null</code> if there is none.
     */
    public static ContestStatus forName(String name) {
        ContestStatus[] statuses = ContestStatus.values();
        for (int i = 0; i < statuses.length; i++) {
            ContestStatus status = statuses[i];
            if (status.getName().equalsIgnoreCase(name)) {
                return status;
            }
        }
        return DRAFT;
    }
}
