/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.model;


/**
 * <p>
 * This is an enumeration for task status.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This enumeration is immutable and thread safe.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public enum TaskStatus {
    /**
     * This is 'not started' task status.
     */
    NOT_STARTED(0L, "Not Started"),

    /**
     * This is 'in progress' task status.
     */
    IN_PROGRESS(1L, "In Progress"),

    /**
     * This is 'wait on dependency' task status.
     */
    WAIT_ON_DEPENDENCY(2L, "Wait On Dependency"),

    /**
     * This is 'completed' task status.
     */
    COMPLETED(3L, "Completed");

    private long id;

    private String name;

    private TaskStatus(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static TaskStatus forId(long id) {
        TaskStatus[] statuses = TaskStatus.values();
        for (int i = 0; i < statuses.length; i++) {
            TaskStatus s = statuses[i];
            if (s.id == id) {
                return s;
            }
        }
        return null;
    }

    public static TaskStatus forName(String name) {
        TaskStatus[] statuses = TaskStatus.values();
        for (int i = 0; i < statuses.length; i++) {
            TaskStatus s = statuses[i];
            if (s.name.equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }
}
