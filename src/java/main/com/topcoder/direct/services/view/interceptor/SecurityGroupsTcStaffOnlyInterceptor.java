/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import com.topcoder.service.user.UserServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>An interceptor for requests to security groups management functionalities. Verifies that current user is in list
 * of users who are allowed to manage security groups. If not then redirects user to <code>Permission Denied</code> 
 * page.</p>
 * 
 * @author notpad
 * @version 1.0 (BUGR-8047 - TopCoder Security Groups - TC-staff-only access)
 */
public class SecurityGroupsTcStaffOnlyInterceptor implements Interceptor {

    /**
     * <p>A <code><String></code> providing the list of handles for users which are allowed to access security groups
     * functionalities.</p>
     */
    private String allowedUsers;

    /**
     * Represents only TC Staff can access group pages when allowedUsers is empty.
     */
    private String tcStaffOnly;
    
    /**
     * <p>Constructs new <code>SecurityGroupsAccessInterceptor</code> instance. This implementation does nothing.</p>
     */
    public SecurityGroupsTcStaffOnlyInterceptor() {
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
        boolean granted = checkPermission();
        if (!granted) {
            HttpServletRequest request = DirectUtils.getServletRequest();
            request.setAttribute("errorPageMessage", "Sorry, only TC staff has permission to access this page.");
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
    public boolean checkPermission() throws UserServiceException {
        // this interceptor should take effect only when the allowed user set is empty and tcStaffOnly flag is true
        if ((this.allowedUsers == null || this.allowedUsers.trim().length() == 0) 
            && this.getTcStaffOnly().equalsIgnoreCase("true")) {
        	TCSubject currentUser = DirectStrutsActionsHelper.getTCSubjectFromSession();
        	if (currentUser == null ||  !DirectUtils.isTcStaff(currentUser)) {
        		return false;
        	} 
        }
        return true;
    }

    /**
     * <p>Sets the list of handles for users which are allowed to access security groups functionalities.</p>
     *
     * @param allowedUsers a <code>Set<String></code> providing the comma-separated handles for users which are allowed 
     *                     to access security groups functionalities.
     */
    public void setAllowedUsers(String allowedUsers) {
        this.allowedUsers = allowedUsers;
    }
    
    /**
     * <p>Gets if only TC Staff can access group pages when allowedUsers is empty.</p>
     *
     * @return if only TC Staff can access group pages when allowedUsers is empty.
     */
    public String getTcStaffOnly() {
    	return this.tcStaffOnly;
    }
    
    /**
     * <p>Sets if only TC Staff can access group pages when allowedUsers is empty.</p>
     *
     * @param tcStaffOnly if only TC Staff can access group pages when allowedUsers is empty
     */
    public void setTcStaffOnly(String tcStaffOnly) {
        this.tcStaffOnly = tcStaffOnly;
    }
}
