/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.topcoder.direct.services.view.util.DirectUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>An interceptor for requests to access pages only TC staff can access.
 * <p/>
 * Verifies that current user has TC staff role.
 * If not then redirects user to <code>Permission Denied</code> page.</p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TC Cockpit Platform Specialist Utilization Report and Graph)
 */
public class TCStaffAccessInterceptor implements Interceptor {

    /**
     * <p>Constructs new <code>InternalStatsAccessInterceptor</code> instance. This implementation does nothing.</p>
     */
    public TCStaffAccessInterceptor() {
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
        HttpServletRequest request = DirectUtils.getServletRequest();
        boolean granted = DirectUtils.isTcStaff(DirectUtils.getTCSubjectFromSession());
        if (!granted) {
            request.setAttribute("errorPageMessage", "Sorry, only TopCoder Staff can access this page.");
            return "permissionDenied";
        } else {
            return actionInvocation.invoke();
        }
    }
}
