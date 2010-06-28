/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.TopCoderDirectAction;
import com.topcoder.direct.services.view.form.ContestIdForm;
import com.topcoder.direct.services.view.util.AuthorizationProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;

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
 * @author isv
 * @version 1.1
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
        long currentUserId = DirectUtils.getTCSubjectFromSession().getUserId();
        boolean granted;
        if (contestIdParam != null) {
            long contestId = Long.parseLong(contestIdParam);
            granted = AuthorizationProvider.isUserGrantedAccessToContest(currentUserId, contestId);
        } else {
            long contestId = Long.parseLong(request.getParameter("projectId"));
            granted = AuthorizationProvider.isUserGrantedAccessToSoftwareContest(currentUserId, contestId);
        }
        if (!granted) {
            return "permissionDenied";
        } else {
            return actionInvocation.invoke();
        }
    }
}
