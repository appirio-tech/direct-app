/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.groups;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.groups.model.DirectProject;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.services.DirectProjectService;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.ProjectDTO;

/**
 * <p>
 * ViewGroupAction is used to perform the view group function. It will log events and errors.
 * </p>
 * <b>Configuration</b>
 * <p>
 * 
 * <pre>
 *   &lt;bean id="viewGroupAction"
 *         class="com.topcoder.security.groups.actions.ViewGroupAction"
 *         scope="prototype" parent="baseGroupAction" init-method="checkInit">
 *         &lt;property name="groupService" ref="groupService"/>
 *         &lt;property name="projectService" ref="groupProjectService"/>
 *         &lt;property name="groupUserService" ref="groupUserService"/>
 * 
 *         &lt;!-- other properties here -->
 *   &lt;/bean>
 * </pre>
 * 
 * </p>
 * <p>
 * <strong>Thread Safety: </strong> It's mutable and not thread safe. However the struts framework will guarantee that
 * it will be used in the thread safe model.
 * </p>
 * 
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Security Groups Frontend - View Group Details) Change notes:
 * <ol>
 * <li>Integrated this action into Direct.</li>
 * </ol>
 * </p>
 * 
 * <p>
 * Version 1.2 (TopCoder Security Groups Frontend - Create Update Group) change notes:
 * <ol>
 *   <li>Update the permission checking logic.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.3 (Release Assembly - TopCoder Security Groups Frontend - Search Delete Groups) change notes:
 * <ol>
 *   <li>Updated to use helper methods in {@link HelperUtility}.</li>
 * </ol>
 * </p>
 *
 * @author woodjhon, hanshuai, backstreetlili, flexme, minhu
 * @version 1.3
 */
@SuppressWarnings("serial")
public class ViewGroupAction extends BaseAction {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = ViewGroupAction.class.getName();

    /** The key in the error page to show error messages */
    private static final String ERROR_MSG_KEY = "errorPageMessage";
    
    /** The error message string */
    private static final String ERROR_MSG = "Sorry, you don't have permission to access this group.";

    /**
     * Purpose: groupId is used to represents the group id. Usage: It's injected. Legal Values: Positive after set
     */
    private long groupId;

    /**
     * Purpose: group is used to represents the group. Usage: It's set by the action methods, and consumed by the front
     * end page. Legal Values: Not null after set
     */
    private Group group;

    /**
     * Purpose: groupService is used to represents the group service. It's required. Usage: It's injected. Legal Values:
     * Not null after set
     */
    private GroupService groupService;

    /**
     * Purpose: projectService is used to retrieve project data. It's required. Usage: It's injected. Legal Values: Not
     * null after set
     */
    private DirectProjectService projectService;

    /**
     * Purpose: userService is used to retrieve user data. It's required. Usage: It's injected. Legal Values: Not null
     * after set
     */
    private UserService groupUserService;

    /**
     * <p>
     * Create the instance.
     * </p>
     */
    public ViewGroupAction() {
        // Empty Constructor.
    }

    /**
     * <p>
     * Check that the required(with config stereotype) fields are injected.
     * </p>
     * 
     * @throws SecurityGroupsActionConfigurationException
     *             is thrown if any of these fields is null:<br>
     *             groupService, projectService, userService
     */
    public void checkInit() {
        super.checkInit();
        ValidationUtility.checkNotNull(groupService, "groupService", SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(projectService, "projectService",
                        SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(groupUserService, "groupUserService", SecurityGroupsActionConfigurationException.class);
    }

    /**
     * <p>
     * Execute method. It will get the group by id. If can not find the group or the user doesn't have permission,an
     * error page will be shown.
     * </p>
     * 
     * @throws SecurityGroupsActionException
     *             if any error is caught during the operation.
     * @return The execution result
     */
    public String execute() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".execute()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);

        try {
            group = groupService.get(groupId);

            if (group == null) {
                HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(
                                ServletActionContext.HTTP_REQUEST);
                request.setAttribute(ERROR_MSG_KEY, ERROR_MSG);
                return ERROR;
            }

            if (!HelperUtility.checkPermission(ServletActionContext.getRequest(), getCurrentUserId(), getAuthorizationService(), group, false, true)) {
                HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(
                                ServletActionContext.HTTP_REQUEST);
                if (request.getAttribute(ERROR_MSG_KEY) == null) {
                    request.setAttribute(ERROR_MSG_KEY, ERROR_MSG);
                }
                return "permissionDenied";
            }

            fillProjectName(group);
            HelperUtility.fillHandle(groupUserService, group);

            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] { SUCCESS });

            return SUCCESS;
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                            "error occurs while viewing group", e));
        } catch (RuntimeException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                            "RuntimeException occurs while viewing group", e));
        }
    }

    /**
     * Execute the action.
     */
    public void executeAction() {
        
    }

    /**
     * Set the name field in DirectProject entity.
     * 
     * @param group
     *            the DirectProject's group
     * @throws SecurityGroupException
     *             if any error is caught during the operation
     */
    private void fillProjectName(Group group) throws SecurityGroupException {
        List<DirectProject> projects = group.getDirectProjects();
        for (DirectProject project : projects) {
            ProjectDTO dto = projectService.get(project.getDirectProjectId());
            if (dto != null) {
                project.setName(dto.getName());
            }
        }
    }

    /**
     * <p>
     * Get group id.
     * </p>
     * 
     * @return the group id
     */
    public long getGroupId() {
        return groupId;
    }

    /**
     * <p>
     * Set group id.
     * </p>
     * 
     * @param groupId
     *            the group id to set.
     */
    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    /**
     * <p>
     * Get group.
     * </p>
     * 
     * @return the group
     */
    public Group getGroup() {
        return group;
    }

    /**
     * <p>
     * Set group.
     * </p>
     * 
     * @param group
     *            the group to set.
     */
    public void setGroup(Group group) {
        this.group = group;
    }

    /**
     * <p>
     * Get group service.
     * </p>
     * 
     * @return the group service
     */
    public GroupService getGroupService() {
        return groupService;
    }

    /**
     * <p>
     * Set group service.
     * </p>
     * 
     * @param groupService
     *            the group service to set.
     */
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * Setter of projectService
     * 
     * @param projectService
     *            the projectService to set
     */
    public void setProjectService(DirectProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Setter of userService
     * 
     * @param groupUserService
     *            the userService to set
     */
    public void setGroupUserService(UserService groupUserService) {
        this.groupUserService = groupUserService;
    }

}
