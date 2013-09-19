/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.task.project;

import java.io.Serializable;
import java.util.List;

/**
 * This DTO class stores the all kinds of task filters.
 *
 * @version 1.0 (TC - Cockpit Tasks Management Assembly 3)
 * @author TCSASSEMBLER
 */
public class TaskFilterDTO implements Serializable {

    /**
     * The due type - anytime
     */
    public static final int DUE_ANYTIME = 0;

    /**
     * The due type - this week
     */
    public static final int DUE_THIS_WEEK = 1;

    /**
     * The due type - next week
     */
    public static final  int DUE_NEXT_WEEK = 2;

    /**
     * The due type - later
     */
    public static final int DUE_LATER = 3;

    /**
     * The due type - past
     */
    public static final int DUE_PAST = 4;

    /**
     * The due type - today
     */
    public static final int DUE_TODAY = 5;

    /**
     * The task name used to filter.
     */
    private String name;

    /**
     * the assignee id used to filter.
     */
    private Long assigneeId;

    /**
     * The due type used to filter.
     */
    private int dueType;

    /**
     * The from date of the due date range.
     */
    private String dueDateFrom;

    /**
     * The to date of the due date range.
     */
    private String dueDateTo;

    /**
     * The priority ids used to filter.
     */
    private List<Long> priorityIds;

    /**
     * The status ids used to filter.
     */
    private List<Long> statusIds;

    /**
     * The milestone ids used to filter.
     */
    private List<Long> milestoneIds;

    /**
     * The contest ids used to filter.
     */
    private List<Long> contestIds;

    /**
     * The flag to indicate whether the filter is applied.
     */
    private boolean applyFilter = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Long assigneeId) {
        this.assigneeId = assigneeId;
    }

    public String getDueDateFrom() {
        return dueDateFrom;
    }

    public void setDueDateFrom(String dueDateFrom) {
        this.dueDateFrom = dueDateFrom;
    }

    public String getDueDateTo() {
        return dueDateTo;
    }

    public void setDueDateTo(String dueDateTo) {
        this.dueDateTo = dueDateTo;
    }

    public List<Long> getPriorityIds() {
        return priorityIds;
    }

    public void setPriorityIds(List<Long> priorityIds) {
        this.priorityIds = priorityIds;
    }

    public List<Long> getStatusIds() {
        return statusIds;
    }

    public void setStatusIds(List<Long> statusIds) {
        this.statusIds = statusIds;
    }

    public List<Long> getMilestoneIds() {
        return milestoneIds;
    }

    public void setMilestoneIds(List<Long> milestoneIds) {
        this.milestoneIds = milestoneIds;
    }

    public List<Long> getContestIds() {
        return contestIds;
    }

    public void setContestIds(List<Long> contestIds) {
        this.contestIds = contestIds;
    }

    public int getDueType() {
        return dueType;
    }

    public void setDueType(int dueType) {
        this.dueType = dueType;
    }

    public boolean isApplyFilter() {
        return applyFilter;
    }

    public void setApplyFilter(boolean applyFilter) {
        this.applyFilter = applyFilter;
    }
}
