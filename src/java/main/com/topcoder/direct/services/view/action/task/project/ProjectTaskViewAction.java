/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.task.project;

import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.project.task.model.Task;
import com.topcoder.direct.services.project.task.model.TaskList;
import com.topcoder.direct.services.project.task.model.TaskPriority;
import com.topcoder.direct.services.project.task.model.TaskStatus;
import com.topcoder.direct.services.project.task.model.UserDTO;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.task.BaseTaskAction;
import com.topcoder.direct.services.view.dto.copilot.CopilotBriefDTO;
import com.topcoder.direct.services.view.dto.task.project.TaskDTO;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import org.apache.commons.lang.StringEscapeUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * The action for project tasks view.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly TC - Cockpit Tasks Management Services Setup and Quick Add Task)
 */
public class ProjectTaskViewAction extends BaseTaskAction implements FormAction<ProjectIdForm> {

    /**
     * All the task statuses.
     */
    private static Map<Long, String> taskStatuses = new LinkedHashMap<Long, String>();

    /**
     * All the task priorities.
     */
    private static Map<Long, String> taskPriorities = new LinkedHashMap<Long, String>();

    /**
     * The date format for the task start date and due date.
     */
    private DateFormat taskDateFormat = new SimpleDateFormat("MM/dd/yyyy");

    private DateFormat taskDateSimpleFormat = new SimpleDateFormat("dd MMMMMMM");

    /**
     * The project ID form.
     */
    private ProjectIdForm formData = new ProjectIdForm();

    /**
     * The task users.
     */
    private Set<Long> taskUserIds = new HashSet<Long>();

    /**
     * The new task.
     */
    private TaskDTO newTask;

    /**
     * The default project task list.
     */
    private TaskList defaultProjectTask;

    /**
     * Static initializer
     */
    static {
        TaskStatus[] statuses = TaskStatus.values();

        for (TaskStatus s : statuses) {
            taskStatuses.put(s.getId(), s.getName());
        }

        TaskPriority[] priorities = TaskPriority.values();

        for (TaskPriority p : priorities) {
            taskPriorities.put(p.getId(), p.getName());
        }
    }

    /**
     * Gets the form data.
     *
     * @return the form data.
     */
    public ProjectIdForm getFormData() {
        return formData;
    }

    /**
     * Sets the form data.
     *
     * @param formData the form data.
     */
    public void setFormData(ProjectIdForm formData) {
        this.formData = formData;
    }


    /**
     * Gets all the task statuses.
     *
     * @return all the task statuses.
     */
    public Map<Long, String> getTaskStatuses() {
        return taskStatuses;
    }


    /**
     * Gets all the task priorities.
     *
     * @return all the task priorities.
     */
    public Map<Long, String> getTaskPriorities() {
        return taskPriorities;
    }

    /**
     * Gets all the task user ids.
     *
     * @return all the task user ids.
     */
    public Set<Long> getTaskUserIds() {
        return taskUserIds;
    }

    /**
     * Gets the new task.
     *
     * @return the new task.
     */
    public TaskDTO getNewTask() {
        return newTask;
    }

    /**
     * Sets the new task.
     *
     * @param newTask the new task.
     */
    public void setNewTask(TaskDTO newTask) {
        this.newTask = newTask;
    }

    /**
     * Gets the default project task.
     *
     * @return the default project task.
     */
    public TaskList getDefaultProjectTask() {
        return defaultProjectTask;
    }

    /**
     * Sets the default project task
     *
     * @param defaultProjectTask the default project task.
     */
    public void setDefaultProjectTask(TaskList defaultProjectTask) {
        this.defaultProjectTask = defaultProjectTask;
    }

    /**
     * The entry view action for the project tasks page.
     *
     * @throws Exception if there is any error.
     */
    @Override
    protected void executeAction() throws Exception {
        // get current user
        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

        defaultProjectTask = getTaskListService().getDefaultTaskList(currentUser.getUserId(), getFormData().getProjectId());

        if (defaultProjectTask.getTasks() != null) {
            Collections.sort(defaultProjectTask.getTasks(), new TaskComparatorByCreationDate());
        }

        setUpTaskUsers();
    }

    /**
     * Helper methods to populate all the task users from managers and copilots
     *
     * @throws Exception if any error
     */
    private void setUpTaskUsers() throws Exception {
        List<DirectProjectMetadata> allMetadata = getMetadataService().getProjectMetadataByProject(formData.getProjectId());

        if (allMetadata != null && allMetadata.size() > 0) {
            for (DirectProjectMetadata data : allMetadata) {
                long keyId = data.getProjectMetadataKey().getId();
                String value = data.getMetadataValue();

                if (value == null || value.trim().length() == 0) {
                    // value does not exist, continue
                    continue;
                }

                if (keyId == 1L || keyId == 2L || keyId == 14L) {
                    // client manager || TopCoder Platform Managers || TopCoder Account Managers
                    taskUserIds.add(Long.parseLong(value));
                }
            }
        }

        List<CopilotBriefDTO> copilots = DataProvider.getCopilotProject(
                DirectUtils.getTCSubjectFromSession().getUserId(), formData.getProjectId()).getCopilots();

        if (copilots != null && copilots.size() > 0) {
            for (CopilotBriefDTO copilot : copilots) {
                taskUserIds.add(copilot.getUserId());
            }
        }
    }


    /**
     * The action operation to handle the ajax request to quickly create a new task.
     *
     * @return the action result code.
     */
    public String quickAddTask() {
        try {
            TaskDTO newTaskDTO = getNewTask();

            Task task = getTaskService().addTask(DirectUtils.getTCSubjectFromSession().getUserId(), createNewTaskFromDTO(getNewTask()));

            ObjectMapper m = new ObjectMapper();
            m.setDateFormat(taskDateSimpleFormat);
            Map<String, Object> result = m.convertValue(task, Map.class);


            setResult(result);

        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * Helper method to convert a TaskDTO to Task model
     *
     * @param taskDTO the TaskDTO instance
     * @return the converted Task instance
     * @throws Exception if there is any error
     */
    private Task createNewTaskFromDTO(TaskDTO taskDTO) throws Exception {

        Task task = new Task();

        String escapedName = StringEscapeUtils.escapeHtml(taskDTO.getName());

        // set the name
        task.setName(escapedName);

        // set the task list id
        task.setTaskListId(taskDTO.getTaskListId());

        // set the TaskStatus enum
        task.setStatus(TaskStatus.forId(taskDTO.getStatusId()));

        // set the task assignee
        if (taskDTO.getAssignUserIds() != null && taskDTO.getAssignUserIds().size() > 0) {
            List<UserDTO> users = new ArrayList<UserDTO>();
            for (Long userId : taskDTO.getAssignUserIds()) {
                UserDTO userDTO = new UserDTO();
                userDTO.setUserId(userId);
                users.add(userDTO);
            }

            task.setAssignees(users);
        }

        // set the task start date if exists
        if (taskDTO.getStartDate() != null) {
            task.setStartDate(taskDateFormat.parse(taskDTO.getStartDate()));
        }

        // set the task due date if exists
        if (taskDTO.getDueDate() != null) {
            task.setDueDate(taskDateFormat.parse(taskDTO.getDueDate()));
        }

        // set default priority - NORMAL
        task.setPriority(TaskPriority.NORMAL);

        // set the creation date
        task.setCreatedDate(new Date());

        // set the created by
        task.setCreatedBy(String.valueOf(DirectUtils.getTCSubjectFromSession().getUserId()));

        return task;
    }

    private static class TaskComparatorByCreationDate implements Comparator<Task> {
        public int compare(Task t1, Task t2) {
            return t2.getCreatedDate().compareTo(t1.getCreatedDate());
        }
    }
}
