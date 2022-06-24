/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.form.ContestIdForm;
import com.topcoder.direct.services.view.util.AuthorizationProvider;
import com.topcoder.direct.services.view.util.DirectUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>An interceptor for requests to secured area. Verifies that current user is granted a permission to access the
 * requested contest. If not then redirects user to <code>Permission Denied</code> page.</p>
 *
 * <p>This interceptor must be mapped to the only actions which implement {@link FormAction} interface and have their
 * form data of {@link ContestIdForm} type.</p>
 *
 * <p>Version 1.1 (Direct Registrants List assembly) change notes:
 *   <ul>
 *     <li>Updated {@link #intercept(ActionInvocation)} method to be independent of the type of the action.</li>
 *   </ul>
 * </p>
 *
 * <p>
 * Version 1.2 (TC Cockpit Permission and Report Update One) change log:
 * <ol>
 *   <li>Updated {@link #intercept(ActionInvocation)} method to use a method
 *   <code>AuthorizationProvider.isUserGrantedAccessToContest</code> to check the user's permission. It was
 *   using two different methods based on the contest type (software contest or studio contest).</li>
 *   <li>Updated {@link #intercept(ActionInvocation)} to set the error page message when user has no permission.</li>
 * </ol>
 * </p>
 * 
 * @author isv, TCSASSEMBER
 * @version 1.2
 */
public class ContestAccessInterceptor implements Interceptor {

    /**
     * <p>Constructs new <code>ContestAccessInterceptor</code> instance. This implementation does nothing.</p>
     */
    public ContestAccessInterceptor() {
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
        String contestIdParam = request.getParameter("contestId");
        long contestId;
        if (contestIdParam != null) {
            contestId = Long.parseLong(contestIdParam);
        } else {
            contestId = Long.parseLong(request.getParameter("projectId"));
        }
        boolean granted = AuthorizationProvider.isUserGrantedAccessToContest(DirectUtils.getTCSubjectFromSession(), contestId);
        if (!granted) {
            request.setAttribute("errorPageMessage", "Sorry, you don't have permission to access this contest.");
            return "permissionDenied";
        } else {
            return actionInvocation.invoke();
        }
    }
}
