/*
 * Copyright (C) 2011 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.groups;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.UserDTO;

/**
 * <p>
 * SearchUserAction is used to perform the search user functionIt will log events and errors. It will audit the
 * methods.
 * </p>
 * <b>Configuration</b>
 * <p>
 * <pre>
 *   &lt;bean id="searchUserAction"
 *         class="com.topcoder.security.groups.actions.SearchUserAction">
 *         &lt;property name="groupUserService" ref="groupUserService"/>
 *         &lt;property name="logger" ref="logger"/>
 *         &lt;property name="auditService" ref="auditService"/>
 *         &lt;property name="authorizationService" ref="authorizationService"/>
 *
 *         &lt;!-- other properties here -->
 *   &lt;/bean>
 *
 * </pre>
 * </p>
 * <p>
 * <strong>Thread Safety: </strong> It's mutable and not thread safe.
 * However the struts framework will guarantee that it will be used in the thread safe model.
 * </p>
 * <p>
 * Version 1.1 (TopCoder Security Groups Frontend - Create Update Group) change notes:
 * <ol>
 *   <li>This class was updated to support ajax calls. Also permission checking was added.</li>
 * </ol>
 * </p>
 * <p>
 * Version 1.2 (48hr Cockpit Group Management Improvement Release Assembly) change notes:
 * <ol>
 *   <li>Added {@link #handles} property and its getter/setter.</li>
 *   <li>Added {@link #getUsersByHandles()} new get users by handles json API.</li>
 * </ol>
 * </p>
 *
 * @author woodjhon, hanshuai, flexme, suno1234
 * @version 1.2
 */
@SuppressWarnings("serial")
public class SearchUserAction extends BaseAction {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = SearchUserAction.class.getName();

    /**
     * Purpose: handle is used to represents the handle. Usage: It's passed as the http input parameter for this
     * action. Legal Values: Not null and not empty after set.
     */
    private String handle;

    /**
     * Purpose: handles is used to represents the handles. Usage: It's passed as the http input parameter for this
     * action. Legal Values: Not null and not empty after set.
     * @since 1.2
     */
    private String[] handles;
    
    /**
     * Purpose: users is used to represents the users. Usage: It's set by the action methods, and consumed by the
     * front end page. Legal Values: Not null and not empty after set. The list should not contain null value.
     */
    private List<UserDTO> users;

    /**
     * Purpose: userService is used to represents the user service. It's required. Usage: It's injected. Legal
     * Values: Not null after set
     */
    private UserService groupUserService;

    /**
     * <p>
     * Create the instance.
     * </p>
     */
    public SearchUserAction() {
        // Empty Constructor.
    }

    /**
     * <p>
     * Check that the required fields are injected.
     * </p>
     * @throws SecurityGroupsActionConfigurationException
     *             is thrown if any of these fields is null:<br>
     *             auditService, authorizationService, userService
     */
    public void checkInit() {
        super.checkInit();
        ValidationUtility.checkNotNull(groupUserService, "groupUserService",
                SecurityGroupsActionConfigurationException.class);
    }

    /**
     * <p>
     * Execute method. It will search the users by the user handle.
     * </p>
     *
     * @throws SecurityGroupsActionException
     *             if any error is caught during the operation.
     */
    public void executeAction() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".executeAction()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            users = groupUserService.search(handle);
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {SUCCESS});
            setResult(users);
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    e.getMessage(), e));
        } catch (RuntimeException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    e.getMessage(), e));
        }
    }

    /**
     * <p>
     * Handles the ajax request to get users' info by handles
     * </p>
     *
     * @return the result code.
     * @since 1.2
     */
    public String getUsersByHandles() {
        final String signature = CLASS_NAME + ".getUsersByHandles()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            if (handles != null && handles.length > 0) {
                users = groupUserService.getUsersByHandles(handles);
                setResult(users);
            }
        } catch (Throwable e) {
            LoggingWrapperUtility.logException(getLogger(), signature, e);
            if (getModel() != null) {
                setResult(new RuntimeException("An error has occurred when attempting to process your request."));
            }
        }
        LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {SUCCESS});
        return SUCCESS;
    }
    
    /**
     * <p>
     * Get handle.
     * </p>
     *
     * @return the handle
     */
    public String getHandle() {
        return handle;
    }

    /**
     * <p>
     * Set handle.
     * </p>
     *
     * @param handle
     *            the handle to set.
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * <p>
     * Get users.
     * </p>
     *
     * @return the users
     */
    public List<UserDTO> getUsers() {
        return users;
    }

    /**
     * <p>
     * Set users.
     * </p>
     *
     * @param users
     *            the users to set.
     */
    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    /**
     * <p>
     * Get user service.
     * </p>
     *
     * @return the user service
     */
    public UserService getGroupUserService() {
        return groupUserService;
    }

    /**
     * <p>
     * Set user service.
     * </p>
     *
     * @param groupUserService
     *            the user service to set.
     */
    public void setGroupUserService(UserService groupUserService) {
        this.groupUserService = groupUserService;
    }
    
    /**
     * <p>
     * Get handles.
     * </p>
     *
     * @return the handles
     * @since 1.2
     */
    public String[] getHandles() {
        return handles;
    }

    /**
     * <p>
     * Set handles.
     * </p>
     *
     * @param handles
     *            the handle to set.
     * @since 1.2
     */
    public void setHandles(String[] handles) {
        this.handles = handles;
    }
}
