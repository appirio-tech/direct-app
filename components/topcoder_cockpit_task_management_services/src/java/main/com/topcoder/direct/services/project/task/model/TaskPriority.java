/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.model;

/**
 * <p>
 * This is an enumeration for task priority.
 * </p>
 * <p/>
 * <p>
 * <strong>Thread Safety: </strong> This enumeration is immutable and thread safe.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public enum TaskPriority {
    /**
     * This is 'high' task priority.
     */
    HIGH(0L, "High"),

    /**
     * This is 'low' task priority.
     */
    LOW(1L, "Low"),

    /**
     * This is 'normal' task priority.
     */
    NORMAL(2L, "Normal");

    private long id;

    private String name;

    private TaskPriority(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static TaskPriority forId(long id) {
        TaskPriority[] priorities = TaskPriority.values();
        for (int i = 0; i < priorities.length; i++) {
            TaskPriority p = priorities[i];
            if (p.id == id) {
                return p;
            }
        }
        return null;
    }

    public static TaskPriority forName(String name) {
        TaskPriority[] priorities = TaskPriority.values();
        for (int i = 0; i < priorities.length; i++) {
            TaskPriority p = priorities[i];
            if (p.name.equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }
}
