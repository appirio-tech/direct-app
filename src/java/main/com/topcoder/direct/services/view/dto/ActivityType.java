/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto;

/**
 * <p>An enumeration over the possible types of activities on the projects.</p>
 *
 * @author isv
 * @version 1.0
 */
public enum ActivityType {

    /**
     * <p>An <code>ActivityType</code> corresponding to <code>Pending Review</code> activity type.</p>
     */
    REVIEW_PENDING("Review Pending", "reviewPending", "Posted By"),

    /**
     * <p>An <code>ActivityType</code> corresponding to <code>Review Complete</code> activity type.</p>
     */
    REVIEW_COMPLETE("Review Complete", "reviewComplete", "Posted By"),

    /**
     * <p>An <code>ActivityType</code> corresponding to <code>Forum Post</code> activity type.</p>
     */
    FORUM_POST("Forum Post", "forumPost", "Posted By"),

    /**
     * <p>An <code>ActivityType</code> corresponding to <code>Task</code> activity type.</p>
     */
    TASK("Task", "task", "Completed By"),

    /**
     * <p>An <code>ActivityType</code> corresponding to <code>Specification Posted</code> activity type.</p>
     */
    SPEC_POSTED("Spec Posted", "specPosted", "Completed By"),

    /**
     * <p>An <code>ActivityType</code> corresponding to <code>Contest Launch</code> activity type.</p>
     */
    CONTEST_LAUNCH("Contest Launch", "contestLaunch", "Posted By"),

    /**
     * <p>An <code>ActivityType</code> corresponding to <code>Contest Launched</code> activity type.</p>
     */
    CONTEST_LAUNCHED("Contest Launched", "contestLaunch", "Posted By"),

    /**
     * <p>An <code>ActivityType</code> corresponding to <code>Contest Launched</code> activity type.</p>
     */
    CONTEST_COMPLETED("Contest Completed", "contestLaunch", "Posted By"),

     /**
     * <p>An <code>ActivityType</code> corresponding to <code>Contest Launched</code> activity type.</p>
     */
    PICK_WINNER("Pick WInners", "task", "Completed By"),

    /**
     * <p>An <code>ActivityType</code> corresponding to <code>Spec Review</code> activity type.</p>
     */
    SPEC_REVIEW("Spec Review", "reviewPending", "Completed By");

    /**
     * <p>A <code>String</code> providing the activity name. Such a name serves as a textual presentation of the
     * activity.</p>
     */
    private String name;

    /**
     * <p>A <code>String</code> providing the activity shortName. Such a short name serves as an ID of the activity.</p>
     */
    private String shortName;

    /**
     * <p>A <code>String</code> providing the textual description of the action performed by user in context of this
     * activity.</p>
     */
    private String actionText;

    /**
     * <p>Constructs new <code>ActivityType</code> instance with specified parameters.</p>
     *
     * @param name a <code>String</code> providing the activity name. Such a name serves as a textual presentation of
     *        the activity.
     * @param shortName a <code>String</code> providing the activity shortName. Such a short name serves as an ID of the
     *        activity.
     * @param actionText a <code>String</code> providing the textual description of the action performed by user in
     *        context of this activity.
     */
    private ActivityType(String name, String shortName, String actionText) {
        this.name = name;
        this.shortName = shortName;
        this.actionText = actionText;
    }

    /**
     * <p>Gets the name of this activity.</p>
     *
     * @return a <code>String</code> providing the activity name. Such a name serves as a textual presentation of the
     *         activity.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Gets the short name of this activity.</p>
     *
     * @return a <code>String</code> providing the activity shortName. Such a short name serves as an ID of the
     *         activity.
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
     * <p>Gets the <code>ActivityType</code> instance matching the specified name.</p>
     *
     * @param name a <code>String</code> providing the name for requested activity type.
     * @return an <code>ActivityType</code> matching the specified name or <code>null</code> if there is none.  
     */
    public static ActivityType forName(String name) {
        String value = (name == null) ? "" : name.trim();
        ActivityType[] types = ActivityType.values();
        for (int i = 0; i < types.length; i++) {
            ActivityType type = types[i];
            if (type.getName().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return SPEC_POSTED;
    }
}
