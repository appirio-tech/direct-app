/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.task.project;

import com.topcoder.direct.services.project.task.model.TaskList;
import com.topcoder.direct.services.view.dto.CommonDTO;

/**
 * <p>
 * The DTO for Project Task View.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly TC - Cockpit Tasks Management Services Setup and Quick Add Task)
 */
public class ProjectTaskViewDTO extends CommonDTO {
    /**
     * The default task list
     */
    private TaskList defaultTaskList;

    /**
     * Gets the default task list
     *
     * @return the default task list.
     */
    public TaskList getDefaultTaskList() {
        return defaultTaskList;
    }

    /**
     * Sets the default task list.
     *
     * @param defaultTaskList the default task list.
     */
    public void setDefaultTaskList(TaskList defaultTaskList) {
        this.defaultTaskList = defaultTaskList;
    }
}
