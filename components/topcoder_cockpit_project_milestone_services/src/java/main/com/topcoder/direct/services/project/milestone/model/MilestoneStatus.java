/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone.model;

/**
 * <p>
 * This enum represents an enumeration of types of milestone statuses.
 * </p>
 * <p>
 * <b>Thread Safety:</b> It's immutable and thread safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public enum MilestoneStatus {
    /**
     * Represents the enum value of the an overdue status.
     */
    OVERDUE,

    /**
     * Represents the enum value of an upcoming status.
     */
    UPCOMING,

    /**
     * Represents the enum value of a completed status.
     */
    COMPLETED
}
