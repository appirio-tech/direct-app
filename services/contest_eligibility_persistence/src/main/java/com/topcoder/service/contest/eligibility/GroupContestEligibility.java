/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibility;

/**
 * <p>
 * Represents the group contest eligibility entity.It extends from ContestEligibility.
 * </p>
 * <p>
 * <strong>Thread Safety</strong>: This class is not thread safe since it is mutable.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GroupContestEligibility extends ContestEligibility {

    /**
     * <p>
     * The serial version UID.
     * </p>
     */
    private static final long serialVersionUID = 5139057663728793256L;

    /**
     * <p>
     * Represents the group id.It is used to indicate which group is eligible.
     * </p>
     */
    private long groupId;

    /**
     * Default constructor.
     */
    public GroupContestEligibility() {
        // empty.
    }

    /**
     * Returns the groupId.
     *
     * @return the groupId.
     */
    public long getGroupId() {
        return groupId;
    }

    /**
     * Sets the groupId with the specified value.
     *
     * @param groupId
     *            the groupId to set.
     */
    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
}