/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import com.topcoder.service.user.UserService;
import com.topcoder.service.user.UserServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>An interceptor for requests to security groups management functionalities. Verifies that current user is in list
 * of users who are allowed to manage security groups. If not then redirects user to <code>Permission Denied</code> 
 * page.</p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0 (Release Assembly - TopCoder Security Groups - Release 2)
 */
public class SecurityGroupsAccessInterceptor implements Interceptor {

    /**
     * <p>A <code>Set<String></code> providing the list of handles for users which are allowed to access security groups
     * functionalities.</p>
     */
    private Set<String> allowedUsers;

    /**
     * <p>A <code>UserService</code> providing the access to user services.</p>
     */
    private UserService userService;

    /**
     * <p>Constructs new <code>SecurityGroupsAccessInterceptor</code> instance. This implementation does nothing.</p>
     */
    public SecurityGroupsAccessInterceptor() {
    }

    /**
     * <p>Initializes this interceptor. This implementation does nothing.</p>
     */
    public void init() {
    }

    /**
     * <p>Destroys this interceptor. This implementation does nothing.</p>
     */
    public void destroy() {
    }

    /**
     * <p>Intercepts the action invocation chain.</p>
     *
     * @param actionInvocation an <code>ActionInvocation</code> providing the current context for action invocation.
     * @return a <code>String</code> referencing the next view or action to route request to or <code>null</code> if
     *         request has been already handled and response committed.
     * @throws Exception if an unexpected error occurs while running the interception chain.
     */
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        boolean granted = isSecurityGroupsUIAvailable();
        if (!granted) {
            HttpServletRequest request = DirectUtils.getServletRequest();
            request.setAttribute("errorPageMessage", "Sorry, you don't have permission to access security groups.");
            return "permissionDenied";
        } else {
            return actionInvocation.invoke();
        }
    }

    /**
     * <p>Checks whether the current user is allowed to access Security Groups UI or not.</p>
     *
     * @return <code>true</code> if current user can access Security Groups UI; <code>false</code> otherwise.
     * @throws UserServiceException if an unexpected error occurs.
     */
    public boolean isSecurityGroupsUIAvailable() throws UserServiceException {
        boolean granted;
        if (this.allowedUsers == null || this.allowedUsers.isEmpty()) {
            granted = true;
        } else {
            granted = false;
            TCSubject currentUser = DirectStrutsActionsHelper.getTCSubjectFromSession();
            if (currentUser != null) {
                String userHandle = getUserService().getUserHandle(currentUser.getUserId());
                if (this.allowedUsers.contains(userHandle)) {
                    granted = true;
                }
            }
        }
        return granted;
    }

    /**
     * <p>Sets the list of handles for users which are allowed to access security groups functionalities.</p>
     *
     * @param allowedUsers a <code>Set<String></code> providing the comma-separated handles for users which are allowed 
     *                     to access security groups functionalities.
     */
    public void setAllowedUsers(String allowedUsers) {
        this.allowedUsers = new HashSet<String>();
        if (allowedUsers != null) {
            String[] users = allowedUsers.split(",");
            for (String handle : users) {
                if (handle.trim().length() > 0) {
                    this.allowedUsers.add(handle.trim());
                }
            }
        }
    }

    /**
     * <p>Gets the access to user services.</p>
     *
     * @return a <code>UserService</code> providing the access to user services.
     */
    public UserService getUserService() {
        return this.userService;
    }

    /**
     * <p>Sets the access to user services.</p>
     *
     * @param userService a <code>UserService</code> providing the access to user services.
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
