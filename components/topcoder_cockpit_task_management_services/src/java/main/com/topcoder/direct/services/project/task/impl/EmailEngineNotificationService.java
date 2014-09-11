/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.impl;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.topcoder.direct.services.project.task.NotificationException;
import com.topcoder.direct.services.project.task.NotificationService;
import com.topcoder.direct.services.project.task.TaskManagementConfigurationException;
import com.topcoder.direct.services.project.task.model.Task;
import com.topcoder.direct.services.project.task.model.TaskList;
import com.topcoder.direct.services.project.task.model.TaskPriority;
import com.topcoder.direct.services.project.task.model.TaskStatus;
import com.topcoder.direct.services.project.task.model.UserDTO;
import com.topcoder.message.email.AddressException;
import com.topcoder.message.email.EmailEngine;
import com.topcoder.message.email.SendingException;
import com.topcoder.message.email.TCSEmailMessage;
import com.topcoder.service.user.UserService;
import com.topcoder.service.user.UserServiceException;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.Template;
import com.topcoder.util.file.TemplateDataFormatException;
import com.topcoder.util.file.TemplateFormatException;
import com.topcoder.util.file.fieldconfig.NodeList;
import com.topcoder.util.file.fieldconfig.NodeListUtility;
import com.topcoder.util.file.fieldconfig.TemplateFields;
import com.topcoder.util.file.templatesource.FileTemplateSource;
import com.topcoder.util.file.templatesource.TemplateSourceException;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This is the Email Engine (TC catalog component) based implementation of service for sending notifications.
 * </p>
 * <p>
 * It uses TC catalog components Email Engine and Document Generator to send template based email
 * notifications.
 * </p>
 * <p>
 * Sample Configuration:
 *
 * <pre>
 *  &lt;bean id=&quot;notificationService&quot;
 *     class=&quot;com.topcoder.direct.services.project.task.impl.EmailEngineNotificationService&quot;&gt;
 *     &lt;property name=&quot;userService&quot; ref=&quot;mockUserService&quot;/&gt;
 *     &lt;property name=&quot;emailSender&quot; value=&quot;donotreply@topcoder.com&quot;/&gt;
 *     &lt;property name=&quot;taskCreationEmailSubjectTemplateText&quot;
 *     value=&quot;Task &amp;quot;%TASK_NAME%&amp;quot; has been creation.&quot;/&gt;
 *     &lt;property name=&quot;taskCreationEmailBodyTemplatePath&quot;
 *     value=&quot;test_files/create_body_template.txt&quot;/&gt;
 *     &lt;property name=&quot;taskStatusChangeEmailSubjectTemplateText&quot;
 *     value=&quot;Task &amp;quot;%TASK_NAME%&amp;quot; status has been changed.&quot;/&gt;
 *     &lt;property name=&quot;taskStatusChangeEmailBodyTemplatePath&quot;
 *     value=&quot;test_files/status_body_template.txt&quot;/&gt;
 *   &lt;/bean&gt;
 * </pre>
 *
 * </p>
 * <p>
 * Sample API Usage:
 * <pre>
 * NotificationService notificationService = (NotificationService) ctx.getBean(&quot;notificationService&quot;);
 * notificatoinService.notifyTaskCreation(123, task);
 * notivicationService.notifyTaskStatusChange(123, TaskStatus.IN_PROGRESS, newTask);
 * </pre>
 * </p>
 * <p>
 * <b>Thread-Safety:</b> This class is mutable, but can be used thread safely under following conditions:
 * setters should not be called after initialization and method arguments will not be used concurrently.
 * </p>
 *
 * <p>
 * Version 1.1 (Module Assembly TC - Cockpit Tasks Management Services Setup and Quick Add Task)
 * <ul>
 *     <li>Updates {@link #notifyTaskCreation(long, com.topcoder.direct.services.project.task.model.Task)}
 *      to get assignee handle from the user service if it's null.
 *     </li>
 * </ul>
 * </p>
 *
 * @author Mozgastik, TCSASSEMBLER
 * @version 1.1
 */
public class EmailEngineNotificationService extends BaseJPAService implements NotificationService {

    /**
     * <p>
     * Represents the name of the class for logging.
     * </p>
     */
    private static final String CLASS_NAME = EmailEngineNotificationService.class.getName();

    /**
     * <p>
     * Represents the service for managing users.
     * </p>
     * <p>
     * It is used by all public business methods to obtain the user handle.
     * </p>
     * <p>
     * It is mutable, has setter for injection.
     * </p>
     * <p>
     * Technically can be any value, but will be validated in checkInitialization method to be not null.
     * </p>
     */
    private UserService userService;

    /**
     * <p>
     * Represents the logger for performing logging. If null, logging will not be performed.
     * </p>
     * <p>
     * It is used by all public business methods for logging.
     * </p>
     * <p>
     * It is mutable, has setter for injection.
     * </p>
     * <p>
     * It can be any value.
     * </p>
     */
    private Log log;

    /**
     * <p>
     * Represents the email address of notification sender.
     * </p>
     * <p>
     * It is used by all public business methods to retrieve the sender.
     * </p>
     * <p>
     * It is mutable, has setter for injection.
     * </p>
     * <p>
     * Technically can be any value, but will be validated in checkInitialization method to be not null/empty.
     * </p>
     */
    private String emailSender;

    /**
     * <p>
     * Represents the email subject template text to be used for constructing messages about task creation.
     * </p>
     * <p>
     * It is used by all public business methods.
     * </p>
     * <p>
     * It is mutable, has setter for injection.
     * </p>
     * <p>
     * Technically can be any value, but will be validated in checkInitialization method to be not null/empty.
     * </p>
     */
    private String taskCreationEmailSubjectTemplateText;

    /**
     * <p>
     * Represents the email body template text to be used for constructing messages about task creation.
     * </p>
     * <p>
     * It is used by all public business methods.
     * </p>
     * <p>
     * It is mutable, has setter for injection.
     * </p>
     * <p>
     * Technically can be any value, but will be validated in checkInitialization method to be not null/empty.
     * </p>
     */
    private String taskCreationEmailBodyTemplatePath;

    /**
     * <p>
     * Represents the email subject template text to be used for constructing messages about task status
     * change.
     * </p>
     * <p>
     * It is used by all public business methods.
     * </p>
     * <p>
     * It is mutable, has setter for injection.
     * </p>
     * <p>
     * Technically can be any value, but will be validated in checkInitialization method to be not null/empty.
     * </p>
     */
    private String taskStatusChangeEmailSubjectTemplateText;

    /**
     * <p>
     * Represents the email body template text to be used for constructing messages about task status change.
     * </p>
     * <p>
     * It is used by all public business methods.
     * </p>
     * <p>
     * It is mutable, has setter for injection.
     * </p>
     * <p>
     * Technically can be any value, but will be validated in checkInitialization method to be not null/empty.
     * </p>
     */
    private String taskStatusChangeEmailBodyTemplatePath;

    /**
     * <p>
     * Creates the instance of EmailEngineNotificationService.
     * </p>
     * <p>
     * This is the default constructor of EmailEngineNotificationService.
     * </p>
     */
    public EmailEngineNotificationService() {
        // does nothing
    }

    /**
     * <p>
     * Validates the configuration parameters.
     * </p>
     * In this class, the required fields are:
     * <pre>
     * userService,
     * emailSender not null or empty,
     * taskCreationEmailSubjectTemplateText not null or empty,
     * taskCreationEmailBodyTemplatePath not null or empty,
     * taskStatusChangeEmailSubjectTemplateText not null or empty,
     * taskStatusChangeEmailBodyTemplatePath not null or empty,
     * </pre>
     *
     * Refer to the class document for the demo configuration.
     *
     * @throws TaskManagementConfigurationException if any configuration parameter has invalid value.
     */
    @PostConstruct
    public void checkInitialization() {
        ServiceHelper.checkState(userService == null, "The userService is not properly injected.");
        ServiceHelper.checkState(emailSender == null || emailSender.trim().length() == 0,
            "The emailSender cannot be null or empty.");
        ServiceHelper.checkState(taskCreationEmailSubjectTemplateText == null
            || taskCreationEmailSubjectTemplateText.trim().length() == 0,
            "The taskCreationEmailSubjectTemplateText cannot be null or empty.");
        ServiceHelper.checkState(taskCreationEmailBodyTemplatePath == null
            || taskCreationEmailBodyTemplatePath.trim().length() == 0,
            "The taskCreationEmailBodyTemplatePath cannot be null or empty.");
        ServiceHelper.checkState(taskStatusChangeEmailSubjectTemplateText == null
            || taskStatusChangeEmailSubjectTemplateText.trim().length() == 0,
            "The taskStatusChangeEmailSubjectTemplateText cannot be null or empty.");
        ServiceHelper.checkState(taskStatusChangeEmailBodyTemplatePath == null
            || taskStatusChangeEmailBodyTemplatePath.trim().length() == 0,
            "The taskStatusChangeEmailBodyTemplatePath cannot be null or empty.");

    }

    /**
     * <p>
     * Sends notification about task creation.
     * </p>
     * @param userId the id of user performing this action.
     * @param task the created task.
     *
     * @throws IllegalArgumentException if task is null or task.getName() is null/empty or task.getStatus() is
     *             null or task.getPriority() is null.
     *
     * @throws NotificationException if any error occurs, for example failed to get the user email from the
     *             userService.
     */
    public void notifyTaskCreation(long userId, Task task) throws NotificationException {
        // prepare for logging
        final String methodName = CLASS_NAME + "#notifyTaskCreation(long userId, Task task)";
        // log the entrance
        ServiceHelper.logEntrance(log, methodName, new String[] {"userId", "task"}, new Object[] {userId, task});

        // validate the parameters
        ServiceHelper.checkNull(log, methodName, task, "task");
        ServiceHelper.checkNullOrEmpty(log, methodName, task.getName(), "name in task");
        ServiceHelper.checkNull(log, methodName, task.getStatus(), "status in task");
        ServiceHelper.checkNull(log, methodName, task.getPriority(), "priority in task");

        // prepare for the email parameter values
        String taskName = task.getName();
        String taskNotes = task.getNotes() == null ? "" : task.getNotes();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date taskStartDate = task.getStartDate();
        Date taskDueDate = task.getDueDate();
        String taskStatus = convertTaskStatus(methodName, task.getStatus());
        String taskPriority = convertPriority(methodName, task.getPriority());

        List<Map<String, String>> assigneesData = new ArrayList<Map<String, String>>();
        if (task.getAssignees() != null) {
            // each map represents an assignee with its id and handle
            for (UserDTO assignee : task.getAssignees()) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("USER_ID", assignee.getUserId() + "");
                String userHandle = assignee.getHandle();
                try {
                    if (userHandle == null) {
                        userHandle = userService.getUserHandle(assignee.getUserId());
                    }
                } catch (UserServiceException e) {
                    throw ServiceHelper.logException(log, methodName,
                           new NotificationException("Failed to get user handle for:" + assignee.getUserId(), e));
                }
                map.put("USER_HANDLE", userHandle);
                assigneesData.add(map);
            }
        }

        // populate email content parameters
        Map<String, Object> params = new HashMap<String, Object>();

        // set the corresponding values
        params.put("TASK_NAME", taskName);
        params.put("TASK_NOTES", taskNotes);
        params.put("TASK_START_DATE", taskStartDate != null ? dateFormat.format(taskStartDate) : taskStartDate);
        params.put("TASK_DUE_DATE", taskDueDate != null ? dateFormat.format(taskDueDate) : taskDueDate);
        params.put("TASK_STATUS", taskStatus);
        params.put("TASK_PRIORITY", taskPriority);
        params.put("TASK_ASSIGNEES", assigneesData);

	    // get Project id and name
        TaskList tl = getEntityManager().find(TaskList.class, task.getTaskListId());
        String projectName = ServiceHelper.projectNameById(log, methodName, getEntityManager(), tl.getProjectId());
        params.put("PROJECT_ID", tl.getProjectId());
        params.put("PROJECT_NAME", projectName);

        // send emails
        if (task.getAssignees() != null) {
            for (UserDTO assignee : task.getAssignees()) {
                try {
                    sendEmail(methodName, taskCreationEmailSubjectTemplateText, taskCreationEmailBodyTemplatePath,
                        emailSender, userService.getEmailAddress(assignee.getUserId()), params);
                } catch (UserServiceException e) {
                    throw ServiceHelper.logException(log, methodName,
                        new NotificationException("Failed to send the email to user:" + assignee.getUserId(), e));
                }
            }
        }

        // log the exit
        ServiceHelper.logExit(log, methodName);
    }

    /**
     * <p>
     * Send notification about task status change.
     * </p>
     *
     * <p>
     * When status changes, the email will sent to the corresponding assignees of the task, and the creator of
     * this task.
     * </p>
     *
     * @param userId the ID of user performing this action.
     * @param oldTask the task status before status change.
     * @param newTask the task after status change.
     * @throws IllegalArgumentException if any of the tasks (oldTask or newTask) is null or have
     *             Task.getName() null/empty or Task.getStatus() null, or the Task.getCreatedBy is null or empty.
     *
     * @throws NotificationException if any error occurs, such as failed to get the user's email addresses.
     */
    public void notifyTaskStatusChange(long userId, TaskStatus oldTask, Task newTask)
        throws NotificationException {

        // prepare for logging
        final String methodName = CLASS_NAME + "#notifyTaskStatusChange(long userId, TaskStatus oldTask, Task newTask)";

        // log the entrance
        ServiceHelper.logEntrance(log, methodName, new String[] {"userId", "oldTask", "newTask"},
            new Object[] {userId, oldTask, newTask});

        // validate the parameters
        ServiceHelper.checkNull(log, methodName, oldTask, "oldTask");
        ServiceHelper.checkNull(log, methodName, newTask, "newTask");
        ServiceHelper.checkNullOrEmpty(log, methodName, newTask.getName(), "name in newTask");
        ServiceHelper.checkNull(log, methodName, newTask.getStatus(), "status in newTask");
        ServiceHelper.checkNullOrEmpty(log, methodName, newTask.getCreatedBy(), "createdBy in newTask");

        // populate email content parameters
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("TASK_NAME", newTask.getName());
        String taskStatusText = convertTaskStatus(methodName, newTask.getStatus());
        params.put("UPDATED_TASK_STATUS", taskStatusText);

        String taskNotes = newTask.getNotes() == null ? "" : newTask.getNotes();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date taskStartDate = newTask.getStartDate();
        Date taskDueDate = newTask.getDueDate();
        String taskStatus = convertTaskStatus(methodName, newTask.getStatus());
        String taskPriority = convertPriority(methodName, newTask.getPriority());

        List<Map<String, String>> assigneesData = new ArrayList<Map<String, String>>();
        if (newTask.getAssignees() != null) {
            // each map represents an assignee with its id and handle
            for (UserDTO assignee : newTask.getAssignees()) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("USER_ID", assignee.getUserId() + "");
                String userHandle = assignee.getHandle();
                try {
                    if (userHandle == null) {
                        userHandle = userService.getUserHandle(assignee.getUserId());
                    }
                } catch (UserServiceException e) {
                    throw ServiceHelper.logException(log, methodName,
                                                     new NotificationException("Failed to get user handle for:" + assignee.getUserId(), e));
                }
                map.put("USER_HANDLE", userHandle);
                assigneesData.add(map);
            }
        }

        params.put("TASK_NOTES", taskNotes);
        params.put("TASK_START_DATE", taskStartDate != null ? dateFormat.format(taskStartDate) : taskStartDate);
        params.put("TASK_DUE_DATE", taskDueDate != null ? dateFormat.format(taskDueDate) : taskDueDate);
        params.put("TASK_STATUS", taskStatus);
        params.put("TASK_PRIORITY", taskPriority);
        params.put("TASK_ASSIGNEES", assigneesData);

        // get Project id and name
        TaskList tl = getEntityManager().find(TaskList.class, newTask.getTaskListId());
        String projectName = ServiceHelper.projectNameById(log, methodName, getEntityManager(), tl.getProjectId());
        params.put("PROJECT_ID", tl.getProjectId());
        params.put("PROJECT_NAME", projectName);

        // send emails
        try {
            String createdByHandle = newTask.getCreatedBy();
            boolean createdBySent = false;
            if (newTask.getAssignees() != null) {
                // send to the assignees
                for (UserDTO assignee : newTask.getAssignees()) {
                    // send to the assignee
                    sendEmail(methodName,
                        taskStatusChangeEmailSubjectTemplateText, taskStatusChangeEmailBodyTemplatePath,
                            emailSender, userService.getEmailAddress(assignee.getUserId()), params);

                    // if this is the same handle as the createdBy user, mark it
                    if (createdByHandle.equals(assignee.getHandle())) {
                        createdBySent = true;
                    }
                }
            }

            if (!createdBySent) {
                // send to the creator if it is not sent
                sendEmail(methodName, taskStatusChangeEmailSubjectTemplateText, taskStatusChangeEmailBodyTemplatePath,
                    emailSender, userService.getEmailAddress(createdByHandle), params);
            }

        } catch (UserServiceException e) {
            // wrap and re-throw it
            throw ServiceHelper.logException(log, methodName,
                new NotificationException("failed to get the user email address", e));
        }

        // log the exit
        ServiceHelper.logExit(log, methodName);
    }

    /**
     * <p>
     * Converts the priority to a readable string.
     * </p>
     * @param methodName the name of the method for logging.
     * @param priority the priority to convert.
     * @return the readable string of priority.
     * @throws NotificationException if the priority is not recognized.
     */
    private String convertPriority(String methodName, TaskPriority priority) throws NotificationException {
        if (priority == TaskPriority.HIGH) {
            return "High";
        } else if (priority == TaskPriority.NORMAL) {
            return "Normal";
        } else if (priority == TaskPriority.LOW) {
            return "Low";
        } else {
            throw ServiceHelper.logException(log, methodName, new NotificationException("Unrecognized task priority:"
                + priority));
        }
    }

    /**
     * <p>
     * Converts the task status to a readable string.
     * </p>
     * @param methodName the name of the method for logging.
     * @param taskStatus the task status to convert.
     * @return the readable string of task status.
     * @throws NotificationException if the task status is not recognized.
     */
    private String convertTaskStatus(final String methodName, TaskStatus taskStatus) throws NotificationException {
        if (taskStatus == TaskStatus.COMPLETED) {
            return "Completed";
        } else if (taskStatus == TaskStatus.IN_PROGRESS) {
            return "In progress";
        } else if (taskStatus == TaskStatus.NOT_STARTED) {
            return "Not started";
        } else if (taskStatus == TaskStatus.WAIT_ON_DEPENDENCY) {
            return "Wait on dependency";
        } else {
            throw ServiceHelper.logException(log, methodName, new NotificationException("Unrecognized task status:"
                + taskStatus));
        }
    }

    /**
     * <p>
     * Sets the service for managing users.
     * </p>
     * @param userService the service for managing users.
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * <p>
     * Sets the logger for performing logging. If null, logging will not be performed.
     * </p>
     * @param log the logger for performing logging. If null, logging will not be performed.
     */
    public void setLog(Log log) {
        this.log = log;
    }

    /**
     * <p>
     * Sets email address of notification sender.
     * </p>
     * @param emailSender the email address of notification.
     */
    public void setEmailSender(String emailSender) {
        this.emailSender = emailSender;
    }

    /**
     * <p>
     * Sets the email subject template text to be used for constructing messages about task creation.
     * </p>
     * @param taskCreationEmailSubjectTemplateText the email subject template text to be used for constructing
     *            messages about task creation.
     */
    public void setTaskCreationEmailSubjectTemplateText(String taskCreationEmailSubjectTemplateText) {
        this.taskCreationEmailSubjectTemplateText = taskCreationEmailSubjectTemplateText;
    }

    /**
     * <p>
     * Set the email body template text to be used for constructing messages about task creation.
     * </p>
     * @param taskCreationEmailBodyTemplatePath the email body template text to be used for constructing
     *            messages about task creation.
     */
    public void setTaskCreationEmailBodyTemplatePath(String taskCreationEmailBodyTemplatePath) {
        this.taskCreationEmailBodyTemplatePath = taskCreationEmailBodyTemplatePath;
    }

    /**
     * <p>
     * Sets the email subject template text to be used for constructing messages about task status change.
     * </p>
     * @param taskStatusChangeEmailSubjectTemplateText the email subject template text to be used for
     *            constructing messages about task status change.
     */
    public void setTaskStatusChangeEmailSubjectTemplateText(String taskStatusChangeEmailSubjectTemplateText) {
        this.taskStatusChangeEmailSubjectTemplateText = taskStatusChangeEmailSubjectTemplateText;
    }

    /**
     * <p>
     * Sets the email body template text to be used for constructing messages about task status change.
     * </p>
     * @param taskStatusChangeEmailBodyTemplatePath the email body template text to be used for constructing
     *            messages about task status change.
     */
    public void setTaskStatusChangeEmailBodyTemplatePath(String taskStatusChangeEmailBodyTemplatePath) {
        this.taskStatusChangeEmailBodyTemplatePath = taskStatusChangeEmailBodyTemplatePath;
    }

    /**
     * <p>
     * Sends an email message generated from templates to the specified recipients. The email message is
     * assumed to have HTML content.
     * </p>
     * @param methodName the name of the method for logging.
     * @param subjectTemplateText the template text of the email message subject.
     * @param bodyTemplatePath the resource or file path of the email message body template.
     * @param emailSender the email address of sender.
     * @param recipient the email address of recipient.
     * @param params the template parameters (String, List and Map values are supported).
     *
     * @throws NotificationException if failed to send the email, for example, failed to parse the template.
     */
    private void sendEmail(String methodName, String subjectTemplateText, String bodyTemplatePath,
        String emailSender, String recipient, Map<String, Object> params) throws NotificationException {

        // create the document generator
        DocumentGenerator documentGenerator = new DocumentGenerator();
        documentGenerator.setDefaultTemplateSource(new FileTemplateSource());
        try {
            // parse email title template
            Template emailSubjectTemplate = documentGenerator.parseTemplate(subjectTemplateText);

            String templateFilePath = Thread.currentThread().getContextClassLoader().getResource(
                    bodyTemplatePath).getFile();

            // read and parse email body template
            Template emailBodyTemplate = documentGenerator.getTemplate(templateFilePath);

            // get email subject template fields
            TemplateFields fields = emailSubjectTemplate.getFields();
            // populate node list with data
            NodeListUtility.populateNodeList(new NodeList(fields.getNodes()), params);
            // generate email title from template
            String emailSubject = documentGenerator.applyTemplate(fields);

            // get email body template fields
            fields = emailBodyTemplate.getFields();
            // populate node list with data
            NodeListUtility.populateNodeList(new NodeList(fields.getNodes()), params);
            // generate email body from template
            String emailBody = documentGenerator.applyTemplate(fields);

            // create email message
            TCSEmailMessage message = new TCSEmailMessage();
            // populate the message
            message.setSubject(emailSubject);
            message.setBody(emailBody);
            message.setFromAddress(emailSender);
            message.addToAddress(recipient, TCSEmailMessage.TO);
            message.setContentType("text/html");

            // send the message
            EmailEngine.send(message);
        } catch (TemplateFormatException e) {
            // log and wrap
            throw ServiceHelper.logException(log, methodName, new NotificationException(
                "The format of the template is invalid.", e));
        } catch (TemplateSourceException e) {
            // log and wrap
            throw ServiceHelper.logException(log, methodName, new NotificationException(
                "The source of the template is invalid.", e));
        } catch (TemplateDataFormatException e) {
            // log and wrap
            throw ServiceHelper.logException(log, methodName, new NotificationException(
                "The data format of the template is invalid.", e));
        } catch (AddressException e) {
            // log and wrap
            throw ServiceHelper.logException(log, methodName, new NotificationException(
                "The email addresses are invalid.", e));
        } catch (ConfigManagerException e) {
            // log and wrap
            throw ServiceHelper.logException(log, methodName, new NotificationException(
                "Failed to send the email, configuration problem exists.", e));
        } catch (SendingException e) {
            // log and wrap
            throw ServiceHelper.logException(log, methodName, new NotificationException(
                "Errors occur while sending the email.", e));
        }
    }
}

