/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.task.project;

/**
 * This DTO class stores the task grouping information.
 *
 * @author TCSASSEMBLER
 * @version 1.0 (TC - Cockpit Tasks Management Assembly 3)
 */
public class TaskGroupDTO {

    /**
     * The group type - no grouping (default, group by task list)
     */
    public static final int NO_GROUPING = 0;

    /**
     * The group type - group by the due date.
     */
    public static final int GROUP_BY_DUE_DATE = 1;

    /**
     * The groupu type id, default to no grouping.
     */
    private int groupTypeId = NO_GROUPING;

    /**
     * The task list id the group is applied. 0 means all the task list
     */
    private long groupListId = 0;

    /**
     * Gets the group type id.
     *
     * @return the group type id.
     */
    public int getGroupTypeId() {
        return groupTypeId;
    }

    /**
     * Sets the group type id.
     *
     * @param groupTypeId the group type id.
     */
    public void setGroupTypeId(int groupTypeId) {
        this.groupTypeId = groupTypeId;
    }

    /**
     * Get the task list id the group wants to applied to.
     *
     * @return the task list id
     */
    public long getGroupListId() {
        return groupListId;
    }

    /**
     * Sets the task list id the group wants to apply to
     *
     * @param groupListId the task list id
     */
    public void setGroupListId(long groupListId) {
        this.groupListId = groupListId;
    }
}
